package controller;

/**
 * Created by wojciech on 07.06.17.
 */
public class UserInput {

    private final double price;
    private final double age;

    public UserInput(double price, double age) {

        this.price = price;
        this.age = age;
    }

    public double getPrice() {
        return price;
    }

    public double getAge() {
        return age;
    }
}
