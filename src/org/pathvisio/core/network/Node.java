// PathVisio,
// a tool for data visualization and analysis using Biological Pathways
// Copyright 2006-2015 BiGCaT Bioinformatics
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.pathvisio.core.network;

import org.bridgedb.Xref;
import org.jetbrains.annotations.Nullable;
import org.pathvisio.core.model.Pathway;
import org.pathvisio.core.model.PathwayElement;

import java.util.HashSet;

/**
 * Node, the elemental structure used as source/target in an Edge object
 * , can be of type data/ reaction node
 * Data nodes are uniquely identified by their Xref
 * Reaction nodes are uniquely identified by their reaction id
 * @author saurabh
 */
public class Node {
    Xref xrefID;
    String name, label;
    HashSet<PathwayElement> dataNodes;
    Edge edge;

    /***
     * Create a node using a Pathway Element
     * preferably a DATANODE
     * @param pathwayElement
     */
    Node(PathwayElement pathwayElement){
        dataNodes = new HashSet<>();
        dataNodes.add(pathwayElement);
        xrefID = pathwayElement.getXref();
        label = pathwayElement.getTextLabel();
        name = pathwayElement.getMapInfoName();
    }

    /**
     * Create a node, reaction node
     * using an Edge
     * @param edge
     */
    Node(Edge edge){
        this.edge = edge;
    }

    /**
     * add a Pathway Element to the data node
     * if they share the same Xref
     * @param pathwayElement
     */
    public void add(PathwayElement pathwayElement){
        if(!isReactionNode())
        dataNodes.add(pathwayElement);
    }

    /**
     * Check if reaction node or not
     * @return true if reaction node
     */
    public boolean isReactionNode(){
        return edge!=null;
    }

    /**
     * @return set of all PathwayElements in the data node
     */
    @Nullable
    public HashSet<PathwayElement> getDataNodes() {
        return dataNodes;
    }


    /**
     * @return Xref of the data node
     */
    @Nullable
    public Xref getId() {
        return xrefID;
    }

    /**
     * get label of the node
     * text label if data node
     * @return
     */
    public String getLabel() {
        if(isReactionNode()) return edge.getReactionID();
        return label;
    }
}
