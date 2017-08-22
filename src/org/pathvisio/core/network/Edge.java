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

import org.pathvisio.core.model.LineType;

import java.util.HashSet;

/**
 * Edge, class to store an interaction between nodes
 * with source nodes, target nodes and arrowhead as edge type
 * uniquely identified with reactionID
 * @author saurabh
 */
public class Edge {
    HashSet<Node> sources, targets;
    LineType edgeType;
    String reactionID;
    Edge(LineType edgeType, String reactionID){
        sources = new HashSet<>();
        targets = new HashSet<>();
        this.edgeType = edgeType;
        this.reactionID = reactionID;
    }

    /**
     * Add a source node to the edge
     * @param node
     */
    public void addSource(Node node){
        sources.add(node);
    }

    /**
     * Add a target node to the edge
     * @param node
     */
    public void addTarget(Node node){
        targets.add(node);
    }

    /**
     * get method for edge's reaction ID
     * @return
     */
    public String getReactionID() {
        return reactionID;
    }

    /**
     * get a set of all sources in the edge
     * @return
     */
    public HashSet<Node> getSources() {
        return sources;
    }

    /**
     * get a set of all targets in the edge
     * @return
     */
    public HashSet<Node> getTargets() {
        return targets;
    }

    /**
     * returns a union of all sources and targets
     * should be used when EdgeType is LineType.LINE
     * @return
     */
    public HashSet<Node> getParticipants() {
        HashSet<Node> participants = new HashSet<>();
        participants.addAll(sources);
        participants.addAll(targets);
        return participants;
    }

    /**
     * Return the primary arrowhead type used in the edge
     * @return
     */
    public LineType getEdgeType() {
        return edgeType;
    }

}
