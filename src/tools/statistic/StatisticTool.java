package tools.statistic;

import com.sun.deploy.util.ArrayUtil;
import tools.basic.BasicArray;
import tools.io.print.MyPrint;
import tools.struct.point.TwoDimensionalIntegerPoint;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StatisticTool {

    public static Double getLogLikelihood(final Double[] value, final Double[][] matrix) {
        Double sum = 0.0;
        Double innerSum;
        for (int k = 0; k < matrix.length; k++) {
            innerSum = 0.0;
            for (int i = 0; i < value.length; i++) {
                innerSum += value[i] * matrix[k][i];
            }
            sum += Math.log(innerSum);
        }
        return sum;
    }


    public static Double[] getExpectationMaximization(final Double[][] matrix, final Double[] noiseValueCountArray, Double stopValue, final Double[] initialValueCountArray) {
        Double[] pValueArray = new Double[initialValueCountArray.length];
        Double[] beforeValueArray = new Double[initialValueCountArray.length];
        Double[] newValueArray = new Double[initialValueCountArray.length];
        System.arraycopy(initialValueCountArray, 0, newValueArray, 0, initialValueCountArray.length);

        Double innerSum, innerInnerSum, pSum, beforeLogLikelihood;
        Double newLogLikelihood = getLogLikelihood(newValueArray, matrix);
        do {
            System.arraycopy(newValueArray, 0, beforeValueArray, 0, newValueArray.length);
            beforeLogLikelihood = newLogLikelihood;
            for (int i = 0; i < pValueArray.length; i++) {
                innerSum = 0.0;
                for (int j = 0; j < noiseValueCountArray.length; j++) {
                    innerInnerSum = 0.0;
                    for (int k = 0; k < beforeValueArray.length; k++) {
                        innerInnerSum += matrix[j][k] * beforeValueArray[k];
                    }
                    innerSum += noiseValueCountArray[j] * matrix[j][i] / innerInnerSum;
                }
                pValueArray[i] = beforeValueArray[i] * innerSum;
            }

            pSum = BasicArray.getSum(pValueArray);

            for (int i = 0; i < pValueArray.length; i++) {
                newValueArray[i] = pValueArray[i] / pSum;
            }
            newLogLikelihood = getLogLikelihood(newValueArray, matrix);
        } while (Math.abs(newLogLikelihood - beforeLogLikelihood) >= stopValue);
        return newValueArray;
    }

    /**
     *
     * @param values
     * @param binomialCoefficients 必须是奇数个
     * @return
     */
    private static Double[] getSmooth(Double[] values, Integer[] binomialCoefficients) {
        Double[] ratios = new Double[binomialCoefficients.length];
        Double outerRatioSum;
        Integer sum = BasicArray.getSum(binomialCoefficients);
        for (int i = 0; i < ratios.length; i++) {
            ratios[i] = binomialCoefficients[i] * 1.0 / sum;
        }
        Integer midIndex = binomialCoefficients.length / 2;
        Double[] result = new Double[values.length];
        for (int i = 0; i < midIndex; i++) {
             outerRatioSum = 0.0;
            for (int j = 0; j <= midIndex - i; j++) {
                outerRatioSum += ratios[j];
            }
            result[i] = values[0] * outerRatioSum;
            for (int j = midIndex - i + 1, k = 1; j < ratios.length; j++, k++) {
                result[i] += values[k] * ratios[j];
            }
        }

        for (int i = result.length - midIndex; i < result.length; i++) {
            outerRatioSum = 0.0;
            for (int j = midIndex + result.length - i - 1; j < ratios.length; j++) {
                outerRatioSum += ratios[j];
            }
            result[i] = values[result.length - 1] * outerRatioSum;
//            for (int j = 0; j < midIndex + result.length - i - 1; j++) {
//                result[i] += values[] * binomialCoefficients[j]
//            }
            for (int j = 0, k = i - midIndex; k < result.length - 1; j++, k++) {
                result[i] += values[k] * ratios[j];
            }
        }

        for (int i = midIndex; i < result.length - midIndex; i++) {
            result[i] = 0.0;
            for (int j = 0; j < ratios.length; j++) {
                result[i] += values[i-midIndex+j] * ratios[j];
            }
        }
        return result;
    }

    public static Double[] getExpectationMaximizationSmooth(final Double[][] matrix, final Double[] noiseValueCountArray, Double stopValue, Integer[] binomialCoefficients, final Double[] initialValueCountArray) {
        Double[] pValueArray = new Double[initialValueCountArray.length];
        Double[] beforeValueArray = new Double[initialValueCountArray.length];
        Double[] newValueArray = new Double[initialValueCountArray.length];
        System.arraycopy(initialValueCountArray, 0, newValueArray, 0, initialValueCountArray.length);

        Double innerSum, innerInnerSum, pSum, beforeLogLikelihood;
        Double newLogLikelihood = getLogLikelihood(newValueArray, matrix);
        do {
            System.arraycopy(newValueArray, 0, beforeValueArray, 0, newValueArray.length);
            beforeLogLikelihood = newLogLikelihood;
            for (int i = 0; i < pValueArray.length; i++) {
                innerSum = 0.0;
                for (int j = 0; j < noiseValueCountArray.length; j++) {
                    innerInnerSum = 0.0;
                    for (int k = 0; k < beforeValueArray.length; k++) {
                        innerInnerSum += matrix[j][k] * beforeValueArray[k];
                    }
                    innerSum += noiseValueCountArray[j] * matrix[j][i] / innerInnerSum;
                }
                pValueArray[i] = beforeValueArray[i] * innerSum;
            }

            pSum = BasicArray.getSum(pValueArray);

            for (int i = 0; i < pValueArray.length; i++) {
                newValueArray[i] = pValueArray[i] / pSum;
            }
            newValueArray = getSmooth(newValueArray, binomialCoefficients);
            newLogLikelihood = getLogLikelihood(newValueArray, matrix);
        } while (Math.abs(newLogLikelihood - beforeLogLikelihood) >= stopValue);
        return newValueArray;
    }


    /**
     * 统计整数坐标点的个数，偏移量x为+b, y为+b
     * @param collection
     * @return
     */
    public static Map<TwoDimensionalIntegerPoint, Integer> countTwoDimensionalIntegerPointNumber(Collection<TwoDimensionalIntegerPoint> collection) {
        Map<TwoDimensionalIntegerPoint, Integer> resultMap = new HashMap<>();
        TwoDimensionalIntegerPoint tempPoint;
        Integer tempCount;
        for (TwoDimensionalIntegerPoint point : collection) {
            tempCount = resultMap.get(point);
            if (tempCount == null) {
                resultMap.put(point, 1);
            } else {
                ++tempCount;
                resultMap.put(point, tempCount);
            }
        }
        return resultMap;
    }




    public static void main(String[] args) {
        Double[] initialCountArray = new Double[]{50.0/3, 50.0/3, 50.0/3};
        Double[] noiseCountArray = new Double[]{10.0, 20.0, 15.0, 5.0};
        Double[][] matrix = new Double[][]{
                {0.2, 0.2, 0.3},
                {0.4, 0.5, 0.3},
                {0.3, 0.1, 0.2},
                {0.1, 0.2, 0.2},
        };
        Double tempLikelihood = getLogLikelihood(initialCountArray, matrix);
//        System.out.println(tempLikelihood);

//        Double[] result = getExpectationMaximization(matrix, noiseCountArray, 0.1, initialCountArray);
//        MyPrint.showDoubleArray(result);

        for (int i = 0; i < 10; i++) {
            MyPrint.showDoubleArray(getExpectationMaximization(matrix, noiseCountArray, 0.0001*(10-i), initialCountArray));
        }

        MyPrint.showSplitLine("*", 150);
        Integer[] binomialCoefficients = new Integer[]{1, 2, 1};

        for (int i = 0; i < 10; i++) {
            MyPrint.showDoubleArray(getExpectationMaximizationSmooth(matrix, noiseCountArray, 0.0001*(10-i), binomialCoefficients, initialCountArray));
        }


    }

    public static void main1(String[] args) {
//        Double[] values = new Double[]{4.0, 12.0, 8.0, 4.0, 16.0, 12.0};
//        Integer[] binomialCoefficients = new Integer[]{1, 2, 1};
//        Double[] values = new Double[]{32.0, 16.0, 48.0, 80.0, 128.0, 112.0};
        Double[] values = new Double[]{32.0, 16.0, 48.0, 80.0, 128.0, 112.0, 128.0, 640.0, 16.0};
        Integer[] binomialCoefficients = new Integer[]{1, 4, 6, 4, 1};
        Double[] result = getSmooth(values, binomialCoefficients);
        MyPrint.showDoubleArray(result);
    }

}
