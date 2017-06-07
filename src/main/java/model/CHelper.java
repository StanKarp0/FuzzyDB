package model;

/**
 * Created by Michał on 07.06.2017.
 */
public class CHelper {

    public double r(double x, double y){
        return Math.abs(x-y)/x;
    }

    //prosta
    public double straight(double x, double x1, double y1, double x2, double y2){
        double a = (y2 - y1)/(x2 - x1);
        double b = y1 - a * x1;
        return a*x+b;
    }

    //1/x
    /*public double inverse(double x, double x1, double y1, double x2, double y2){
        double a = (y2 - y1)/(x2 - x1);
        double b = y1 - a * x1;
        return a*x+b;
        return (1/((x*0.5)+1-x1))*y1;
    }*/
}
