package net.thumbtack.school.boxes;

import net.thumbtack.school.area.HasArea;
import net.thumbtack.school.figures.v3.Figure;

public class Box<T extends Figure> implements HasArea {
    private T obj;

    public Box(T obj) {
        setContent(obj);
    }

    public T getContent() {
        return obj;
    }

    public void setContent(T obj) {
        this.obj = obj;
    }

    @Override
    public double getArea() {
        return obj.getArea();
    }

    public boolean isAreaEqual(Box obj) {
        return this.getArea() == obj.getArea();
    }

    public static boolean isAreaEqual(Box obj1, Box obj2) {
        return obj1.getArea() == obj2.getArea();
    }

}
