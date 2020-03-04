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

    //REVU: тут ты используешь Box - шаблонный класс, но не указываешь для него тип
    // правильно будет: Box<тут что-то про тип>
    // в примерах в лекции это точно есть
    public boolean isAreaEqual(Box obj) {
        return this.getArea() == obj.getArea();
    }

    //REVU: то же про Box
    public static boolean isAreaEqual(Box obj1, Box obj2) {
        return obj1.getArea() == obj2.getArea();
    }

}
