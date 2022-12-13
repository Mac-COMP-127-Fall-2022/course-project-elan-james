package pinball;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Strokable;

public class Wall {
    private Line wall;
    private double maxAngle, minAngle;
    private static double MAX_NUMBER_OF_DEGREES_ROTATAED = 90;
   
    // private Rectangle wall;
    // private double x1, y1, x2, y2;
    
    public Wall(double x1, double y1, double x2, double y2, Color color, CanvasWindow canvas) {
        wall = new Line(x1, y1, x2, y2);
        wall.setStrokeWidth(10);
        wall.setStrokeColor(color);
        canvas.add(wall);
        minAngle = this.getRotationInDegrees();
        maxAngle = 270;
        
        // wall = new Rectangle(x1, y1, width, 10);
        // wall.setFillColor(Color.BLUE);
        // rectangleLayer.add(wall);
    }

    private double getTotalDegreesRotatedUsing360(Boolean isALeftpaddle) {
        double degreesInTotal = 0;
        if (isALeftpaddle && this.getRotationInDegrees() < 180) {
            degreesInTotal = minAngle- this.getRotationInDegrees();
        } 
        if (isALeftpaddle && this.getRotationInDegrees() > 180) {
            degreesInTotal = 360 - maxAngle + minAngle;
        }
        if (!isALeftpaddle) {
            degreesInTotal = this.getRotationInDegrees() - minAngle;
        } 
        System.out.println(degreesInTotal);
        return degreesInTotal;
    }
    
    public double getRotationInDegrees() {
        return degreeOutOf360(Math.toDegrees(this.getEndpoint2().subtract(getEndpoint1()).angle()));
    }

    public double getRotationOutOfPlusMinus180Degrees() {
        return Math.toDegrees(this.getEndpoint2().subtract(getEndpoint1()).angle());
    }
   
    public double getMinAngle() {
        return minAngle;
    }

    private double getCorrectDirectionForRotation(Boolean rotateClockWise, double angleInRads) {
        double direction = 0;
        if (!rotateClockWise) {
            direction = -angleInRads;
        } else {
            direction = angleInRads;
        }
        return direction;
    }

    private double degreeOutOf360(double degree) {
        if (degree >= 0) {
            return degree;
        } else {
            return (180 - degree) + 180;
        }
    }
    
    public void rotateBy(double angleInRads, Boolean rotateClockWise, Boolean isALeftpaddle) {
        double direction = getCorrectDirectionForRotation(rotateClockWise, angleInRads);
        // if (getTotalDegreesRotatedUsing360(isALeftpaddle) < MAX_NUMBER_OF_DEGREES_ROTATAED + 10 
        // && getTotalDegreesRotatedUsing360(isALeftpaddle) >= -10) {
            wall.setEndPosition(
                getEndpoint2().subtract(getEndpoint1())
                    .rotate(direction)
                    .add(getEndpoint1()));
        // }
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
