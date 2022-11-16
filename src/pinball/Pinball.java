package pinball;

import edu.macalester.graphics.CanvasWindow;

public class Pinball {
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;
    private CanvasWindow canvas;
    private Ball ball;
    private Flipper leftFlipper, rightFlipper;
    
    public Pinball() {
        canvas = new CanvasWindow("Pinball", CANVAS_WIDTH, CANVAS_HEIGHT);
        createBall();
        createFlippers();
        updateBall();
    }

    public void createBall() {
        ball = new Ball(CANVAS_WIDTH/2, CANVAS_HEIGHT/2, canvas, 50, 50);
    }

    public void createFlippers() {
        leftFlipper = new Flipper(canvas, 50, 700, 40);
        rightFlipper = new Flipper(canvas, 500, 700, 140);
    }

    public void updateBall() {
        canvas.animate(() -> ball.updatePosition(0.1, CANVAS_WIDTH, CANVAS_HEIGHT, canvas));
    }

    public static void main(String[] args) {
        new Pinball();
    }
}
