package fuzzy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wojciech on 06.06.17.
 */
public class Fuzzy {

    private final List<Rule> rules;
    private final Variable output;

    public Fuzzy(Variable output) {
        this.rules = new LinkedList<>();
        this.output = output;
    }

    public void addRule(Rule r) {
        rules.add(r);
    }

    public Map<String, Double> fuzzy(Map<String, Double> args) {
        Map<String, Double> wages = new HashMap<>();
        for (Rule r : rules) {
            Map<String, Double> res = r.apply(args);
            for (Map.Entry<String, Double> entry : res.entrySet()) {
                double m = Math.max(wages.getOrDefault(entry.getKey(), 0.), entry.getValue());
                wages.put(entry.getKey(), m);
            }
        }

        return wages;
    }

    public double crisp(Map<String, Double> args) {
        return output.defuzzification(fuzzy(args));
    }
}
