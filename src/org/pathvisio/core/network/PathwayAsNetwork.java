package org.pathvisio.core.network;

import org.bridgedb.Xref;
import org.pathvisio.core.model.*;
import sun.plugin.javascript.navig.Anchor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by saurabhk351 on 09/07/2017.
 */

public class PathwayAsNetwork {

    private final Pathway pathway;
    private HashMap<Xref,Node> nodes = new HashMap<>();
    private HashMap<PathwayElement, Edge> lineEdges = new HashMap<>();
    private HashSet<Edge> edges = new HashSet<>();
    private ArrayList<PathwayElement> lines = new ArrayList<>();

    private int compareLines(PathwayElement t1, PathwayElement t2){
        GraphLink.GraphIdContainer
                source1 = pathway.getGraphIdContainer(t1.getMStart().getGraphRef()),
                target1 = pathway.getGraphIdContainer(t1.getMEnd().getGraphRef()),
                source2 = pathway.getGraphIdContainer(t2.getMStart().getGraphRef()),
                target2 = pathway.getGraphIdContainer(t2.getMEnd().getGraphRef());
        return ((source2 instanceof PathwayElement)?1:0) +
                ((target2 instanceof PathwayElement)?1:0) -
                ((source1 instanceof PathwayElement)?1:0) -
                ((target1 instanceof PathwayElement)?1:0);
    }
    public PathwayAsNetwork(Pathway pathway){
        this.pathway = pathway;
        assert (pathway!=null);
        for(PathwayElement pathwayElement : pathway.getDataObjects()){
            switch (pathwayElement.getObjectType()){
                case DATANODE:
                    addNode(pathwayElement);
                    break;
                case LINE:
                    lines.add(pathwayElement);
            }
        }
        lines.sort(this::compareLines);
        for (PathwayElement line:
                lines) {
            updateEdges(pathway,line);
        }
    }

    private Node addNode(PathwayElement pathwayElement){
        Xref xref = pathwayElement.getXref();
        if(!nodes.containsKey(xref))
            nodes.put(xref,new Node(pathwayElement));
        else
            nodes.get(xref).add(pathwayElement);
        return nodes.get(xref);
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
                if(pline.getMEnd().getGraphRef().equals(otherAnchor.getGraphId())
                        ||pline.getMStart().getGraphRef().equals(otherAnchor.getGraphId())) {
                    otherLine = pline;
                    break;
                }
            }
        }
        return otherLine;
    }


    private void updateEdges(Pathway p,PathwayElement line){
        GraphLink.GraphIdContainer source = p.getGraphIdContainer(line.getMStart().getGraphRef());
        GraphLink.GraphIdContainer target = p.getGraphIdContainer(line.getMEnd().getGraphRef());
        MAnchor anchorT=null;
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
        if(line.getEndLineType()==LineType.LINE && targetElement!=null
                && targetElement.getObjectType()==ObjectType.DATANODE){
            temp = sourceElement;
            sourceElement = targetElement;
            targetElement = temp;
        }
        // True if the line has a direct connection to both source and target
        if (sourceElement!=null && sourceElement.getObjectType()==ObjectType.DATANODE &&
                targetElement!=null &&targetElement.getObjectType()==ObjectType.DATANODE) {
            // Create Interaction object if not already
            if(edge==null) {
                edge = new Edge(line.getEndLineType());
            }
        }
        else if(sourceElement!=null && sourceElement.getObjectType()==ObjectType.DATANODE
                && anchorT!=null){
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
                    edge = new Edge(pline.getEndLineType());
            }
        }
        // Same as for source
        else if(targetElement!=null && targetElement.getObjectType()==ObjectType.DATANODE
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
                    edge = new Edge(pline.getEndLineType());
            }
        }
        // Update all structures
        // Also note since they are HashSets, duplicates are implicitly checked
        // If failed to create an or use an already instantiated interaction object
        // it should be an invalid Interaction
        if(edge==null)
            return;
        Node sourceNode, targetNode;
        sourceNode = addNode(sourceElement);
        targetNode = addNode(targetElement);
        edge.addSource(sourceNode);
        edge.addTarget(targetNode);
        lineEdges.put(line,edge);
        edges.add(edge);
        if(pline!=null)
            lineEdges.put(pline,edge);
    }

    public Pathway getPathway() {
        return pathway;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    public HashMap<PathwayElement, Edge> getLineEdges() {
        return lineEdges;
    }

    public HashMap<Xref, Node> getNodes() {
        return nodes;
    }
}
