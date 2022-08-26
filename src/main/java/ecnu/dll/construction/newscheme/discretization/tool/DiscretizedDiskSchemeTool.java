package ecnu.dll.construction.newscheme.discretization.tool;

import cn.edu.ecnu.collection.SetUtils;
import cn.edu.ecnu.struct.pair.BasicPair;
import cn.edu.ecnu.struct.pair.IdentityPair;
import cn.edu.ecnu.struct.pair.PairListUtils;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;

import java.util.*;

@SuppressWarnings("ALL")
public class DiscretizedDiskSchemeTool {
    public static double getShrinkAreaSize(Integer sizeB, int xIndex, int yIndex) {
        double delta = sizeB * 1.0 / Math.sqrt(xIndex * xIndex + yIndex * yIndex) - 1;
        double tempShrinkAreaSize = 4 * (delta * xIndex + 0.5) * (delta * yIndex + 0.5);
        if (tempShrinkAreaSize < 0) {
            // 这里不可能两个坐标差都小于0，因此只要有一个小于0，即面积为负，就设置为0
            tempShrinkAreaSize = 0;
        } else if (tempShrinkAreaSize > 1) {
            tempShrinkAreaSize = 1;
        }
        return tempShrinkAreaSize;
    }

    /**
     * 根据给定的sizeB计算45度方向边界cell坐标
     * @param sizeB
     * @return
     */
    public static IdentityPair<Integer> calculate45EdgeIndex(Integer sizeB) {
        double index45Prime = sizeB / Math.sqrt(2) - 0.5;
        Integer upperIndex45 = (int) Math.ceil(index45Prime);
        return new IdentityPair<>(upperIndex45, upperIndex45);
    }

    /**
     *  获取内部纯高概率cell坐标(不含0方向和45方向)
     * @param outerCellIndexList 边缘cell按照y坐标从小到大排列的列表
     * @return
     */
    public static List<IdentityPair<Integer>> getInnerCell(List<IdentityPair<Integer>> outerCellIndexList) {
        IdentityPair<Integer> tempPair;
        Integer tempX, tempY;
        List<IdentityPair<Integer>> innerCellIndexList;
        TreeSet<IdentityPair<Integer>> treeSet = new TreeSet<>();
        for (int k = 0; k < outerCellIndexList.size(); k++) {
            tempPair = outerCellIndexList.get(k);
            tempX = tempPair.getKey();
            tempY = tempPair.getValue();
            for (int i = k + 2; i < tempX; i++) {
                treeSet.add(new IdentityPair<>(i, tempY));
            }
        }
        innerCellIndexList = new ArrayList<>(treeSet);
        return innerCellIndexList;
    }

    /**
     * 根据给定的sizeB计算45度方向cell的分割面积
     * @param sizeB
     * @return
     */
    public static Double get45EdgeIndexSplitArea(Integer sizeB) {
        double index45Prime = sizeB / Math.sqrt(2) - 0.5;
        Integer upperIndex45 = (int) Math.ceil(index45Prime);
        Integer lowerIndex45 = (int) Math.floor(index45Prime);
        Double edge45Area = null;
        double differ = index45Prime - lowerIndex45;
        if (differ < 0.5) {
            edge45Area = 4 * differ * differ;
        } else {
            edge45Area = 1.0;
        }
        return edge45Area;
    }

    /**
     * 根据给定的b长度，计算边界cell集合的坐标
     * @param sizeB
     * @return
     */
    public static List<IdentityPair<Integer>> calculateOuterCellIndexList(Integer sizeB) {
        List<IdentityPair<Integer>> outerCellIndexList;
        double sqrt2 = Math.sqrt(2);
        double tempDiff = sizeB / sqrt2 - 0.5;
        double r_1 = Math.floor(tempDiff) * sqrt2 + 1/sqrt2;
        double r = Math.sqrt(r_1 * (r_1  + sqrt2) + 1);
        int outerCellSize = (int)Math.ceil(tempDiff) - (int)Math.floor(r / sizeB);
//        if (outerCellSize < 0) {
//            System.out.println("error outerCellSize!");
//        }
        outerCellIndexList = new ArrayList<>(outerCellSize);
        int xIndexTemp;
        for (int i = 1; i <= outerCellSize; i++) {
            xIndexTemp = (int) Math.ceil(Math.sqrt(sizeB * sizeB - Math.pow(i-0.5,2))-0.5);
            outerCellIndexList.add(new IdentityPair<>(xIndexTemp, i));
        }
        return outerCellIndexList;
    }

    /**
     * 角度区间(0,45)之间的输出边界cell列表
     */
    public static List<IdentityPair<Integer>> getOutputBorderOuterCellList(List<IdentityPair<Integer>> outerCellIndexList, Integer sizeD, Integer sizeB) {
        List<IdentityPair<Integer>> outputBorderOuterCellList = new ArrayList<>();
        IdentityPair<Integer> tempPair;
        for (int i = 1; i < sizeD; i++) {
            outputBorderOuterCellList.add(new IdentityPair<>(sizeD + sizeB - 1, i));
        }
        for (int i = 0; i < outerCellIndexList.size(); i++) {
            tempPair = outerCellIndexList.get(i);
            outputBorderOuterCellList.add(new IdentityPair<>(tempPair.getKey() + sizeD - 1, tempPair.getValue() + sizeD - 1));
        }
        return outputBorderOuterCellList;
    }


    /**
     *
     * @param sortedOuterCellIndexList 按照Y坐标排序好的（0，45）度之间的边界
     * @param sizeD
     * @param sizeB
     * @return 右移后，[0,90]度之间的外部增量
     */
    public static TreeSet<IdentityPair<Integer>> getMoveRightIncrementPureLowProbabilityCellSet(List<IdentityPair<Integer>> sortedOuterCellIndexList, Integer sizeD, Integer sizeB) {
        /**
         *    1. 构建[0,90]度的边界值
         *    2. 计算右移后的边界值
         *    3. 将原始的[0,90]度边界值分为两类：[0,45) 和 [45,90] 两部分
         *          (1) [0,45) 部分由于每行只有一个，因此可以直接遍历每行计算出增加的cell
         *          (2) [45,90] 部分要按照行进行分组，计算每组右移增加的值
         *    4. 将增加的值记录到TreeSet中按照点本身顺序进行排序输出
         */
        TreeSet<IdentityPair<Integer>> resultSet = new TreeSet<>();
        /*
            1. 构建[0,90]度的边界值
         */
        // [0]方向边界值（只记录横坐标）
        Integer border0 = sizeB;
        // [45]方向边界值
        IdentityPair<Integer> border45 = DiscretizedDiskSchemeTool.calculate45EdgeIndex(sizeB);
        // [45,90)度边界值
        Collection<IdentityPair<Integer>> upperPartCollection = PairListUtils.getExchangeKeyValuePairList(sortedOuterCellIndexList);
        IdentityPair<Integer> tempLastPair = sortedOuterCellIndexList.get(sortedOuterCellIndexList.size() - 1);
        if (border45.getValue() > tempLastPair.getValue()) { // 此处是为了判断边界值是否已到border45同一水平
            upperPartCollection.add(border45);
        } else {
            upperPartCollection.add(tempLastPair);
        }
        //(45,90)度边界值按照纵坐标分组(行分组)(方便找到最右侧点)
        TreeMap<Integer, TreeSet<BasicPair<Integer, Integer>>> yIndexMap = PairListUtils.groupByValue(upperPartCollection);


        /*
            2. 计算右移后的边界值
         */
        // [0] 方向右移
        Integer border0MoveRightXIndex = sizeB + sizeD - 1;
        Integer tempYIndex, tempXIndex, tempMovedXIndex;
        for (int i = sizeB + 1; i <= border0MoveRightXIndex; i++) {
            resultSet.add(new IdentityPair<>(i, 0));
        }
        // (0,45) 方向右移 todo: 遍历sortedOuterCellIndexList纵坐标
        for (IdentityPair<Integer> pair : sortedOuterCellIndexList) {
            tempYIndex = pair.getValue();
            tempXIndex = pair.getKey();
            tempMovedXIndex = tempXIndex + sizeD - 1;
            for (int x = tempXIndex + 1; x <= tempMovedXIndex; x++) {
                resultSet.add(new IdentityPair<>(x, tempYIndex));
            }
        }
        // [45,90] 方向右移 todo: 遍历upperPartList每个列组
        for (Map.Entry<Integer, TreeSet<BasicPair<Integer, Integer>>> entry : yIndexMap.entrySet()) {
            tempYIndex = entry.getKey();
            tempXIndex = entry.getValue().last().getKey();
            tempMovedXIndex = tempXIndex + sizeD;
            for (int x = tempXIndex + 1; x < tempMovedXIndex; x++) {
                resultSet.add(new IdentityPair<>(x, tempYIndex));
            }
        }
        return resultSet;
    }

    /**
     *
     * @param sortedOuterCellIndexList 按照Y坐标排序好的（0，45）度之间的边界
     * @param sizeD
     * @param sizeB
     * @param movedRightAddedCells 右移后增加的cell
     * @return 右移且上移后，[0,90]度之间的外部增量
     */
    public static TreeSet<IdentityPair<Integer>> getMoveRightUpperIncrementPureLowProbabilityCellSet(List<IdentityPair<Integer>> sortedOuterCellIndexList, Integer sizeD, Integer sizeB, Collection<IdentityPair<Integer>> movedRightAddedCells) {
        /**
         *    1. 将边界cell和右移增加的cell movedRightAddedCell合并产生新的cell集合 middleSet
         *    2. 将middleSet按照x坐标分组
         *    3. 计算上移的额外增加cell moveUpAddedCell
         *    4. 将moveUpAddedCell与movedRightAddedCell合并，即为输出结果
         */
        Integer tempXIndex, tempYIndex, tempAddedYIndex;
        TreeSet<IdentityPair<Integer>> resultSet = new TreeSet<>();

        IdentityPair<Integer> border45 = DiscretizedDiskSchemeTool.calculate45EdgeIndex(sizeB);
        TreeSet<IdentityPair<Integer>> middleSet = new TreeSet<>(sortedOuterCellIndexList);
        middleSet.addAll(PairListUtils.getExchangeKeyValuePairList(sortedOuterCellIndexList));
        middleSet.add(border45);
        middleSet.add(new IdentityPair<>(0, sizeB));
        middleSet.addAll(movedRightAddedCells);
        TreeMap<Integer, TreeSet<BasicPair<Integer, Integer>>> middleMap = PairListUtils.groupByKey(middleSet);
        for (Map.Entry<Integer, TreeSet<BasicPair<Integer, Integer>>> middleEntry : middleMap.entrySet()) {
            tempXIndex = middleEntry.getKey();
            tempYIndex = middleEntry.getValue().last().getValue();
            tempAddedYIndex = tempYIndex + sizeD - 1;
            for (int y = tempYIndex + 1; y <= tempAddedYIndex; y++) {
                resultSet.add(new IdentityPair<>(tempXIndex, y));
            }
        }
        resultSet.addAll(movedRightAddedCells);
        return resultSet;
    }

    /**
     * 根据原始点，返回放缩的点（不改变原始点）
     * @param originalCollection
     * @param xFactor
     * @param yFactor
     * @return
     */
    public static Collection<IdentityPair<Integer>> factorChangeIntegerPair(Collection<IdentityPair<Integer>> originalCollection, Integer xFactor, Integer yFactor) {
        Collection<IdentityPair<Integer>> result = new ArrayList<>();
        Integer tempXIndex, tempYIndex;
        for (IdentityPair<Integer> identityPair : originalCollection) {
            tempXIndex = identityPair.getKey();
            tempYIndex = identityPair.getValue();
            result.add(new IdentityPair(tempXIndex * xFactor, tempYIndex * yFactor));
        }
        return result;
    }

    /**
     * 以(0,0)为原始点，计算纯低概率部分的point集合
     * @return
     */
    public static TreeSet<IdentityPair<Integer>> getResidualPureLowCells(List<IdentityPair<Integer>> sortedOuterCellIndexList, Integer sizeD, Integer sizeB){
        /**
         *  1. 计算第二象限的
         *  2. 计算第四象限的
         *  3. 计算第一象限的
         */
        TreeSet<IdentityPair<Integer>> resultSet = new TreeSet<>();
        TreeSet<IdentityPair<Integer>> moveRightAddedCellSet = DiscretizedDiskSchemeTool.getMoveRightIncrementPureLowProbabilityCellSet(sortedOuterCellIndexList, sizeD, sizeB);

        Collection<IdentityPair<Integer>> fourthQuadrantAddedCells = DiscretizedDiskSchemeTool.factorChangeIntegerPair(moveRightAddedCellSet, 1, -1);
        Collection<IdentityPair<Integer>> secondQuadrantAddedCells = DiscretizedDiskSchemeTool.factorChangeIntegerPair(PairListUtils.getExchangeKeyValuePairList(moveRightAddedCellSet), -1, 1);
        TreeSet<IdentityPair<Integer>> firstQuadrantAddedCells = DiscretizedDiskSchemeTool.getMoveRightUpperIncrementPureLowProbabilityCellSet(sortedOuterCellIndexList, sizeD, sizeB, moveRightAddedCellSet);

        resultSet.addAll(fourthQuadrantAddedCells);
        resultSet.addAll(secondQuadrantAddedCells);
        resultSet.addAll(firstQuadrantAddedCells);

        return resultSet;
    }


    /**
     * 返回所有可能的输出cell
     * @param outerCellIndexCollection
     * @param sizeD
     * @param sizeB
     * @param upperIndex45
     * @return
     */
    public static List<TwoDimensionalIntegerPoint> getNoiseIntegerPointTypeList(Collection<IdentityPair<Integer>> outerCellIndexCollection, Integer sizeD, Integer sizeB, Integer upperIndex45) {
        TreeSet<TwoDimensionalIntegerPoint> treeSet = new TreeSet<>();
        /*
            添加原始cell的中心十字区域和其经过右移、上移sizeD个cell所覆盖的cell （含坐标方向）
         */
        int moveDistance, upperIndex;
        moveDistance = upperIndex = sizeB + sizeD;
        for (int i = -sizeB; i < 0; i++) {
            for (int j = 0; j < sizeD; j++) {
                treeSet.add(new TwoDimensionalIntegerPoint(i, j));
                treeSet.add(new TwoDimensionalIntegerPoint(i + moveDistance, j));
            }
        }
        for (int i = 0; i < sizeD; i++) {
            for (int j = -sizeB; j < upperIndex; j++) {
                treeSet.add(new TwoDimensionalIntegerPoint(i, j));
            }
        }


        /*
            添加被打散为四部分的高概率cell (含45方向)
         */
//        int upperIndex45 = (int) Math.ceil(this.sizeB / Math.sqrt(2)-0.5);

        // 处理45方向
        for (int i = 1; i <= upperIndex45; i++) {
            treeSet.add(new TwoDimensionalIntegerPoint(-i, -i));
            treeSet.add(new TwoDimensionalIntegerPoint(-i, i + sizeD - 1));
            treeSet.add(new TwoDimensionalIntegerPoint(i + sizeD - 1, -i));
            treeSet.add(new TwoDimensionalIntegerPoint(i + sizeD - 1, i + sizeD - 1));
        }
        // 处理其他部分
        int tempX, j;
        for (IdentityPair<Integer> tempPair : outerCellIndexCollection) {
            j = tempPair.getValue();
            tempX = tempPair.getKey();
            for (int i = j + 1; i <= tempX; i++) {
                treeSet.add(new TwoDimensionalIntegerPoint(-i, -j));
                treeSet.add(new TwoDimensionalIntegerPoint(-j, -i));
                treeSet.add(new TwoDimensionalIntegerPoint(-i, j + sizeD - 1));
                treeSet.add(new TwoDimensionalIntegerPoint(-j, i + sizeD - 1));
                treeSet.add(new TwoDimensionalIntegerPoint(i + sizeD - 1, -j));
                treeSet.add(new TwoDimensionalIntegerPoint(j + sizeD - 1, -i));
                treeSet.add(new TwoDimensionalIntegerPoint(i + sizeD - 1, j + sizeD - 1));
                treeSet.add(new TwoDimensionalIntegerPoint(j + sizeD - 1, i + sizeD - 1));
            }
        }
        return new ArrayList<>(treeSet);
    }


    /**
     * 根据给定的原始cell，返回整个输出空间中除去高概率部分和中间概率部分的纯低概率的cell的有序集合
     * @param realPoint
     * @return
     */
    public static TreeSet<TwoDimensionalIntegerPoint> getResidualPureLowCellsByPoint(TwoDimensionalIntegerPoint realPoint, List<TwoDimensionalIntegerPoint> noiseIntegerPointTypeList, List<IdentityPair<Integer>> innerCellIndexList, List<IdentityPair<Integer>> outerCellIndexList, Integer sizeB, Integer upperIndex45) {
        Integer realPointX = realPoint.getXIndex();
        Integer realPointY = realPoint.getYIndex();

        /*
            1. 构建集合包含：2π范围内部点，四段高概率坐标轴(含原始点)，四段高概率45轴，四部分外边界点
         */
        List<TwoDimensionalIntegerPoint> list = new ArrayList<>();
        IdentityPair<Integer> tempPair;
        Integer tempX, tempY;
        // (1) 2π范围内部点
        for (int i = 0; i < innerCellIndexList.size(); i++) {
            tempPair = innerCellIndexList.get(i);
            tempX = tempPair.getKey();
            tempY = tempPair.getValue();
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempX, realPointY+tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempY, realPointY+tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempX, realPointY+tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempY, realPointY+tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempX, realPointY-tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempY, realPointY-tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempX, realPointY-tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempY, realPointY-tempX));
        }
        // 四段高概率坐标轴(含原始点)
        list.add(realPoint);
        for (int i = 1; i <= sizeB; i++) {
            list.add(new TwoDimensionalIntegerPoint(realPointX+i, realPointY));
            list.add(new TwoDimensionalIntegerPoint(realPointX-i, realPointY));
            list.add(new TwoDimensionalIntegerPoint(realPointX, realPointY+i));
            list.add(new TwoDimensionalIntegerPoint(realPointX, realPointY-i));
        }
        // 四段高概率45轴(包含混合概率45轴点)
        for (int i = 1; i <= upperIndex45; i++) {
            list.add(new TwoDimensionalIntegerPoint(realPointX+i, realPointY+i));
            list.add(new TwoDimensionalIntegerPoint(realPointX-i, realPointY+i));
            list.add(new TwoDimensionalIntegerPoint(realPointX+i, realPointY-i));
            list.add(new TwoDimensionalIntegerPoint(realPointX-i, realPointY-i));
        }
        // 四部分外边界点
        for (int i = 0; i < outerCellIndexList.size(); i++) {
            tempPair = outerCellIndexList.get(i);
            tempX = tempPair.getKey();
            tempY = tempPair.getValue();
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempX, realPointY+tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempY, realPointY+tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempX, realPointY+tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempY, realPointY+tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempX, realPointY-tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempY, realPointY-tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempX, realPointY-tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempY, realPointY-tempX));
        }

        /*
            2. 从noise列表中除去上面集合的点
         */
        return SetUtils.getResidualOrderedElement(noiseIntegerPointTypeList, list);
    }



    public static Integer getOptimalSizeBOfDiskScheme(double epsilon, int sizeD) {
        double mA = Math.exp(epsilon) - 1 - epsilon;
        double mB = 1 - (1 - epsilon) * Math.exp(epsilon);
//        return (int)Math.ceil((2*mB+Math.sqrt(4*mB*mB+Math.PI*Math.exp(epsilon)*mA*mB))/(Math.PI*Math.exp(epsilon)*mA) * sizeD);
        return (int)Math.floor((2*mB+Math.sqrt(4*mB*mB+Math.PI*Math.exp(epsilon)*mA*mB))/(Math.PI*Math.exp(epsilon)*mA) * sizeD);
    }

}
