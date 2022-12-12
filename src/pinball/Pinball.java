package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.events.Key;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Pinball {
    private static final int CANVAS_WIDTH = 500;
    private static final int CANVAS_HEIGHT = 650;
    private CanvasWindow canvas;
    private Ball ball;
    private Flipper leftFlipper, rightFlipper;
    private Reflector reflector1, reflector2, reflector3, reflector4, reflector5, 
        reflector6, reflector7, reflector8, reflector9, reflector10;
    private List<Reflector> reflectors;
    private Wall wall1, wall2, wall3, wall4, wall5, wall6, wall7, wall8;
    private List<Wall> walls;
    private Spring spring;
    private GraphicsGroup rectangleLayer;
    private GraphicsGroup circleLayer;
    private Points points;
    private int lives = 3;
    
    public Pinball() {
        canvas = new CanvasWindow("Pinball", CANVAS_WIDTH, CANVAS_HEIGHT);
        rectangleLayer = new GraphicsGroup();
        circleLayer = new GraphicsGroup();
        canvas.add(rectangleLayer);
        canvas.add(circleLayer);
        createBall();
        createFlippers();
        createReflectors();
        createWalls();
        // createSpring();
        createPoints();
        canvas.animate(() -> {
            handleBallInteractions();
            moveFlippers();
        });
        // moveSpring();
        unPresssed();
    }

    public void createBall() {
        ball = new Ball(450, 80, canvas, 50, -90, rectangleLayer);
    }

    public void createFlippers() {
        leftFlipper = new Flipper(150, 550, true, 40, rectangleLayer);
        rightFlipper = new Flipper(280, 550, true, 140, rectangleLayer);
    }

    public void createReflectors() {
        reflector1 = new Reflector(225, 150);
        reflector2 = new Reflector(75, 250);
        reflector3 = new Reflector(225, 350);
        reflector4 = new Reflector(375, 250);
        // reflector5 = new Reflector(25, 50);
        // reflector6 = new Reflector(475, 50);
        reflector7 = new Reflector(60, 470, 3);
        reflector8 = new Reflector(440, 470, 3);
        reflector9 = new Reflector(100, 440, 3);
        reflector10 = new Reflector(400, 440, 3);
        circleLayer.add(reflector1.getGraphics());
        circleLayer.add(reflector2.getGraphics());
        circleLayer.add(reflector3.getGraphics());
        circleLayer.add(reflector4.getGraphics());
        // circleLayer.add(reflector5.getGraphics());
        // circleLayer.add(reflector6.getGraphics());
        circleLayer.add(reflector7.getGraphics());
        circleLayer.add(reflector8.getGraphics());
        circleLayer.add(reflector9.getGraphics());
        circleLayer.add(reflector10.getGraphics());
        reflectors = Arrays.asList(reflector1, reflector2, reflector3, reflector4, reflector7, 
            reflector8, reflector9, reflector10);
    }

    public void createWalls() {
        wall1 = new Wall(85, 455, 155, 530, 10, rectangleLayer);
        wall2 = new Wall(80, 400, 80, 450, 10, rectangleLayer);
        wall3 = new Wall(415, 455, 345, 530, 10, rectangleLayer);
        wall4 = new Wall(420, 400, 420, 450, 10, rectangleLayer);
        wall5 = new Wall(0, 450, 200, 630, 10, rectangleLayer);
        wall6 = new Wall(500, 450, 300, 630, 10, rectangleLayer);
        // wall7 = new Wall(455, 150, 455, 650, 10, rectangleLayer);
        // wall8 = new Wall(400, 0, 510, 100, 10, rectangleLayer);
        walls = Arrays.asList(wall1, wall2, wall3, wall4, wall5, wall6);
    }

    public void createSpring() {
        spring = new Spring(rectangleLayer);
    }

    public void createPoints() {
        points = new Points(canvas);
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

    public void handleBallInteractions() {
        ball.moveBall(0.1, CANVAS_WIDTH, CANVAS_HEIGHT);
        ball.checkCanvasWallCollision(0.1, CANVAS_WIDTH, CANVAS_HEIGHT);
        for (Reflector reflector : reflectors) {
            if (ball.bounceOffReflector(reflector)) {
                if (ball.getCenter().getY() < 100) {
                    points.addPoints(20);
                } else if (ball.getCenter().getY() > 400) {
                    points.addPoints(5);
                } else {
                    points.addPoints(10);
                }
            } 
        }
        if (ball.checkCollision(0.1, rectangleLayer)) {
            ball.updateCollisionPosition(0.1);
        }
        ball.checkWallCollision(walls);
        // belowFlippers();
    }

    public void moveSpring() {
        canvas.onDrag(event -> spring.updatePosition(event.getPosition().getY()));
    }

    public void belowFlippers() {
        if (ball.getCenterY() > 600) {
            lives --;
            if (lives == 0) {
                GraphicsText lose = new GraphicsText("No more lives left");
                GraphicsText pointTotal = new GraphicsText("You ended with " + points.getPoints() + " points!");
                lose.setPosition(20, 250);
                lose.setFont(FontStyle.BOLD, 50);
                pointTotal.setPosition(40, 330);
                pointTotal.setFont(FontStyle.BOLD, 30);
                canvas.add(lose);
                canvas.add(pointTotal);
                canvas.draw();
                canvas.pause(10000);
                canvas.closeWindow();
            }
            reset();
        }
    }

    public void reset() {
        ball.resetBall(rectangleLayer);
        createBall();
        GraphicsText livesLeft = new GraphicsText("You have " + lives + " lives left");
        livesLeft.setPosition(40, 325);
        livesLeft.setFont(FontStyle.BOLD, 40);
        canvas.add(livesLeft);
        canvas.draw();
        canvas.pause(3000);
        canvas.remove(livesLeft);
    }

    public static void main(String[] args) {
        new Pinball();
    }
}
