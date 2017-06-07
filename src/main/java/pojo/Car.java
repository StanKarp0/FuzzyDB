package pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Michał on 06.06.2017.
 */
@XmlRootElement
public class Car {
    //do rozbudowy o kolejne zmienne w zaleznosci od potrzeb, ale to pewnie juz na koncu projektu
    private int price;
    private int age;

    public int getPrice(){
        return price;
    }

    public int getAge(){
        return age;
    }

    @XmlElement
    public void setPrice(int price){
        this.price = price;
    }

    @XmlElement
    public void setAge(int age){
        this.age = age;
    }

    @Override
    public String toString() {
        return "Price: "+price+", Age: "+age;
    }
}
