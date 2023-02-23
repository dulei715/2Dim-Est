package mechanism_test;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.basicscheme.geo_i.DiscretizationGeoI;
import ecnu.dll.construction.comparedscheme.sem_geo_i.SubsetExponentialGeoI;
import ecnu.dll.construction.comparedscheme.sem_geo_i.tool.SubsetExponentialGeoITool;
import org.junit.Test;

public class GeoITest {
    @Test
    public void fun2() {
        double unitSize = 0.5;
        double deltaTheta = Math.pow(10, -4);
        double deltaR = 0.002;
        double maxRadius = 100;

        double epsilon = 0.5;

        DiscretizationGeoI geoI = new DiscretizationGeoI(deltaTheta, deltaR, unitSize, maxRadius, epsilon);

        TwoDimensionalIntegerPoint originalPoint = new TwoDimensionalIntegerPoint(1, 2);
        for (int i = 0; i < 10; i++) {
            TwoDimensionalIntegerPoint noisePoint = geoI.getIntegerNoisePoint(originalPoint);
            System.out.println(noisePoint);
        }




    }

    @Test
    public void fun3() {
        double unitSize = 0.5;
        double deltaTheta = 0.0004;
        double deltaR = 0.001;
        double maxRadius = 100;

        double epsilon = 0.5;

        DiscretizationGeoI geoI = new DiscretizationGeoI(deltaTheta, deltaR, unitSize, maxRadius, epsilon);

        TwoDimensionalIntegerPoint originalPoint = new TwoDimensionalIntegerPoint(1, 2);
        for (int i = 0; i < 10; i++) {
            TwoDimensionalIntegerPoint noisePoint = geoI.getIntegerNoisePoint(originalPoint);
            System.out.println(noisePoint);
        }




    }

    @Test
    public void fun4() {
        double unitSize = 50;
        double deltaTheta = 0.0001;
        double deltaR = 0.001;
        double maxRadius = 40000;

        double epsilon = 0.5;

        DiscretizationGeoI geoI = new DiscretizationGeoI(deltaTheta, deltaR, unitSize, maxRadius, epsilon);

        TwoDimensionalIntegerPoint originalPoint = new TwoDimensionalIntegerPoint(1, 2);
        for (int i = 0; i < 10; i++) {
            TwoDimensionalIntegerPoint noisePoint = geoI.getIntegerNoisePoint(originalPoint);
            System.out.println(noisePoint);
        }

    }

    @Test
    public void fun5() {
        int sizeD = 2;
        double epsilon = 0.5;
        double totalDistance = SubsetExponentialGeoITool.get2NormTotalDistanceInGrid(sizeD);
        System.out.println(totalDistance);
        int kValue = SubsetExponentialGeoITool.getSetSizeKWithMeanEpsilon(sizeD * sizeD, totalDistance, epsilon);
        System.out.println(kValue);
    }

    @Test
    public void fun6() {
        int maxSizeD = 40;
        double[] epsilonArray = Constant.ALTER_PRIVACY_BUDGET_ARRAY;
//        double[] epsilonArray = BasicArray.getIncreasedoubleNumberArray(0.5, 0.01, 2);
//        double[] epsilonArray = BasicArray.getIncreasedoubleNumberArray(2.6, 0.01, 2.6);
//        double[] epsilonArray = BasicArray.getIncreasedoubleNumberArray(1, 0.01, 1);
//        double[] epsilonArray = BasicArray.getIncreasedoubleNumberArray(0.61, 0.01, 3.3);
        double totalDistance, tempEpsilon;
        int tempK;
        for (int d = 1; d <= maxSizeD; d++) {
            totalDistance = SubsetExponentialGeoITool.get2NormTotalDistanceInGrid(d);
            System.out.print("sizeD = " + d + ": ");
            for (int epsilonIndex = 0; epsilonIndex < epsilonArray.length; epsilonIndex++) {
                tempEpsilon = epsilonArray[epsilonIndex];
                tempK = SubsetExponentialGeoITool.getSetSizeKWithMeanEpsilon(d*d, totalDistance, tempEpsilon);
                System.out.print(tempK + " ");
            }
            System.out.println();
        }
    }

}
