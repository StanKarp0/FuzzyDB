package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pojo.Car;

/**
 * Created by wojciech on 08.06.17.
 */
public class CarView {

    private final IntegerProperty price;
    private final IntegerProperty age;
    private final IntegerProperty hp;

    public CarView(Car c) {
        price = new SimpleIntegerProperty(c.getPrice());
        age = new SimpleIntegerProperty(c.getPrice());
        hp = new SimpleIntegerProperty(c.getPrice());
    }

}
