package fuzzy;

import org.junit.Test;
import pojo.Car;
import xml.XmlManager;

import java.util.List;

/**
 * Created by Michał on 06.06.2017.
 */
public class XmlTest {

    @Test
    public void read(){
        XmlManager manager = new XmlManager();
        List<Car> list = manager.load("cars.xml");
        for (Car c: list) {
            System.out.println("new car:");
            System.out.println("price = " + c.getPrice());
            System.out.println("age = " + c.getAge());
        }

    }
}
