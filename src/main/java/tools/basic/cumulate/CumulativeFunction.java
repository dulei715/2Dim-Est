package tools.basic.cumulate;

import javafx.util.Pair;
import tools.basic.BasicArray;
import tools.struct.point.Point;

public class CumulativeFunction {
    public static double getCumulativeFunctionValue(double[] distributionData, int num) {
        double result = 0;
        for (int i = 0; i < num; i++) {
            result += distributionData[i];
        }
        return result;
    }

    public static double getCumulativeFunctionValue(double[][] distributionData, int xNum, int yNum) {
        double result = 0;
        for (int i = 0; i < xNum; i++) {
            for (int j = 0; j < yNum; j++) {
                result += distributionData[i][j];
            }
        }
        return result;
    }

}
