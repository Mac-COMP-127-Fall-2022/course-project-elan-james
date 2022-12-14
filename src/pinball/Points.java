package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;

// Elan Levin and James McCarthy
/**
 * Creates the points and point counter. Contains methods to get the point total and add points.
 */
public class Points {
    private double points;
    private GraphicsText pointCounter;

    public Points(CanvasWindow canvas) {
        pointCounter = new GraphicsText();
        pointCounter.setPosition(10, 20);
        pointCounter.setText("Points: 0");
        canvas.add(pointCounter);
    }

    public double getPoints() {
        return points;
    }

    /**
     * Gives player points and updates text
     */
    public void addPoints(double num) {
        points += num;
        pointCounter.setText("Points: " + points);
    }
}
