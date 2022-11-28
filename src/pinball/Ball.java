package pinball;

import java.awt.Color;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Point;

public class Ball {
    private Ellipse ball;
    public static final double GRAVITY = -9.8;
    private double x, y, dx, dy;
    private GraphicsText pointCounter;
    private double points;

    public Ball(double x, double y, CanvasWindow canvas, double initialSpeed, double initialAngle, GraphicsGroup pinballLayer) {
        this.x = x;
        this.y = y;
        ball = new Ellipse(x, y, 15, 15);
        ball.setFillColor(Color.BLACK);
        pinballLayer.add(ball);
        double initialAngleRadians = Math.toRadians(initialAngle);
        dx = initialSpeed * Math.cos(initialAngleRadians);
        dy = initialSpeed * Math.sin(initialAngleRadians) * -1;
        pointCounter = new GraphicsText();
        pointCounter.setPosition(400, 50);
        pointCounter.setText("Points: 0");
        canvas.add(pointCounter);
    }

    public double getCenterX() {
        return x + ball.getWidth()/2;
    }

    public double getCenterY() {
        return y + ball.getHeight()/2;
    }

    public double getRadius() {
        return ball.getWidth()/2;
    }

    public Point getCenter() {
        return ball.getCenter();
    }

    public boolean updatePosition(double dt, double maxX, double maxY, GraphicsGroup pinballLayer) {
        x += dx * dt;
        y += dy * dt;
        if (checkCollision(ball.getX(), ball.getY(), pinballLayer)) {
            dy = -dy;
            y += dy * dt;
            ball.setPosition(x, y);
            dy -= GRAVITY * dt;
            return true;
        }
        if ((x > 0 && x < maxX) && (y > 0 && y < maxY)) {
            ball.setPosition(x, y);
            dy -= GRAVITY * dt;
            return true;
        } else if (x <= 0 || x >= maxX) {
            dx = -dx;
            x += dx * dt;
            ball.setPosition(x, y);
            return true;
        } else if (y <= 0 || y >= maxY) {
            dy = -dy;
            y += dy * dt;
            ball.setPosition(x, y);
            return true;
        }
        return false;
    }

    public boolean checkCollision(double x, double y, GraphicsGroup pinballLayer) {
        GraphicsObject point1 = pinballLayer.getElementAt(x + (ball.getWidth()/2), y - 4);
        GraphicsObject point2 = pinballLayer.getElementAt(x - 4, y + (ball.getWidth()/2));
        GraphicsObject point3 = pinballLayer.getElementAt(x + (ball.getWidth()/2), y + ball.getWidth() + 4);
        GraphicsObject point4 = pinballLayer.getElementAt(x + ball.getWidth() + 4, y + (ball.getWidth()/2));
        if (point1 != null || point2 != null || point3 != null || point4 != null) {
            return true;
        }
        return false;
    }

    public boolean checkCircleCollision(Ellipse ball, Reflector reflector) {
        Point ballCenter = ball.getCenter();
        Point reflectorCenter = reflector.getCenter();
        double xDif = ballCenter.getX() - reflectorCenter.getX();
        double yDif = ballCenter.getY() - reflectorCenter.getY();
        double distanceSquared = xDif * xDif + yDif * yDif;
        boolean collision = distanceSquared <= (ball.getWidth()/2 + reflector.getRadius()) * (ball.getWidth()/2 + reflector.getRadius());
        return collision;
    }

    public boolean updateCircleCollisionPosition(double dt, List<Reflector> reflectors) {
        for (Reflector reflector : reflectors) {
            if (checkCircleCollision(ball, reflector)) {
                dy = -dy;
                y += dy * dt;
                ball.setPosition(x, y);
                dy -= GRAVITY * dt;
                points += 10;
                pointCounter.setText("Points: " + points);
                return true;
            }
        }
        return false;
    }
}
