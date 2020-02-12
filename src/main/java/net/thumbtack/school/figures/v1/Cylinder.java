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

    public Cylinder() {
       this(1,1);
    }
    //Создает Cylinder  с центром в точке (0, 0) с радиусом 1 и высотой 1.

    public int getHeight() {
        return height;
    }
    //Возвращает высоту Cylinder.

    public void setHeight(int height) {
        this.height = height;
    }
    //Устанавливает высоту Cylinder.

    public double getVolume() {
        return super.getArea() * height;
    }
    //Возвращает объем цилиндра.

    public boolean isInside(int x, int y, int z) {

        return super.isInside(x, y) && Math.abs(z) >= 0 && Math.abs(z) <= height;
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