package ecnu.dll.construction.analysis.e_to_lp.abstract_class;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.basic.TransformLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.tools.CellDistanceTool;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.struct.ThreePartsStruct;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class DAMLocalPrivacy extends TransformLocalPrivacy<TwoDimensionalIntegerPoint, TwoDimensionalIntegerPoint> {

    protected DiscretizedDiskScheme damScheme = null;

    protected Integer sizeD = null;

    protected Integer sizeB = null;

    protected Double probabilityQ;

    protected Double probabilityP;
    protected DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator = null;

    /**
     * 用于降低 getTotalProbabilityGivenIntermediateElement 和 getPairWeightedDistance 两个函数的重复部分
     */
    private TwoDimensionalIntegerPoint tempIntermediateCell = null;
    private ThreePartsStruct<TwoDimensionalIntegerPoint> tempSplitCellStruct = null;



    @Deprecated
    public DAMLocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer sizeD, List<TwoDimensionalIntegerPoint> intermediateSetList, Integer sizeB, DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator, Double probabilityP, Double probabilityQ) {
        super(originalSetList, intermediateSetList);
        this.sizeD = sizeD;
        this.sizeB = sizeB;
        this.distanceCalculator = distanceCalculator;
        this.probabilityP = probabilityP;
        this.probabilityQ = probabilityQ;
    }

    public DAMLocalPrivacy(DiscretizedDiskScheme damScheme, DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator) {
        super(null, null);
        this.damScheme = damScheme;
        super.originalSetList = this.damScheme.getRawIntegerPointTypeList();
        super.intermediateSetList = this.damScheme.getNoiseIntegerPointTypeList();
        this.sizeD = this.damScheme.getSizeD();
        this.sizeB = this.damScheme.getSizeB();
        this.distanceCalculator = distanceCalculator;
        this.probabilityP = this.damScheme.getConstP();
        this.probabilityQ = this.damScheme.getConstQ();
    }

    /**
     * 保证damScheme不空；
     * 注意不能直接调用damScheme的resetEpsilon方法，只能通过本方法修改epsilon.
     * @param epsilon
     */
    public void resetEpsilon (Double epsilon) {
        this.damScheme.resetEpsilon(epsilon, true);
        super.intermediateSetList = this.damScheme.getNoiseIntegerPointTypeList();
        this.sizeB = this.damScheme.getSizeB();
        this.probabilityP = this.damScheme.getConstP();
        this.probabilityQ = this.damScheme.getConstQ();
    }


    public Integer getSizeD() {
        return sizeD;
    }

    public Integer getSizeB() {
        return sizeB;
    }

    public DistanceTor<TwoDimensionalIntegerPoint> getDistanceCalculator() {
        return distanceCalculator;
    }

    public void setDistanceCalculator(DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public void resetBasicInfo(List<TwoDimensionalIntegerPoint> originalSetList, List<TwoDimensionalIntegerPoint> intermediateSetList, Integer sizeB) {
        super.originalSetList = originalSetList;
        super.intermediateSetList = intermediateSetList;
        this.sizeD = originalSetList.size();
        this.sizeB = sizeB;
    }

    public void setTempSplitCellStruct(TwoDimensionalIntegerPoint intermediateCell) {
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
    protected double getTotalProbabilityGivenIntermediateElement(TwoDimensionalIntegerPoint intermediateElement) {
        double totalProbability = 0;
        setTempSplitCellStruct(intermediateElement);
        totalProbability += this.probabilityP * this.tempSplitCellStruct.getHighProbabilityCellCollection().size();
        totalProbability += this.probabilityQ * this.tempSplitCellStruct.getLowProbabilityCellCollection().size();
        Map<TwoDimensionalIntegerPoint, Double> mixProbabilityMap = this.tempSplitCellStruct.getMixProbabilityCellCollection();
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
    protected double getPairWeightedDistance(TwoDimensionalIntegerPoint intermediateElement) {
        double pairWeightedDistance = 0;
        this.setTempSplitCellStruct(intermediateElement);
        Collection<TwoDimensionalIntegerPoint> highProbabilityCollection = this.tempSplitCellStruct.getHighProbabilityCellCollection();
        Collection<TwoDimensionalIntegerPoint> lowProbabilityCollection = this.tempSplitCellStruct.getLowProbabilityCellCollection();
        Map<TwoDimensionalIntegerPoint, Double> mixProbabilityCollection = this.tempSplitCellStruct.getMixProbabilityCellCollection();
        pairWeightedDistance += CellDistanceTool.getTotalDistanceWithinGivenCollection(highProbabilityCollection, this.distanceCalculator) * this.probabilityP * this.probabilityP;
        pairWeightedDistance += CellDistanceTool.getTotalDistanceWithinGivenCollection(lowProbabilityCollection, this.distanceCalculator) * this.probabilityQ * this.probabilityQ;
        pairWeightedDistance += CellDistanceTool.getWeightedDistanceWithinGivenMap(mixProbabilityCollection, this.distanceCalculator, this.probabilityP, this.probabilityQ);
        pairWeightedDistance += CellDistanceTool.getTotalDistanceBetweenTwoGivenCollection(highProbabilityCollection, lowProbabilityCollection, this.distanceCalculator) * this.probabilityP * this.probabilityQ;
        pairWeightedDistance += CellDistanceTool.getPartWeightedDistanceWithinGivenCollectionAndMap(mixProbabilityCollection, highProbabilityCollection, 1, this.distanceCalculator, this.probabilityP, this.probabilityQ);
        pairWeightedDistance += CellDistanceTool.getPartWeightedDistanceWithinGivenCollectionAndMap(mixProbabilityCollection, lowProbabilityCollection, 0, this.distanceCalculator, this.probabilityP, this.probabilityQ);
        return pairWeightedDistance;
    }

//    @Override
//    protected List<TwoDimensionalIntegerPoint> generateIntermediateSetList() {
//        return null;
//    }
}
