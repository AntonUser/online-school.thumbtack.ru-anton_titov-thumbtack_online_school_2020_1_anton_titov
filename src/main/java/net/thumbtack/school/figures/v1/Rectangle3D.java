package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Rectangle3D extends Rectangle {
    //+REVU: тебе для определения Rectangle3D дополнительно нужно только height
    private int height;

    public Rectangle3D(Point2D leftTop, Point2D rightBottom, int height){
        super(leftTop, rightBottom);
        this.height = height;
    }
    //Создает Rectangle3D по координатам углов основания (левого верхнего и правого нижнего) и высотой.

    public Rectangle3D(int xLeft, int yTop, int xRight, int yBottom, int height){

        this(new Point2D(xLeft, yTop), new Point2D(xRight, yBottom), height);
    }
   // Создает Rectangle3D по координатам углов основания (левого верхнего и правого нижнего) и высотой.


    public Rectangle3D(int length, int width, int height){
        this(new Point2D(0, -width), new Point2D(length,0),height);
    }
   // Создает Rectangle3D, левый нижний угол которого находится в начале координат, а  длина, ширина и высота задаются.

    //+REVU: удобнее было бы использовать Rectangle3D(int length, int width, int height) внутри этого конструктора
    public Rectangle3D(){
        this(1, 1, 1);
    }
    //Создает Rectangle3D с размерами (1, 1, 1), левый нижний угол которого находится в начале координат.

    //+REVU: все методы, которые просто вызывают методы базового класса через super можно смело удалить - они и так наследуются

    public int getHeight(){
        return height;
    }
    //Возвращает высоту параллелепипеда.

    public void setHeight(int height){
        this.height = height;
    }
    //Устанавливает высоту параллелепипеда.

    public double getVolume(){
        return super.getArea() * height;
    }
 //Возвращает объем параллелепипеда.

    public boolean isInside(int x, int y, int z){
       return this.isInside(new Point3D(x, y, z)) && z <= height;
    }
    //Определяет, лежит ли точка (x, y, z) внутри Rectangle3D. Если точка лежит на стороне, считается, что она лежит внутри.

    public boolean isInside(Point3D point){

        //+REVU: упрости до return условие;
        return isInside(point.getX(), point.getY(), point.getZ());
    }
    //Определяет, лежит ли точка point внутри Rectangle3D. Если точка лежит на стороне, считается, что она лежит внутри.

    public boolean isIntersects(Rectangle3D rectangle){
            return super.isIntersects(rectangle);
    }
    //Определяет, пересекается  ли Rectangle3D с другим Rectangle3D. Считается, что параллелепипеды пересекаются, если у них есть хоть одна общая точка.
    public boolean isInside(Rectangle3D rectangle){
        //+REVU: упрости до return условие;
        return super.isInside(rectangle) && rectangle.height == this.height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle3D that = (Rectangle3D) o;
        return height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }
    //методы equals и hashCode.
}
