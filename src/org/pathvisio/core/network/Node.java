package org.pathvisio.core.network;

import org.bridgedb.Xref;
import org.pathvisio.core.model.Pathway;
import org.pathvisio.core.model.PathwayElement;

import java.util.HashSet;

/**
 * Created by saurabhk351 on 09/07/2017.
 */
public class Node {
    Xref xrefID;
    String name, label;
    HashSet<PathwayElement> dataNodes;
    Edge edge;

    Node(PathwayElement pathwayElement){
        dataNodes = new HashSet<>();
        dataNodes.add(pathwayElement);
        xrefID = pathwayElement.getXref();
        label = pathwayElement.getTextLabel();
        name = pathwayElement.getMapInfoName();
    }

    Node(Edge edge){
        this.edge = edge;
    }

    public void add(PathwayElement pathwayElement){
        dataNodes.add(pathwayElement);
    }

    public boolean isReactionNode(){
        return edge!=null;
    }

    public HashSet<PathwayElement> getDataNodes() {
        return dataNodes;
    }

    public Xref getId() {
        return xrefID;
    }

    public String getLabel() {
        if(isReactionNode()) return edge.getReactionID();
        return label;
    }
}
