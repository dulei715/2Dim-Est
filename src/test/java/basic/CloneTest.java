package basic;

import cn.edu.dll.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import ecnu.dll.construction.schemes.compared_schemes.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import org.junit.Test;

public class CloneTest {
    @Test
    public void fun1() throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
        double privacyBudget = 2.0;
        double gridLength = 1.0;
        double inputLength = 4.0;
//        double kParameter = 0.5;
        double xLeft = 0.0;
        double yLeft = 0.0;
        DiscretizedSubsetExponentialGeoI geoI = new DiscretizedSubsetExponentialGeoI(privacyBudget, gridLength, inputLength, xLeft, yLeft, new TwoNormTwoDimensionalIntegerPointDistanceTor());
        Integer setSizeK = geoI.getSetSizeK();
        System.out.println(setSizeK);
        DiscretizedSubsetExponentialGeoI cloneGeoI = (DiscretizedSubsetExponentialGeoI) geoI.clone();
        Integer cloneSetSizeK = cloneGeoI.getSetSizeK();
        System.out.println(cloneSetSizeK);
    }
}
