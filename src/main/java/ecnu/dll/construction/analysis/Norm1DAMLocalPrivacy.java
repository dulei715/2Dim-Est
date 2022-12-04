package ecnu.dll.construction.analysis;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.OneNormIntegerIdentityPairDistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;
import ecnu.dll.construction.analysis.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.abstract_class.RAMLocalPrivacy;

import java.util.List;

public class Norm1DAMLocalPrivacy extends DAMLocalPrivacy {


    private static final DistanceTor<IdentityPair<Integer>> distanceCalculator = new OneNormIntegerIdentityPairDistanceTor();

    public Norm1DAMLocalPrivacy(List<IdentityPair<Integer>> originalSetList, List<IdentityPair<Integer>> intermediateSetList, Integer sizeB, Double probabilityP, Double probabilityQ) {
        super(originalSetList, intermediateSetList, sizeB, distanceCalculator, probabilityP, probabilityQ);
    }





}
