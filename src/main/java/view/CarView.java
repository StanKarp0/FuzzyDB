package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pojo.Car;

import java.util.Calendar;

/**
 * Created by wojciech on 08.06.17.
 */
public class CarView {

    private final String name;
    private final String type;
    private final String eCapacity;
    private final int price;
    private final int year;
    private final int hp;
    private final boolean isDiesel;
    private final boolean isAutomatic;

    public CarView(Car c) {
        price = c.getPrice();
        year = Calendar.getInstance().get(Calendar.YEAR)-c.getAge();
        hp = c.getHp();
        name = c.getName();
        type = c.getType();
        eCapacity = c.geteCapacity();
        isDiesel = c.isDiesel();
        isAutomatic = c.isAutomatic();
    }

    public int getPrice(){
        return price;
    }

    public int getYear(){
        return year;
    }

    public int getHp(){
        return hp;
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public String getEcapacity(){
        return eCapacity;
    }

    public String getEngine(){
        return isDiesel ? "Diesel" : "Benzynowy";
    }

    public String getTransmission(){
        return isAutomatic ? "Automatyczna" : "Manualna";
    }

}
