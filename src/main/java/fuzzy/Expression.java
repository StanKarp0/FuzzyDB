package fuzzy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wojciech on 06.06.17.
 */
@FunctionalInterface
public interface Expression {

    double apply(Map<String, Double> args);

    default Expression or(Expression e) {
        return args -> Math.max(apply(args), e.apply(args));
    }

    default Expression and(Expression e) {
        return args -> Math.min(apply(args), e.apply(args));
    }

    default Rule then(String value) {
        return args -> {
            Map<String, Double> res = new HashMap<>();
            res.put(value, apply(args));
            return res;
        };
    }
}