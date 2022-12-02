package ecnu.dll.construction.analysis;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;

import java.util.List;

public abstract class RAMLocalPrivacy extends TransformLocalPrivacy <IdentityPair<Integer>>{

    protected DistanceTor<IdentityPair<Integer>> distanceCalculator = null;

    public RAMLocalPrivacy(List<IdentityPair<Integer>> originalSetList, List<IdentityPair<Integer>> intermediateSetList, DistanceTor<IdentityPair<Integer>> distanceCalculator) {
        super(originalSetList, intermediateSetList);
        this.distanceCalculator = distanceCalculator;
    }


    @Override
    protected double getTotalProbabilityGivenIntermediateElement(IdentityPair<Integer> intermediateElement) {
        return 0;
    }

    @Override
    protected double getPairWeightedDistance(IdentityPair<Integer> intermediateElement) {
        return 0;
    }
}
