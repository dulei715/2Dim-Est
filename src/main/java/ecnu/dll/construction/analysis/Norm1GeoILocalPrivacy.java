package ecnu.dll.construction.analysis;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.OneNormIntegerIdentityPairDistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.OneNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.abstract_class.GeoILocalPrivacy;

import java.util.List;

public class Norm1GeoILocalPrivacy extends GeoILocalPrivacy {
    private static final DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator = new OneNormTwoDimensionalIntegerPointDistanceTor();

    public Norm1GeoILocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Double[] massArray, Double epsilon, Double omega) {
        super(originalSetList, massArray, epsilon, omega, distanceCalculator);
    }
}
