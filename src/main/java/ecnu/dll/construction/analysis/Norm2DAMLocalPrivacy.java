package ecnu.dll.construction.analysis;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormIntegerIdentityPairDistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.abstract_class.RAMLocalPrivacy;
import ecnu.dll.construction.analysis.tools.CellDistanceTool;

import java.util.List;

public class Norm2DAMLocalPrivacy extends DAMLocalPrivacy {


    private static final DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator = new TwoNormTwoDimensionalIntegerPointDistanceTor();

    public Norm2DAMLocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, List<TwoDimensionalIntegerPoint> intermediateSetList, Integer sizeB, Double probabilityP, Double probabilityQ) {
        super(originalSetList, intermediateSetList, sizeB, distanceCalculator, probabilityP, probabilityQ);
    }

}
