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
import java.util.Random;
import java.util.Set;

// Elan Levin and James McCarthy
/**
 * The Pinball game. Creates the ball, reflctors, flippers, and walls on the canvas.
 * Keeps track of the player's lives and how many points they have.
 */
public class Pinball {
    private static final int CANVAS_WIDTH = 500;
    private static final int CANVAS_HEIGHT = 650;
    private static final double PHYSICS_TIMESTEP = 0.0002;

    private CanvasWindow canvas;
    private Ball ball;
    private List<Reflector> reflectors;
    private Wall leftFlipper, rightFlipper;
    private List<Wall> walls;
    private boolean leftKeyIsPressed = false, rightKeyIsPressed = false;
    private boolean isPaused;

    private GraphicsGroup gameLayer;

    private Points points;
    private int lives = 1;

    private double physicsTimer = 0;
    
    /**
     * A pinball game with 3 rounds. Rounds 2 and 3 are triggered by pushing any button. 
     * After you lose your third life, the program does nothing else. Points are accumulated
     * for hitting the circles on the screen.
     */
    public Pinball() {
        canvas = new CanvasWindow("Pinball", CANVAS_WIDTH, CANVAS_HEIGHT);
        gameLayer = new GraphicsGroup();
        Image background = new Image("background.png");
        background.setMaxWidth(500);
        background.setMaxHeight(900);
        canvas.add(background);
        canvas.add(gameLayer);
        createBall();
        createReflectors();
        createWalls();
        createPoints();
        canvas.animate((dt) -> {
            physicsTimer += dt;
            while (physicsTimer > 0) {
                ballWallInteractions(PHYSICS_TIMESTEP);
                physicsTimer -= PHYSICS_TIMESTEP;
            }
            ballReflectorInteractions();
            handleFlippers(dt);
            flipperFlipLambdas();
            unpressedLambdas();
            belowFlippers();
        });
    }

    public void createBall() {
        Random rand = new Random();
        ball = new Ball(rand.nextDouble(200, 300), 95, 400, rand.nextDouble(-135, -45), gameLayer);
    }

    public void createReflectors() {
        reflectors = Arrays.asList(new Reflector(250, 150),
        new Reflector(100, 250),
        new Reflector(250, 350),
        new Reflector(400, 250),
        new Reflector(25, 80),
        new Reflector(475, 80),
        new Reflector(250, 30, 40));
        gameLayer.add(reflectors.get(0).getGraphics());
        gameLayer.add(reflectors.get(1).getGraphics());
        gameLayer.add(reflectors.get(2).getGraphics());
        gameLayer.add(reflectors.get(3).getGraphics());
        gameLayer.add(reflectors.get(4).getGraphics());
        gameLayer.add(reflectors.get(5).getGraphics());
        gameLayer.add(reflectors.get(6).getGraphics());
    }

    public void createWalls() {
        leftFlipper = new Wall(155, 530, 215, 550, Color.BLACK, gameLayer);
        rightFlipper = new Wall(345, 530, 285, 550, Color.BLACK, gameLayer);
        walls = Arrays.asList(new Wall(85, 455, 155, 530, Color.GREEN, gameLayer),
        new Wall(80, 400, 80, 450, Color.GREEN, gameLayer), 
        new Wall(415, 455, 345, 530, Color.GREEN, gameLayer),
        new Wall(420, 400, 420, 450, Color.GREEN, gameLayer), 
        new Wall(0, 450, 200, 630, Color.GREEN, gameLayer),
        new Wall(500, 450, 300, 630, Color.GREEN, gameLayer),
        new Wall(190, 5, 310, 5, Color.CYAN, gameLayer),
        new Wall(190, 55, 310, 55, Color.CYAN, gameLayer), 
        new Wall(115, 35, 190, 5, Color.CYAN, gameLayer),
        new Wall(115, 85, 190, 55, Color.CYAN, gameLayer),
        new Wall(310, 5, 385, 35, Color.CYAN, gameLayer),
        new Wall(310, 55, 385, 85, Color.CYAN, gameLayer),
        new Wall(0, 0, 500, 0, Color.CYAN, gameLayer),
        new Wall(500, 0, 500, 650, Color.CYAN, gameLayer),
        new Wall(0, 650, 500, 650, Color.CYAN, gameLayer),
        new Wall(0, 0, 0, 650, Color.CYAN, gameLayer),
        leftFlipper, rightFlipper);
    }

    public void createPoints() {
        points = new Points(canvas);
    }

    /**
     * Moves the ball and handles ball to wall collision logic
     * @param dt
     */
    public void ballWallInteractions(double dt) {
        ball.moveBall(dt, isPaused);
        for (Wall wall : walls) {
            if (ball.checkWallCollision(wall)) {
                points.addPoints(1);
            }
        }
    }
    
    /**
     * Handles ball to reflector collision logic
     */
    public void ballReflectorInteractions() {
        for (Reflector reflector : reflectors) {
            reflector.setColor(Color.CYAN);
            if (ball.checkCircleCollision(ball, reflector)) {
                ball.bounceOffCircleWithCenter(reflector.getCenter().getX(), reflector.getCenter().getY());
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

    public void handleFlippers(double dt) {
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
    }

    /**
     * Sets bools when right and left arrow keys are pressed
     */
    public void flipperFlipLambdas() {
        Set<Key> keys = canvas.getKeysPressed();
        if (keys.contains(Key.LEFT_ARROW)) {
            leftKeyIsPressed = true;
        }
        if (keys.contains(Key.RIGHT_ARROW)) {
            rightKeyIsPressed = true;
        }
    }

    /**
     * Sets bools when right and left arrow keys are pressed
     */
    public void unpressedLambdas() {
        canvas.onKeyUp(event -> {
            if (event.getKey().equals(Key.LEFT_ARROW)) {
                leftKeyIsPressed = false;
            }
            if (event.getKey().equals(Key.RIGHT_ARROW)) {
                rightKeyIsPressed = false;
            }
        });
    }
   
    /**
     * Handles lose logic for each round
     */
    public void belowFlippers() {
        if (ball.getCenter().getY() > 555) {
            lives --;
            if (lives == 0) {
                gameOver();
            } else {
                isPaused = true;
                initializeRound();
            }
        }
    }

    /**
     * Starts each round
     */
    public void initializeRound() {
        ball.removeBall(gameLayer);
        createBall();
        GraphicsText livesLeft = new GraphicsText("    You have " + lives
        + " lives left. \n \n \n Click to start next round");
        livesLeft.setPosition(55, 210);
        livesLeft.setFont(FontStyle.BOLD, 30);
        canvas.add(livesLeft);
        canvas.draw();
        canvas.pause(3000);
        canvas.remove(livesLeft);
        canvas.onKeyDown(event -> { 
            isPaused = false;
        });
    }

    /**
     * Handles game ending logic
     */
    public void gameOver() {
        GraphicsText lose = new GraphicsText("No more lives left");
        GraphicsText pointTotal = new GraphicsText("You ended with \n" + points.getPoints() + " points!");
        lose.setPosition(70, 220);
        lose.setFont(FontStyle.BOLD, 40);
        pointTotal.setPosition(128, 260);
        pointTotal.setFont(FontStyle.BOLD, 30);
        canvas.add(lose);
        canvas.add(pointTotal);
        canvas.draw();
        canvas.pause(10000);
        canvas.closeWindow();
    }

    public static void main(String[] args) {
        new Pinball();
    }
}
