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
    String name, label, id=null;
    HashSet<PathwayElement> dataNodes;

    Node(PathwayElement pathwayElement){
        dataNodes = new HashSet<>();
        dataNodes.add(pathwayElement);
        xrefID = pathwayElement.getXref();
        label = pathwayElement.getTextLabel();
        name = pathwayElement.getMapInfoName();
    }

    Node(PathwayElement pathwayElement, String id){
        dataNodes = new HashSet<>();
        dataNodes.add(pathwayElement);
        xrefID = pathwayElement.getXref();
        label = pathwayElement.getTextLabel();
        name = pathwayElement.getMapInfoName();
        this.id = id;
    }

    public void add(PathwayElement pathwayElement){
        dataNodes.add(pathwayElement);
    }

    public void add(PathwayElement pathwayElement, String id){
        dataNodes.add(pathwayElement);
        this.id = id;
    }

    public boolean isReactionNode(){
        return id!=null;
    }

    public HashSet<PathwayElement> getDataNodes() {
        return dataNodes;
    }

    public Xref getId() {
        return xrefID;
    }

    public String getLabel() {
        if(isReactionNode()) return id;
        return label;
    }
}
