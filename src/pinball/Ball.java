package pinball;

import java.awt.Color;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

public class Ball {
    private Ellipse ball;
    public static final double GRAVITY = -9.8;
    private double x, y, dx, dy;

    public Ball(double x, double y, CanvasWindow canvas, double initialSpeed, double initialAngle) {
        this.x = x;
        this.y = y;
        ball = new Ellipse(x, y, 15, 15);
        ball.setFillColor(Color.BLACK);
        canvas.add(ball);
        double initialAngleRadians = Math.toRadians(initialAngle);
        dx = initialSpeed * Math.cos(initialAngleRadians);
        dy = initialSpeed * Math.sin(initialAngleRadians) * -1;
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

    public boolean updatePosition(double dt, double maxX, double maxY, CanvasWindow canvas) {
        x += dx * dt;
        y += dy * dt;
        if (checkCollision(ball.getX(), ball.getY(), canvas)) {
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

    public boolean checkCircleCollision(Ellipse ball, Reflector reflector) {
        Point ballCenter = ball.getCenter();
        Point reflectorCenter = reflector.getCenter();
        double xDif = ballCenter.getX() - reflectorCenter.getX();
        double yDif = ballCenter.getY() - reflectorCenter.getY();
        double distanceSquared = xDif * xDif + yDif * yDif;
        boolean collision = distanceSquared <= (ball.getWidth()/2 + reflector.getRadius()) * (ball.getWidth()/2 + reflector.getRadius());
        // double xDif = this.getCenterX() - reflector.getCenterX();
        // double yDif = this.getCenterY() - reflector.getCenterY();
        // double distanceSquared = xDif * xDif + yDif * yDif;
        // boolean collision = distanceSquared <= (7.5 + 25) * (7.5 + 25); // radius of ball = 7.5, radius of reflector = 25
        // double closestdistsq = Math.pow(this.getCenterX() - dx, 2) + Math.pow(this.getCenterY() - dy, 2);
        // if (closestdistsq <= Math.pow(7.5 + 25, 2)) {
        //     return true;
        // } else {
        //     return false;
        // }
        return collision;
    }

    public boolean updateCircleCollisionPosition(double dt, List<Reflector> reflectors) {
        for (Reflector reflector : reflectors) {
            if (checkCircleCollision(ball, reflector)) {
                dy = -dy;
                y += dy * dt;
                ball.setPosition(x, y);
                dy -= GRAVITY * dt;
                return true;
            }
        }
        return false;
    }
}
