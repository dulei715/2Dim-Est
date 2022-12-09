package ecnu.dll.construction.analysis.basic_impl;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.abstract_class.RAMLocalPrivacy;
import ecnu.dll.construction.analysis.tools.CellDistanceTool;

import java.util.List;

public class BasicRAMLocalPrivacy extends RAMLocalPrivacy {
    public BasicRAMLocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer sizeD, List<TwoDimensionalIntegerPoint> intermediateSetList, Integer sizeB, DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator, Double probabilityP, Double probabilityQ) {
        super(originalSetList, sizeD, intermediateSetList, sizeB, distanceCalculator, probabilityP, probabilityQ);
    }

    @Override
    protected double getSquareDistance() {
        return CellDistanceTool.getTotalDistanceWithinGivenCollection(super.originalSetList, distanceCalculator);
    }
}
