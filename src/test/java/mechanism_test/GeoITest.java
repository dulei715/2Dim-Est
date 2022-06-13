package mechanism_test;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.basicscheme.geo_i.DiscretizationGeoI;
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
}
