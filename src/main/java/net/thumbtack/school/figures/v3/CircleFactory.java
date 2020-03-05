package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.figures.v1.Point2D;

public class CircleFactory {
    private static int counter;

    public static Circle createCircle(Point2D center, int radius, Color color) throws ColorException {
        Circle circle = new Circle(center, radius, color);
        counter++;
        return circle;
    }

    public static Circle createCircle(Point2D center, int radius, String colorString) throws ColorException {
        return createCircle(center, radius, Color.valueOf(colorString));
    }

    public static int getCircleCount() {
        return counter;
    }

    public static void reset() {
        counter = 0;
    }

}
