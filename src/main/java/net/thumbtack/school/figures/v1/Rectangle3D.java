package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Rectangle3D extends Rectangle {
    //REVU: тебе для определения Rectangle3D дополнительно нужно только height
    private int z1, z2, height;

    public Rectangle3D(Point2D leftTop, Point2D rightBottom, int height){
        super(leftTop, rightBottom);
        //Point3D ptL = new Point3D(leftTop.getX(),leftTop.getY(),0);

        z1 = 0;
        z2 = height;
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

    //REVU: удобнее было бы использовать Rectangle3D(int length, int width, int height) внутри этого конструктора
    public Rectangle3D(){
        this(new Point2D(0,-1), new  Point2D(1,0), 1);
    }
    //Создает Rectangle3D с размерами (1, 1, 1), левый нижний угол которого находится в начале координат.

    //REVU: все методы, которые просто вызывают методы базового класса через super можно смело удалить - они и так наследуются
    public Point2D getTopLeft(){
        return super.getTopLeft();
    }
   // Возвращает левую верхнюю точку Rectangle основания.

    public Point2D getBottomRight(){
        return super.getBottomRight();
    }
    //Возвращает правую нижнюю точку Rectangle основания.

    public int getHeight(){
        return height;
    }
    //Возвращает высоту параллелепипеда.

    public void setTopLeft(Point2D topLeft){
        super.setTopLeft(topLeft);
    }
    //Устанавливает левую верхнюю точку Rectangle основания.

    public void setBottomRight(Point2D bottomRight){
        super.setBottomRight(bottomRight);
    }
    //Устанавливает правую нижнюю точку Rectangle основания.

    public void setHeight(int height){
        z2 = height;
    }
    //Устанавливает высоту параллелепипеда.

    public int getLength(){
        return super.getLength();
    }
    //Возвращает длину  прямоугольника основания.

    public int getWidth(){
       return super.getWidth();
    }
   // Возвращает ширину прямоугольника основания.

    public void moveRel(int dx, int dy){
        super.moveRel(dx, dy);
    }
    //Передвигает Rectangle3D на (dx, dy). Высота не изменяется.

    public void enlarge(int nx, int ny){
        super.enlarge(nx, ny);
    }
    //Увеличивает стороны Rectangle основания в (nx, ny) раз при сохранении координат левой верхней вершины; высота не изменяется.

    public double getArea(){
        return super.getArea();
    }
    //Возвращает площадь прямоугольника  основания.

    public double getPerimeter(){
       return super.getPerimeter();
    }
    //Возвращает периметр прямоугольника основания.

    public double getVolume(){
        return super.getArea() * z2;
    }
    //Возвращает объем параллелепипеда.

    public boolean isInside(int x, int y){
        return this.isInside(new Point2D(x,y));
    }
   // Определяет, лежит ли точка (x, y) внутри Rectangle основания. Если точка лежит на стороне, считается, что она лежит внутри.

    public boolean isInside(Point2D point){
        return super.isInside(point);
    }
    //Определяет, лежит ли точка point внутри Rectangle основания. Если точка лежит на стороне, считается, что она лежит внутри.

    public boolean isInside(int x, int y, int z){
       return this.isInside(new Point3D(x, y, z));
    }
    //Определяет, лежит ли точка (x, y, z) внутри Rectangle3D. Если точка лежит на стороне, считается, что она лежит внутри.

    public boolean isInside(Point3D point){
        //REVU: упрости до return условие;
        if(super.isInside(new Point2D(point.getX(),point.getY())) && point.getZ() <= z2 && point.getZ() >= z1) return true;
        else return false;
    }
    //Определяет, лежит ли точка point внутри Rectangle3D. Если точка лежит на стороне, считается, что она лежит внутри.

    public boolean isIntersects(Rectangle3D rectangle){
      return super.isIntersects(rectangle);
    }
    //Определяет, пересекается  ли Rectangle3D с другим Rectangle3D. Считается, что параллелепипеды пересекаются, если у них есть хоть одна общая точка.

    public boolean isInside(Rectangle3D rectangle){
        //REVU: упрости до return условие;
        if(super.isInside(rectangle) && Math.abs(rectangle.z1) >= this.z1 &&
                Math.abs(rectangle.z2) <= Math.abs(this.z2))return true;
        else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle3D that = (Rectangle3D) o;
        return z1 == that.z1 &&
                z2 == that.z2 &&
                height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), z1, z2, height);
    }
//методы equals и hashCode.


}
