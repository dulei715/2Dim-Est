package ecnu.dll.construction.analysis.tools;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CellDistanceTool {

    public static final int ONE_NORM = 1;
    public static final int TWO_NORM = 2;
    public static double getTotalDistanceWithinGivenCollection(Collection<IdentityPair<Integer>> cellCollection, int normType) {
        if (normType != ONE_NORM && normType != TWO_NORM) {
            throw new RuntimeException("Not support norm type: " + normType + " !");
        }
        double totalDistance = 0;
        IdentityPair<Integer> tempCellA, tempCellB;
        List<IdentityPair> cellList = new ArrayList<>(cellCollection);
        if (ONE_NORM == normType) {
            for (int i = 0; i < cellList.size(); i++) {
                tempCellA = cellList.get(i);
                for (int j = i + 1; j < cellList.size(); j++) {
                    tempCellB = cellList.get(j);
                    totalDistance += Math.abs(tempCellA.getKey() - tempCellB.getKey()) + Math.abs(tempCellA.getValue() - tempCellB.getValue());
                }
            }
        } else {
            for (int i = 0; i < cellList.size(); i++) {
                tempCellA = cellList.get(i);
                for (int j = i + 1; j < cellList.size(); j++) {
                    tempCellB = cellList.get(j);
                    totalDistance += Math.sqrt(Math.pow(tempCellA.getKey() - tempCellB.getKey(), 2) + Math.pow(tempCellA.getValue() - tempCellB.getValue(), 2));
                }
            }
        }
        return totalDistance * 2;
    }

    public static double getTotalDistanceWithinGivenCollection(Collection<IdentityPair<Integer>> cellCollection, DistanceTor<IdentityPair<Integer>> distanceCalculator) {
        double totalDistance = 0;
        IdentityPair<Integer> tempCellA, tempCellB;
        List<IdentityPair> cellList = new ArrayList<>(cellCollection);
        for (int i = 0; i < cellList.size(); i++) {
            tempCellA = cellList.get(i);
            for (int j = i + 1; j < cellList.size(); j++) {
                tempCellB = cellList.get(j);
//                totalDistance += Math.abs(tempCellA.getKey() - tempCellB.getKey()) + Math.abs(tempCellA.getValue() - tempCellB.getValue());
                totalDistance += distanceCalculator.getDistance(tempCellA, tempCellB);
            }
        }
        return totalDistance * 2;
    }

    public static double getTotalDistanceBetweenTwoGivenCollection(Collection<IdentityPair<Integer>> cellCollectionA, Collection<IdentityPair<Integer>> cellCollectionB, DistanceTor<IdentityPair<Integer>> distanceCalculator) {
        double totalDistance = 0;
        IdentityPair<Integer> tempCellA, tempCellB;
        List<IdentityPair<Integer>> cellListA = new ArrayList<>(cellCollectionA);
        List<IdentityPair<Integer>> cellListB = new ArrayList<>(cellCollectionB);
        for (int i = 0; i < cellListA.size(); i++) {
            tempCellA = cellListA.get(i);
            for (int j = 0; j < cellListB.size(); j++) {
                tempCellB = cellListB.get(j);
                totalDistance += distanceCalculator.getDistance(tempCellA, tempCellB);
            }
        }
        return totalDistance * 2;
    }

    public static double getWeightedDistance(double realDistance, double shrinkAreaSizeA, double shrinkAreaSizeB, double probabilityP, double probabilityQ) {
        double remainAreaSizeA = 1 - shrinkAreaSizeA;
        double remainAreaSizeB = 1 - shrinkAreaSizeB;
        return realDistance * (shrinkAreaSizeA * probabilityP + remainAreaSizeA * probabilityQ) * (shrinkAreaSizeB * probabilityP + remainAreaSizeB * probabilityQ);
    }

    public static double getWeightedDistanceWithinGivenMap(Map<IdentityPair<Integer>, Double> cellMap, DistanceTor<IdentityPair<Integer>> distanceCalculator, double probabilityP, double probabilityQ) {
        double totalDistance = 0;
        IdentityPair<Integer> tempCellA, tempCellB;
        double tempRealDistance, tempShrinkAreaSizeA;
        List<IdentityPair<Integer>> cellList = new ArrayList<>(cellMap.keySet());
        for (int i = 0; i < cellList.size(); i++) {
            tempCellA = cellList.get(i);
            tempShrinkAreaSizeA = cellMap.get(tempCellA);
            for (int j = i + 1; j < cellList.size(); j++) {
                tempCellB = cellList.get(j);
                tempRealDistance = distanceCalculator.getDistance(tempCellA, tempCellB);
                totalDistance += getWeightedDistance(tempRealDistance, tempShrinkAreaSizeA, cellMap.get(tempCellB), probabilityP, probabilityQ);
            }
        }
        return totalDistance * 2;
    }

    public static double getPartWeightedDistanceWithinGivenCollectionAndMap(Map<IdentityPair<Integer>, Double> cellMap, Collection<IdentityPair<Integer>> cellCollection, double collectionShrinkValue, DistanceTor<IdentityPair<Integer>> distanceCalculator, double probabilityP, double probabilityQ) {
        double totalDistance = 0;
        IdentityPair<Integer> tempCellA;
        double tempRealDistance, tempShrinkAreasSizeA;
        for (Map.Entry<IdentityPair<Integer>, Double> mapEntry : cellMap.entrySet()) {
            tempCellA = mapEntry.getKey();
            tempShrinkAreasSizeA = mapEntry.getValue();
            for (IdentityPair<Integer> tempCellB : cellCollection) {
                tempRealDistance = distanceCalculator.getDistance(tempCellA, tempCellB);
                totalDistance += getWeightedDistance(tempRealDistance, tempShrinkAreasSizeA, collectionShrinkValue, probabilityP, probabilityQ);
            }
        }
        return totalDistance * 2;
    }

}
