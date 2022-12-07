package ecnu.dll.construction.analysis.basic_impl;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.abstract_class.RAMLocalPrivacy;
import ecnu.dll.construction.analysis.tools.CellDistanceTool;

import java.util.List;

public class BasicDAMLocalPrivacy extends DAMLocalPrivacy {
    public BasicDAMLocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, List<TwoDimensionalIntegerPoint> intermediateSetList, Integer sizeB, DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator, Double probabilityP, Double probabilityQ) {
        super(originalSetList, intermediateSetList, sizeB, distanceCalculator, probabilityP, probabilityQ);
    }

}
