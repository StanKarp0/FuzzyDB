package model;

import controller.UserInput;
import org.junit.Test;
import pojo.Car;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wojciech on 07.06.17.
 */
public class DaoTest {
    @Test
    public void find() throws Exception {

        Dao dao = new Dao();
        List<Car> cars = dao.find(new UserInput(10000, 10, Car.Engine.PETROL));
        cars.forEach(System.out::println);

    }

}