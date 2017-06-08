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
            System.out.println("moc = " + c.getHp());
            System.out.println("diesel = " + c.isDiesel());
            System.out.println("automatyczny = " + c.isAutomatic());
        }

    }

    @Test
    public void generateXml(){
        Random r = new Random();
        for(int i = 0; i < 40; i++) {
            System.out.println("\t<car>\n" +
                    "\t\t<price>"+(r.nextInt(47000)+3000)+"</price>\n" +
                    "\t\t<age>"+r.nextInt(10)+"</age>\n" +
                    "\t\t<hp>"+(r.nextInt(200)+50)+"</hp>\n" +
                    "\t\t<diesel>"+r.nextBoolean()+"</diesel>\n" +
                    "\t\t<automatic>"+r.nextBoolean()+"</automatic>\n" +
                    "\t\t<name>"+"Marka Model"+r.nextInt(20)+"</name>\n" +
                    "\t\t<type>"+"Nadwozie"+r.nextInt(4)+"</type>\n" +
                    "\t\t<ecapacity>"+(Double.valueOf(r.nextInt(30)+8)/10.0)+"</ecapacity>\n" +
                    "\t</car>");
        }

    }
}
