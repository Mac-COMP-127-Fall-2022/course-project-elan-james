package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public class Reflector {
    private Ellipse reflector;
    private double x, y;

    public Reflector(double x, double y, GraphicsGroup pinballLayer) {
        reflector = new Ellipse(x, y, 50, 50);
        pinballLayer.add(reflector);
    }

    public double getCenterX() {
        return x + reflector.getWidth()/2;
    }

    public double getCenterY() {
        return y + reflector.getHeight()/2;
    }

    public double getRadius() {
        return reflector.getWidth()/2;
    }

    public Point getCenter() {
        return reflector.getCenter();
    }

}
