package ecnu.dll.construction.analysis.e_to_lp;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;

import java.util.List;

public class Norm2DAMLocalPrivacy extends DAMLocalPrivacy {


    private static final DistanceTor<TwoDimensionalIntegerPoint> norm2DistanceCalculator = new TwoNormTwoDimensionalIntegerPointDistanceTor();

    @Deprecated
    public Norm2DAMLocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer sizeD, List<TwoDimensionalIntegerPoint> intermediateSetList, Integer sizeB, Double probabilityP, Double probabilityQ) {
        super(originalSetList, sizeD, intermediateSetList, sizeB, norm2DistanceCalculator, probabilityP, probabilityQ);
    }

    public Norm2DAMLocalPrivacy(DiscretizedDiskScheme damScheme) {
        super(damScheme, norm2DistanceCalculator);
    }
}
