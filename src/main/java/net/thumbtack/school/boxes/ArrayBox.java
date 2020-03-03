package net.thumbtack.school.boxes;

import net.thumbtack.school.figures.v3.Figure;

public class ArrayBox<T> {
    T[] array;

    public ArrayBox(T[] array) {
        setContent(array);
    }

    public T[] getContent() {
        return array;
    }

    public void setContent(T[] array) {
        this.array = array;
    }

    public Object getElement(int i) {
        return array[i];
    }

    public void setElement(int i, T elem) {
        this.array[i] = elem;
    }

    public boolean isSameSize(ArrayBox array) {
        return this.array.length == array.getContent().length;
    }


}
