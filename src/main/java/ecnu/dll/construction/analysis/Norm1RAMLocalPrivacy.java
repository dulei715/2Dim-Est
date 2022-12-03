package ecnu.dll.construction.analysis;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.OneNormIntegerIdentityPairDistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;
import ecnu.dll.construction.analysis.abstract_class.RAMLocalPrivacy;

import java.util.List;

public class Norm1RAMLocalPrivacy extends RAMLocalPrivacy {


    private static final DistanceTor<IdentityPair<Integer>> distanceCalculator = new OneNormIntegerIdentityPairDistanceTor();

    public Norm1RAMLocalPrivacy(List<IdentityPair<Integer>> originalSetList, List<IdentityPair<Integer>> intermediateSetList, Integer sizeB, Double probabilityP, Double probabilityQ) {
        super(originalSetList, intermediateSetList, sizeB, distanceCalculator, probabilityP, probabilityQ);
    }


    @Override
    protected double getSquareDistance() {
        return Double.valueOf(this.sizeD * (this.sizeD + 1) * (this.sizeD - 1) / 3 * 2 * this.sizeD * this.sizeD);
    }


}
