package ecnu.dll.construction.analysis.e_to_lp;

import cn.edu.dll.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.dll.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.GeoILocalPrivacy;
import ecnu.dll.construction.other_schemes.compared_schemes.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;

import java.util.List;


public class Norm2GeoILocalPrivacy extends GeoILocalPrivacy {
    private static final DistanceTor<TwoDimensionalIntegerPoint> norm2DistanceCalculator = new TwoNormTwoDimensionalIntegerPointDistanceTor();

    @Deprecated
    public Norm2GeoILocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer setSizeK, Double[] massArray, Double epsilon, Double omega) {
        super(originalSetList, setSizeK, massArray, epsilon, omega, norm2DistanceCalculator);
    }

    public Norm2GeoILocalPrivacy(DiscretizedSubsetExponentialGeoI geoIScheme) {
        super(geoIScheme, norm2DistanceCalculator);
    }
}
