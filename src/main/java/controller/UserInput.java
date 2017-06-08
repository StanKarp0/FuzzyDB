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
    private final String type;

    public UserInput(double price, double age, double hp, Engine engine, Transmission transmission, String type) {

        this.price = price;
        this.age = age;
        this.hp = hp;
        this.engine = engine;
        this.transmission = transmission;
        this.type = type;
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

    public boolean isTypeSelected() {return type!=null;}

    public boolean isAutomatic() {return transmission == Transmission.AUTOMATIC;}

    public boolean isDiesel() {return engine == Engine.DIESEL;}

    public String getType() {return type;}

    @Override
    public String toString() {
        return "Input: " + age;
    }
}
