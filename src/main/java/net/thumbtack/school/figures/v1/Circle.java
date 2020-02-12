package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Circle {
    private int xCenter, yCenter, radius;

    public Circle(Point2D center, int radius) {
        xCenter = center.getX();
        yCenter = center.getY();
        this.radius = radius;
    }

    public Circle(int xCenter, int yCenter, int radius) {
        this(new Point2D(xCenter, yCenter), radius);
    }

    public Circle(int radius) {
        this(new Point2D(0, 0), radius);
    }

    public Circle() {
        this(1);
    }

    public Point2D getCenter() {
        return new Point2D(xCenter, yCenter);
    }

    public int getRadius() {
        return radius;
    }

    public void setCenter(Point2D center) {
        xCenter = center.getX();
        yCenter = center.getY();
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void moveRel(int dx, int dy) {
        xCenter += dx;
        yCenter += dy;
    }

    public void enlarge(int n) {
        radius *= n;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public boolean isInside(int x, int y) {
        x = x - xCenter;
        y = y - yCenter;
        return radius * radius >= x * x + y * y;
    }

    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle)) return false;
        Circle circle = (Circle) o;
        return xCenter == circle.xCenter &&
                yCenter == circle.yCenter &&
                getRadius() == circle.getRadius();
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCenter, yCenter, getRadius());
    }

}
