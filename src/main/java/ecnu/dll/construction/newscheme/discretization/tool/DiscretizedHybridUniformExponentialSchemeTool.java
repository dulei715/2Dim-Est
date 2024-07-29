package ecnu.dll.construction.newscheme.discretization.tool;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.newscheme.discretization.struct.Annular;
import ecnu.dll.construction.newscheme.discretization.struct.AreaMessage;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

@SuppressWarnings("Duplicates")
public class DiscretizedHybridUniformExponentialSchemeTool {
    /**
     *  含右不含左
     *  仅处理(0,π/4)
     * @param pointAreaMessageMap
     * @param leftOuterPointList 按照Y坐标排好序的左边界point集合 (Y坐标从1开始)
     * @param rightOuterPointList 按照Y坐标排好序的右坐标point集合 (Y坐标从1开始)
     * @param rightB 右边界所在圆弧的半径
     */
    @Deprecated
    public static void addCombinationProbability(TreeMap<TwoDimensionalIntegerPoint, AreaMessage> pointAreaMessageMap, List<TwoDimensionalIntegerPoint> leftOuterPointList, List<TwoDimensionalIntegerPoint
            > rightOuterPointList, Integer rightB) {
        int i = 0, tempYIndex;
        int tempLeftX, tempRightX;
        double tempShrankAreaSize;
        for (; i < leftOuterPointList.size(); i++) {
            tempYIndex = i + 1;
            tempLeftX = leftOuterPointList.get(i).getXIndex();
            tempRightX = rightOuterPointList.get(i).getXIndex();
            for (int j = tempLeftX + 1; j < tempRightX; j++) {
                pointAreaMessageMap.put(new TwoDimensionalIntegerPoint(j, tempYIndex), new AreaMessage(rightB, 1.0));
            }
            tempShrankAreaSize = DiscretizedDiskSchemeTool.getShrinkAreaSize(rightB, tempRightX, tempYIndex);
            pointAreaMessageMap.put(new TwoDimensionalIntegerPoint(tempRightX, tempYIndex), new AreaMessage(rightB, tempShrankAreaSize));
        }
    }

    public static Annular getInnerAreaAnnular(List<TwoDimensionalIntegerPoint> leftOuterPointList, List<TwoDimensionalIntegerPoint> rightOuterPointList, Integer leftB, Integer rightB) {
        int i = 0, tempYIndex;
        int tempLeftX, tempRightX;
        double tempLeftRemainAreaSize, tempRightShrankAreaSize;
        TreeMap<TwoDimensionalIntegerPoint, Double> remainAreaMap = new TreeMap<>(), partAreaMap = new TreeMap<>();
        TreeSet<TwoDimensionalIntegerPoint> includedSet = new TreeSet<>();
        for (; i < leftOuterPointList.size(); i++) {
            tempYIndex = i + 1;
            tempLeftX = leftOuterPointList.get(i).getXIndex();
            tempRightX = rightOuterPointList.get(i).getXIndex();
            tempLeftRemainAreaSize = 1 - DiscretizedDiskSchemeTool.getShrinkAreaSize(leftB,tempLeftX, tempYIndex);
            remainAreaMap.put(new TwoDimensionalIntegerPoint(tempLeftX, tempYIndex), tempLeftRemainAreaSize);
            for (int j = tempLeftX + 1; j < tempRightX; j++) {
                includedSet.add(new TwoDimensionalIntegerPoint(j, tempYIndex));
            }
            // 当两个环形在当前你Y坐标上相交的不是同一个cell的时候，记录后者的partArea, 否则不记录
            if (tempRightX > tempLeftX) {
                tempRightShrankAreaSize = DiscretizedDiskSchemeTool.getShrinkAreaSize(rightB, tempRightX, tempYIndex);
                partAreaMap.put(new TwoDimensionalIntegerPoint(tempRightX, tempYIndex), tempRightShrankAreaSize);
            }
        }
        for (; i < rightOuterPointList.size(); i++) {
            tempYIndex = i + 1;
            tempLeftX = tempYIndex + 1;
            tempRightX = rightOuterPointList.get(i).getXIndex();
            for (int j = tempLeftX; j < tempRightX; j++) {
                includedSet.add(new TwoDimensionalIntegerPoint(j, tempYIndex));
            }
            tempRightShrankAreaSize = DiscretizedDiskSchemeTool.getShrinkAreaSize(rightB, tempRightX, tempYIndex);
            partAreaMap.put(new TwoDimensionalIntegerPoint(tempRightX,tempYIndex), tempRightShrankAreaSize);
        }
        return new Annular(leftB, rightB, remainAreaMap, includedSet, partAreaMap);
    }

    /***
     *
     * @param leftEdgePoint
     * @param rightEdgePoint
     * @param leftB
     * @param rightB
     * @return
     */
    public static Annular get45LinearAnnular(TwoDimensionalIntegerPoint leftEdgePoint, TwoDimensionalIntegerPoint rightEdgePoint, Integer leftB, Integer rightB) {
        int i = 0, tempYIndex;
        int tempLeftX, tempRightX;
        double tempLeftRemainAreaSize, tempRightShrankAreaSize;
        TreeMap<TwoDimensionalIntegerPoint, Double> remainAreaMap = new TreeMap<>(), partAreaMap = new TreeMap<>();
        TreeSet<TwoDimensionalIntegerPoint> includedSet = new TreeSet<>();
        // todo: 45方向的annular
        tempLeftX = leftEdgePoint.getXIndex();
        tempRightX = rightEdgePoint.getXIndex();
        tempLeftRemainAreaSize = 1 - DiscretizedDiskSchemeTool.get45EdgeIndexSplitArea(leftB);
        remainAreaMap.put(new TwoDimensionalIntegerPoint(tempLeftX, tempLeftX), tempLeftRemainAreaSize);
        for (int j = tempLeftX + 1; j < tempRightX; j++) {
            includedSet.add(new TwoDimensionalIntegerPoint(j, j));
        }
        // 此处判断是为了考虑相邻两个环形边界是否会交在一个cell里。如果交在一个cell里，那后面的环形边界不记录
        if (tempRightX > tempLeftX) { // 两个相邻环形没交在同一个cell里
            tempRightShrankAreaSize = DiscretizedDiskSchemeTool.get45EdgeIndexSplitArea(rightB);
            partAreaMap.put(new TwoDimensionalIntegerPoint(tempRightX, tempRightX), tempRightShrankAreaSize);
        }
        return new Annular(leftB, rightB, remainAreaMap, includedSet, partAreaMap);
    }
}
