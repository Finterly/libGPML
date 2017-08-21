package org.pathvisio.core.network;

import org.bridgedb.Xref;
import org.jetbrains.annotations.Nullable;
import org.pathvisio.core.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * PathwayAsNetwork, re-imagine the pathway as a network structure
 * with data nodes (DATANODE) and reaction nodes (Edge)
 * connected through Edge objects
 */

public class PathwayAsNetwork {

    private final Pathway pathway;
    private HashMap<Xref,Node> dataNodes = new HashMap<>();
    private HashMap<Edge,Node> reactionNodes = new HashMap<>();
    private HashSet<Edge> edges = new HashSet<>();
    private HashMap<PathwayElement, Edge> lineEdges = new HashMap<>();
    private ArrayList<PathwayElement> lines = new ArrayList<>();
    private HashMap<Node, HashSet<Edge>> nodeEdges = new HashMap<>();

    /**
     * Constructor for @PathwayAsNetwork class
     * Uses the pathway to a create a graph like interpretation of the pathway
     * @param pathway
     */
    public PathwayAsNetwork(Pathway pathway){
        this.pathway = pathway;
        assert (pathway!=null);
        for(PathwayElement pathwayElement : pathway.getDataObjects()){
            switch (pathwayElement.getObjectType()){
                case DATANODE:
                    addDataNode(pathwayElement);
                    break;
                case LINE:
                    lines.add(pathwayElement);
            }
        }

        lines.sort(this::compareLines);

        for (PathwayElement line:
                lines) {
            mapEdges(pathway,line);
        }
    }

    /**
     * A comparison function for comparing two lines,
     * used to sort to create a priority for mapping lines
     * double connected > singly connected > not connected
     * @param t1
     * @param t2
     * @return
     */
    private int compareLines(PathwayElement t1, PathwayElement t2){
        GraphLink.GraphIdContainer
                   source1 = pathway.getGraphIdContainer(t1.getMStart().getGraphRef()),
                   target1 = pathway.getGraphIdContainer(t1.getMEnd().getGraphRef()),
                   source2 = pathway.getGraphIdContainer(t2.getMStart().getGraphRef()),
                   target2 = pathway.getGraphIdContainer(t2.getMEnd().getGraphRef());
        return (isDataNode(source2)?1:0) +
                   (isDataNode(target2)?1:0) -
                   (isDataNode(source1)?1:0) -
                   (isDataNode(target1)?1:0);
    }

    private int reactions=0;

    /**
     * create reaction IDs for new reactions
     * @return
     */
    private String getReactionID(){
        return "R"+(++reactions);
    }

    /**
     * Create a datanode of type Node using a pathwayElement of type DATANODE
     * @param pathwayElement
     */
    private void addDataNode(PathwayElement pathwayElement){
        Xref xref = pathwayElement.getXref();
        if(xref==null||xref.getId().equals("")||xref.getDataSource()==null) return;
        if(!dataNodes.containsKey(xref))
            dataNodes.put(xref,new Node(pathwayElement));
        else
            dataNodes.get(xref).add(pathwayElement);
    }

    /**
     * Create a reaction node of type Node using an Edge
     * @param edge
     */
    private void addReactionNode(Edge edge){
        String reactionID = getReactionID();
        if(!reactionNodes.containsKey(edge))
            reactionNodes.put(edge,new Node(edge));
    }

    /**
     * create an entry for a node connected with an edge
     * @param node
     * @param edge
     */
    private void addNodeEdge(Node node, Edge edge){
        if(nodeEdges.getOrDefault(node,null)==null)
            nodeEdges.put(node,new HashSet<>());
        nodeEdges.get(node).add(edge);
    }

    /**
     * get the node object of a DATANODE pathway element
     * returns null if not present in the map
     * @param pathwayElement
     * @return
     */
    private Node getDataNode(PathwayElement pathwayElement){
        return dataNodes.getOrDefault(pathwayElement.getXref(), null);
    }

    /**
     * return the node object of an Edge
     * returns null if not present in the map
     * @param edge
     * @return
     */
    private Node getReactionNode(Edge edge){
        return reactionNodes.getOrDefault(edge, null);
    }

    /**
     * get any other line connected to the current anchor's line through another anchor
     * @param anchorT
     * @return
     */
    private PathwayElement getConnectedLine(MAnchor anchorT){
        MAnchor otherAnchor = null;
        PathwayElement otherLine = null;
        for(MAnchor anchor:anchorT.getParent().getMAnchors())
            if(anchor!=anchorT){
                otherAnchor=anchor;
                break;
            }
        if(otherAnchor!=null){
            for(PathwayElement pline:lines){
                if(otherAnchor.getGraphId().equals(pline.getMEnd().getGraphRef())
                        ||otherAnchor.getGraphId().equals(pline.getMStart().getGraphRef())) {
                    otherLine = pline;
                    break;
                }
            }
        }
        return otherLine;
    }

    /**
     * to check if a GraphIdContainer Object is a DATANODE/COMPLEX pathway
     * @param container
     * @return
     */
    private boolean isDataNode(GraphLink.GraphIdContainer container){
        if(container==null || !(container instanceof PathwayElement))
            return false;

        PathwayElement pathwayElement = (PathwayElement) container;
        boolean result = pathwayElement.getObjectType()==ObjectType.DATANODE;

        if(pathwayElement.getObjectType()==ObjectType.GROUP){
            for(PathwayElement pe:pathway.getGroupElements(pathwayElement.getGraphId()))
                if(pe.getObjectType()==ObjectType.DATANODE)
                    result=true;
        }
        return result;
    }

    /**
     * checks both ends of the line
     * adds/updates an Edge if both ends connected to some datanode directly
     * or through an anchor
     * @param p
     * @param line
     */
    private void mapEdges(Pathway p, PathwayElement line){
        GraphLink.GraphIdContainer source = p.getGraphIdContainer(line.getMStart().getGraphRef());
        GraphLink.GraphIdContainer target = p.getGraphIdContainer(line.getMEnd().getGraphRef());
        MAnchor anchorT=null;
        HashSet<Node> sourceNodes, targetNodes;
        Node reactionNode = null;
        boolean isRegulator = false;
        PathwayElement sourceElement = null, targetElement = null, temp, pline = null;
        // If the line has no valid references to source and target containers,
        // It is not connected and could not be a valid Interaction
        if(source==null||target==null) return;

        // Check if source or target are PathwayELement DataNode or MAnchors
        if(source instanceof PathwayElement)
            sourceElement = (PathwayElement)source;

        if(target instanceof PathwayElement)
            targetElement = (PathwayElement)target;

        if(source instanceof MAnchor) anchorT = (MAnchor) source;
        if(target instanceof MAnchor) anchorT = (MAnchor) target;

        Edge edge = lineEdges.get(line);

        // If no arrowhead and line end has a dataNode then dataNode should be a source
        // so they need to be swapped otherwise
        if(line.getEndLineType()==LineType.LINE
                && isDataNode(targetElement)){
            temp = sourceElement;
            sourceElement = targetElement;
            targetElement = temp;
        }
        // True if the line has a direct connection to both source and target
        if (isDataNode(sourceElement) &&
                isDataNode(targetElement)) {
            // Create Interaction object if not already
            if(edge==null) {
                edge = new Edge(line.getEndLineType(), getReactionID());
            }
        }
        else if(isDataNode(sourceElement)
                 && anchorT!=null){
            // check for no arrowhead, if arrowhead then it must be a regulator
            isRegulator = line.getEndLineType()!=LineType.LINE;
            // get the GraphIdContainer at the end of the current line
            target = p.getGraphIdContainer(anchorT.getParent().getMEnd().getGraphRef());
            pline = anchorT.getParent();
            // if the other end of the line was not connected to a dataNode
            if(target==null){
            pline = getConnectedLine(anchorT);
            if(pline!=null)
                target = p.getGraphIdContainer(getConnectedLine(anchorT).getMEnd().getGraphRef());
            }
            // target is any target we found, temp is the last anchor the target was connected to
            if(target instanceof PathwayElement){
                targetElement = (PathwayElement) target;
                edge = lineEdges.get(pline);
                if(edge==null)
                    edge = new Edge(pline.getEndLineType(),getReactionID());
            }
            if(isRegulator) {
                reactionNode = getReactionNode(lineEdges.get(pline));
                if(pline!=null)
                edge = new Edge(pline.getEndLineType(),getReactionID());
            }
        }
        // Same as for source
        else if(isDataNode(targetElement)
                && anchorT!=null){
            source = p.getGraphIdContainer(anchorT.getParent().getMStart().getGraphRef());
            pline = anchorT.getParent();
            if(source==null){
                pline = getConnectedLine(anchorT);
                if(pline!=null)
                    source = p.getGraphIdContainer(getConnectedLine(anchorT).getMStart().getGraphRef());
            }
            if(source instanceof PathwayElement){
                sourceElement = (PathwayElement) source;
                edge = lineEdges.get(pline);
                if(edge==null)
                    edge = new Edge(pline.getEndLineType(),getReactionID());
            }
        }
        // Update all structures
        // Also note since they are HashSets, duplicates are implicitly checked
        // If failed to create an or use an already instantiated interaction object
        // it should be an invalid Interaction
        if(edge==null)
            return;
        sourceNodes = getDataNodes(sourceElement);
        targetNodes = getDataNodes(targetElement);

        // Nodes could point to null pointer in case the Xref was not complete
        if(sourceNodes==null||
                (isRegulator&&reactionNode==null)||
                (!isRegulator&&targetNodes==null))
            return;
        for(Node sourceNode:sourceNodes){
        edge.addSource(sourceNode);
        addNodeEdge(sourceNode,edge);
        }
        if(isRegulator) {
            edge.addTarget(reactionNode);
            addNodeEdge(reactionNode, edge);
        } else {
            for(Node targetNode:targetNodes) {
                edge.addTarget(targetNode);
                addNodeEdge(targetNode,edge);
            }
        }
        lineEdges.put(line,edge);
        addReactionNode(edge);
        edges.add(edge);
    }

    /**
     * Get the root Pathway used to instantiate
     * this PathwayAsNetwork object
     * @return Pathway
     */
    public Pathway getPathway() {
        return pathway;
    }

    /**
     * Get a set containing all edges found in the pathway
     * @return HashSet<Edge>
     */
    public HashSet<Edge> getEdges() {
        return edges;
    }

    /**
     * get a data node by its Xref value
     * @param xref
     * @return
     */
    @Nullable
    public Node getDataNodeByXref(Xref xref) {
        return dataNodes.getOrDefault(xref, null);
    }


    /**
     * return a set of data nodes in a PathwayElement in case it is a complex
     * @param pathwayElement
     * @return
     */
    @Nullable
    private HashSet<Node> getDataNodes(PathwayElement pathwayElement) {
        if(pathwayElement==null)
            return null;
        HashSet<Node> nodes = new HashSet<>();
        if(pathwayElement.getObjectType()==ObjectType.DATANODE) {
            if (getDataNode(pathwayElement) != null)
                nodes.add(getDataNode(pathwayElement));
        }
        else if(pathwayElement.getObjectType()==ObjectType.GROUP)
            for(PathwayElement pe:pathway.getGroupElements(pathwayElement.getGraphId()))
                if(getDataNode(pe)!=null)
                    nodes.add(getDataNode(pe));
        if(nodes.size()==0)
            return null;
        return nodes;
    }

    /**
     * get a set of edges connected with a node
     * @param node
     * @return
     */
    @Nullable
    public HashSet<Edge> getEdgesFromNode(Node node) {
        return nodeEdges.getOrDefault(node,null);
    }

    /**
     * get all edges in a tab separated string
     * can be imported in Cytoscape (SIF format)
     * @return
     */
    public String toTSV(){
        StringBuilder res = new StringBuilder();
        for(Edge edge:edges){
            for(Node source:edge.getSources())
                res.append(source.getLabel())
                    .append('\t')
                    .append(LineType.LINE.getName())
                    .append('\t')
                    .append(edge.getReactionID())
                    .append('\n');
            for(Node target:edge.getTargets())
                res.append(edge.getReactionID())
                    .append('\t')
                    .append(edge.edgeType.getName())
                    .append('\t')
                    .append(target.getLabel())
                    .append('\n');
        }
        return res.toString();
    }
}


