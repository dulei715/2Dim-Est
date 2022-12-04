package ecnu.dll.construction.analysis.abstract_class;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;
import ecnu.dll.construction.analysis.basic.TransformLocalPrivacy;
import ecnu.dll.construction.analysis.tools.CellDistanceTool;
import ecnu.dll.construction.newscheme.discretization.struct.ThreePartsStruct;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class DAMLocalPrivacy extends TransformLocalPrivacy<IdentityPair<Integer>> {

    protected Integer sizeD = null;

    protected Integer sizeB = null;

    protected Double probabilityQ;

    protected Double probabilityP;
    protected DistanceTor<IdentityPair<Integer>> distanceCalculator = null;

    /**
     * 用于降低 getTotalProbabilityGivenIntermediateElement 和 getPairWeightedDistance 两个函数的重复部分
     */
    private IdentityPair<Integer> tempIntermediateCell = null;
    private ThreePartsStruct<IdentityPair<Integer>> tempSplitCellStruct = null;



    public DAMLocalPrivacy(List<IdentityPair<Integer>> originalSetList, List<IdentityPair<Integer>> intermediateSetList, Integer sizeB, DistanceTor<IdentityPair<Integer>> distanceCalculator, Double probabilityP, Double probabilityQ) {
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

    public void setTempSplitCellStruct(IdentityPair<Integer> intermediateCell) {
        if (intermediateCell == null || intermediateCell.equals(this.tempIntermediateCell)) {
            return;
        }
        this.tempIntermediateCell = intermediateCell;
        this.tempSplitCellStruct = DiscretizedDiskSchemeTool.getSplitCellsInInputArea(this.tempIntermediateCell, this.sizeD, this.sizeB);
    }


    /**
     * @param intermediateElement
     * @return
     */
    @Override
    protected double getTotalProbabilityGivenIntermediateElement(IdentityPair<Integer> intermediateElement) {
        double totalProbability = 0;
        setTempSplitCellStruct(intermediateElement);
        totalProbability += this.probabilityP * this.tempSplitCellStruct.getHighProbabilityCellCollection().size();
        totalProbability += this.probabilityQ * this.tempSplitCellStruct.getLowProbabilityCellCollection().size();
        Map<IdentityPair<Integer>, Double> mixProbabilityMap = this.tempSplitCellStruct.getMixProbabilityCellCollection();
        for (Double shrinkAreaSize : mixProbabilityMap.values()) {
            totalProbability += this.probabilityP * shrinkAreaSize + this.probabilityQ * (1 - shrinkAreaSize);
        }
        return totalProbability;
    }

    /**
     * @param intermediateElement
     * @return
     */
    @Override
    protected double getPairWeightedDistance(IdentityPair<Integer> intermediateElement) {
        double pairWeightedDistance = 0;
        this.setTempSplitCellStruct(intermediateElement);
        Collection<IdentityPair<Integer>> highProbabilityCollection = this.tempSplitCellStruct.getHighProbabilityCellCollection();
        Collection<IdentityPair<Integer>> lowProbabilityCollection = this.tempSplitCellStruct.getLowProbabilityCellCollection();
        Map<IdentityPair<Integer>, Double> mixProbabilityCollection = this.tempSplitCellStruct.getMixProbabilityCellCollection();
        pairWeightedDistance += CellDistanceTool.getTotalDistanceWithinGivenCollection(highProbabilityCollection, this.distanceCalculator) * this.probabilityP * this.probabilityP;
        pairWeightedDistance += CellDistanceTool.getTotalDistanceWithinGivenCollection(lowProbabilityCollection, this.distanceCalculator) * this.probabilityQ * this.probabilityQ;
        pairWeightedDistance += CellDistanceTool.getWeightedDistanceWithinGivenMap(mixProbabilityCollection, this.distanceCalculator, this.probabilityP, this.probabilityQ);
        pairWeightedDistance += CellDistanceTool.getTotalDistanceBetweenTwoGivenCollection(highProbabilityCollection, lowProbabilityCollection, this.distanceCalculator) * this.probabilityP * this.probabilityQ;
        pairWeightedDistance += CellDistanceTool.getPartWeightedDistanceWithinGivenCollectionAndMap(mixProbabilityCollection, highProbabilityCollection, 1, this.distanceCalculator, this.probabilityP, this.probabilityQ);
        pairWeightedDistance += CellDistanceTool.getPartWeightedDistanceWithinGivenCollectionAndMap(mixProbabilityCollection, lowProbabilityCollection, 0, this.distanceCalculator, this.probabilityP, this.probabilityQ);
        return pairWeightedDistance;
    }
}
