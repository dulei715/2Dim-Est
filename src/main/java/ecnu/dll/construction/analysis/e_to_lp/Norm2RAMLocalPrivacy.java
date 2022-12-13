package ecnu.dll.construction.analysis.e_to_lp;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.RAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.tools.CellDistanceTool;

import java.util.List;

public class Norm2RAMLocalPrivacy extends RAMLocalPrivacy {


    private static final DistanceTor<TwoDimensionalIntegerPoint> norm2DistanceCalculator = new TwoNormTwoDimensionalIntegerPointDistanceTor();

    public Norm2RAMLocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer sizeD, List<TwoDimensionalIntegerPoint> intermediateSetList, Integer sizeB, Double probabilityP, Double probabilityQ) {
        super(originalSetList, sizeD, intermediateSetList, sizeB, Norm2RAMLocalPrivacy.norm2DistanceCalculator, probabilityP, probabilityQ);
    }


    @Override
    protected double getSquareDistance() {
        return CellDistanceTool.getTotalDistanceWithinGivenCollection(super.originalSetList, norm2DistanceCalculator);
    }


}
