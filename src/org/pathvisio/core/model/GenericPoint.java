package org.pathvisio.core.model;

import java.util.Set;

/**
 * Represents a generic point in an coordinates.length dimensional space.
 * The point is automatically a {@link GraphLink.GraphIdContainer} and therefore lines
 * can link to the point.
 * @see MPoint
 * @see PathwayElement.MAnchor
 * @author thomas
 *
 */
abstract class GenericPoint implements Cloneable, GraphLink.GraphIdContainer
{
    private double[] coordinates;

    private String graphId;
    private PathwayElement pathwayElementParent;

    GenericPoint(double[] coordinates, PathwayElement pathwayElement)
    {
        this.coordinates = coordinates;
        pathwayElementParent = pathwayElement;
    }

    GenericPoint(GenericPoint p)
    {
        coordinates = new double[p.coordinates.length];
        System.arraycopy(p.coordinates, 0, coordinates, 0, coordinates.length);
        if (p.graphId != null)
            graphId = p.graphId;
        pathwayElementParent = p.pathwayElementParent;
    }

    protected void moveBy(double[] delta)
    {
        for(int i = 0; i < coordinates.length; i++) {
            coordinates[i] += delta[i];
        }
        pathwayElementParent.fireObjectModifiedEvent(PathwayElementEvent.createCoordinatePropertyEvent(pathwayElementParent));
    }

    protected void moveTo(double[] coordinates)
    {
        this.coordinates = coordinates;
        pathwayElementParent.fireObjectModifiedEvent(PathwayElementEvent.createCoordinatePropertyEvent(pathwayElementParent));
    }

    protected void moveTo(GenericPoint p)
    {
        coordinates = p.coordinates;
        pathwayElementParent.fireObjectModifiedEvent(PathwayElementEvent.createCoordinatePropertyEvent(pathwayElementParent));;
    }

    protected double getCoordinate(int i) {
        return coordinates[i];
    }

    public String getGraphId()
    {
        return graphId;
    }

    public String setGeneratedGraphId()
    {
        setGraphId(pathwayElementParent.parent.getUniqueGraphId());
        return graphId;
    }

    public void setGraphId(String v)
    {
        GraphLink.setGraphId(v, this, pathwayElementParent.parent);
        graphId = v;
        pathwayElementParent.fireObjectModifiedEvent(PathwayElementEvent.createSinglePropertyEvent(pathwayElementParent, StaticProperty.GRAPHID));
    }

    public Object clone() throws CloneNotSupportedException
    {
        GenericPoint p = (GenericPoint) super.clone();
        if (graphId != null)
            p.graphId = graphId;
        return p;
    }

    public Set<GraphLink.GraphRefContainer> getReferences()
    {
        return GraphLink.getReferences(this, pathwayElementParent.parent);
    }

    public Pathway getPathway() {
        return pathwayElementParent.parent;
    }

    public PathwayElement getParent()
    {
        return pathwayElementParent;
    }
}
