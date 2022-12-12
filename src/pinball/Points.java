package pinball;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.Rectangle;

public class Points {
    private double points;
    private Rectangle pointLine;
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

    public void addPoints(double num) {
        points += num;
        pointCounter.setText("Points: " + points);
    }
}
