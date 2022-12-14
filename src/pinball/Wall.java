package pinball;

import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.Point;

// Elan Levin and James McCarthy
/**
 * Walls make up the flippers, on screen walls, and edge walls. Each is a line
 * with a start and end Point. Contains methods to animate the flippers.
 */
public class Wall {
    private Line wall;
    private boolean isAFlipper;
    private double maxAngleInDegrees, minAngleInDegrees; 

    public Wall(double x1, double y1, double x2, double y2, Color color, GraphicsGroup gameLayer) {
        wall = new Line(x1, y1, x2, y2);
        wall.setStrokeWidth(10);
        wall.setStrokeColor(color);
        gameLayer.add(wall);
        minAngleInDegrees = this.getRotationInDegrees();
        maxAngleInDegrees = -90;
    }

    /**
     * Gets the walls rotation in degrees
     */
    public double getRotationInDegrees() {
        return Math.toDegrees(this.getEndpoint2().subtract(getEndpoint1()).angle());
    }
   
    /**
     * Returns degrees based on the direction the flipper/wall should move
     */
    public double getCorrectDirectionForRotation(Boolean rotateClockWise, double angleInRads) {
        double direction = 0;
        if (!rotateClockWise) {
            direction = -angleInRads;
        } else {
            direction = angleInRads;
        }
        return direction;
    }
    
    /**
     * Moves the second point of the line around the first point
     */
    public void rotateBy(double angleInRads, Boolean rotateClockWise, Boolean isALeftFlipper) {
        double direction = getCorrectDirectionForRotation(rotateClockWise, angleInRads);
        isAFlipper = true;
        if (isWithinRotationBounds(isALeftFlipper)) {
            wall.setEndPosition(
                getEndpoint2().subtract(getEndpoint1())
                .rotate(direction)
                .add(getEndpoint1()));
        }
        if (!isWithinRotationBounds(isALeftFlipper)) {
            wall.setEndPosition(
                getEndpoint2().subtract(getEndpoint1())
                .rotate(-direction)
                .add(getEndpoint1()));
        } 
    }
    
    /**
     * Returns true if wall is within it's bounds of rotation
     */
    public boolean isWithinRotationBounds(Boolean isALeftFlipper) {
        boolean itCan = true;
        boolean isPositive = false;
        if (this.getRotationInDegrees() >= 0) {
            isPositive = true; 
        }
        if (isALeftFlipper && this.getRotationInDegrees() <= minAngleInDegrees && Math.abs(this.getRotationInDegrees()) <= Math.abs(maxAngleInDegrees)) {
            itCan = true;
        } 
        else if (!isALeftFlipper && Math.abs(this.getRotationInDegrees()) >= Math.abs(maxAngleInDegrees)) {
            itCan = true;
            if (isPositive && this.getRotationInDegrees() < minAngleInDegrees) {
                itCan = false;
            }
        } else {
            itCan = false;
        }
        return itCan; 
    }

    public Point getEndpoint1() {
        return new Point(wall.getX1(), wall.getY1());
    }

    public Point getEndpoint2() {
        return new Point(wall.getX2(), wall.getY2());
    }

    public double getX1() {
        return wall.getX1();
    }

    public double getX2() {
        return wall.getX2();
    }

    public double getY1() {
        return wall.getY1();
    }

    public double getY2() {
        return wall.getY2();
    }

    public Point getCenter1() {
        return new Point(wall.getX1(), wall.getY1());
    }

    public Point getCenter2() {
        return new Point(wall.getX2(), wall.getY2());
    }

    public double getWidth() {
        return wall.getStrokeWidth();
    }

    public boolean isThisAFlipper() {
        return isAFlipper;
    }
}
