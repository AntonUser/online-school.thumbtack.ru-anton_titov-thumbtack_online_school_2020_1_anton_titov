package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Triangle {
    private int x1, y1, x2, y2, x3, y3;
    public Triangle(Point2D point1, Point2D point2, Point2D point3){
        x1 = point1.getX();
        y1 = point1.getY();
        x2 = point2.getX();
        y2 = point2.getY();
        x3 = point3.getX();
        y3 = point3.getY();
    }
    //Создает Triangle по координатам трех точек.

    public Point2D getPoint1(){
        Point2D point1 = new Point2D(x1, y1);
        return point1;
    }
    //Возвращает точку 1 треугольника.

    public Point2D getPoint2(){
    Point2D point2 = new Point2D(x2, y2);
    return point2;
    }
    //Возвращает точку 2 треугольника.

    public Point2D getPoint3(){
    Point2D point3 = new Point2D(x3, y3);
    return point3;
    }
    //Возвращает точку 3 треугольника.

    public void setPoint1(Point2D point){
        x1 = point.getX();
        y1 = point.getY();
    }
    //Устанавливает точку 1 треугольника.

    public void setPoint2(Point2D point){
        x2 = point.getX();
        y2 = point.getY();
    }
    //Устанавливает точку 2 треугольника.

    public void setPoint3(Point2D point){
        x3 = point.getX();
        y3 = point.getY();
    }
    //Устанавливает точку 3 треугольника.

    public double getSide12(){
        return Math.sqrt(Math.pow((x2 - x1),2)+Math.pow((y2 - y1),2));
    }
   // Возвращает длину стороны 1-2.

    public double getSide13(){
        return Math.sqrt(Math.pow((x3 - x1),2)+Math.pow((y3 - y1),2));
    }
    //Возвращает длину стороны 1-3.

    public double getSide23(){
        return Math.sqrt(Math.pow((x2 - x3),2)+Math.pow((y2 - y3),2));
    }
    //Возвращает длину стороны 2-3.

    public void moveRel(int dx, int dy){
        x1+=dx;     y1+=dy;
        x2+=dx;     y2+=dy;
        x3+=dx;     y3+=dy;
    }
    //Передвигает Triangle на (dx, dy).

    public double getArea(){
        return Math.sqrt((getPerimeter()/2)*((getPerimeter()/2)-getSide12())*((getPerimeter()/2)-getSide13())*((getPerimeter()/2)-getSide23()));
    }
    //Возвращает площадь треугольника.

    public double getPerimeter(){
        return getSide12()+getSide13()+getSide23();
    }
   // Возвращает периметр треугольника.

    public boolean isInside(int x, int y){
        if((x1 - x) * (y2 - y1) - (x2 - x1) * (y1 - y) <= 0 &&
           (x2 - x) * (y3 - y2) - (x3 - x2) * (y2 - y) <= 0 &&
           (x3 - x) * (y1 - y3) - (x1 - x3) * (y3 - y) <= 0 ||
                (x1 - x) * (y2 - y1) - (x2 - x1) * (y1 - y) >= 0 &&
                (x2 - x) * (y3 - y2) - (x3 - x2) * (y2 - y) >= 0 &&
                (x3 - x) * (y1 - y3) - (x1 - x3) * (y3 - y) >= 0)return true;
        else return false;
    }
    //Определяет, лежит ли точка (x, y) внутри Triangle. Если точка лежит на стороне треугольника, считается, что она лежит внутри.

    public boolean isInside(Point2D point){
        if((x1 - point.getX()) * (y2 - y1) - (x2 - x1) * (y1 - point.getY()) <= 0 &&
           (x2 - point.getX()) * (y3 - y2) - (x3 - x2) * (y2 - point.getY()) <= 0 &&
           (x3 - point.getX()) * (y1 - y3) - (x1 - x3) * (y3 - point.getY()) <= 0 ||
                        (x1 - point.getX()) * (y2 - y1) - (x2 - x1) * (y1 - point.getY()) >= 0 &&
                        (x2 - point.getX()) * (y3 - y2) - (x3 - x2) * (y2 - point.getY()) >= 0 &&
                        (x3 - point.getX()) * (y1 - y3) - (x1 - x3) * (y3 - point.getY()) >= 0)return true;
        else return false;
    }
    //Определяет, лежит ли точка point внутри Triangle. Если точка лежит на стороне треугольника, считается, что она лежит внутри.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triangle)) return false;
        Triangle triangle = (Triangle) o;
        return x1 == triangle.x1 &&
                y1 == triangle.y1 &&
                x2 == triangle.x2 &&
                y2 == triangle.y2 &&
                x3 == triangle.x3 &&
                y3 == triangle.y3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, y1, x2, y2, x3, y3);
    }

    //методы equals и hashCode.
    //Не пишите эти методы сами, используйте средства IDEA. Обращаем внимание на то, что
}
