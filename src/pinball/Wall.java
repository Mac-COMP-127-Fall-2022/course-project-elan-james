package pinball;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.Strokable;

public class Wall {
    private Line wall;
    // private Rectangle wall;
    // private double x1, y1, x2, y2;

    public Wall(double x1, double y1, double x2, double y2, double width, GraphicsGroup rectanglGroup) {
        wall = new Line(x1, y1, x2, y2);
        wall.setStrokeWidth(width);
        wall.setStrokeColor(Color.BLUE);
        rectanglGroup.add(wall);

        // wall = new Rectangle(x1, y1, width, 10);
        // wall.setFillColor(Color.BLUE);
        // rectangleLayer.add(wall);
    }

    private Point getEndpoint1() {
        return new Point(wall.getX1(), wall.getY1());
    }

    private Point getEndpoint2() {
        return new Point(wall.getX2(), wall.getY2());
    }

    public double getRotation() {
        return getEndpoint2().subtract(getEndpoint1()).angle();
    }

    public void rotateBy(double angle) {
        wall.setEndPosition(
            getEndpoint2().subtract(getEndpoint1())
                .rotate(angle)
                .add(getEndpoint1()));
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
}
