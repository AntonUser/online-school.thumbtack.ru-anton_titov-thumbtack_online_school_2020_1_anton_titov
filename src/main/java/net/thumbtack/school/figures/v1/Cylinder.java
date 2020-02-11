package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Cylinder extends Circle {
    private int height;

    public Cylinder(Point2D center, int radius, int height) {
        super(center, radius);
        this.height = height;
    }
    //Создает Cylinder по координатам центра, значению радиуса и высоте.

    public Cylinder(int xCenter, int yCenter, int radius, int height) {
        this(new Point2D(xCenter, yCenter), radius, height);
    }
    //Создает Cylinder по координатам центра, значению радиуса и высоте.

    public Cylinder(int radius, int height) {
        this(new Point2D(0, 0), radius, height);
    }
    //Создает Cylinder  с центром в точке (0, 0) с указанными радиусом и высотой.

    //REVU: удобнее было бы использовать конструктор Cylinder(int radius, int height) внутри этого
    public Cylinder() {
        this(new Point2D(0, 0), 1, 1);
    }
    //Создает Cylinder  с центром в точке (0, 0) с радиусом 1 и высотой 1.

    //REVU: все методы, которые просто вызывают методы базового класса через super можно смело удалить - они и так наследуются
    public Point2D getCenter() {
        return super.getCenter();
    }
    //Возвращает координаты центра Cylinder.

    public int getRadius() {
        return super.getRadius();
    }
    //Возвращает радиус Cylinder.

    public void setCenter(Point2D center) {
        super.setCenter(center);
    }
    //Устанавливает координаты центра Cylinder.

    public void setRadius(int radius) {
        super.setRadius(radius);
    }
    //Устанавливает радиус Cylinder.

    public int getHeight() {
        return height;
    }
    //Возвращает высоту Cylinder.

    public void setHeight(int height) {
        this.height = height;
    }
    //Устанавливает высоту Cylinder.

    public void moveRel(int dx, int dy) {
        super.moveRel(dx, dy);
    }
    //Передвигает Cylinder на (dx, dy).

    public void enlarge(int n) {
        super.enlarge(n);
    }
    //Увеличивает радиус Cylinder в n раз, не изменяя центра и высоты.

    public double getArea() {
        return super.getArea();
    }
    //Возвращает площадь круга основания цилиндра.

    public double getPerimeter() {
        return super.getPerimeter();
    }
    //Возвращает периметр окружности основания цилиндра.

    public double getVolume() {
        return super.getArea() * height;
    }
    //Возвращает объем цилиндра.

    public boolean isInside(int x, int y, int z) {
        //REVU: упрости до return условие;
        if (super.isInside(x, y) && Math.abs(z) >= 0 && Math.abs(z) <= height) return true;
        else return false;
    }
    //Определяет, лежит ли точка (x, y, z) внутри Cylinder. Если точка лежит на поверхности, считается, что она лежит внутри.

    public boolean isInside(Point3D point) {
        return this.isInside(point.getX(), point.getY(), point.getZ());
    }
    //Определяет, лежит ли точка point внутри Cylinder. Если точка лежит на поверхности, считается, что она лежит внутри.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cylinder cylinder = (Cylinder) o;
        return height == cylinder.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }

    // методы equals и hashCode.
}