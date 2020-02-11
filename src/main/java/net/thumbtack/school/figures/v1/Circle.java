package net.thumbtack.school.figures.v1;

import java.util.Objects;

//REVU:
// 1) убери, пожалуйста, все комментарии из кода, иначе сложно читать
// 2) также проверь форматирование, у IDEA сть автоформатирование - Ctrl + Alt + L
public class Circle {
    private int xCenter, yCenter, radius;

    public Circle(Point2D center, int radius){
    xCenter = center.getX();
    yCenter = center.getY();
    this.radius = radius;
    }
    //Создает Circle по координатам центра и значению радиуса.

    public Circle(int xCenter, int yCenter, int radius){
       this(new Point2D(xCenter, yCenter), radius);
     }
    //Создает Circle по координатам центра и значению радиуса.

    public Circle(int radius){
        this(new Point2D(0, 0), radius);
     }
    //Создает Circle с центром в точке (0,0) указанного радиуса.

    public Circle(){
        //REVU: удобнее было бы использовать конструктор Circle(int radius) - просто this(1);
        this(new Point2D(0, 0), 1);
     }
    //Создает Circle с центром в точке (0,0) с радиусом 1.

    public Point2D getCenter(){
        //REVU: можно сразу return new Point2D(...)
        Point2D coordcenter = new Point2D(xCenter, yCenter);
        return coordcenter;
    }
    //Возвращает центр Circle.

    public int getRadius(){
        return radius;
    }
   // Возвращает радиус Circle.

    public void setCenter(Point2D center){
        xCenter = center.getX();
        yCenter = center.getY();
    }
    //Устанавливает центр Circle.

    public void setRadius(int radius){
        this.radius = radius;
    }
   // Устанавливает радиус Circle.

    public void moveRel(int dx, int dy){
        xCenter+=dx;
        yCenter+=dy;
    }
   // Передвигает Circle на (dx, dy).

    public void enlarge(int n){
        radius*=n;
    }
  //  Увеличивает радиус Circle в n раз, не изменяя центра.

    public double getArea(){
        //REVU: для степени 2 лучше использовать обычное умножение
        return Math.PI * Math.pow(radius,2);
    }
   // Возвращает площадь круга.

    public double getPerimeter(){
        return 2 * Math.PI * radius;
    }
   // Возвращает периметр окружности (длину окружности).

    public boolean isInside(int x, int y){
        x = x-xCenter;
        y = y-yCenter;
        //REVU: возможно, было бы удобнее сравнивать radius^2 с x^2+y^2
        if(radius -(int)(Math.sqrt(Math.pow(x,2) + Math.pow(y,2))) >= 0) return true;
        //REVU: можно упростить до return условие;
        else return false;
    }
   // Определяет, лежит ли точка (x, y) внутри Circle. Если точка лежит на окружности, считается, что она лежит внутри.

    //REVU: используй метод public boolean isInside(int x, int y) внутри этого, не дублируй код
    public boolean isInside(Point2D point){
    int x = point.getX()-xCenter;
    int y = point.getY()-yCenter;
        if(radius -(int)(Math.sqrt(Math.pow(x,2) + Math.pow(y,2))) >= 0) return true;
        else return false;
    }
   // Определяет, лежит ли точка point внутри Circle. Если точка лежит на окружности, считается, что она лежит внутри.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle)) return false;
        Circle circle = (Circle) o;
        return xCenter == circle.xCenter &&
                yCenter == circle.yCenter &&
                getRadius() == circle.getRadius();
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCenter, yCenter, getRadius());
    }

    // методы equals и hashCode.
   // Не пишите эти методы сами, используйте средства IDEA.

}
