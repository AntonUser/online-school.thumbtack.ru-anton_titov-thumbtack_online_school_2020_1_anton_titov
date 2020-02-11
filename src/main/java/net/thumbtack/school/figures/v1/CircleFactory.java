package net.thumbtack.school.figures.v1;

//REVU:
// 1) убери, пожалуйста, все комментарии из кода, иначе сложно читать
// 2) также проверь форматирование, у IDEA сть автоформатирование - Ctrl + Alt + L
public class CircleFactory {
    //REVU: все поля класса должны быть private
    public static int counter;
    public static Circle createCircle(Point2D center, int radius){
    Circle circle = new Circle(center, radius);
    counter++;
    return circle;
    }
    //Создает Circle по координатам центра и значению радиуса.

    public static int getCircleCount(){
        return counter;
    }
    //Возвращает количество Circle, созданных с помощью метода createCircle.

    public static void reset(){
        counter = 0;
    }
    //Устанавливает количество Circle, созданных с помощью метода createCircle, равным 0 (иными словами, реинициализирует фабрику).

}
