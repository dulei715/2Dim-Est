package tools.collection;

import java.util.List;

public class ListUtils {
    public static Double sum(List<Double> list) {
        Double result = new Double(0);
        for (Double elem : list) {
            result += elem;
        }
        return result;
    }

    public static double getSum(List<double[]> list) {
        double result = 0;
        for (double[] doubles : list) {
            for (double dElem : doubles) {
                result += dElem;
            }
        }
        return result;
    }

}
