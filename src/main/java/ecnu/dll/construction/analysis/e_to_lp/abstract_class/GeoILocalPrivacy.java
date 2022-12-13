package ecnu.dll.construction.analysis.e_to_lp.abstract_class;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.collection.ListUtils;
import cn.edu.ecnu.collection.SetUtils;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.basic.TransformLocalPrivacy;

import java.util.ArrayList;
import java.util.List;

public abstract class GeoILocalPrivacy extends TransformLocalPrivacy<TwoDimensionalIntegerPoint, Integer> {
//    protected Integer sizeD = null;

    protected Integer setSizeK = null;

    protected Double[] massArray = null;

    protected Double omega = null;

    protected Double radix = null;

    protected DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator = null;

    protected List<List<Integer>> intermediateIndexSetList = null;

    protected double[][] probabilityMatrix = null;



    private void initialize() {
        this.probabilityMatrix = new double[this.originalSetList.size()][this.intermediateIndexSetList.size()];
        double tempDistance;
        for (int i = 0; i < this.originalSetList.size(); i++) {
            int j = 0;
            for (; j < this.intermediateIndexSetList.size() - 1; j++) {
                tempDistance = ListUtils.getMinimalDistanceFromElementToList(i, this.intermediateIndexSetList.get(j), this.originalSetList);
                this.probabilityMatrix[i][j] = Math.pow(this.radix, tempDistance) / this.omega;
            }
            this.probabilityMatrix[i][j] = 1 - this.massArray[i] / this.omega;
        }
    }

    public GeoILocalPrivacy(List<TwoDimensionalIntegerPoint> originalSetList, Integer setSizeK, Double[] massArray, Double epsilon, Double omega, DistanceTor<TwoDimensionalIntegerPoint> distanceTor) {
        super(originalSetList, null);
        this.setSizeK = setSizeK;
        this.massArray = massArray;
        this.radix = Math.exp(-epsilon);
        this.omega = omega;
        this.distanceCalculator = distanceTor;
        this.intermediateIndexSetList = SetUtils.getSubsetList(this.originalSetList.size(), this.setSizeK, 0);
        this.intermediateIndexSetList.add(new ArrayList<>());
        // 这里的intermediateSetList是中间元素的索引的索引，而非中间元素
        this.intermediateSetList = BasicArray.getIncreaseIntegerNumberList(0, 1, this.intermediateIndexSetList.size() - 1);
        this.initialize();
    }

//    private double getToSubsetProbability(int cellIndex, List<Integer> subsetIndexList) {
//        if (subsetIndexList.isEmpty()) {
//            return 1 - this.massArray[cellIndex] / this.omega;
//        }
//        Double distance = ListUtils.getMinimalDistanceFromElementToList(cellIndex, subsetIndexList, this.originalSetList);
//        return Math.pow(this.radix, distance) / this.omega;
//    }
    private double getToSubsetProbability(int cellIndex, Integer subsetIndexListIndex) {
        return this.probabilityMatrix[cellIndex][subsetIndexListIndex];
    }


    @Override
    protected double getTotalProbabilityGivenIntermediateElement(Integer intermediateElementIndexListIndex) {
        double resultValue = 0;
        for (int i = 0; i < this.originalSetList.size(); i++) {
            resultValue += getToSubsetProbability(i, intermediateElementIndexListIndex);
        }
        return resultValue;
    }

    @Override
    protected double getPairWeightedDistance(Integer intermediateElementIndexListIndex) {
        double resultValue = 0;
        double tempProbability;
        TwoDimensionalIntegerPoint tempPoint;
        for (int i = 0; i < this.originalSetList.size(); i++) {
            tempProbability = getToSubsetProbability(i, intermediateElementIndexListIndex);
            tempPoint = this.originalSetList.get(i);
            for (int j = i + 1; j < this.originalSetList.size(); j++) {
                resultValue += tempProbability * getToSubsetProbability(j, intermediateElementIndexListIndex) * this.distanceCalculator.getDistance(tempPoint, this.originalSetList.get(j));
            }
        }
        return resultValue * 2;
    }


}
