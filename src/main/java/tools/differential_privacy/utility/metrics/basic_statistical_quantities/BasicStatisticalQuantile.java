package tools.differential_privacy.utility.metrics.basic_statistical_quantities;

import org.apache.commons.math3.util.Pair;

public class BasicStatisticalQuantile {



    private static final double[] intervalArray = new double[] {
        0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9
    };

    /**
     * 返回均值的差的绝对值
     * @param realMean
     * @param estimatedMean
     * @return
     */
    public static double getMean(double realMean, double estimatedMean) {
        return Math.abs(realMean - estimatedMean);
    }

    public static double getVariance(double realVariance, double estimatedVariance) {
        return Math.abs(realVariance - estimatedVariance);
    }


    private static Pair<Integer, Double> getQuantilePointIndex(double[] data, double upperBoundCumulateValue, int beginIndex, double initialValue) {
        int len = data.length;
        if (beginIndex < 0 || initialValue > upperBoundCumulateValue) {
            throw new RuntimeException("The index should be positive! Besides initial value should be no larger than upperBoundCumulateValue!");
        }
        if (beginIndex >= len) {
            return new Pair<Integer, Double>(len - 1, initialValue);
        }
        double value = initialValue, tempValue;
        int i;
        for (i = beginIndex; i < len; i++) {
            tempValue = value + data[i];
            if (tempValue > upperBoundCumulateValue) {
                break;
            }
            value = tempValue;

        }
        return new Pair<Integer, Double>((i-1), value);

    }


    public static double getQuantilesValue(double[] realData, double[] statisticData) {
        double differSum = 0;
        int dataLen = realData.length;
        if (statisticData.length != realData.length) {
            throw new RuntimeException("The length of statistic data is not equaled to the real data");
        }
        double realDataCumulate = 0, statisticDataCumulate = 0;
        int realDataIndex = -1, statisticDataIndex = -1;
        Pair<Integer, Double> tempRealPair, tempStatisticPair;
        for (int i = 0; i < intervalArray.length; i++) {
            tempRealPair = getQuantilePointIndex(realData, intervalArray[i], realDataIndex + 1, realDataCumulate);
            realDataIndex = tempRealPair.getKey();
            realDataCumulate = tempRealPair.getValue();

            tempStatisticPair = getQuantilePointIndex(statisticData, intervalArray[i], statisticDataIndex + 1, statisticDataCumulate);
            statisticDataIndex = tempStatisticPair.getKey();
            statisticDataCumulate = tempStatisticPair.getValue();

            differSum += Math.abs(realDataCumulate - statisticDataCumulate);

        }
        return differSum / intervalArray.length;
    }

    public static void main(String[] args) {





    }





























}
