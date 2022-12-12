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

    public void createPointLine(double x1, double y1, double x2, double y2, GraphicsGroup rectangleLayer) {
        Line pointLine = new Line(x1, y1, x2, y2);
        pointLine.setStrokeWidth(10);
        rectangleLayer.add(pointLine);
    }

    public void createPointsLine(double x, double y, double angle, GraphicsGroup rectangleLayer) {
        Rectangle pointLine = new Rectangle(x, y, 15, 8);
        pointLine.rotateBy(angle);
        rectangleLayer.add(pointLine);
    }

    public double getPoints() {
        return points;
    }

    public void addPoints(double num) {
        points += num;
        pointCounter.setText("Points: " + points);
    }

    public GraphicsObject getGraphics() {
        return pointLine;
    }
}
