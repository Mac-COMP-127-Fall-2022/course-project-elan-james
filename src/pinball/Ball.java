package pinball;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;

public class Ball {
    private Ellipse ball;
    public static final double GRAVITY = -9.8;
    private double centerX, centerY, dx, dy;

    public Ball(double centerX, double centerY, CanvasWindow canvas, double initialSpeed, double initialAngle) {
        this.centerX = centerX;
        this.centerY = centerY;
        ball = new Ellipse(centerX, centerY, 15, 15);
        ball.setFillColor(Color.BLACK);
        canvas.add(ball);
        double initialAngleRadians = Math.toRadians(initialAngle);
        dx = initialSpeed * Math.cos(initialAngleRadians);
        dy = initialSpeed * Math.sin(initialAngleRadians) * -1;
    }

    public boolean updatePosition(double dt, double maxX, double maxY, CanvasWindow canvas) {
        centerX += dx * dt;
        centerY += dy * dt;
        if (checkCollision(centerX, centerY, canvas)) {
            dy = -dy;
            centerY += dy * dt;
            ball.setPosition(centerX, centerY);
            dy -= GRAVITY * dt;
            return true;
        }
        if ((centerX > 0 && centerX < maxX) && (centerY > 0 && centerY < maxY)) {
            ball.setPosition(centerX, centerY);
            dy -= GRAVITY * dt;
            return true;
        } else if (centerX <= 0 || centerX >= maxX) {
            dx = -dx;
            centerX += dx * dt;
            ball.setPosition(centerX, centerY);
            return true;
        } else if (centerY <= 0 || centerY >= maxY) {
            dy = -dy;
            centerY += dy * dt;
            ball.setPosition(centerX, centerY);
            return true;
        }
        return false;
    }

    public boolean checkCollision(double x, double y, CanvasWindow canvas) {
        GraphicsObject point1 = canvas.getElementAt(x + (ball.getWidth()/2), y - 4);
        GraphicsObject point2 = canvas.getElementAt(x - 4, y + (ball.getWidth()/2));
        GraphicsObject point3 = canvas.getElementAt(x + (ball.getWidth()/2), y + ball.getWidth() + 4);
        GraphicsObject point4 = canvas.getElementAt(x + ball.getWidth() + 4, y + (ball.getWidth()/2));
        if (point1 != null || point2 != null || point3 != null || point4 != null) {
            return true;
        }
        return false;
    }
}
