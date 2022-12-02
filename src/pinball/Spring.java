package pinball;

import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Spring {
    Rectangle spring;

    public Spring(GraphicsGroup rectangleLayer) {
        spring = new Rectangle(475, 600, 10, 50);
        spring.setFillColor(Color.BLACK);
        rectangleLayer.add(spring);
    }

    public void updatePosition(double y) {
        spring.setY(y);
    }
}
