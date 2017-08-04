package org.pathvisio.core.model;


import org.pathvisio.core.util.Utils;

import java.awt.geom.Point2D;

/**
 * This class represents the Line.Graphics.Point element in GPML.
 * @author thomas
 *
 */
public class MPoint extends GenericPoint implements GraphLink.GraphRefContainer
{
    private String graphRef;
    private boolean relativeSet;


    public MPoint(double x, double y, PathwayElement parent)
    {
        super(new double[] { x, y, 0, 0 }, parent);
    }

    MPoint(MPoint p)
    {
        super(p);
        if (p.graphRef != null)
            graphRef = p.graphRef;
    }

    public void moveBy(double dx, double dy)
    {
        super.moveBy(new double[] { dx, dy, 0, 0 });
    }

    public void moveTo(double x, double y)
    {
        super.moveTo(new double[] { x, y, 0, 0 });
    }

    public void setX(double nx)
    {
        if (nx != getX())
            moveBy(nx - getX(), 0);
    }

    public void setY(double ny)
    {
        if (ny != getY())
            moveBy(0, ny - getY());
    }

    public double getX()
    {
        if(isRelative()) {
            return getAbsolute().getX();
        } else {
            return getCoordinate(0);
        }
    }

    public double getY()
    {
        if(isRelative()) {
            return getAbsolute().getY();
        } else {
            return getCoordinate(1);
        }
    }

    protected double getRawX() {
        return getCoordinate(0);
    }

    protected double getRawY() {
        return getCoordinate(1);
    }

    public double getRelX() {
        return getCoordinate(2);
    }

    public double getRelY() {
        return getCoordinate(3);
    }

    private Point2D getAbsolute() {
        return getGraphIdContainer().toAbsoluteCoordinate(
                new Point2D.Double(getRelX(), getRelY())
        );
    }


    public void setRelativePosition(double rx, double ry) {
        moveTo(new double[] { getX(), getY(), rx, ry });
        relativeSet = true;
    }

    /**
     * Checks if the position of this point should be stored
     * as relative or absolute coordinates
     * @return true if the coordinates are relative, false if not
     */
    public boolean isRelative() {
        Pathway p = getPathway();
        if(p != null) {
            GraphLink.GraphIdContainer gc = getPathway().getGraphIdContainer(graphRef);
            return gc != null;
        }
        return false;
    }

    /**
     * Helper method for converting older GPML files without
     * relative coordinates.
     * @return true if {@link #setRelativePosition(double, double)} was called to
     * set the relative coordinates, false if not.
     */
    protected boolean relativeSet() {
        return relativeSet;
    }

    private GraphLink.GraphIdContainer getGraphIdContainer() {
        return getPathway().getGraphIdContainer(graphRef);
    }

    public String getGraphRef()
    {
        return graphRef;
    }

    /**
     * Set a reference to another object with a graphId. If a parent is set,
     * this will automatically deregister the previously held reference and
     * register the new reference as necessary
     *
     * @param v
     *            reference to set.
     */
    public void setGraphRef(String v)
    {
        if (!Utils.stringEquals(graphRef, v))
        {
            if (getPathway() != null)
            {
                if (graphRef != null)
                {
                    getPathway().removeGraphRef(graphRef, this);
                }
                if (v != null)
                {
                    getPathway().addGraphRef(v, this);
                }
            }
            graphRef = v;
//            getParent().updateConnections();
        }
    }

    public Object clone() throws CloneNotSupportedException
    {
        MPoint p = (MPoint) super.clone();
        if (graphRef != null)
            p.graphRef = graphRef;
        return p;
    }

    public Point2D toPoint2D() {
        return new Point2D.Double(getX(), getY());
    }

    /**
     * Link to an object. Current absolute coordinates
     * will be converted to relative coordinates based on the
     * object to link to.
     */
    public void linkTo(GraphLink.GraphIdContainer idc) {
        Point2D rel = idc.toRelativeCoordinate(toPoint2D());
        linkTo(idc, rel.getX(), rel.getY());
    }

    /**
     * Link to an object using the given relative coordinates
     */
    public void linkTo(GraphLink.GraphIdContainer idc, double relX, double relY) {
        String id = idc.getGraphId();
        if(id == null) id = idc.setGeneratedGraphId();
        setGraphRef(idc.getGraphId());
        setRelativePosition(relX, relY);
    }

    /** note that this may be called any number of times when this point is already unlinked */
    public void unlink()
    {
        if (graphRef != null)
        {
            if (getPathway() != null)
            {
                Point2D abs = getAbsolute();
                moveTo(abs.getX(), abs.getY());
            }
            relativeSet = false;
            setGraphRef(null);
            getParent().fireObjectModifiedEvent(PathwayElementEvent.createCoordinatePropertyEvent(getParent()));
        }
    }

    public Point2D toAbsoluteCoordinate(Point2D p) {
        return new Point2D.Double(p.getX() + getX(), p.getY() + getY());
    }

    public Point2D toRelativeCoordinate(Point2D p) {
        return new Point2D.Double(p.getX() - getX(), p.getY() - getY());
    }

    /**
     * Find out if this point is linked to an object.
     * Returns true if a graphRef exists and is not an empty string
     */
    public boolean isLinked() {
        String ref = getGraphRef();
        return ref != null && !"".equals(ref);
    }

    public void refeeChanged()
    {
        // called whenever the object being referred to has changed.
        getParent().fireObjectModifiedEvent(PathwayElementEvent.createCoordinatePropertyEvent(getParent()));
    }
}
