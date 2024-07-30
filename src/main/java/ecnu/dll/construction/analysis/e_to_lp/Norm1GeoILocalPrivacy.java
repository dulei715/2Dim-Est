package ecnu.dll.construction.analysis.e_to_lp;

import cn.edu.dll.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.dll.differential_privacy.cdp.basic_struct.impl.OneNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.GeoILocalPrivacy;
import ecnu.dll.construction.other_schemes.compared_schemes.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;

import java.util.List;


public class Norm1GeoILocalPrivacy extends GeoILocalPrivacy {
    private static final DistanceTor<TwoDimensionalIntegerPoint> norm1DistanceCalculator = new OneNormTwoDimensionalIntegerPointDistanceTor();

    @Deprecated
    public Norm1GeoILocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer setSizeK, Double[] massArray, Double epsilon, Double omega) {
        super(originalSetList, setSizeK, massArray, epsilon, omega, norm1DistanceCalculator);
    }

    public Norm1GeoILocalPrivacy(DiscretizedSubsetExponentialGeoI geoIScheme) {
        super(geoIScheme, norm1DistanceCalculator);
    }
}
