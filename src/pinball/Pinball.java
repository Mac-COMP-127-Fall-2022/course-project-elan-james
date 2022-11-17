package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;
import java.util.Set;

public class Pinball {
    private static final int CANVAS_WIDTH = 500;
    private static final int CANVAS_HEIGHT = 650;
    private CanvasWindow canvas;
    private Ball ball;
    private Flipper leftFlipper, rightFlipper;
    private Reflector reflector;
    
    public Pinball() {
        canvas = new CanvasWindow("Pinball", CANVAS_WIDTH, CANVAS_HEIGHT);
        createBall();
        createFlippers();
        createReflectors();
        canvas.animate(() -> {
            updateBall();
            moveFlippers();
        });
        moveFlippers();
        unPresssed();
    }

    public void createBall() {
        ball = new Ball(CANVAS_WIDTH/2, 100, canvas, 50, -50);
    }

    public void createFlippers() {
        leftFlipper = new Flipper(150, 550, 40, canvas);
        rightFlipper = new Flipper(275, 550, 140, canvas);
    }

    public void createReflectors() {
        reflector = new Reflector(CANVAS_WIDTH/2, 200, canvas);
        reflector = new Reflector(100, CANVAS_HEIGHT/2, canvas);
        reflector = new Reflector(CANVAS_WIDTH/2, 500, canvas);
        reflector = new Reflector(400, CANVAS_HEIGHT/2, canvas);
    }

    public void updateBall() {
        ball.updatePosition(0.1, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);
    }

    public void moveFlippers() {
        Set<Key> keys = canvas.getKeysPressed();
        if (keys.contains(Key.LEFT_ARROW)) {
            leftFlipper.movePaddleUp(-30);
            leftFlipper.setPressed(true);
        }
        if (keys.contains(Key.RIGHT_ARROW)) {
            rightFlipper.movePaddleUp(30);
            rightFlipper.setPressed(true);
        }
    }

    public void unPresssed() {
        canvas.onKeyUp(event -> {
            if (event.getKey().equals(Key.LEFT_ARROW)) {
                leftFlipper.setPressed(false);
                leftFlipper.movePaddleDown(-30);
            }
            if (event.getKey().equals(Key.RIGHT_ARROW)) {
                rightFlipper.setPressed(false);
                rightFlipper.movePaddleDown(30);
            }
        });
    }

    public static void main(String[] args) {
        new Pinball();
    }
}
