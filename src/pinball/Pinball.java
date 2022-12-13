package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.events.Key;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Pinball {
    private static final int CANVAS_WIDTH = 500;
    private static final int CANVAS_HEIGHT = 650;
    private static final double PHYSICS_TIMESTEP = 0.0002;

    private CanvasWindow canvas;
    private Ball ball;
    private Flipper leftFlipper, rightFlipper;
    private Reflector reflector1, reflector2, reflector3, reflector4, reflector5, 
        reflector6, reflector7;
    private List<Reflector> reflectors;
    private Wall leftPaddle, rightPaddle, wall1, wall2, wall3, wall4, wall5, wall6, wall7, wall8, wall9, wall10, wall11, wall12;
    private List<Wall> walls;
    // private Spring spring;
    private Boolean leftKeyIsPressed, rightKeyIsPressed;

    private GraphicsGroup rectangleLayer;
    private GraphicsGroup circleLayer;

    private Points points;
    private int lives = 3;

    private double physicsTimer = 0;
    
    public Pinball() {
        canvas = new CanvasWindow("Pinball", CANVAS_WIDTH, CANVAS_HEIGHT);
        Image background = new Image("background.png");
        background.setMaxWidth(500);
        background.setMaxHeight(900);
        canvas.add(background);
        rectangleLayer = new GraphicsGroup();
        circleLayer = new GraphicsGroup();
        canvas.add(rectangleLayer);
        canvas.add(circleLayer);
        leftKeyIsPressed = false;
        rightKeyIsPressed = false;
        createBall();
        createReflectors();
        createWalls();
        createPoints();
        canvas.animate((dt) -> {
            physicsTimer += dt;
            while (physicsTimer > 0) {
                handleBallInteractions(PHYSICS_TIMESTEP);
                physicsTimer -= PHYSICS_TIMESTEP;
            }
            handlePaddles(dt);
            flipperFlipLambdas(dt);
            UnpressedLambdas(dt);
            // moveSpring();
            ballReflectorInteractions();
        });
    }
    

    private void handlePaddles(double dt) {
        if(leftKeyIsPressed) {
            leftPaddle.rotateBy(dt * 5, false, true);
        }// } else {
        //     leftPaddle.rotateBy(dt * 5, true, true);
        // }
        if(rightKeyIsPressed) {
            rightPaddle.rotateBy(dt * 5, true, true);
        }
    }// } else {
        //     rightPaddle.rotateBy(dt * 5, false, true);
        // }

    public void createBall() {
        ball = new Ball(100, 100, 500, -90, rectangleLayer);
    }

    public void createFlippers() {
        leftFlipper = new Flipper(150, 550, true, 40, rectangleLayer);
        rightFlipper = new Flipper(280, 550, true, 140, rectangleLayer);
    }

    public void createReflectors() {
        reflector1 = new Reflector(250, 150);
        reflector2 = new Reflector(100, 250);
        reflector3 = new Reflector(250, 350);
        reflector4 = new Reflector(400, 250);
        reflector5 = new Reflector(25, 80);
        reflector6 = new Reflector(475, 80);
        reflector7 = new Reflector(250, 30, 40);
        circleLayer.add(reflector1.getGraphics());
        circleLayer.add(reflector2.getGraphics());
        circleLayer.add(reflector3.getGraphics());
        circleLayer.add(reflector4.getGraphics());
        circleLayer.add(reflector5.getGraphics());
        circleLayer.add(reflector6.getGraphics());
        circleLayer.add(reflector7.getGraphics());
        reflectors = Arrays.asList(reflector1, reflector2, reflector3, reflector4, reflector5, reflector6, reflector7);
    }

    public void createWalls() {
        wall1 = new Wall(85, 455, 155, 530, Color.GREEN, canvas);
        wall2 = new Wall(80, 400, 80, 450, Color.GREEN, canvas);
        wall3 = new Wall(415, 455, 345, 530, Color.GREEN, canvas);
        wall4 = new Wall(420, 400, 420, 450, Color.GREEN, canvas);
        wall5 = new Wall(0, 450, 200, 630, Color.GREEN, canvas);
        wall6 = new Wall(500, 450, 300, 630, Color.GREEN, canvas);
        wall7 = new Wall(190, 5, 310, 5, Color.CYAN, canvas);
        wall8 = new Wall(190, 55, 310, 55, Color.CYAN, canvas);
        wall9 = new Wall(115, 35, 190, 5, Color.CYAN, canvas);
        wall10 = new Wall(115, 85, 190, 55, Color.CYAN, canvas);
        wall11 = new Wall(310, 5, 385, 35, Color.CYAN, canvas);
        wall12 = new Wall(310, 55, 385, 85, Color.CYAN, canvas);
        leftPaddle = new Wall(200, 550, 280, 550, Color.BLACK, canvas);
        rightPaddle = new Wall(320, 551, 250, 550, Color.BLACK, canvas);
        walls = Arrays.asList(wall1, wall2, wall3, wall4, wall5, wall6, wall7, wall8, 
            wall9, wall10, wall11, wall12, leftPaddle, rightPaddle);
    }

    public void createPoints() {
        points = new Points(canvas);
    }

    public void flipperFlipLambdas(double dt) {
        Set<Key> keys = canvas.getKeysPressed();
        if (keys.contains(Key.LEFT_ARROW)) {
            leftKeyIsPressed = true;
        }
        if (keys.contains(Key.RIGHT_ARROW)) {
            rightKeyIsPressed = true;
        }
    }

    public void UnpressedLambdas(double dt) {
        canvas.onKeyUp(event -> {
            if (event.getKey().equals(Key.LEFT_ARROW)) {
                leftKeyIsPressed = false;
            }
            if (event.getKey().equals(Key.RIGHT_ARROW)) {
                rightKeyIsPressed = false;
            }
        });
    }

    public void handleBallInteractions(double dt) {
        ball.moveBall(dt, CANVAS_WIDTH, CANVAS_HEIGHT);
        ball.checkCanvasWallCollision(dt, CANVAS_WIDTH, CANVAS_HEIGHT);
        if (ball.checkCollision(rectangleLayer)) {
            ball.updateCollisionPosition(dt);
        }
        for (Wall wall : walls) {
            if (ball.checkWallCollision(wall)) {
                points.addPoints(1);
            }
        }
        // belowFlippers();
    }

    public void ballReflectorInteractions() {
        for (Reflector reflector : reflectors) {
            reflector.setColor(Color.CYAN);
            if (ball.bounceOffReflector(reflector)) {
                reflector.setColor(Color.YELLOW);
                if (ball.getCenter().getY() < 100) {
                    if (reflector.getRadius() < 25) {
                        points.addPoints(100);
                    } else {
                        points.addPoints(20);
                    }
                } else if (ball.getCenter().getY() > 400) {
                    points.addPoints(5);
                } else {
                    points.addPoints(10);
                }
            } 
        }
    }

    public void belowFlippers() {
        if (ball.getCenterY() > 600) {
            lives --;
            if (lives == 0) {
                GraphicsText lose = new GraphicsText("No more lives left");
                GraphicsText pointTotal = new GraphicsText("You ended with " + points.getPoints() + " points!");
                lose.setPosition(20, 250);
                lose.setFont(FontStyle.BOLD, 50);
                pointTotal.setPosition(20, 330);
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
