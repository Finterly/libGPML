package org.pathvisio.core.model;

import java.awt.*;
import java.util.HashSet;

/**
 * Created by saurabhk351 on 03/07/2017.
 */
public class Interaction {
    private HashSet<PathwayElement> source = new HashSet<>();
    private HashSet<PathwayElement> target = new HashSet<>();
    public enum InteractionType{
        sometype,
        anothertype
    };

    public void addSource(PathwayElement pathwayElement){
        source.add(pathwayElement);
    }

    public void addTarget(PathwayElement pathwayElement){
        target.add(pathwayElement);
    }

    public HashSet<PathwayElement> getSource(){
        return source;
    }

    public HashSet<PathwayElement> getTarget(){
        return target;
    }

}
