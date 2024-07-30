package mechanism_test;

import cn.edu.dll.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import ecnu.dll.construction.other_schemes.compared_schemes.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import org.junit.Test;

public class SubsetExponentialGeoITest {
    @Test
    public void fun1() throws InstantiationException, IllegalAccessException {
        Double epsilon  = 0.1;
        Double cellLength = 0.1;
        Double inputLength = 2D;
        double xBound = 0;
        double yBound = 0;
        DiscretizedSubsetExponentialGeoI scheme = new DiscretizedSubsetExponentialGeoI(epsilon, cellLength, inputLength, xBound, yBound, new TwoNormTwoDimensionalIntegerPointDistanceTor());
        System.out.println("haha");
    }
}
