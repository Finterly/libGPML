package org.pathvisio.core.network;

import org.pathvisio.core.model.LineType;

import java.util.HashSet;

/**
 * Edge, class to store an interaction between nodes
 * with source nodes, target nodes and arrowhead as edge type
 * uniquely identified with reactionID
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
