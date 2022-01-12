package tools.differential_privacy.utility.metrics.distance_quantities;

import javafx.util.Pair;
import tools.basic.BasicArray;
import tools.basic.cumulate.CumulativeFunction;
import tools.io.print.MyPrint;
import tools.struct.point.Point;
import tools.struct.point.TwoDimensionalPointTool;

public class WassersteinDistance {
    private Point[] points = null;

    /**
     * Get Wasserstein distance
     * @param distributionDataA
     * @param distributionDataB
     * @return
     */
    public static double getWassersteinDistance(double[] distributionDataA, double[] distributionDataB) {
        if (distributionDataA.length != distributionDataB.length) {
            throw new RuntimeException("The two distribution's element size is not equal!");
        }
        int length = distributionDataA.length;
        double result = 0;
        for (int i = 1; i <= length; i++) {
            result += Math.abs(CumulativeFunction.getCumulativeFunctionValue(distributionDataA, i) - CumulativeFunction.getCumulativeFunctionValue(distributionDataB, i));
        }
        return result;
    }

//    public static double getWassersteinDistanceError(double[][] distributionDataA, double[][] distributionDataB) {
//        if (distributionDataA.length != distributionDataB.length || distributionDataA[0].length != distributionDataB[0].length) {
//            throw new RuntimeException("The two distribution's element size is not equal!");
//        }
//        double result = 0;
//        int xLen = distributionDataA.length;
//        int yLen = distributionDataA[0].length;
//        int minLen = BasicArray.getMinValuePair(xLen, yLen).getValue();
//
//        double[][] diagValueA = new double[xLen][yLen];
//        double[][] diagValueB = new double[xLen][yLen];
//        BasicArray.setDoubleArrayToZero(diagValueA);
//        BasicArray.setDoubleArrayToZero(diagValueB);
//
//
//        int totalLen = xLen + yLen - 1;
//        for (int t = 0; t < totalLen; t++) {
//            int row = 0, column = 0, n = t;
//            while ((n+1) > 0) {
//                row = n;
//                column = t - row;
//                if (row < xLen && column < yLen) {
////                    System.out.println(distributionDataA[row][column]);
//                    diagValueA[row][column] = distributionDataA[row][column];
//                    diagValueB[row][column] = distributionDataB[row][column];
//                    int topIndex = row - 1;
//                    int leftIndex = column - 1;
//                    if (topIndex >= 0) {
//                        diagValueA[row][column] += diagValueA[topIndex][column];
//                        diagValueB[row][column] += diagValueB[topIndex][column];
//                    }
//                    if (leftIndex >= 0) {
//                        diagValueA[row][column] += diagValueA[row][leftIndex];
//                        diagValueB[row][column] += diagValueB[row][leftIndex];
//                    }
//                    System.out.println(diagValueA[row][column] + "; " + diagValueB[row][column]);
//                }
//                --n;
//            }
//            MyPrint.showSplitLine("*", 100);
//        }
//        return result;
//    }
    public static double getWassersteinDistance(double[][] distributionDataA, double[][] distributionDataB) {
        if (distributionDataA.length != distributionDataB.length || distributionDataA[0].length != distributionDataB[0].length) {
            throw new RuntimeException("The two distribution's element size is not equal!");
        }
        double result = 0;
        int xLen = distributionDataA.length;
        int yLen = distributionDataA[0].length;
        for (int i = 1; i <= xLen; i++) {
            for (int j = 1; j <= yLen; j++) {
//                System.out.println(CumulativeFunction.getCumulativeFunctionValue(distributionDataA, i, j));
                result += Math.abs(CumulativeFunction.getCumulativeFunctionValue(distributionDataA, i, j) - CumulativeFunction.getCumulativeFunctionValue(distributionDataB, i, j));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        double[][] dataA = new double[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };

        double[][] dataB = new double[][]{
                {1, 5, 3},
                {4, 2, 6},
                {7, 8, 9},
                {12, 11, 10}
        };
        double result = getWassersteinDistance(dataA, dataB);
        System.out.println(result);

    }

}
