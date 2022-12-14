package pinball;

import java.awt.Color;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public class Ball {
    private Ellipse ball;
    public static final double GRAVITY = -100;
    private double x, y, dx, dy;

    public Ball(double x, double y, double initialSpeed, double initialAngle, GraphicsGroup gameLayer) {
        this.x = x;
        this.y = y;
        ball = new Ellipse(x, y, 15, 15);
        ball.setFillColor(Color.BLACK);
        gameLayer.add(ball);
        double initialAngleRadians = Math.toRadians(initialAngle);
        dx = initialSpeed * Math.cos(initialAngleRadians);
        dy = initialSpeed * Math.sin(initialAngleRadians) * -1;
    }

    public double getCenterY() {
        return ball.getCenter().getY();
    }

    public Point getCenter() {
        return ball.getCenter();
    }

    private double getWidth() {
        return ball.getWidth();
    }

    /**
     * Moves the ball around according to it's x and y velocity 
     * @param dt time
     * @param isPaused stops moving ball while game is paused
     */
    public void moveBall(double dt, boolean isPaused) {
        if (!isPaused) {
            x += dx * dt;
            y += dy * dt;
            ball.setPosition(x, y);
            dy -= GRAVITY * dt;
        }
    }
 
    /**
     * tests if a ball is colliding with the reflector 
     * @param reflector
     * @return
     */
    public boolean bounceOffReflector(Reflector reflector) {
        if (checkCircleCollision(this, reflector)) {
            bounceOffCircleWithCenter(reflector.getCenter().getX(), reflector.getCenter().getY());
            return true;
        } else {
            return false;
        }
    }
/**
 * see return:
 * @param ball
 * @param reflector
 * @return true if ball is colliding with reflector
 */
    public boolean checkCircleCollision(Ball ball, Reflector reflector) {
        Point ballCenter = ball.getCenter();
        Point reflectorCenter = reflector.getCenter();
        double xDif = ballCenter.getX() - reflectorCenter.getX();
        double yDif = ballCenter.getY() - reflectorCenter.getY();
        double distanceSquared = xDif * xDif + yDif * yDif;
        boolean collision = distanceSquared <= (ball.getWidth()/2 + reflector.getRadius()) * (ball.getWidth()/2 + reflector.getRadius());
        return collision;
    }

    /**
     * causes this ball to bounce of a circle with center:
     * @param centerX
     * @param centerY
     */
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

    /**
     * Figures out if the ball is touching one of the walls or paddles (lines)
     * also adds some why value if the ball is hitting a flipper to reduce bugs.
     * @param wall
     * @return true if ball radius is inside a wall's stroke width
     */
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
            if (wall.isThisAFlipper()) {
                this.y -= 10;
            } 
            return true;
        }
        return false;
    }

    /**
     * Uses two lines in point form
     * See return:
     * @param line1_1
     * @param line1_2
     * @param line2_1
     * @param line2_2
     * @return returns the point the two lines intersect at
     */
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

    /**
     * Checks to see if given point is within a line. 
     * line is taken in point form.
     * @param point
     * @param startPoint
     * @param endPoint
     * @return true if point is on line
     */
    public boolean checkPointWithinLine(Point point, Point startPoint, Point endPoint) {
        double minX = Math.min(startPoint.getX(), endPoint.getX());
        double minY = Math.min(startPoint.getY(), endPoint.getY());

        double maxX = Math.max(startPoint.getX(), endPoint.getX());
        double maxY = Math.max(startPoint.getY(), endPoint.getY());

        return point.getX() >= minX && point.getX() <= maxX && point.getY() >= minY && point.getY() <= maxY;
    }

    /**
     * removes the ball from the graphics group
     * @param rectangleLayer
     */
    public void removeBall(GraphicsGroup rectangleLayer) {
        rectangleLayer.remove(ball);
    }
}
