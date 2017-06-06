package pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Michał on 06.06.2017.
 */
@XmlRootElement(name="cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsList {


    @XmlElement(name="car")
    private List<Car> cars = null;


    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }



}
