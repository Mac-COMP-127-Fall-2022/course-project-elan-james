package pinball;

import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Path;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class Flipper {
    private Rectangle flipper;
    private Path flipper1;
    private boolean isPressed;
   
    public Flipper(double x, double y, double angle, GraphicsGroup rectangleLayer) {
        flipper = new Rectangle(x, y, 70, 10);
        isPressed = false;
        flipper.setFillColor(Color.BLACK);
        flipper.rotateBy(angle);
        rectangleLayer.add(flipper);

        // flipper1 = new Path(new Point(150, 550), new Point(200, 575), new Point(205, 580), new Point(205, 580));
        // pinballLayer.add(flipper1);
    }

    public void movePaddleUp(double angle) {
        if (!isPressed){
            flipper.rotateBy(angle);
        }
    }

    public void movePaddleDown(double angle) {
        flipper.rotateBy(-angle);
    }
 
    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }
}
