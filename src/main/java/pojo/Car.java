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
    private int hp;

    private boolean isDiesel;
    private boolean isAutomatic;

    //wlasciwosci nie biorace udzialu w obliczeniach
    private String name;
    private String type;
    private String eCapacity;
    //end

    public int getPrice(){
        return price;
    }

    public int getAge(){
        return age;
    }

    public int getHp(){
        return hp;
    }

    public boolean isDiesel() {return isDiesel;}

    public boolean isAutomatic() {return isAutomatic;}

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public String geteCapacity(){
        return eCapacity;
    }

    @XmlElement
    public void setPrice(int price){
        this.price = price;
    }

    @XmlElement
    public void setAge(int age){
        this.age = age;
    }

    @XmlElement
    public void setHp(int hp){
        this.hp = hp;
    }

    @XmlElement
    public void setAutomatic(boolean isAutomatic){
        this.isAutomatic = isAutomatic;
    }

    @XmlElement
    public void setDiesel(boolean isDiesel){
        this.isDiesel = isDiesel;
    }

    @XmlElement
    public void setName(String name) {this.name = name;}

    @XmlElement
    public void setType(String type) {this.type = type;}

    @XmlElement
    public void setEcapacity(String eCapacity) {this.eCapacity = eCapacity;}

    @Override
    public String toString() {
        return "Price: "+price+", Age: "+age+", Hp: "+hp+", isAutomatic: "+isAutomatic+", isDiesel: "+isDiesel;
    }

}
