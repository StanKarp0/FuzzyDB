package fuzzy;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static fuzzy.FuzzyFnc.*;

/**
 * Created by wojciech on 06.06.17.
 */
public class FuzzyTest {

    @Test
    public void fuzzy() throws Exception {
        Variable service = new Variable("Service", 0., 10.);
        service.addMFnc("Poor", gaussmf(1.5, 0.));
        service.addMFnc("Good", gaussmf(1.5, 5.));
        service.addMFnc("Excellent", gaussmf(1.5, 10.));

        Variable food = new Variable("Food", 0., 10.);
        food.addMFnc("Rancid", trapmf(-0.1, 0, 1., 3.));
        food.addMFnc("Delicious", trapmf(7., 8., 10., 11.));

        Variable tip = new Variable("Tip", 0., 30.);
        tip.addMFnc("Cheap", trimf(0., 5., 10.));
        tip.addMFnc("Average", trimf(10., 15., 20.));
        tip.addMFnc("Generous", trimf(20., 25., 30.));

        Fuzzy fuzzyTip = new Fuzzy(tip);

        fuzzyTip.addRule(service.eq("Poor").or(food.eq( "Rancid")).then("Cheap"));
        fuzzyTip.addRule(service.eq("Good").then("Average"));
        fuzzyTip.addRule(service.eq("Excellent").or(food.eq("Delicious")).then("Generous"));

        Map<String, Double> input = new HashMap<>();
        input.put("Service", 0.3);
        input.put("Food", 9.);
        Map<String, Double> output = fuzzyTip.fuzzy(input);
        Assert.assertTrue(Math.abs(output.get("Generous")-1.0) < 1e-5);
        Assert.assertTrue(Math.abs(output.get("Average")-0.007380684537674243) < 1e-5);
        Assert.assertTrue(Math.abs(output.get("Cheap")-0.9801986733067553) < 1e-5);
        Assert.assertTrue(Math.abs(fuzzyTip.crisp(input)-15.102619934091068) < 1e-5);
    }

    @Test
    public void crisp() throws Exception {

        Variable service = new Variable("Service", 0., 10.);
        service.addMFnc("Poor", gaussmf(1.5, 0.));
        service.addMFnc("Good", gaussmf(1.5, 5.));
        service.addMFnc("Excellent", gaussmf(1.5, 10.));

        Variable food = new Variable("Food", 0., 10.);
        food.addMFnc("Rancid", trapmf(-0.1, 0, 1., 3.));
        food.addMFnc("Delicious", trapmf(7., 8., 10., 11.));

        Variable tip = new Variable("Tip", 0., 30.);
        tip.addMFnc("Cheap", trimf(0., 5., 10.));
        tip.addMFnc("Average", trimf(10., 15., 20.));
        tip.addMFnc("Generous", trimf(20., 25., 30.));

        Fuzzy fuzzyTip = new Fuzzy(tip);

        fuzzyTip.addRule(service.eq("Poor").or(food.eq( "Rancid")).then("Cheap"));
        fuzzyTip.addRule(service.eq("Good").then("Average"));
        fuzzyTip.addRule(service.eq("Excellent").or(food.eq("Delicious")).then("Generous"));

        for(double s = 0; s <= 10.; s += 0.25) {
            for(double f = 0.; f <= 10.; f += 0.25) {
                Map<String, Double> input = new HashMap<>();
                input.put("Service", s);
                input.put("Food", f);
                double output = fuzzyTip.crisp(input);
                System.out.println(s + " " + f + " " + output);
            }
        }
    }

    @Test
    public void crisp2() throws Exception {

        Variable service = new Variable("Service", 0., 10.);
        service.addMFnc("Poor", gaussmf(1.5, 0.));
        service.addMFnc("Good", gaussmf(1.5, 5.));
        service.addMFnc("Excellent", gaussmf(1.5, 10.));

        Variable food = new Variable("Food", 0., 10.);
        food.addMFnc("Rancid", trapmf(-0.1, 0, 1., 10.));
        food.addMFnc("Delicious", trapmf(0., 8., 10., 11.));

        Variable tip = new Variable("Tip", 0., 30.);
        tip.addMFnc("Cheap", trimf(0., 5., 10.));
        tip.addMFnc("Average", trimf(10., 15., 20.));
        tip.addMFnc("Generous", trimf(20., 25., 30.));

        Fuzzy fuzzyTip = new Fuzzy(tip);

        fuzzyTip.addRule(service.eq("Poor").and(food.eq( "Rancid")).then("Cheap"));
        fuzzyTip.addRule(service.eq("Poor").and(food.eq( "Delicious")).then("Average"));

        fuzzyTip.addRule(service.eq("Good").and(food.eq( "Rancid")).then("Average"));
        fuzzyTip.addRule(service.eq("Good").and(food.eq( "Delicious")).then("Average"));

        fuzzyTip.addRule(service.eq("Excellent").and(food.eq( "Rancid")).then("Average"));
        fuzzyTip.addRule(service.eq("Excellent").and(food.eq( "Delicious")).then("Generous"));

        for(double s = 0; s <= 10.; s += 0.25) {
            for(double f = 0.; f <= 10.; f += 0.25) {
                Map<String, Double> input = new HashMap<>();
                input.put("Service", s);
                input.put("Food", f);
                double output = fuzzyTip.crisp(input);
                System.out.println(s + " " + f + " " + output);
            }
        }
    }

    @Test
    public void crisp3() throws Exception {

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

        for(double s = 0; s <= 1.01; s+= 0.02){
            for(double f = 0.; f <= 1.01; f += 0.02) {
                Map<String, Double> map = new HashMap<>();
                map.put("price_adapt", s);
                map.put("age_adapt", f);
                System.out.println(s + " " + f + " " + attractFuzzy.crisp(map));
            }
        }
    }

}