package model;

import controller.UserInput;
import fuzzy.*;
import pojo.Car;
import xml.XmlManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by wojciech on 07.06.17.
 */
public class Dao {

    private final List<Model<Car, UserInput>> models;

    private double muPriceAdapt(double x, double y) {
        double r = Math.abs(x - y)/x;
        if(x < y)
            if(r < 0.1) return (0.1 - 0.5 * r) / 0.1;
            else if(r < 0.15) return (0.075 - 0.5 * r) / 0.05;
            else return 0;
        else
            if (r < 0.15) return 1;
            else if (r < 0.3) return (0.3 - r)/0.15;
            else return 0;
    }

    private double muAgeAdapt(double x, double y) {
        double r = Math.abs(x - y)/x;
        if(x < y) return r < 0.25 ? (0.25 - r)/0.25 : 0.;
        else return r < 0.4 ? (0.4 - r)/0.4 : 0.;
    }

    private double muHpAdapt(double x, double y) {
        double r = Math.abs(x - y)/x;
        if(r < 0.05) return 1.;
        if(r < 0.25) return (0.25 - r) / 0.20;
        return 0.;
    }

    public Dao(){
        Variable price_adapt = new Variable("price_adapt", 0., 1.);
        price_adapt.addMFnc("low", FuzzyFnc.gaussmf(0.15, 0));
        price_adapt.addMFnc("medium", FuzzyFnc.gaussmf(0.15, 0.5));
        price_adapt.addMFnc("high", FuzzyFnc.gaussmf(0.15, 1.));

        Variable age_adapt = new Variable("age_adapt", 0., 1.);
        age_adapt.addMFnc("low", FuzzyFnc.gaussmf(0.15, 0));
        age_adapt.addMFnc("medium", FuzzyFnc.gaussmf(0.15, 0.5));
        age_adapt.addMFnc("high", FuzzyFnc.gaussmf(0.15, 1.));

        Variable hp_adapt = new Variable("hp_adapt", 0., 1.);
        hp_adapt.addMFnc("low", FuzzyFnc.gaussmf(0.15, 0));
        hp_adapt.addMFnc("medium", FuzzyFnc.gaussmf(0.15, 0.5));
        hp_adapt.addMFnc("high", FuzzyFnc.gaussmf(0.15, 1.));

        Variable attract = new Variable("attract", 0., 1.);
        attract.addMFnc("low", FuzzyFnc.gaussmf(0.15, 0));
        attract.addMFnc("medium", FuzzyFnc.gaussmf(0.15, 0.5));
        attract.addMFnc("high", FuzzyFnc.gaussmf(0.15, 1.));

        Fuzzy attractFuzzy = new Fuzzy(attract);
        //high
        attractFuzzy.addRule(price_adapt.eq("high").then("high"));
        attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("high")).then("high"));
        attractFuzzy.addRule(price_adapt.eq("medium").and(hp_adapt.eq("high")).then("high"));

        //medium
        attractFuzzy.addRule(price_adapt.eq("high").and(age_adapt.eq("low")).then("medium"));
        attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("medium")).then("medium"));
        attractFuzzy.addRule(hp_adapt.eq("medium").or(age_adapt.eq("medium")).then("medium"));
        attractFuzzy.addRule(hp_adapt.eq("high").or(age_adapt.eq("low")).then("medium"));
        attractFuzzy.addRule(price_adapt.eq("high").and(hp_adapt.eq("low")).then("medium"));

        //low
        attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("low")).then("low"));
        attractFuzzy.addRule(price_adapt.eq("low").then("low"));
        attractFuzzy.addRule(hp_adapt.eq("low").then("low"));


        Function<Car, Model<Car, UserInput>> builder = Model.builder(attractFuzzy, (car, userInput) -> {
            Map<String, Double> result = new HashMap<>();

            result.put("price_adapt", muPriceAdapt(userInput.getPrice(), car.getPrice()));
            result.put("age_adapt", muAgeAdapt(userInput.getAge(), car.getAge()));
            result.put("age_adapt", muHpAdapt(userInput.getHp(), car.getHp()));

            return result;
        }, (car, input) -> {
            double val = 0;
            if(input.isDieselSelected())
                if(car.isDiesel() != input.isDiesel()) val -= 0.15;//do dostosowania pozniej, bo nie wiem jakie wyniki beda wychodzily
            if(input.isAutomaticSelected())
                if(car.isAutomatic() != input.isAutomatic()) val -= 0.35;
            return val;
        });

        XmlManager manager = new XmlManager();
        List<Car> cars = manager.load("cars.xml");
        this.models = new ArrayList<>();

        for (Car c: cars)
            models.add(builder.apply(c));
    }

    public List<Car> find(UserInput input) {
        return Model.sort(input, models);
    }

}
