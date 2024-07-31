package privacy_test;

import cn.edu.dll.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.dll.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Initialized;
import ecnu.dll.construction.analysis.lp_to_e.version_1.SubsetGeoITransformEpsilon;
import ecnu.dll.construction.schemes.compared_schemes.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
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

    @Test
    public void fun2() {
//        Double epsilon = 0.7;
        Double epsilon = 5D;
        Double[] sizeDArray = new Double[]{
                1D, 5D, 10D, 15D, 20D
        };
        Double sizeD;
        for (int i = 0; i < sizeDArray.length; i++) {
            sizeD = sizeDArray[i];
            double tempLocalPrivacy = Initialized.damELPExtendedTable.getLowerBoundLocalPrivacyByEpsilon(sizeD, epsilon);
            double transformedEpsilon = Initialized.subGeoIELPExtendedTable.getEpsilonByUpperBoundLocalPrivacy(sizeD, tempLocalPrivacy);
            System.out.println(transformedEpsilon);
        }
    }
    @Test
    public void fun3() {
//        Double epsilon = 0.7;
        Double[] epsilonArray = new Double[]{
                0.7, 1.4, 2.1, 2.8, 3.5
        };
        Double epsilon;
        Double sizeD = 10D;
        for (int i = 0; i < epsilonArray.length; i++) {
            epsilon = epsilonArray[i];
            double tempLocalPrivacy = Initialized.damELPExtendedTable.getLowerBoundLocalPrivacyByEpsilon(sizeD, epsilon);
            double transformedEpsilon = Initialized.subGeoIELPExtendedTable.getEpsilonByUpperBoundLocalPrivacy(sizeD, tempLocalPrivacy);
            System.out.println(transformedEpsilon);
        }
    }



}
