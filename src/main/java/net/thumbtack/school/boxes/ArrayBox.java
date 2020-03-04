package net.thumbtack.school.boxes;

//REVU: следует удалить ненужный импорт
import net.thumbtack.school.figures.v3.Figure;

//REVU: параметр T может быть любого типа?
// мне кажется, нужны какие-то ограничения на параметр
public class ArrayBox<T> {
    //REVU: ле класса должно быть private
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

    //REVU: тут ты используешь ArrayBox - шаблонный класс, но не указываешь для него тип
    // правильно будет: ArrayBox<тут что-то про тип>
    // в примерах в лекции это точно есть
    public boolean isSameSize(ArrayBox array) {
        return this.array.length == array.getContent().length;
    }


}
