package fuzzy;

import controller.UserInput;
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
        attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("high")).then("high"));
        attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("medium")).then("medium"));
        attractFuzzy.addRule(price_adapt.eq("medium").and(age_adapt.eq("low")).then("low"));
        attractFuzzy.addRule(price_adapt.eq("low").then("low"));

        Function<Car, Model<Car, UserInput>> builder = Model.builder(attractFuzzy, (car, userInput) -> {
            Map<String, Double> result = new HashMap<>();

            double userPrice = userInput.getPrice();
            double userAge = userInput.getAge();

            result.put("price_adapt", Math.abs(userPrice - car.getPrice()) / userPrice);
            result.put("age_adapt", Math.abs(userAge - car.getAge()) / userAge);

            return result;
        });

        XmlManager manager = new XmlManager();
        List<Car> list = manager.load("cars.xml");
        List<Model<Car, UserInput>> models = new ArrayList<>();
        for (Car c: list)
            models.add(builder.apply(c));

        UserInput input = new UserInput(20000, 5);
        Model.sort(input, models);

        models.forEach(m ->
                System.out.println(m.get().getAge() + " | " + m.get().getPrice() + " | " + m.getResult(input)));
    }

}