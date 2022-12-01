package pinball;

import java.util.ArrayList;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;

public class Test {
    private static final int CANVAS_WIDTH = 500;
    private static final int CANVAS_HEIGHT = 650;
    private CanvasWindow canvas;
    private GraphicsGroup pinballLayer;
    private Ball ball;
    private Reflector reflector1;
    private ArrayList<Reflector> reflectors = new ArrayList<>();

    public Test() {
        canvas = new CanvasWindow("Pinball Test", CANVAS_WIDTH, CANVAS_HEIGHT);
        pinballLayer = new GraphicsGroup();
        canvas.add(pinballLayer);
        ball = new Ball(CANVAS_WIDTH/2, 100, canvas, 50, -85, pinballLayer);
        reflector1 = new Reflector(CANVAS_WIDTH/2, CANVAS_HEIGHT/2, pinballLayer);
        reflectors.add(reflector1);
        canvas.animate(() -> {
            updateBall();
        });
    }

    public void updateBall() {
        ball.checkWallCollision(0.1, CANVAS_WIDTH, CANVAS_HEIGHT, pinballLayer);
        // ball.updateCircleCollisionPosition(0.1, reflectors);
    }

    public static void main(String[] args) {
        new Test();
    }
}
