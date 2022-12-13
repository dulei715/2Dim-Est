package ecnu.dll.construction.analysis.e_to_lp.basic_impl;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.DAMLocalPrivacy;

import java.util.List;

public class BasicDAMLocalPrivacy extends DAMLocalPrivacy {
    public BasicDAMLocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer sizeD, List<TwoDimensionalIntegerPoint> intermediateSetList, Integer sizeB, DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator, Double probabilityP, Double probabilityQ) {
        super(originalSetList, sizeD, intermediateSetList, sizeB, distanceCalculator, probabilityP, probabilityQ);
    }

}
