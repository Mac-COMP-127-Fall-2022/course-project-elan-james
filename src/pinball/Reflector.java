package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;

public class Reflector {
    private Ellipse reflector;

    public Reflector(int x, int y, CanvasWindow canvas) {
        reflector = new Ellipse(x, y, 50, 50);
        canvas.add(reflector);
    }
}
