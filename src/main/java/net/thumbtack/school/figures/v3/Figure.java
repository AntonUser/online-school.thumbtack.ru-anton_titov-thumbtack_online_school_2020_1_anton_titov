package net.thumbtack.school.figures.v3;

import net.thumbtack.school.area.HasArea;
import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorErrorCode;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.colors.Colored;
import net.thumbtack.school.figures.v1.Point2D;

import java.util.Objects;

public abstract class Figure implements Colored, HasArea {
    private Color color;

    public Figure(Color color) throws ColorException {
        setColor(color);
    }

    public Figure(String colorString) throws ColorException {
        setColor(colorString);
    }

    public abstract double getArea();

    abstract double getPerimeter();

    abstract void moveRel(int dx, int dy);

    abstract boolean isInside(int x, int y);

    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
    }

    @Override
    public void setColor(Color color) throws ColorException {
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    @Override
    public Color getColor() {
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

