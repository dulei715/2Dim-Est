package privacy_test;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.lp_to_e.SubsetGeoITransformEpsilon;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import org.junit.Test;

public class SubsetGeoITransformEpsilonTest {
    @Test
    public void fun1() throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
//        double privacyBudget = 2.0;
        double privacyBudget = 0.5;
        double gridLength = 1.0;
//        double inputLength = 4.0;
        double inputLength = 7.0;
//        double kParameter = 0.5;
        double xLeft = 0.0;
        double yLeft = 0.0;
        DistanceTor<TwoDimensionalIntegerPoint> distanceTor = new TwoNormTwoDimensionalIntegerPointDistanceTor();

        double beginEpsilon = 0.5;
        double epsilonStep = 0.01;
        double endEpsilon = 2.5;

        DiscretizedSubsetExponentialGeoI geoIScheme = new DiscretizedSubsetExponentialGeoI(privacyBudget, gridLength, inputLength, xLeft, yLeft, distanceTor);

        SubsetGeoITransformEpsilon geoITransformEpsilon = new SubsetGeoITransformEpsilon(beginEpsilon, epsilonStep, endEpsilon, geoIScheme, SubsetGeoITransformEpsilon.Local_Privacy_Distance_Norm_Two);
        double epsilon = geoITransformEpsilon.getEpsilonByLocalPrivacy(1.7);
        System.out.println(epsilon);
        DiscretizedSubsetExponentialGeoI scheme = geoITransformEpsilon.getSubsetGeoISchemeByNewEpsilon(epsilon);
        System.out.println(scheme);
    }
}
