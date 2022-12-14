package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
/**
 * basically acts as a scoreboard
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

    /**
     * getter
     */
    public double getPoints() {
        return points;
    }

    /**
     * gives player points and updates text
     */
    public void addPoints(double num) {
        points += num;
        pointCounter.setText("Points: " + points);
    }
}
