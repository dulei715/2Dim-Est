package ecnu.dll.construction.analysis.abstract_class;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;
import ecnu.dll.construction.analysis.basic.TransformLocalPrivacy;
import ecnu.dll.construction.analysis.tools.CellDistanceTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedRhombusSchemeTool;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public abstract class RAMLocalPrivacy extends TransformLocalPrivacy<IdentityPair<Integer>, IdentityPair<Integer>> {

    protected Integer sizeD = null;

    protected Integer sizeB = null;

    protected Double probabilityQ;

    protected Double probabilityP;
    protected DistanceTor<IdentityPair<Integer>> distanceCalculator = null;

    /**
     * 用于降低 getTotalProbabilityGivenIntermediateElement 和 getPairWeightedDistance 两个函数的重复部分
     */
    private IdentityPair<Integer> tempIntermediateCell = null;
    private Collection<IdentityPair<Integer>> tempCrossCellCollection = null;

    public RAMLocalPrivacy(List<IdentityPair<Integer>> originalSetList, List<IdentityPair<Integer>> intermediateSetList, Integer sizeB, DistanceTor<IdentityPair<Integer>> distanceCalculator, Double probabilityP, Double probabilityQ) {
        super(originalSetList, intermediateSetList);
        this.sizeD = originalSetList.size();
        this.sizeB = sizeB;
        this.distanceCalculator = distanceCalculator;
        this.probabilityP = probabilityP;
        this.probabilityQ = probabilityQ;
    }

    public Integer getSizeD() {
        return sizeD;
    }

    public Integer getSizeB() {
        return sizeB;
    }

    public DistanceTor<IdentityPair<Integer>> getDistanceCalculator() {
        return distanceCalculator;
    }

    public void setDistanceCalculator(DistanceTor<IdentityPair<Integer>> distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public void resetBasicInfo(List<IdentityPair<Integer>> originalSetList, List<IdentityPair<Integer>> intermediateSetList, Integer sizeB) {
        super.originalSetList = originalSetList;
        super.intermediateSetList = intermediateSetList;
        this.sizeD = originalSetList.size();
        this.sizeB = sizeB;
    }

    public void setTempCrossCellCollection(IdentityPair<Integer> intermediateCell) {
        if (intermediateCell == null || intermediateCell.equals(this.tempIntermediateCell)) {
            return;
        }
        this.tempIntermediateCell = intermediateCell;
        this.tempCrossCellCollection = DiscretizedRhombusSchemeTool.getCrossCellCollectionWithinInputCells(this.tempIntermediateCell, this.sizeD, this.sizeB);
    }


    protected abstract double getSquareDistance();



    /**
     * @param intermediateElement
     * @return
     */
    @Override
    protected double getTotalProbabilityGivenIntermediateElement(IdentityPair<Integer> intermediateElement) {
        double totalProbability = 0;
        int[] partSizeArray = new int[2];

        this.setTempCrossCellCollection(intermediateElement);
        partSizeArray[0] = this.tempCrossCellCollection.size();

        partSizeArray[1] = this.originalSetList.size() - partSizeArray[0];
        totalProbability = partSizeArray[0] * this.probabilityP + partSizeArray[1] * this.probabilityQ;
        return totalProbability;
    }

    /**
     * @param intermediateElement
     * @return
     */
    @Override
    protected double getPairWeightedDistance(IdentityPair<Integer> intermediateElement) {
        double pairWeightedDistance = 0;
        double totalDistance = this.getSquareDistance();
        double[] partDistanceArray = new double[3];

        this.setTempCrossCellCollection(intermediateElement);

        partDistanceArray[0] = CellDistanceTool.getTotalDistanceWithinGivenCollection(this.tempCrossCellCollection, this.distanceCalculator);

        pairWeightedDistance += partDistanceArray[0] * this.probabilityP * this.probabilityP;
        Collection outerCell = CollectionUtils.subtract(super.originalSetList, this.tempCrossCellCollection);
        partDistanceArray[1] = CellDistanceTool.getTotalDistanceWithinGivenCollection(outerCell, this.distanceCalculator);
        pairWeightedDistance += partDistanceArray[1] * this.probabilityQ * this.probabilityQ;
        partDistanceArray[2] = totalDistance - partDistanceArray[0] - partDistanceArray[1];
        pairWeightedDistance += partDistanceArray[2] * this.probabilityP * this.probabilityQ;
        return pairWeightedDistance;
    }

//    @Override
//    protected List<IdentityPair<Integer>> generateIntermediateSetList() {
//        return null;
//    }
}
