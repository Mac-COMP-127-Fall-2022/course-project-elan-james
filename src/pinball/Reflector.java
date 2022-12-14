package pinball;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import java.awt.Color;

public class Reflector {
    private Ellipse reflector;

    public Reflector(double x, double y) {
        this(x, y, 50);
    }

    public Reflector(double x, double y, double diameter) {
        reflector = new Ellipse(x, y, diameter, diameter);
        reflector.setCenter(x, y);
    }

    public GraphicsObject getGraphics() {
        return reflector;
    }

    public double getRadius() {
        return reflector.getWidth()/2;
    }

    public Point getCenter() {
        return reflector.getCenter();
    }

    public void setColor(Color color) {
        reflector.setFillColor(color);
    }
}
