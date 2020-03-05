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

    public <V extends Figure, W extends Figure> boolean isAreaEqual(PairBox<V, W> obj) {
        return this.getArea() == obj.getArea();
    }

    public static <V extends Figure, W extends Figure, A extends Figure, B extends Figure>
    boolean isAreaEqual(PairBox<V, W> obj1, PairBox<A, B> obj2) {
        return obj1.getArea() == obj2.getArea();
    }
}
