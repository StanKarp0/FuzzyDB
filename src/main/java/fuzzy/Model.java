package fuzzy;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by wojciech on 07.06.17.
 */
public class Model<T, U> {

    private final T t;
    private final BiFunction<T, U, Double> fnc;
    private final BiFunction<T, U, Double> binary;

    private Model(T t,
                  BiFunction<T, U, Double> fnc,
                  BiFunction<T, U, Double> binary
    ) {
        this.t = t;
        this.fnc = fnc;
        this.binary = binary;
    }

    public double getResult(U userInput) {
        return fnc.apply(t, userInput) + binary.apply(t, userInput);
    }

    public T get() {
        return t;
    }

    public static <T, U> Function<T, Model<T, U>> builder(
            Fuzzy output,
            BiFunction<T, U, Map<String, Double>> fnc,
            BiFunction<T, U, Double> binary
    ) {
        return t -> new Model<>(t, (t2, u) ->
                fnc.andThen(output::crisp).apply(t2, u), binary);
    }

    public static <T, U> List<T> sort(U userInput, List<Model<T, U>> models) {
        class Pair {
            private final T t;
            private final double d;

            Pair(T t, double d) {
                this.t = t;
                this.d = d;
            }
            T getT() {
                return t;
            }
            double getD() {
                return 1-d;
            }
        }

        List<Pair> pairs = new ArrayList<>();
        for(Model<T, U> m: models)
            pairs.add(new Pair(m.get(), m.getResult(userInput)));
        pairs.sort(Comparator.comparingDouble(Pair::getD));
        List<T> result = new ArrayList<>();
        for(Pair p: pairs)
            result.add(p.getT());
        return result;
    }



}
