package fuzzy;

import controller.UserInput;
import model.CHelper;
import org.junit.Test;
import pojo.Car;
import xml.XmlManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * Created by wojciech on 07.06.17.
 */
public class ModelTest {
    @Test
    public void sort() throws Exception {

        // Logika odwrotna
        // 0 - calkowite przystosowanie
        // 1 - brak przystosowania
        Variable price_adapt = new Variable("price_adapt", 0., 1.);
        price_adapt.addMFnc("high", FuzzyFnc.gaussmf(0.15, 0));
        price_adapt.addMFnc("medium", FuzzyFnc.gaussmf(0.15, 0.5));
        price_adapt.addMFnc("low", FuzzyFnc.gaussmf(0.15, 1.));

        Variable age_adapt = new Variable("age_adapt", 0., 1.);
        age_adapt.addMFnc("high", FuzzyFnc.gaussmf(0.15, 0));
        age_adapt.addMFnc("medium", FuzzyFnc.gaussmf(0.15, 0.5));
        age_adapt.addMFnc("low", FuzzyFnc.gaussmf(0.15, 1.));

        Variable attract = new Variable("attract", 0., 1.);
        attract.addMFnc("high", FuzzyFnc.gaussmf(0.15, 0));
        attract.addMFnc("medium", FuzzyFnc.gaussmf(0.15, 0.5));
        attract.addMFnc("low", FuzzyFnc.gaussmf(0.15, 1.));

        Fuzzy attractFuzzy = new Fuzzy(attract);
        attractFuzzy.addRule(price_adapt.eq("high").then("high"));
        //attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("high")).then("high"));
        attractFuzzy.addRule(price_adapt.eq("high").and(age_adapt.eq("low")).then("medium"));
        attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("medium")).then("medium"));
        //attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("low")).then("low"));
        attractFuzzy.addRule(price_adapt.eq("low").then("low"));

        Function<Car, Model<Car, UserInput>> builder = Model.builder(attractFuzzy, (car, userInput) -> {
            Map<String, Double> result = new HashMap<>();

            double userPrice = userInput.getPrice();
            double userAge = userInput.getAge();
            double userHp = userInput.getHp();

            double carPrice = car.getPrice();
            double carAge = car.getAge();
            double carHp = car.getHp();


            double price;
            double age;

            double r;

            double x, y;

            double adapt;

            CHelper ch = new CHelper();

            //price function START
            //price function price.jpg
            x = userPrice;
            y = carPrice;
            r = ch.r(x, y);
            if(x - y < 0){
                if(r<0.1){
                    adapt = ch.straight(y, x, 1, x*1.1, 0.5);
                }else if(r<0.15){
                    adapt = ch.straight(y, x*1.1, 0.5, x*1.15, 0);
                }else{
                    adapt = 0;
                }
            }else{
                if(r<0.15){
                    adapt = 1;
                }else if(r<0.3){
                    adapt = ch.straight(y, x*0.85, 1, x*0.7, 0);
                } else{
                    adapt = 0;
                }
            }
            double priceAdapt = 1-adapt;
            //price function END

            //age function START
            //age function age.jpg
            x = userAge;
            y = carAge;
            r = ch.r(x, y);
            if(x - y < 0){
                if(r<0.25){
                    adapt = ch.straight(y, x, 1, x*1.3, 0);
                }else{
                    adapt = 0;
                }
            }else{
                if(r<0.4){
                    adapt = ch.straight(y, x, 1, x*0.6, 0);
                } else{
                    adapt = 0;
                }
            }
            double ageAdapt = 1-adapt;//Math.abs(userPrice - car.getPrice()) / userPrice;
            //price function END

            //hp function START
            //hp function hp.jpg
            x = userHp;
            y = carHp;
            r = ch.r(x, y);
            if(r<0.05){
                adapt = 1;
            }else if(r<0.25){
                adapt = (-r+0.25)/0.20;
            }else{
                adapt = 0;
            }
            double hpAdapt = 1-adapt;//Math.abs(userPrice - car.getPrice()) / userPrice;
            //price function END



            //double ageAdapt = Math.abs(userAge - car.getAge()) / userAge;
            result.put("price_adapt", priceAdapt);
            result.put("age_adapt", ageAdapt);
            result.put("hp_adapt", hpAdapt);

            return result;
        }, (car, input) -> {
            double val = 0;
            if(car.isDiesel() != input.isDiesel()) val += 0.15;//do dostosowania pozniej, bo nie wiem jakie wyniki beda wychodzily
            if(car.isAutomatic() != input.isAutomatic()) val += 0.35;
            return val;
        });

        XmlManager manager = new XmlManager();
        List<Car> list = manager.load("cars.xml");
        List<Model<Car, UserInput>> models = new ArrayList<>();
        for (Car c: list)
            models.add(builder.apply(c));

        UserInput input = new UserInput(32000, 8, 90, false, false);
        Model.sort(input, models);

        models.forEach(m ->
                System.out.println("age:" + m.get().getAge() + " | " + "price:" + m.get().getPrice() + " | " + "hp:" + m.get().getHp() +
                        " | " + "diesel:" + m.get().isDiesel() +  " | " + "automatic:" + m.get().isAutomatic() + " |||||| " +
                        m.getResult(input)));
    }

}