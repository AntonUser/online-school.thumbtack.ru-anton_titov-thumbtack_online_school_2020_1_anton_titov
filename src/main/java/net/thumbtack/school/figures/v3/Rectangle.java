package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.figures.v1.Point2D;

import java.util.Objects;

public class Rectangle extends Figure {
    private int x1, x2, y1, y2;

    public Rectangle(Point2D leftTop, Point2D rightBottom, Color color) throws ColorException {
        super(color);
        setRectangle(leftTop, rightBottom);
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom, Color color) throws ColorException {
        this(new Point2D(xLeft, yTop), new Point2D(xRight, yBottom), color);
    }

    public Rectangle(int length, int width, Color color) throws ColorException {
        this(new Point2D(0, -width), new Point2D(length, 0), color);
    }

    public Rectangle(Color color) throws ColorException {
        this(1, 1, color);
    }

    //REVU: используй уже существующий конструктор внутри этого
    public Rectangle(Point2D leftTop, Point2D rightBottom, String colorString) throws ColorException {
        super(colorString);
        setRectangle(leftTop, rightBottom);
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom, String colorString) throws ColorException {
        this(new Point2D(xLeft, yTop), new Point2D(xRight, yBottom), colorString);
    }

    public Rectangle(int length, int width, String colorString) throws ColorException {
        this(new Point2D(0, -width), new Point2D(length, 0), colorString);
    }

    public Rectangle(String colorString) throws ColorException {
        this(1, 1, colorString);
    }

    private void setRectangle(Point2D leftTop, Point2D rightBottom) {
        setTopLeft(leftTop);
        setBottomRight(rightBottom);
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

    @Override
    public double getArea() {
        return getWidth() * getLength();
    }

    public double getPerimeter() {
        return getWidth() * 2 + getLength() * 2;
    }

    public boolean isInside(int x, int y) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    //REVU: после реализации этого метода в Figure, его можно будет отсюда удалить
    @Override
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
        if (!super.equals(o)) return false;
        Rectangle rectangle = (Rectangle) o;
        return x1 == rectangle.x1 &&
                x2 == rectangle.x2 &&
                y1 == rectangle.y1 &&
                y2 == rectangle.y2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x1, x2, y1, y2);
    }
}