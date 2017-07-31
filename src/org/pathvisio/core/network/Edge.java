package org.pathvisio.core.network;

import org.pathvisio.core.model.LineType;

import java.util.HashSet;

/**
 * Created by saurabhk351 on 09/07/2017.
 */
public class Edge {
    Node source,target;
    LineType edgeType;
    Edge(LineType edgeType){
        this.edgeType = edgeType;
    }

    public void setSource(Node node){
        source = node;
    }

    public void setTarget(Node node){
        target = node;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public LineType getEdgeType() {
        return edgeType;
    }

}
