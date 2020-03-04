package net.thumbtack.school.cars;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorErrorCode;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.colors.Colored;

public class Car implements Colored {
    private int weight, maxSpeed;
    private String model;
    private Color color;

    public Car(String model, int weight, int maxSpeed, Color color) throws ColorException {
        setModel(model);
        setWeight(weight);
        setMaxSpeed(maxSpeed);
        setColor(color);
    }

    //REVU: используй уже существующий конструктор внутри этого
    public Car(String model, int weight, int maxSpeed, String colorString) throws ColorException {
        setModel(model);
        setWeight(weight);
        setMaxSpeed(maxSpeed);
        setColor(colorString);
    }

    public String getModel() {
        return this.model;
    }


    public void setModel(String model) {
        this.model = model;
    }

    public int getWeight() {
        return this.weight;
    }


    public void setWeight(int weight) {
        this.weight = weight;
    }


    public int getMaxSpeed() {
        return this.maxSpeed;
    }


    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    //REVU: тело if должно быть в {} и начинаться с новой строки
    @Override
    public void setColor(Color color) throws ColorException {
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    //REVU: ть сделаешь реализацию по-умолчанию в интерфейсе, этот метод можно будет удалить
    @Override
    public void setColor(String colorString) throws ColorException {
        this.color = Color.colorFromString(colorString);
    }

}
