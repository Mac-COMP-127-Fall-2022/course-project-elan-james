package pinball;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import java.awt.Color;
/**
* ellipses the ball can bounce off of to gain points. This is a glorified ellipse. 
*/
public class Reflector {
    private Ellipse reflector;

    /**
     * The standard reflector constructor
     */
    public Reflector(double x, double y) {
        this(x, y, 50);
    }

    /**
     * constructor for the circles that help with wall collision and other small tasks
     */
    public Reflector(double x, double y, double diameter) {
        reflector = new Ellipse(x, y, diameter, diameter);
        reflector.setCenter(x, y);
    }

    /**
     * Getters and setters:
     */
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
