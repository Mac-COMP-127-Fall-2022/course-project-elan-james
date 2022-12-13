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
    private List<Reflector> reflectors;
    private Wall leftFlipper, rightFlipper;
    private List<Wall> walls;
    private Boolean leftKeyIsPressed, rightKeyIsPressed;

    private GraphicsGroup rectangleLayer;
    private GraphicsGroup circleLayer;

    private Points points;
    private int lives = 2;

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
            unpressedLambdas(dt);
            ballReflectorInteractions();
        });
    }

    public void handlePaddles(double dt) {
        if(leftKeyIsPressed) {
            leftFlipper.rotateBy(dt * 5, false, true);
        } else {
            leftFlipper.rotateBy(dt * 3, true, true);
        }
        if(rightKeyIsPressed) {
            rightFlipper.rotateBy(dt * 5, true, false);
        } else {
            rightFlipper.rotateBy(dt * 3, false, false);
        }
<<<<<<< Updated upstream
=======
    }// } else {
        //     rightPaddle.rotateBy(dt * 5, false, true);
        // }

    public void createBall() {
        ball = new Ball(300, 200, 500, -90, rectangleLayer);
>>>>>>> Stashed changes
    }

    public void createBall() {
        ball = new Ball(100, 100, 400, -90, rectangleLayer);
    }

    public void createReflectors() {
        reflectors = Arrays.asList(new Reflector(250, 150),
        new Reflector(100, 250),
        new Reflector(250, 350),
        new Reflector(400, 250),
        new Reflector(25, 80),
        new Reflector(475, 80),
        new Reflector(250, 30, 40));
        circleLayer.add(reflectors.get(0).getGraphics());
        circleLayer.add(reflectors.get(1).getGraphics());
        circleLayer.add(reflectors.get(2).getGraphics());
        circleLayer.add(reflectors.get(3).getGraphics());
        circleLayer.add(reflectors.get(4).getGraphics());
        circleLayer.add(reflectors.get(5).getGraphics());
        circleLayer.add(reflectors.get(6).getGraphics());
    }

    public void createWalls() {
<<<<<<< Updated upstream
        leftFlipper = new Wall(155, 530, 215, 550, Color.BLACK, canvas);
        rightFlipper = new Wall(345, 530, 285, 550, Color.BLACK, canvas);
=======
        leftPaddle = new Wall(155, 530, 215, 530, Color.BLACK, canvas);
        rightPaddle = new Wall(345, 530, 285, 530, Color.BLACK, canvas);
>>>>>>> Stashed changes
        walls = Arrays.asList(new Wall(85, 455, 155, 530, Color.GREEN, canvas),
        new Wall(80, 400, 80, 450, Color.GREEN, canvas), 
        new Wall(415, 455, 345, 530, Color.GREEN, canvas),
        new Wall(420, 400, 420, 450, Color.GREEN, canvas), 
        new Wall(0, 450, 200, 630, Color.GREEN, canvas),
        new Wall(500, 450, 300, 630, Color.GREEN, canvas),
        new Wall(190, 5, 310, 5, Color.CYAN, canvas),
        new Wall(190, 55, 310, 55, Color.CYAN, canvas), 
        new Wall(115, 35, 190, 5, Color.CYAN, canvas),
        new Wall(115, 85, 190, 55, Color.CYAN, canvas),
        new Wall(310, 5, 385, 35, Color.CYAN, canvas),
        new Wall(310, 55, 385, 85, Color.CYAN, canvas),
        leftFlipper, rightFlipper);
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

    public void unpressedLambdas(double dt) {
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
                gameOver();
            } else {
                reset();
            }
        }
    }

    public void gameOver() {
        GraphicsText lose = new GraphicsText("No more lives left");
        GraphicsText pointTotal = new GraphicsText("You ended with " + points.getPoints() + " points!");
        lose.setPosition(20, 220);
        lose.setFont(FontStyle.BOLD, 50);
        pointTotal.setPosition(20, 305);
        pointTotal.setFont(FontStyle.BOLD, 30);
        canvas.add(lose);
        canvas.add(pointTotal);
        canvas.draw();
        canvas.pause(10000);
        canvas.closeWindow();
    }

    public void reset() {
        ball.removeBall(rectangleLayer);
        createBall();
        GraphicsText livesLeft = new GraphicsText("You have " + lives + " lives left");
        livesLeft.setPosition(40, 310);
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
