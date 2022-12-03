package ecnu.dll.construction.analysis.abstract_class;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;
import ecnu.dll.construction.analysis.basic.TransformLocalPrivacy;
import ecnu.dll.construction.analysis.tools.CellDistanceTool;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public abstract class DAMLocalPrivacy extends TransformLocalPrivacy<IdentityPair<Integer>> {

    protected Integer sizeD = null;

    protected Integer sizeB = null;

    protected Double probabilityQ;

    protected Double probabilityP;
    protected DistanceTor<IdentityPair<Integer>> distanceCalculator = null;



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


    /**
     * 判断给定的 judgePoint 是否在以 centerPoint 为中心的高概率范围内
     * @param centerPoint
     * @param sizeB
     * @param judgePoint
     * @return
     */
    protected static boolean isInHighProbabilityArea(IdentityPair<Integer> centerPoint, int sizeB, IdentityPair<Integer> judgePoint) {
        return Math.abs(centerPoint.getKey() - judgePoint.getKey()) + Math.abs(centerPoint.getValue() - judgePoint.getValue()) <= sizeB ? true : false;
    }

    protected Collection<IdentityPair<Integer>> getCrossCell(IdentityPair<Integer> centerPoint) {
        return getCrossCell(centerPoint, this.sizeD, this.sizeB);
    }


    protected static Collection<IdentityPair<Integer>> getCrossCell(IdentityPair<Integer> centerPoint, int sizeD, int sizeB) {
        HashSet<IdentityPair<Integer>> resultSet = new HashSet<>();
        IdentityPair<Integer> judgePoint;
        for (int i = 0; i < sizeD; i++) {
            for (int j = 0; j < sizeD; j++) {
                judgePoint = new IdentityPair<>(i, j);
                if (isInHighProbabilityArea(centerPoint, sizeB, judgePoint)){
                    resultSet.add(judgePoint);
                }
            }
        }
        return resultSet;
    }





    /**
     * @param intermediateElement
     * @return
     */
    @Override
    protected double getTotalProbabilityGivenIntermediateElement(IdentityPair<Integer> intermediateElement) {
        double totalProbability = 0;
//        int[] partSizeArray = new int[3];
        Collection<IdentityPair<Integer>>[] partCollection = new Collection[3];
        partCollection[0] =
        partSizeArray[0] = ;

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

        this.setTempCrossCell(intermediateElement);

        partDistanceArray[0] = CellDistanceTool.getTotalDistanceWithinGivenCollection(this.tempCrossCell, this.distanceCalculator);

        pairWeightedDistance += partDistanceArray[0] * this.probabilityP * this.probabilityP;
        Collection outerCell = CollectionUtils.subtract(super.originalSetList, this.tempCrossCell);
        partDistanceArray[1] = CellDistanceTool.getTotalDistanceWithinGivenCollection(outerCell, this.distanceCalculator);
        pairWeightedDistance += partDistanceArray[1] * this.probabilityQ * this.probabilityQ;
        partDistanceArray[2] = totalDistance - partDistanceArray[0] - partDistanceArray[1];
        pairWeightedDistance += partDistanceArray[2] * this.probabilityP * this.probabilityQ;
        return pairWeightedDistance;
    }
}
