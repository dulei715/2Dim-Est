package ecnu.dll.construction.analysis;

import cn.edu.ecnu.struct.pair.IdentityPair;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

public class RAMGridCrossDistance extends GridCrossTool {

    public static final int ONE_NORM = 1;
    public static final int TWO_NORM = 2;

    private int normType;

    public RAMGridCrossDistance(int normType) {
        if (normType < 1 && normType > 2) {
            throw new RuntimeException("The norm type " + normType + "is not supported!");
        }
        this.normType = normType;
    }

    public RAMGridCrossDistance(TreeSet<IdentityPair<Integer>> inputCellSet, Integer sizeB, int normType) {
        super(inputCellSet, sizeB);
        if (normType < 1 && normType > 2) {
            throw new RuntimeException("The norm type " + normType + "is not supported!");
        }
        this.normType = normType;
    }

    public RAMGridCrossDistance(Integer sizeD, Integer sizeB, int normType) {
        super(sizeD, sizeB);
        if (normType < 1 && normType > 2) {
            throw new RuntimeException("The norm type " + normType + "is not supported!");
        }
        this.normType = normType;
    }

    public static double getTotalDistanceWithinGivenCollection(Collection<IdentityPair<Integer>> cellCollection, int normType) {
        if (normType < 1 && normType > 2) {
            throw new RuntimeException("The norm type " + normType + "is not supported!");
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

//    public static Integer getSquareDistance(int sizeD, int normType) {
//        if (normType < 1 && normType > 2) {
//            throw new RuntimeException("The norm type " + normType + "is not supported!");
//        }
//        if (normType == 1) {
//            return sizeD * (sizeD + 1) * (sizeD - 1) / 3 * 2 * sizeD * sizeD;
//        }
//
//    }

    public Double getSquareDistance() {

        if (this.normType == 1) {
            return Double.valueOf(this.sizeD * (this.sizeD + 1) * (this.sizeD - 1) / 3 * 2 * this.sizeD * this.sizeD);
        }
        return getTotalDistanceWithinGivenCollection(super.inputCellSet, this.normType);


    }

    /**
     * 获取内内，外外，内外总距离
     * @param centerCell
     * @return [内内，外外，内外] 距离
     */
    public double[] getTotalDistance(IdentityPair<Integer> centerCell) {
        double totalDistance = this.getSquareDistance();
        double[] resultDistance = new double[3];
        Collection<IdentityPair<Integer>> crossCell = super.getCrossCell(centerCell);
        resultDistance[0] = getTotalDistanceWithinGivenCollection(crossCell, this.normType);
        Collection outerCell = CollectionUtils.subtract(super.inputCellSet, crossCell);
        resultDistance[1] = getTotalDistanceWithinGivenCollection(outerCell, this.normType);
        resultDistance[2] = totalDistance - resultDistance[0] - resultDistance[1];
        return resultDistance;
    }





}
