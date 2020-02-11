package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Rectangle {
    //REVU: Length, Width тебе не нужны в качестве полей - ты всегда можешь их посчитать через x1, x2, y1, y2
    // а так же у тебя есть методы getLength() и getWidth(), которые можно использовать
    private int x1, x2, y1, y2, Length, Width;
    public Rectangle(Point2D leftTop, Point2D rightBottom){
        x1 = leftTop.getX();
        y1 = leftTop.getY();
        x2 = rightBottom.getX();
        y2 = rightBottom.getY();
        Length = Math.abs(x2 - x1);
        Width = Math.abs(y2 - y1);
     }
    //Создает Rectangle по координатам углов - левого верхнего и правого нижнего.

   public Rectangle(int xLeft, int yTop, int xRight, int yBottom){
        this(new Point2D(xLeft,yTop), new Point2D(xRight,yBottom));
    }
   // Создает Rectangle по координатам углов - левого верхнего и правого нижнего.

    public Rectangle(int length, int width){
        this(new Point2D(0,-width), new Point2D(length,0));
    }
   // Создает Rectangle, левый нижний угол которого находится в начале координат, а  длина (по оси X) и ширина (по оси Y) задаются.

    //REVU: было бы удобнее использовать внутри этого конструктора Rectangle(int length, int width)
    public Rectangle(){
      this(new Point2D(0,-1), new Point2D(1,0));
      }
   // Создает Rectangle с размерами (1,1), левый нижний угол которого находится в начале координат.

   public Point2D getTopLeft(){
        //REVU: можно сразу return new Point2D(x1,y1);
        Point2D topLeft = new Point2D(x1,y1);
        return topLeft;
   }
  //  Возвращает левую верхнюю точку Rectangle.

    public Point2D getBottomRight(){
        //REVU: можно сразу return new Point2D(x2,y2);
        Point2D rightBottom = new Point2D(x2,y2);
        return rightBottom;
    }
  //  Возвращает правую нижнюю точку Rectangle.

    public void setTopLeft(Point2D topLeft){
        x1 = topLeft.getX();
        y1 = topLeft.getY();
    }
   // Устанавливает левую верхнюю точку Rectangle.

    public void setBottomRight(Point2D bottomRight){
        x2 = bottomRight.getX();
        y2 = bottomRight.getY();
    }
  //  Устанавливает правую нижнюю точку Rectangle.

    public int getLength(){
        return Length;
    }
  //  Возвращает длину прямоугольника.

    public int getWidth(){
        return Width;
    }
 //   Возвращает ширину прямоугольника.

    public void moveRel(int dx, int dy){
        x1+=dx;
        y1+=dy;
        x2+=dx;
        y2+=dy;
    }
  //  Передвигает Rectangle на (dx, dy).

    public void enlarge(int nx, int ny){
        x2 = x1 + Length*nx;
        y2 = y1 + Width*ny;
    }
  //  Увеличивает стороны Rectangle в (nx, ny) раз при сохранении координат левой верхней вершины.

    public double getArea(){
        return Width*Length;
    }
  //  Возвращает площадь прямоугольника.

    public double getPerimeter(){
        return Width*2 + Length*2;
    }
  //  Возвращает периметр прямоугольника.

    public boolean isInside(int x, int y){
        //REVU: упрости до return условие;
        if(x>=x1 && x<=x2 && y>=y1 && y<= y2) return true;
        else return false;
    }
  //  Определяет, лежит ли точка (x, y) внутри Rectangle. Если точка лежит на стороне, считается, что она лежит внутри.

    public boolean isInside(Point2D point){
        //REVU: используй метод isInside(int x, int y) внутри этого
        if(point.getX()>=x1 && point.getX()<=x2 && point.getY()>=y1 && point.getY()<= y2) return true;
        else return false;
    }
 //   Определяет, лежит ли точка point внутри Rectangle. Если точка лежит на стороне, считается, что она лежит внутри.

    public boolean isIntersects(Rectangle rectangle) {
        //REVU: упрости до return условие;
 if(rectangle.getTopLeft().getX()>getBottomRight().getX()||
    rectangle.getBottomRight().getX() < getTopLeft().getX() ||
    rectangle.getTopLeft().getY()>getBottomRight().getY() ||
    rectangle.getBottomRight().getY() < getTopLeft().getY())
     return false;
        else return true;
    }

//   Определяет, пересекается  ли Rectangle с другим Rectangle. Считается, что прямоугольники пересекаются, если у них есть хоть одна общая точка.
public boolean isInside(Rectangle rectangle){
    //REVU: упрости до return условие;
    if(rectangle.getBottomRight().getX() < getBottomRight().getX() &&
       rectangle.getBottomRight().getY() < getBottomRight().getY() &&
       rectangle.getTopLeft().getX() > getTopLeft().getX() &&
       rectangle.getTopLeft().getY() > getTopLeft().getY())
        return true;
            else return false;
}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rectangle)) return false;
        Rectangle rectangle = (Rectangle) o;
        return x1 == rectangle.x1 &&
                x2 == rectangle.x2 &&
                y1 == rectangle.y1 &&
                y2 == rectangle.y2 &&
                getLength() == rectangle.getLength() &&
                getWidth() == rectangle.getWidth();
    }
    //   Определяет, лежит ли rectangle целиком внутри текущего Rectangle.
    @Override
    public int hashCode() {
        return Objects.hash(x1, x2, y1, y2, getLength(), getWidth());
    }

  //  методы equals и hashCode.
 //   Не пишите эти методы сами, используйте средства IDEA.



}
