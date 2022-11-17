package pinball;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;

public class Flipper {
    private Rectangle flipper;
    private boolean isPressed;
   
    public Flipper(CanvasWindow canvas, int x, int y, int angle) {
        flipper = new Rectangle(x, y, 70, 10);
        isPressed = false;
        flipper.setFillColor(Color.BLACK);
        flipper.rotateBy(angle);
        canvas.add(flipper);
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
