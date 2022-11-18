package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;

public class Reflector {
    private Ellipse reflector;
    private double x, y;

    public Reflector(double x, double y, CanvasWindow canvas) {
        reflector = new Ellipse(x, y, 50, 50);
        canvas.add(reflector);
    }

    public double getCenterX() {
        return x + reflector.getWidth()/2;
    }

    public double getCenterY() {
        return y + reflector.getHeight()/2;
    }

}
