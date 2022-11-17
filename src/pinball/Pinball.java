package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;

public class Pinball {
    private static final int CANVAS_WIDTH = 500;
    private static final int CANVAS_HEIGHT = 650;
    private CanvasWindow canvas;
    private Ball ball;
    private Flipper leftFlipper, rightFlipper;
    
    public Pinball() {
        canvas = new CanvasWindow("Pinball", CANVAS_WIDTH, CANVAS_HEIGHT);
        createBall();
        createFlippers();
        canvas.animate(() -> updateBall());
        moveFlippers();
    }

    public void createBall() {
        ball = new Ball(CANVAS_WIDTH/2, CANVAS_HEIGHT/2, canvas, 50, -50);
    }

    public void createFlippers() {
        leftFlipper = new Flipper(canvas, 150, 550, 40);
        rightFlipper = new Flipper(canvas, 275, 550, 140);
    }

    public void updateBall() {
        ball.updatePosition(0.1, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);
    }

    public void moveFlippers() {
        canvas.onMouseDown(event -> leftFlipper.updatePosition(event.getPosition().angle()));
    }

    public static void main(String[] args) {
        new Pinball();
    }
}
