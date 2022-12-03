package ecnu.dll.construction.analysis.tools;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.struct.pair.IdentityPair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
}
