package net.thumbtack.school.colors;


public interface Colored {

    void setColor(Color color) throws ColorException;

    Color getColor();

    default void setColor(String colorString) throws ColorException {
        setColor(Color.colorFromString(colorString));
    }
}
