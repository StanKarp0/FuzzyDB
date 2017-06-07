package fuzzy;

import org.junit.Test;
import pojo.Car;
import xml.XmlManager;

import java.util.List;
import java.util.Random;

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

    @Test
    public void generateXml(){
        Random r = new Random();
        for(int i = 0; i < 100; i++) {
            System.out.println("\t<car>\n" +
                    "\t\t<price>"+(r.nextInt(47000)+3000)+"</price>\n" +
                    "\t\t<age>"+r.nextInt(10)+"</age>\n" +
                    "\t</car>");
        }

    }
}