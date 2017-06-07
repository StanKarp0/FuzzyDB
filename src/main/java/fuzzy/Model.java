package fuzzy;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by wojciech on 07.06.17.
 */
public class Model<T, U> {

    private final T t;
    private final BiFunction<T, U, Double> fnc;
    private final BiFunction<T, U, Double> binary;

    private Model(T t, BiFunction<T, U, Double> fnc, BiFunction<T, U, Double> binary) {
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
        return t -> new Model<>(t, fnc.andThen(output::crisp), binary);
    }

    public static <T, U> void sort(U userInput, List<Model<T, U>> models) {
        models.sort(Comparator.comparingDouble(model -> model.getResult(userInput)));
    }

}
