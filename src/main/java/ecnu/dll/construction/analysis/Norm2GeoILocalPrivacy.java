package ecnu.dll.construction.analysis;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.OneNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.abstract_class.GeoILocalPrivacy;

import java.util.List;

public class Norm2GeoILocalPrivacy extends GeoILocalPrivacy {
    private static final DistanceTor<TwoDimensionalIntegerPoint> norm2DistanceCalculator = new TwoNormTwoDimensionalIntegerPointDistanceTor();

    public Norm2GeoILocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer setSizeK, Double[] massArray, Double epsilon, Double omega) {
        super(originalSetList, setSizeK, massArray, epsilon, omega, norm2DistanceCalculator);
    }
}
