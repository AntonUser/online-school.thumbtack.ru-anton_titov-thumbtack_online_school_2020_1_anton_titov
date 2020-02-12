package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Triangle {
    private int x1, y1, x2, y2, x3, y3;

    public Triangle(Point2D point1, Point2D point2, Point2D point3) {
        x1 = point1.getX();
        y1 = point1.getY();
        x2 = point2.getX();
        y2 = point2.getY();
        x3 = point3.getX();
        y3 = point3.getY();
    }

    public Point2D getPoint1() {
        return new Point2D(x1, y1);
    }

    public Point2D getPoint2() {
        return new Point2D(x2, y2);
    }

    public Point2D getPoint3() {
        return new Point2D(x3, y3);
    }

    public void setPoint1(Point2D point) {
        x1 = point.getX();
        y1 = point.getY();
    }

    public void setPoint2(Point2D point) {
        x2 = point.getX();
        y2 = point.getY();
    }

    public void setPoint3(Point2D point) {
        x3 = point.getX();
        y3 = point.getY();
    }

    private double getLength(int n1, int n2) {
        return (n2 - n1) * (n2 - n1);
    }

    public double getSide12() {
        return Math.sqrt(getLength(x1, x2) + getLength(y1, y2));
    }

    public double getSide13() {
        return Math.sqrt(getLength(x1, x3) + getLength(y1, y3));
    }

    public double getSide23() {
        return Math.sqrt(getLength(x2, x3) + getLength(y2, y3));
    }

    public void moveRel(int dx, int dy) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
        x3 += dx;
        y3 += dy;
    }

    public double getArea() {
        return Math.sqrt((getPerimeter() / 2) * ((getPerimeter() / 2) - getSide12()) * ((getPerimeter() / 2) - getSide13()) * ((getPerimeter() / 2) - getSide23()));
    }

    public double getPerimeter() {
        return getSide12() + getSide13() + getSide23();
    }

    public boolean isInside(int x, int y) {
        int area1 = (x1 - x) * (y2 - y1) - (x2 - x1) * (y1 - y);
        int area2 = (x2 - x) * (y3 - y2) - (x3 - x2) * (y2 - y);
        int area3 = (x3 - x) * (y1 - y3) - (x1 - x3) * (y3 - y);
        return area1 <= 0 && area2 <= 0 && area3 <= 0 ||
                area1 >= 0 && area2 >= 0 && area3 >= 0;
    }

    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return x1 == triangle.x1 &&
                y1 == triangle.y1 &&
                x2 == triangle.x2 &&
                y2 == triangle.y2 &&
                x3 == triangle.x3 &&
                y3 == triangle.y3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, y1, x2, y2, x3, y3);
    }
}
