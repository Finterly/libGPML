package org.pathvisio.core.network;

import org.bridgedb.Xref;
import org.omg.CORBA.DATA_CONVERSION;
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
    private HashMap<Xref,Node> dataNodes = new HashMap<>();
    private HashMap<PathwayElement,Node> reactionNodes = new HashMap<>();
    private HashSet<Edge> edges = new HashSet<>();
    private HashMap<MLine,String> edgesEnum = new HashMap<>();
    private ArrayList<PathwayElement> lines = new ArrayList<>();

    public PathwayAsNetwork(Pathway pathway){
        this.pathway = pathway;
        assert (pathway!=null);
        for(PathwayElement pathwayElement : pathway.getDataObjects()){
            switch (pathwayElement.getObjectType()){
                case DATANODE:
                    addDataNode(pathwayElement);
                    break;
                case LINE:
                    addReactionNode(pathwayElement);
                    lines.add(pathwayElement);
            }
        }
        for (PathwayElement line:
                lines) {
            updateEdges(pathway,line);
        }
    }

    private String getReactionID(PathwayElement pathwayElement){
        int size = reactionNodes.size()+1;
        return "R"+size;
    }

    private void addDataNode(PathwayElement pathwayElement){
        Xref xref = pathwayElement.getXref();
        if(!dataNodes.containsKey(xref))
            dataNodes.put(xref,new Node(pathwayElement));
        else
            dataNodes.get(xref).add(pathwayElement);
    }


    private void addReactionNode(PathwayElement pathwayElement){
        String reactionID = getReactionID(pathwayElement);
        if(!reactionNodes.containsKey(pathwayElement))
            reactionNodes.put(pathwayElement,new Node(pathwayElement, reactionID));
        else
            reactionNodes.get(pathwayElement).add(pathwayElement, reactionID);
    }

    private Node getNode(PathwayElement pathwayElement){
        if(pathwayElement instanceof MLine && reactionNodes.containsKey(pathwayElement))
            return reactionNodes.get(pathwayElement);
        else
            return dataNodes.getOrDefault(pathwayElement.getXref(), null);
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

        // If no arrowhead and line end has a dataNode then dataNode should be a source
        // so they need to be swapped otherwise
        if(line.getEndLineType()==LineType.LINE && targetElement!=null
                && targetElement.getObjectType()==ObjectType.DATANODE){
            temp = sourceElement;
            sourceElement = targetElement;
            targetElement = temp;
        }
        if(sourceElement!=null && sourceElement.getObjectType()==ObjectType.DATANODE
                    && line.getEndLineType()==LineType.LINE && anchorT!=null){
            // get the GraphIdContainer at the end of the current line
            targetElement = anchorT.getParent();
        }
        // Same as for source
        else if(targetElement!=null && targetElement.getObjectType()==ObjectType.DATANODE
                && anchorT!=null){
            sourceElement = anchorT.getParent();
        }

        if(sourceElement==null||targetElement==null){
            // could not find connections on both ends
            // not a valid interaction
            return;
        }
        // Update all structures
        Node sourceNode, targetNode, lineNode;
        lineNode = getNode(line);
        sourceNode = getNode(sourceElement);
        targetNode = getNode(targetElement);
        Edge edge1 = new Edge(line.getStartLineType()), edge2 = new Edge(line.getEndLineType());
        if(sourceNode!=null){
            edge1.setSource(sourceNode);
            edge1.setTarget(lineNode);
            edges.add(edge1);
        }
        if(targetNode!=null){
            edge2.setSource(lineNode);
            edge2.setTarget(targetNode);
            edges.add(edge2);
        }
    }

    public Pathway getPathway() {
        return pathway;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    public HashMap<Xref, Node> getDataNodes() {
        return dataNodes;
    }

    public String toTSV(){
        StringBuilder res = new StringBuilder();
        for(Edge edge:edges){
            res.append(edge.getSource().getLabel())
                .append('\t')
                .append(edge.edgeType.getName())
                .append('\t')
                .append(edge.getTarget().getLabel())
                .append('\n');
        }
        return res.toString();
    }
}


