package net.thumbtack.school.colors;

public interface Colored {
    void setColor(Color color) throws ColorException;

    Color getColor();

    //REVU: этот метод можно сделать default и аписать его реализацию при помощи метода setColor(Color color)
    void setColor(String colorString) throws ColorException;
}
