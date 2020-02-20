package net.thumbtack.school.figures.v2;

import net.thumbtack.school.figures.v1.Point2D;

public class CircleFactory {
    private static int counter;

    public static Circle createCircle(Point2D center, int radius, int color) {
        Circle circle = new Circle(center, radius, color);
        counter++;
        return circle;
    }

    public static int getCircleCount() {
        return counter;
    }

    public static void reset() {
        counter = 0;
    }

}
