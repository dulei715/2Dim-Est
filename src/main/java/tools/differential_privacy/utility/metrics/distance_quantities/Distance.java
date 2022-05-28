package tools.differential_privacy.utility.metrics.distance_quantities;


import tools.basic.cumulate.CumulativeFunction;
import tools.differential_privacy.utility.metrics.basic_statistical_quantities.BasicStatisticalQuantile;

public class Distance {
    /**
     * Get L1 distance
     * @param dataA
     * @param dataB
     * @return
     */
    public static double getL1Distance(double[] dataA, double[] dataB) {
        double result = 0;
        for (int i = 0; i < dataA.length; i++) {
            result += Math.abs(dataA[i] - dataB[i]);
        }
        return result;
    }

    /**
     * Get L2 distance
     * @param dataA
     * @param dataB
     * @return
     */
    public static double getL2Distance(double[] dataA, double[] dataB) {
        double result = 0;
        for (int i = 0; i < dataA.length; i++) {
            result += Math.pow(dataA[i] - dataB[i], 2);
        }
        return Math.sqrt(result);
    }

    /**
     * Get KL divergence
     * @param dataA
     * @param dataB
     * @return
     */
    public static double getKLDivergence(double[] dataA, double[] dataB) {
        double result = 0;
        for (int i = 0; i < dataA.length; i++) {
            result += dataA[i] * Math.log(dataA[i]/dataB[i]);
        }
        return result;
    }



    /**
     * Get Kolmogorov-Smirnov distance
     * @param distributionDataA
     * @param distributionDataB
     * @param num
     * @return
     */
    public static double getKSDistance(double[] distributionDataA, double[] distributionDataB, int num) {
        double result = 0, temp;
        for (int i = 1; i <= num; i++) {
            temp = Math.abs(CumulativeFunction.getCumulativeFunctionValue(distributionDataA, i) - CumulativeFunction.getCumulativeFunctionValue(distributionDataB, i));
            if (temp > result) {
                result = temp;
            }
        }
        return result;
    }


}
