package net.thumbtack.school.figures.v2;

import net.thumbtack.school.figures.v1.Point2D;

import java.util.Objects;

abstract class Figure implements Colored {
    private int color;

    public Figure(int color) {
        this.color = color;
    }

    abstract double getArea();

    abstract double getPerimeter();

    abstract void moveRel(int dx, int dy);

    abstract boolean isInside(int x, int y);

    abstract boolean isInside(Point2D point);

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return color == figure.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}

