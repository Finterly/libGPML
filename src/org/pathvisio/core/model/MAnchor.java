package org.pathvisio.core.model;

import java.awt.geom.Point2D;

/**
 * This class represents the Line.Graphics.Anchor element in GPML
 * @author thomas
 *
 */
public class MAnchor extends GenericPoint {
    AnchorType shape = AnchorType.NONE;

    public MAnchor(double position, PathwayElement parent) {
        super(new double[] { position }, parent);
    }

    public MAnchor(MAnchor a) {
        super(a);
        shape = a.shape;
    }

    public void setShape(AnchorType type) {
        if(!this.shape.equals(type) && type != null) {
            this.shape = type;
            getParent().fireObjectModifiedEvent(PathwayElementEvent.createSinglePropertyEvent(getParent(), StaticProperty.LINESTYLE));
        }
    }

    public AnchorType getShape() {
        return shape;
    }

    public double getPosition() {
        return getCoordinate(0);
    }

    public void setPosition(double position) {
        if(position != getPosition()) {
            moveBy(position - getPosition());
        }
    }

    public void moveBy(double delta) {
        super.moveBy(new double[] { delta });
    }

    public Point2D toAbsoluteCoordinate(Point2D p) {
        Point2D l = ((MLine)getParent()).getConnectorShape().fromLineCoordinate(getPosition());
        return new Point2D.Double(p.getX() + l.getX(), p.getY() + l.getY());
    }

    public Point2D toRelativeCoordinate(Point2D p) {
        Point2D l = ((MLine)getParent()).getConnectorShape().fromLineCoordinate(getPosition());
        return new Point2D.Double(p.getX() - l.getX(), p.getY() - l.getY());
    }
}

