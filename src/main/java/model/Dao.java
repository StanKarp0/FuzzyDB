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
        if(r > 0.5) return 1.;
        if(r < 0.1) return 0.;
        return (r - 0.1)/0.4;
    }

    private double muAgeAdapt(double x, double y) {
        double r = Math.abs(x - y)/x;
        if(r > 0.5) return 1.;
        if(r < 0.2) return 0.;
        return (r - 0.2) / 0.3;
    }

    public Dao(){
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
        attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("high")).then("high"));
        attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("medium")).then("medium"));
        attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("low")).then("low"));
        attractFuzzy.addRule(price_adapt.eq("low").then("low"));

        Function<Car, Model<Car, UserInput>> builder = Model.builder(attractFuzzy, (car, userInput) -> {
            Map<String, Double> result = new HashMap<>();

            result.put("price_adapt", muPriceAdapt(userInput.getPrice(), car.getPrice()));
            result.put("age_adapt", muAgeAdapt(userInput.getAge(), car.getAge()));

            return result;
        }, (car, input) -> car.getEngine() == input.getEngine() ? 0. : 1.);

        XmlManager manager = new XmlManager();
        List<Car> cars = manager.load("cars.xml");
        this.models = new ArrayList<>();

        for (Car c: cars)
            models.add(builder.apply(c));
    }

    public List<Car> find(UserInput input) {
        List<Car> result = new ArrayList<>();
        Model.sort(input, models);
        for(Model<Car, UserInput> m: models)
            result.add(m.get());
        return result;
    }

}
