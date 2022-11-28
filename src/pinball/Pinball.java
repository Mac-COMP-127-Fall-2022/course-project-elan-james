package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.events.Key;

import java.util.ArrayList;
import java.util.Set;

public class Pinball {
    private static final int CANVAS_WIDTH = 500;
    private static final int CANVAS_HEIGHT = 650;
    private CanvasWindow canvas;
    private Ball ball;
    private Flipper leftFlipper, rightFlipper;
    private Reflector reflector1, reflector2, reflector3, reflector4;
    private ArrayList<Reflector> reflectors = new ArrayList<>();
    private Wall wall1, wall2, wall3, wall4, wall5, wall6, wall7;
    private GraphicsGroup pinballLayer;
    
    public Pinball() {
        canvas = new CanvasWindow("Pinball", CANVAS_WIDTH, CANVAS_HEIGHT);
        pinballLayer = new GraphicsGroup();
        canvas.add(pinballLayer);
        createBall();
        createFlippers();
        createReflectors();
        createWalls();
        reflectors.add(reflector1);
        reflectors.add(reflector2);
        reflectors.add(reflector3);
        reflectors.add(reflector4);
        // canvas.animate(() -> {
        //     updateBall();
        //     moveFlippers();
        // });
        moveFlippers();
        unPresssed();
    }

    public void createBall() {
        ball = new Ball(CANVAS_WIDTH/2, 100, canvas, 50, -100, pinballLayer);
    }

    public void createFlippers() {
        leftFlipper = new Flipper(150, 550, 40, pinballLayer);
        rightFlipper = new Flipper(275, 550, 140, pinballLayer);
    }

    public void createReflectors() {
        reflector1 = new Reflector(225, 150, pinballLayer);
        reflector2 = new Reflector(75, 250, pinballLayer);
        reflector3 = new Reflector(225, 350, pinballLayer);
        reflector4 = new Reflector(375, 250, pinballLayer);
    }

    public void createWalls() {
        wall1 = new Wall(85, 455, 155, 530, 10, pinballLayer);
        wall2 = new Wall(80, 400, 80, 450, 10, pinballLayer);
        wall3 = new Wall(410, 455, 340, 530, 10, pinballLayer);
        wall4 = new Wall(415, 400, 415, 450, 10, pinballLayer);
        wall5 = new Wall(40, 500, 110, 575, 10, pinballLayer);
        wall6 = new Wall(455, 480, 385, 555, 10, pinballLayer);
        wall7 = new Wall(455, 400, 455, 475, 10, pinballLayer);
    }

    public void updateBall() {
        ball.updatePosition(0.1, CANVAS_WIDTH, CANVAS_HEIGHT, pinballLayer);
        ball.updateCircleCollisionPosition(0.1, reflectors);
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
