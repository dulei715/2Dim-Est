package ecnu.dll.construction.analysis.e_to_lp;

import cn.edu.dll.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.dll.differential_privacy.cdp.basic_struct.impl.OneNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.schemes.new_scheme.discretization.DiscretizedDiskScheme;

import java.util.List;

public class Norm1DAMLocalPrivacy extends DAMLocalPrivacy {


    private static final DistanceTor<TwoDimensionalIntegerPoint> norm1DistanceCalculator = new OneNormTwoDimensionalIntegerPointDistanceTor();

    @Deprecated
    public Norm1DAMLocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer sizeD, List<TwoDimensionalIntegerPoint> intermediateSetList, Integer sizeB, Double probabilityP, Double probabilityQ) {
        super(originalSetList, sizeD, intermediateSetList, sizeB, norm1DistanceCalculator, probabilityP, probabilityQ);
    }

    public Norm1DAMLocalPrivacy(DiscretizedDiskScheme damScheme) {
        super(damScheme, norm1DistanceCalculator);
    }
}
