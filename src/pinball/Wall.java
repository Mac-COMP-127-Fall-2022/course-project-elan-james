package pinball;

import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class Wall {
    private Line wall;
    // private Rectangle wall;
    // private double x1, y1, x2, y2;

    public Wall(double x1, double y1, double x2, double y2, double width, GraphicsGroup rectangleLayer) {
        wall = new Line(x1, y1, x2, y2);
        wall.setStrokeWidth(width);
        wall.setStrokeColor(Color.BLUE);
        rectangleLayer.add(wall);

        // wall = new Rectangle(x1, y1, width, 10);
        // wall.setFillColor(Color.BLUE);
        // rectangleLayer.add(wall);
    }

    public Point getPosition() {
        return wall.getPosition();
    }

    // public double getX1() {
    //     return wall.getX1();
    // }

    // public double getX2() {
    //     return wall.getX2();
    // }

    // public double getY1() {
    //     return wall.getY1();
    // }

    // public double getY2() {
    //     return wall.getY2();
    // }
}
