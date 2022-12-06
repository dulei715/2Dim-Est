package ecnu.dll.construction.analysis.tools;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CellDistanceTool {

    public static final int ONE_NORM = 1;
    public static final int TWO_NORM = 2;
    public static double getTotalDistanceWithinGivenCollection(Collection<TwoDimensionalIntegerPoint> cellCollection, int normType) {
        if (normType != ONE_NORM && normType != TWO_NORM) {
            throw new RuntimeException("Not support norm type: " + normType + " !");
        }
        double totalDistance = 0;
        TwoDimensionalIntegerPoint tempCellA, tempCellB;
        List<TwoDimensionalIntegerPoint> cellList = new ArrayList<>(cellCollection);
        if (ONE_NORM == normType) {
            for (int i = 0; i < cellList.size(); i++) {
                tempCellA = cellList.get(i);
                for (int j = i + 1; j < cellList.size(); j++) {
                    tempCellB = cellList.get(j);
                    totalDistance += Math.abs(tempCellA.getXIndex() - tempCellB.getXIndex()) + Math.abs(tempCellA.getYIndex() - tempCellB.getYIndex());
                }
            }
        } else {
            for (int i = 0; i < cellList.size(); i++) {
                tempCellA = cellList.get(i);
                for (int j = i + 1; j < cellList.size(); j++) {
                    tempCellB = cellList.get(j);
                    totalDistance += Math.sqrt(Math.pow(tempCellA.getXIndex() - tempCellB.getXIndex(), 2) + Math.pow(tempCellA.getYIndex() - tempCellB.getYIndex(), 2));
                }
            }
        }
        return totalDistance * 2;
    }

    public static double getTotalDistanceWithinGivenCollection(Collection<TwoDimensionalIntegerPoint> cellCollection, DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator) {
        double totalDistance = 0;
        TwoDimensionalIntegerPoint tempCellA, tempCellB;
        List<TwoDimensionalIntegerPoint> cellList = new ArrayList<>(cellCollection);
        for (int i = 0; i < cellList.size(); i++) {
            tempCellA = cellList.get(i);
            for (int j = i + 1; j < cellList.size(); j++) {
                tempCellB = cellList.get(j);
//                totalDistance += Math.abs(tempCellA.getXIndex() - tempCellB.getXIndex()) + Math.abs(tempCellA.getYIndex() - tempCellB.getYIndex());
                totalDistance += distanceCalculator.getDistance(tempCellA, tempCellB);
            }
        }
        return totalDistance * 2;
    }

    public static double getTotalDistanceBetweenTwoGivenCollection(Collection<TwoDimensionalIntegerPoint> cellCollectionA, Collection<TwoDimensionalIntegerPoint> cellCollectionB, DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator) {
        double totalDistance = 0;
        TwoDimensionalIntegerPoint tempCellA, tempCellB;
        List<TwoDimensionalIntegerPoint> cellListA = new ArrayList<>(cellCollectionA);
        List<TwoDimensionalIntegerPoint> cellListB = new ArrayList<>(cellCollectionB);
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

    public static double getWeightedDistanceWithinGivenMap(Map<TwoDimensionalIntegerPoint, Double> cellMap, DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator, double probabilityP, double probabilityQ) {
        double totalDistance = 0;
        TwoDimensionalIntegerPoint tempCellA, tempCellB;
        double tempRealDistance, tempShrinkAreaSizeA;
        List<TwoDimensionalIntegerPoint> cellList = new ArrayList<>(cellMap.keySet());
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

    public static double getPartWeightedDistanceWithinGivenCollectionAndMap(Map<TwoDimensionalIntegerPoint, Double> cellMap, Collection<TwoDimensionalIntegerPoint> cellCollection, double collectionShrinkValue, DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator, double probabilityP, double probabilityQ) {
        double totalDistance = 0;
        TwoDimensionalIntegerPoint tempCellA;
        double tempRealDistance, tempShrinkAreasSizeA;
        for (Map.Entry<TwoDimensionalIntegerPoint, Double> mapEntry : cellMap.entrySet()) {
            tempCellA = mapEntry.getKey();
            tempShrinkAreasSizeA = mapEntry.getValue();
            for (TwoDimensionalIntegerPoint tempCellB : cellCollection) {
                tempRealDistance = distanceCalculator.getDistance(tempCellA, tempCellB);
                totalDistance += getWeightedDistance(tempRealDistance, tempShrinkAreasSizeA, collectionShrinkValue, probabilityP, probabilityQ);
            }
        }
        return totalDistance * 2;
    }

}
