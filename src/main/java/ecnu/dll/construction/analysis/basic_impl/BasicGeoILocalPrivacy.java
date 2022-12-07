package ecnu.dll.construction.analysis.basic_impl;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.abstract_class.GeoILocalPrivacy;

import java.util.List;

public class BasicGeoILocalPrivacy extends GeoILocalPrivacy {

    public BasicGeoILocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer setSizeK, Double[] massArray, Double epsilon, Double omega, DistanceTor<TwoDimensionalIntegerPoint> distanceTor) {
        super(originalSetList, setSizeK, massArray, epsilon, omega, distanceTor);
    }
}
