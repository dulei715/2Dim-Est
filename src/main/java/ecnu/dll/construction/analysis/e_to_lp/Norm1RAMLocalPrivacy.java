package ecnu.dll.construction.analysis.e_to_lp;

import cn.edu.dll.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.dll.differential_privacy.cdp.basic_struct.impl.OneNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.RAMLocalPrivacy;
import ecnu.dll.construction.newscheme.discretization.DiscretizedRhombusScheme;

import java.util.List;

public class Norm1RAMLocalPrivacy extends RAMLocalPrivacy {


    private static final DistanceTor<TwoDimensionalIntegerPoint> norm1DistanceCalculator = new OneNormTwoDimensionalIntegerPointDistanceTor();

    @Deprecated
    public Norm1RAMLocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer sizeD, List<TwoDimensionalIntegerPoint> intermediateSetList, Integer sizeB, Double probabilityP, Double probabilityQ) {
        super(originalSetList, sizeD, intermediateSetList, sizeB, norm1DistanceCalculator, probabilityP, probabilityQ);
    }

    public Norm1RAMLocalPrivacy(DiscretizedRhombusScheme ramScheme) {
        super(ramScheme, norm1DistanceCalculator);
    }

    @Override
    protected double getSquareDistance() {
        return Double.valueOf(this.sizeD * (this.sizeD + 1) * (this.sizeD - 1) / 3 * 2 * this.sizeD * this.sizeD);
    }


}
