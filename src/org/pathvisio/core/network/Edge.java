package org.pathvisio.core.network;

import org.pathvisio.core.model.LineType;

import java.util.HashSet;

/**
 * Created by saurabhk351 on 09/07/2017.
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

    public void addSource(Node node){
        sources.add(node);
    }

    public void addTarget(Node node){
        targets.add(node);
    }

    public String getReactionID() {
        return reactionID;
    }

    public HashSet<Node> getSources() {
        return sources;
    }

    public HashSet<Node> getTargets() {
        return targets;
    }

    public HashSet<Node> getParticipants() {
        HashSet<Node> participants = new HashSet<>();
        participants.addAll(sources);
        participants.addAll(targets);
        return participants;
    }

    public LineType getEdgeType() {
        return edgeType;
    }

}
