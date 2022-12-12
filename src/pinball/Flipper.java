package pinball;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Flipper {
    private Rectangle flipper;
    private boolean isPressed;
    private double width, height;
   
    public Flipper(double x, double y, boolean isOnTheLeft, double angle, CanvasWindow canvas) {
        width = 90;
        height = 10;
        isPressed = false;
        
        GraphicsObject image = new Image(x, y, "flipper.png");
        image.setAnchor(x + 5, y + height/2);
        image.setScale(.225);
        if (isOnTheLeft) {
            image.rotateBy(180);
        }
        canvas.add(image);
        
        flipper = new Rectangle(x, y + height/2, 90, 10);
        flipper.setAnchor(x + 5, y + height/2);
        flipper.setFillColor(Color.BLACK);

        if (!isOnTheLeft) {
            flipper.rotateBy(180+ angle);  
        } else { 
            flipper.rotateBy(angle);  
        }
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
