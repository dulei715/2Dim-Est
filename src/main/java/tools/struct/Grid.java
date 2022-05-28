package tools.struct;

import tools.RandomUtil;
import tools.io.print.MyPrint;

public class Grid {
    public static Integer[] toGridIndex(Double gridLen, Double... indexes) {
        Integer[] result = new Integer[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            result[i] = (int)Math.floor(indexes[i] / gridLen);
        }
        return result;
    }

    public static void main(String[] args) {
        int len = 10;
        double gridLen = 5.0;
        Double[] inputValues = new Double[len];
        Integer[] outputValues;
        for (int i = 0; i < len; i++) {
            inputValues[i] = RandomUtil.getRandomDouble(-100.0, 100.0);
        }
        outputValues = Grid.toGridIndex(gridLen, inputValues);
        MyPrint.showDoubleArray(inputValues);
        MyPrint.showIntegerArray(outputValues);
    }
}
