package pinball;

import java.awt.Color;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

public class Ball {
    private Ellipse ball;
    public static final double GRAVITY = -100;
    private double x, y, dx, dy;

    public Ball(double x, double y, double initialSpeed, double initialAngle, GraphicsGroup rectangleLayer) {
        this.x = x;
        this.y = y;
        ball = new Ellipse(x, y, 15, 15);
        ball.setFillColor(Color.BLACK);
        rectangleLayer.add(ball);
        double initialAngleRadians = Math.toRadians(initialAngle);
        dx = initialSpeed * Math.cos(initialAngleRadians);
        dy = initialSpeed * Math.sin(initialAngleRadians) * -1;
    }

    public double getX() {
        return ball.getX();
    }

    public double getY() {
        return ball.getY();
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

    public double getWidth() {
        return ball.getWidth();
    }

    public boolean moveBall(double dt, double maxX, double maxY) {
        x += dx * dt;
        y += dy * dt;
        if ((x > 0 && x < maxX) && (y > 0 && y < maxY)) {
            ball.setPosition(x, y);
            dy -= GRAVITY * dt;
            return true;
        }
        return false;
    }

    public boolean checkCanvasWallCollision(double dt, double maxX, double maxY) {
        if (x <= 0 || x >= maxX) {
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

    public boolean checkCollision(GraphicsGroup rectangleLayer) {
        GraphicsObject point1 = rectangleLayer.getElementAt(x + (ball.getWidth()/2), y - 4);
        GraphicsObject point2 = rectangleLayer.getElementAt(x - 4, y + (ball.getWidth()/2));
        GraphicsObject point3 = rectangleLayer.getElementAt(x + (ball.getWidth()/2), y + ball.getWidth() + 4);
        GraphicsObject point4 = rectangleLayer.getElementAt(x + ball.getWidth() + 4, y + (ball.getWidth()/2));
        if (point1 != null || point2 != null || point3 != null || point4 != null) {
            return true;
        }
        return false;
    }

    public boolean bounceOffReflector(Reflector reflector) {
        if (checkCircleCollision(this, reflector)) {
            bounceOffCircleWithCenter(reflector.getCenter().getX(), reflector.getCenter().getY());
            return true;
        } else {
            return false;
        }
    }

    public boolean checkCircleCollision(Ball ball, Reflector reflector) {
        Point ballCenter = ball.getCenter();
        Point reflectorCenter = reflector.getCenter();
        double xDif = ballCenter.getX() - reflectorCenter.getX();
        double yDif = ballCenter.getY() - reflectorCenter.getY();
        double distanceSquared = xDif * xDif + yDif * yDif;
        boolean collision = distanceSquared <= (ball.getWidth()/2 + reflector.getRadius()) * (ball.getWidth()/2 + reflector.getRadius());
        return collision;
    }

    public void bounceOffCircleWithCenter(double centerX, double centerY) {
        double diffBetweenX = ball.getCenter().getX() - centerX;
        double diffBetweenY = ball.getCenter().getY() - centerY;
        Point ballVelocity = new Point(dx, dy);
        Point centerDiff = new Point(diffBetweenX, diffBetweenY);
        centerDiff = centerDiff.scale(1 / centerDiff.magnitude());
        double dotProduct = centerDiff.getX() * ballVelocity.getX() + centerDiff.getY() * ballVelocity.getY();
        Point newVelocity = ballVelocity.subtract(centerDiff.scale(dotProduct * 2));
        dx = newVelocity.getX();
        dy = newVelocity.getY();
    }

    public boolean checkWallCollision(Wall wall) {
        Point normalToWall =
            wall.getCenter1().subtract(wall.getCenter2())
                .rotate(Math.toRadians(90))
                .add(ball.getCenter());
        Point ballWallIntersection =
            getLineIntersection(
                ball.getCenter(), normalToWall,
                wall.getCenter1(), wall.getCenter2());

        if (checkPointWithinLine(ballWallIntersection, wall.getCenter1(), wall.getCenter2())
                && ball.getWidth() / 2 + wall.getWidth() / 2 > ball.getCenter().distance(ballWallIntersection)) {
            bounceOffCircleWithCenter(ballWallIntersection.getX(), ballWallIntersection.getY());
            if (wall.getEndpoint2() != new Point(wall.getX2(), wall.getY2())) {
                this.x += 3;
            } 
            return true;
        } else {
            bounceOffReflector(
                new Reflector(wall.getX1(), wall.getY1(), wall.getWidth()));
            bounceOffReflector(
                new Reflector(wall.getX2(), wall.getY2(), wall.getWidth()));
        }
        return false;
    }

    public Point getLineIntersection(Point line1_1, Point line1_2, Point line2_1, Point line2_2) {
        double line1A = line1_2.getY() - line1_1.getY();
        double line1B = line1_1.getX() - line1_2.getX();
        double line1C = line1A * line1_1.getX() + line1B * line1_1.getY();

        double line2A = line2_2.getY() - line2_1.getY();
        double line2B = line2_1.getX() - line2_2.getX();
        double line2C = line2A * line2_1.getX() + line2B * line2_1.getY();

        double det = line1A * line2B - line2A * line1B;
        double x = (line2B * line1C - line1B * line2C) / det;
        double y = (line1A * line2C - line2A * line1C) / det;
        return new Point(x, y);
    }

    public boolean checkPointWithinLine(Point point, Point startPoint, Point endPoint) {
        double minX = Math.min(startPoint.getX(), endPoint.getX());
        double minY = Math.min(startPoint.getY(), endPoint.getY());

        double maxX = Math.max(startPoint.getX(), endPoint.getX());
        double maxY = Math.max(startPoint.getY(), endPoint.getY());

        return point.getX() >= minX && point.getX() <= maxX && point.getY() >= minY && point.getY() <= maxY;
    }

    public boolean updateCollisionPosition(double dt) {
        dy = -dy;
        y += dy * dt;
        ball.setPosition(x, y);
        dy -= GRAVITY * dt;
        return true;
    }

    public void resetBall(GraphicsGroup rectangleLayer) {
        rectangleLayer.remove(ball);
    }
}
