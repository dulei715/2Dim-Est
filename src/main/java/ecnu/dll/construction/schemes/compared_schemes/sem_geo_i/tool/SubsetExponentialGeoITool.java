package ecnu.dll.construction.schemes.compared_schemes.sem_geo_i.tool;


import cn.edu.dll.struct.grid.Grid;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

import java.util.List;

public class SubsetExponentialGeoITool {

    public static double get2NormTotalDistanceInGrid(int sideLengthD) {
        List<TwoDimensionalIntegerPoint> inputElementList = Grid.generateTwoDimensionalIntegerPoint(sideLengthD, 0, 0);
        double differentElementsDistanceSum = 0.0;
        double tempDistance;
        TwoDimensionalIntegerPoint elementA, elementB;
        for (int i = 0; i < inputElementList.size(); i++) {
            elementA = inputElementList.get(i);
            // 处理 j>i 情况
            for (int j = i + 1; j < inputElementList.size(); j++) {
                elementB = inputElementList.get(j);
                tempDistance = elementA.getDistance(elementB);
                differentElementsDistanceSum += tempDistance * 2;
            }

        }
        return differentElementsDistanceSum;
    }
    public static int getSetSizeKWithMeanEpsilon(int inputElementSize, double differentElementsDistanceSum, double epsilon) {
        if (inputElementSize < 2) {
            return 1;
        }
        double epsilonMean = differentElementsDistanceSum * epsilon / (inputElementSize * (inputElementSize - 1));
        return  (int) Math.ceil(inputElementSize / (Math.exp(epsilonMean) + 1));
    }


}
