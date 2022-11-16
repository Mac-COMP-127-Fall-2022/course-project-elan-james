package pinball;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

public class Flipper {
    private Rectangle flipper;
    
    public Flipper(CanvasWindow canvas, int x, int y, int angle) {
        flipper = new Rectangle(x, y, 70, 10);
        flipper.setFillColor(Color.BLACK);
        flipper.rotateBy(angle);
        canvas.add(flipper);
    }
}
