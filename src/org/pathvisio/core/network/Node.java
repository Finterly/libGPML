package org.pathvisio.core.network;

import org.bridgedb.Xref;
import org.pathvisio.core.model.Pathway;
import org.pathvisio.core.model.PathwayElement;

import java.util.HashSet;

/**
 * Created by saurabhk351 on 09/07/2017.
 */
public class Node {
    Xref id;
    String name, label;
    HashSet<PathwayElement> dataNodes;

    Node(PathwayElement pathwayElement){
        dataNodes = new HashSet<>();
        dataNodes.add(pathwayElement);
        id = pathwayElement.getXref();
        label = pathwayElement.getTextLabel();
        name = pathwayElement.getMapInfoName();
    }

    public void add(PathwayElement pathwayElement){
        dataNodes.add(pathwayElement);
    }

    public HashSet<PathwayElement> getDataNodes() {
        return dataNodes;
    }
}
