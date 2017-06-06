package fuzzy;

/**
 * Created by wojciech on 06.06.17.
 */
@FunctionalInterface
public interface FuzzyFnc {

    double apply(double v);

    static FuzzyFnc trapmf(double a, double b, double c, double d) {
        final double bma = b - a, dmc = d - c;
        return x -> Math.max(Math.min(Math.min((x - a) / bma, (d - x) / dmc), 1.), 0.);
    }

    static FuzzyFnc trimf(double a, double b, double c) {
        final double bma = b - a, cmb = c - b;
        return x -> Math.max(Math.min((x - a) / bma, (c - x) / cmb), 0.);
    }

    static FuzzyFnc gaussmf(double sig, double c) {
        final double a = -1. / (2. * sig * sig);
        return x -> Math.exp(a * Math.pow(x - c, 2.));
    }


}
