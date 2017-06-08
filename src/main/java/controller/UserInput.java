package controller;

/**
 * Created by wojciech on 07.06.17.
 */
public class UserInput {

    private final double price;
    private final double age;
    private final double hp;
    private final Engine engine;
    private final Transmission transmission;

    public UserInput(double price, double age, double hp, Engine engine, Transmission transmission) {

        this.price = price;
        this.age = age;
        this.hp = hp;
        this.engine = engine;
        this.transmission = transmission;
    }

    public double getPrice() {
        return price;
    }

    public double getAge() {
        return age;
    }

    public double getHp() {return hp;}

    public boolean isAutomaticSelected() {return transmission != Transmission.NONE;}

    public boolean isDieselSelected() {return engine != Engine.NONE;}

    public boolean isAutomatic() {return transmission == Transmission.AUTOMATIC;}

    public boolean isDiesel() {return engine == Engine.DIESEL;}

    @Override
    public String toString() {
        return "Input: " + age;
    }
}
