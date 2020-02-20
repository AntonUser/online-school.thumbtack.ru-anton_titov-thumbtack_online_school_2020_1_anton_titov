package net.thumbtack.school.figures.v2;

import net.thumbtack.school.figures.v1.Point2D;

import java.util.Objects;

public class Circle extends Figure {
    private int xCenter, yCenter, radius;

    public Circle(Point2D center, int radius, int color) {
        super(color);
        xCenter = center.getX();
        yCenter = center.getY();
        this.radius = radius;
    }

    public Circle(int xCenter, int yCenter, int radius, int color) {
        this(new Point2D(xCenter, yCenter), radius, color);
    }

    public Circle(int radius, int color) {
        this(new Point2D(0, 0), radius, color);
    }

    public Circle(int color) {
        this(1, color);
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

    @Override
    public void moveRel(int dx, int dy) {
        xCenter += dx;
        yCenter += dy;
    }

    public void enlarge(int n) {
        radius *= n;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
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
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Circle circle = (Circle) o;
        return xCenter == circle.xCenter &&
                yCenter == circle.yCenter &&
                radius == circle.radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), xCenter, yCenter, radius);
    }
}
