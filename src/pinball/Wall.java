package pinball;

import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Line;

public class Wall {
    private Line wall;
    private double x1, y1, x2, y2;

    public Wall(double x1, double y1, double x2, double y2, double width, GraphicsGroup rectangleLayer) {
        wall = new Line(x1, y1, x2, y2);
        wall.setStrokeWidth(width);
        wall.setStrokeColor(Color.BLUE);
        rectangleLayer.add(wall);
    }
}
