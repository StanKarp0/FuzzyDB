package controller;

import pojo.Car;

/**
 * Created by wojciech on 07.06.17.
 */
public class UserInput {

    private final double price;
    private final double age;
    private final double hp;
    private final boolean isDiesel;
    private final boolean isAutomatic;

    public UserInput(double price, double age, double hp, boolean isDiesel, boolean isAutomatic) {

        this.price = price;
        this.age = age;
        this.hp = hp;
        this.isDiesel = isDiesel;
        this.isAutomatic = isAutomatic;
    }

    public double getPrice() {
        return price;
    }

    public double getAge() {
        return age;
    }

    public double getHp() {return hp;}

    public boolean isAutomatic() {return isAutomatic;}

    public boolean isDiesel() {return isDiesel;}
}
