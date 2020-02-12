package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Rectangle {
    private int x1, x2, y1, y2;

    public Rectangle(Point2D leftTop, Point2D rightBottom) {
        x1 = leftTop.getX();
        y1 = leftTop.getY();
        x2 = rightBottom.getX();
        y2 = rightBottom.getY();
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom) {
        this(new Point2D(xLeft, yTop), new Point2D(xRight, yBottom));
    }

    public Rectangle(int length, int width) {
        this(new Point2D(0, -width), new Point2D(length, 0));
    }

    public Rectangle() {
        this(1, 1);
    }

    public Point2D getTopLeft() {
        return new Point2D(x1, y1);
    }

    public Point2D getBottomRight() {
        return new Point2D(x2, y2);
    }

    public void setTopLeft(Point2D topLeft) {
        x1 = topLeft.getX();
        y1 = topLeft.getY();
    }

    public void setBottomRight(Point2D bottomRight) {
        x2 = bottomRight.getX();
        y2 = bottomRight.getY();
    }

    public int getLength() {
        return Math.abs(x2 - x1);
    }

    public int getWidth() {
        return Math.abs(y2 - y1);
    }

    public void moveRel(int dx, int dy) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
    }

    public void enlarge(int nx, int ny) {
        x2 = x1 + getLength() * nx;
        y2 = y1 + getWidth() * ny;
    }

    public double getArea() {
        return getWidth() * getLength();
    }

    public double getPerimeter() {
        return getWidth() * 2 + getLength() * 2;
    }

    public boolean isInside(int x, int y) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
    }

    public boolean isIntersects(Rectangle rectangle) {
        return getTopLeft().getX() < rectangle.getBottomRight().getX() &&
                getBottomRight().getX() > rectangle.getTopLeft().getX() &&
                getTopLeft().getY() < rectangle.getBottomRight().getY() &&
                getBottomRight().getY() > rectangle.getTopLeft().getY();
    }

    public boolean isInside(Rectangle rectangle) {
        return rectangle.getBottomRight().getX() < getBottomRight().getX() &&
                rectangle.getBottomRight().getY() < getBottomRight().getY() &&
                rectangle.getTopLeft().getX() > getTopLeft().getX() &&
                rectangle.getTopLeft().getY() > getTopLeft().getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return x1 == rectangle.x1 &&
                x2 == rectangle.x2 &&
                y1 == rectangle.y1 &&
                y2 == rectangle.y2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, x2, y1, y2);
    }
}