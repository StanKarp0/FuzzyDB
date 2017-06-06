package xml;

import pojo.Car;
import pojo.CarsList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michał on 06.06.2017.
 */
public class XmlManager{

    public List<Car> load(String fileName){
        List<Car> list = new ArrayList<>();
        try {
            JAXBContext context = JAXBContext.newInstance(CarsList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //System.out.println(new java.io.File( "." ).getCanonicalPath());
            CarsList cars = (CarsList) unmarshaller.unmarshal(new FileInputStream(fileName));

            for(Car car : cars.getCars())
            {
                list.add(car);
            }
        }catch(Exception e){
            System.out.println("XmlManager load()" + e);
        }
        return list;
    }

}
