package net.thumbtack.school.boxes;

import net.thumbtack.school.area.HasArea;
import net.thumbtack.school.figures.v3.Figure;

public class PairBox<T extends Figure, K extends Figure> implements HasArea {
    private T firstContent;
    private K secondContent;

    public PairBox(T firstContent, K secondContent) {
        setContentFirst(firstContent);
        setContentSecond(secondContent);
    }

    public T getContentFirst() {
        return firstContent;
    }

    public void setContentFirst(T firstContent) {
        this.firstContent = firstContent;
    }

    public K getContentSecond() {
        return secondContent;
    }

    public void setContentSecond(K secondContent) {
        this.secondContent = secondContent;
    }

    @Override
    public double getArea() {
        return this.firstContent.getArea() + this.secondContent.getArea();
    }

    public boolean isAreaEqual(PairBox obj) {
        return this.getArea() == obj.getArea();
    }

    public static boolean isAreaEqual(PairBox obj1, PairBox obj2) {
        return obj1.getArea() == obj2.getArea();
    }
}
