package controller;

import pojo.Car;

/**
 * Created by wojciech on 07.06.17.
 */
public class UserInput {

    private final double price;
    private final double age;
    private final Car.Engine engine;

    public UserInput(double price, double age, Car.Engine engine) {

        this.price = price;
        this.age = age;
        this.engine = engine;
    }

    public double getPrice() {
        return price;
    }

    public double getAge() {
        return age;
    }

    public Car.Engine getEngine() {
        return engine;
    }
}
