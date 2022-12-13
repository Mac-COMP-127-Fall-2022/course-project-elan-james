package pinball;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Strokable;

public class Wall {
    private Line wall;
    private double maxAngleInDegrees, minAngleInDegrees;
    private static double MAX_NUMBER_OF_DEGREES_ROTATAED = 90;
   
    // private Rectangle wall;
    // private double x1, y1, x2, y2;
    
    public Wall(double x1, double y1, double x2, double y2, Color color, CanvasWindow canvas) {
        wall = new Line(x1, y1, x2, y2);
        wall.setStrokeWidth(10);
        wall.setStrokeColor(color);
        canvas.add(wall);
        minAngleInDegrees = this.getRotationInDegrees();
        maxAngleInDegrees = -90;
        
        // wall = new Rectangle(x1, y1, width, 10);
        // wall.setFillColor(Color.BLUE);
        // rectangleLayer.add(wall);
    }

    
    public double getRotationInDegrees() {
        return Math.toDegrees(this.getEndpoint2().subtract(getEndpoint1()).angle());
    }
   
    public double getMinAngleInDegrees() {
        return minAngleInDegrees;
    }

    public double getCorrectDirectionForRotation(Boolean rotateClockWise, double angleInRads) {
        double direction = 0;
        if (!rotateClockWise) {
            direction = -angleInRads;
        } else {
            direction = angleInRads;
        }
        return direction;
    }
    
    public void rotateBy(double angleInRads, Boolean rotateClockWise, Boolean isALeftpaddle) {
        double direction = getCorrectDirectionForRotation(rotateClockWise, angleInRads);
        if (isWithinRotationBounds(isALeftpaddle)) {
            wall.setEndPosition(
                getEndpoint2().subtract(getEndpoint1())
                .rotate(direction)
                .add(getEndpoint1()));
        }
        if (!isWithinRotationBounds(isALeftpaddle)) {
            wall.setEndPosition(
                getEndpoint2().subtract(getEndpoint1())
                .rotate(-direction)
                .add(getEndpoint1()));
        } 
    }
            
    public boolean isWithinRotationBounds(Boolean isALeftpaddle) {
        boolean itCan = true;
        boolean isPositive = false;
        if (this.getRotationInDegrees() >= 0) {
            isPositive = true; 
        }
        if (isALeftpaddle && this.getRotationInDegrees() <= minAngleInDegrees && Math.abs(this.getRotationInDegrees()) <= Math.abs(maxAngleInDegrees)) {
            itCan = true;
        } 
        else if (!isALeftpaddle && Math.abs(this.getRotationInDegrees()) >= Math.abs(maxAngleInDegrees)) {
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

    public Strokable getGraphics() {
        return wall;
    }

    public Point getPosition() {
        return wall.getPosition();
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

    public void setColor(Color color) {
        wall.setStrokeColor(color);
    }
}
