package net.thumbtack.school.figures.v1;

public class CircleFactory {
    private static int counter;

    public static Circle createCircle(Point2D center, int radius) {
        Circle circle = new Circle(center, radius);
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
