package pinball;

import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Strokable;

public class Wall {
    private Line wall;

    public Wall(double x1, double y1, double x2, double y2, Color color, GraphicsGroup rectanglGroup) {
        wall = new Line(x1, y1, x2, y2);
        wall.setStrokeWidth(10);
        wall.setStrokeColor(color);
        rectanglGroup.add(wall);
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

    public void setColor(Color color) {
        wall.setStrokeColor(color);
    }
}
