package pinball;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import java.awt.Color;

// Elan Levin and James McCarthy
/**
* Ellipses the ball can bounce off of to gain points. Can be created with a set diameter of a diameter of choice. 
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
     * Constructor for reflectors of any given diameter
     */
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
