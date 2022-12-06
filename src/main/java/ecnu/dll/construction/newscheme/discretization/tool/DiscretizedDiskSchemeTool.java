package ecnu.dll.construction.newscheme.discretization.tool;

import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.collection.SetUtils;
import cn.edu.ecnu.struct.pair.BasicPair;
import cn.edu.ecnu.struct.pair.PairListUtils;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPointUtils;
import ecnu.dll.construction.newscheme.discretization.struct.ThreePartsStruct;

import java.util.*;

@SuppressWarnings("ALL")
public class DiscretizedDiskSchemeTool {
    /**
     * 这里只考虑 [0,π/4] 上的 shrink （如果有cross，则原点与cell中心点的连线必然与cell的左边界相交）
     *
     * @param sizeB
     * @param xIndex
     * @param yIndex
     * @return
     */
    public static double getShrinkAreaSize(Integer sizeB, int xIndex, int yIndex) {
        double indexLength = Math.sqrt(xIndex * xIndex + yIndex * yIndex);
        if (sizeB >= indexLength) {
            return 1;
        }
        double delta = sizeB * 1.0 / indexLength - 1;
        double partX = delta * xIndex + 0.5;
        if (partX <= 0) {
            return 0;
        }
        double tempShrinkAreaSize = 4 * partX * (delta * yIndex + 0.5);
        return tempShrinkAreaSize;
    }

    public static TwoDimensionalIntegerPoint transformToWithin45(TwoDimensionalIntegerPoint centerCell, TwoDimensionalIntegerPoint judgeCell) {
        Integer[] differenceArray = new Integer[2];
        differenceArray[0] = Math.abs(centerCell.getXIndex() - judgeCell.getXIndex());
        differenceArray[1] = Math.abs(centerCell.getYIndex() - judgeCell.getYIndex());
        if (differenceArray[0] < differenceArray[1]) {
            ArraysUtils.swap(differenceArray, 0, 1);
        }
        return new TwoDimensionalIntegerPoint(differenceArray[0], differenceArray[1]);
    }

    /**
     * 根据给定的sizeB计算45度方向边界cell坐标
     * @param sizeB
     * @return
     */
    public static TwoDimensionalIntegerPoint calculate45EdgeIndex(Integer sizeB) {
        double index45Prime = sizeB / Math.sqrt(2) - 0.5;
        Integer upperIndex45 = (int) Math.ceil(index45Prime);
        return new TwoDimensionalIntegerPoint(upperIndex45, upperIndex45);
    }

    /**
     *  获取内部纯高概率cell坐标(不含0方向和45方向)
     * @param highProbabilityBorderCellIndexList 边缘cell按照y坐标从小到大排列的列表
     * @return
     */
    public static List<TwoDimensionalIntegerPoint> getInnerCell(List<TwoDimensionalIntegerPoint> highProbabilityBorderCellIndexList) {
        TwoDimensionalIntegerPoint tempPair;
        Integer tempX, tempY;
        List<TwoDimensionalIntegerPoint> innerCellIndexList;
        TreeSet<TwoDimensionalIntegerPoint> treeSet = new TreeSet<>();
        for (int k = 0; k < highProbabilityBorderCellIndexList.size(); k++) {
            tempPair = highProbabilityBorderCellIndexList.get(k);
            tempX = tempPair.getXIndex();
            tempY = tempPair.getYIndex();
            for (int i = k + 2; i < tempX; i++) {
                treeSet.add(new TwoDimensionalIntegerPoint(i, tempY));
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
    public static List<TwoDimensionalIntegerPoint> calculateHighProbabilityBorderCellIndexList(Integer sizeB) {
        List<TwoDimensionalIntegerPoint> outerCellIndexList;
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
            outerCellIndexList.add(new TwoDimensionalIntegerPoint(xIndexTemp, i));
        }
        return outerCellIndexList;
    }

    /**
     * 角度区间(0,45)之间的输出边界cell列表
     */
    public static List<TwoDimensionalIntegerPoint> getOutputBorderOuterCellList(List<TwoDimensionalIntegerPoint> highProbabilityBorderCellIndexList, Integer sizeD, Integer sizeB) {
        List<TwoDimensionalIntegerPoint> outputBorderOuterCellList = new ArrayList<>();
        TwoDimensionalIntegerPoint tempPair;
        for (int i = 1; i < sizeD; i++) {
            outputBorderOuterCellList.add(new TwoDimensionalIntegerPoint(sizeD + sizeB - 1, i));
        }
        for (int i = 0; i < highProbabilityBorderCellIndexList.size(); i++) {
            tempPair = highProbabilityBorderCellIndexList.get(i);
            outputBorderOuterCellList.add(new TwoDimensionalIntegerPoint(tempPair.getXIndex() + sizeD - 1, tempPair.getYIndex() + sizeD - 1));
        }
        return outputBorderOuterCellList;
    }


    public static Integer getUpperIndex45(Integer sizeB) {
        Double index45Prime = sizeB / Math.sqrt(2) - 0.5;
        return  (int) Math.ceil(index45Prime);
    }


    /**
     *
     * @param sortedHighProbabilityBorderCellIndexList 按照Y坐标排序好的（0，45）度之间的边界
     * @param sizeD
     * @param sizeB
     * @return 右移后，[0,90]度之间的外部增量
     */
    public static TreeSet<TwoDimensionalIntegerPoint> getMoveRightIncrementPureLowProbabilityCellSet(List<TwoDimensionalIntegerPoint> sortedHighProbabilityBorderCellIndexList, Integer sizeD, Integer sizeB) {
        /**
         *    1. 构建[0,90]度的边界值
         *    2. 计算右移后的边界值
         *    3. 将原始的[0,90]度边界值分为两类：[0,45) 和 [45,90] 两部分
         *          (1) [0,45) 部分由于每行只有一个，因此可以直接遍历每行计算出增加的cell
         *          (2) [45,90] 部分要按照行进行分组，计算每组右移增加的值
         *    4. 将增加的值记录到TreeSet中按照点本身顺序进行排序输出
         */
        TreeSet<TwoDimensionalIntegerPoint> resultSet = new TreeSet<>();
        /*
            1. 构建[0,90]度的边界值
         */
        // [0]方向边界值（只记录横坐标）
        Integer border0 = sizeB;
        // [45]方向边界值
        TwoDimensionalIntegerPoint border45 = DiscretizedDiskSchemeTool.calculate45EdgeIndex(sizeB);
        // [45,90)度边界值
        Collection<TwoDimensionalIntegerPoint> upperPartCollection = TwoDimensionalIntegerPointUtils.getExchangeXYIndexList(sortedHighProbabilityBorderCellIndexList);
        TwoDimensionalIntegerPoint tempLastPair = sortedHighProbabilityBorderCellIndexList.get(sortedHighProbabilityBorderCellIndexList.size() - 1);
        if (border45.getYIndex() > tempLastPair.getYIndex()) { // 此处是为了判断边界值是否已到border45同一水平
            upperPartCollection.add(border45);
        } else {
            upperPartCollection.add(tempLastPair);
        }
        //(45,90)度边界值按照纵坐标分组(行分组)(方便找到最右侧点)
        TreeMap<Integer, TreeSet<TwoDimensionalIntegerPoint>> yIndexMap = TwoDimensionalIntegerPointUtils.groupByYIndex(upperPartCollection);


        /*
            2. 计算右移后的边界值
         */
        // [0] 方向右移
        Integer border0MoveRightXIndex = sizeB + sizeD - 1;
        Integer tempYIndex, tempXIndex, tempMovedXIndex;
        for (int i = sizeB + 1; i <= border0MoveRightXIndex; i++) {
            resultSet.add(new TwoDimensionalIntegerPoint(i, 0));
        }
        // (0,45) 方向右移 todo: 遍历sortedOuterCellIndexList纵坐标
        for (TwoDimensionalIntegerPoint pair : sortedHighProbabilityBorderCellIndexList) {
            tempYIndex = pair.getYIndex();
            tempXIndex = pair.getXIndex();
            tempMovedXIndex = tempXIndex + sizeD - 1;
            for (int x = tempXIndex + 1; x <= tempMovedXIndex; x++) {
                resultSet.add(new TwoDimensionalIntegerPoint(x, tempYIndex));
            }
        }
        // [45,90] 方向右移 todo: 遍历upperPartList每个列组
        for (Map.Entry<Integer, TreeSet<TwoDimensionalIntegerPoint>> entry : yIndexMap.entrySet()) {
            tempYIndex = entry.getKey();
            tempXIndex = entry.getValue().last().getXIndex();
            tempMovedXIndex = tempXIndex + sizeD;
            for (int x = tempXIndex + 1; x < tempMovedXIndex; x++) {
                resultSet.add(new TwoDimensionalIntegerPoint(x, tempYIndex));
            }
        }
        return resultSet;
    }

    /**
     *
     * @param sortedHighProbabilityBorderCellIndexList 按照Y坐标排序好的（0，45）度之间的边界
     * @param sizeD
     * @param sizeB
     * @param movedRightAddedCells 右移后增加的cell
     * @return 右移且上移后，[0,90]度之间的外部增量
     */
    public static TreeSet<TwoDimensionalIntegerPoint> getMoveRightUpperIncrementPureLowProbabilityCellSet(List<TwoDimensionalIntegerPoint> sortedHighProbabilityBorderCellIndexList, Integer sizeD, Integer sizeB, Collection<TwoDimensionalIntegerPoint> movedRightAddedCells) {
        /**
         *    1. 将边界cell和右移增加的cell movedRightAddedCell合并产生新的cell集合 middleSet
         *    2. 将middleSet按照x坐标分组
         *    3. 计算上移的额外增加cell moveUpAddedCell
         *    4. 将moveUpAddedCell与movedRightAddedCell合并，即为输出结果
         */
        Integer tempXIndex, tempYIndex, tempAddedYIndex;
        TreeSet<TwoDimensionalIntegerPoint> resultSet = new TreeSet<>();

        TwoDimensionalIntegerPoint border45 = DiscretizedDiskSchemeTool.calculate45EdgeIndex(sizeB);
        TreeSet<TwoDimensionalIntegerPoint> middleSet = new TreeSet<>(sortedHighProbabilityBorderCellIndexList);
        middleSet.addAll(TwoDimensionalIntegerPointUtils.getExchangeXYIndexList(sortedHighProbabilityBorderCellIndexList));
        middleSet.add(border45);
        middleSet.add(new TwoDimensionalIntegerPoint(0, sizeB));
        middleSet.addAll(movedRightAddedCells);
        TreeMap<Integer, TreeSet<TwoDimensionalIntegerPoint>> middleMap = TwoDimensionalIntegerPointUtils.groupByXIndex(middleSet);
        for (Map.Entry<Integer, TreeSet<TwoDimensionalIntegerPoint>> middleEntry : middleMap.entrySet()) {
            tempXIndex = middleEntry.getKey();
            tempYIndex = middleEntry.getValue().last().getYIndex();
            tempAddedYIndex = tempYIndex + sizeD - 1;
            for (int y = tempYIndex + 1; y <= tempAddedYIndex; y++) {
                resultSet.add(new TwoDimensionalIntegerPoint(tempXIndex, y));
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
    public static Collection<TwoDimensionalIntegerPoint> factorChangeIntegerPair(Collection<TwoDimensionalIntegerPoint> originalCollection, Integer xFactor, Integer yFactor) {
        Collection<TwoDimensionalIntegerPoint> result = new ArrayList<>();
        Integer tempXIndex, tempYIndex;
        for (TwoDimensionalIntegerPoint twoDimensionalIntegerPoint : originalCollection) {
            tempXIndex = twoDimensionalIntegerPoint.getXIndex();
            tempYIndex = twoDimensionalIntegerPoint.getYIndex();
            result.add(new TwoDimensionalIntegerPoint(tempXIndex * xFactor, tempYIndex * yFactor));
        }
        return result;
    }

    /**
     * 以(0,0)为原始点，计算纯低概率部分的point集合
     * @return
     */
    public static TreeSet<TwoDimensionalIntegerPoint> getResidualPureLowCells(List<TwoDimensionalIntegerPoint> sortedHighProbabilityCellIndexList, Integer sizeD, Integer sizeB){
        /**
         *  1. 计算第二象限的
         *  2. 计算第四象限的
         *  3. 计算第一象限的
         */
        TreeSet<TwoDimensionalIntegerPoint> resultSet = new TreeSet<>();
        TreeSet<TwoDimensionalIntegerPoint> moveRightAddedCellSet = DiscretizedDiskSchemeTool.getMoveRightIncrementPureLowProbabilityCellSet(sortedHighProbabilityCellIndexList, sizeD, sizeB);

        Collection<TwoDimensionalIntegerPoint> fourthQuadrantAddedCells = DiscretizedDiskSchemeTool.factorChangeIntegerPair(moveRightAddedCellSet, 1, -1);
        Collection<TwoDimensionalIntegerPoint> secondQuadrantAddedCells = DiscretizedDiskSchemeTool.factorChangeIntegerPair(TwoDimensionalIntegerPointUtils.getExchangeXYIndexList(moveRightAddedCellSet), -1, 1);
        TreeSet<TwoDimensionalIntegerPoint> firstQuadrantAddedCells = DiscretizedDiskSchemeTool.getMoveRightUpperIncrementPureLowProbabilityCellSet(sortedHighProbabilityCellIndexList, sizeD, sizeB, moveRightAddedCellSet);

        resultSet.addAll(fourthQuadrantAddedCells);
        resultSet.addAll(secondQuadrantAddedCells);
        resultSet.addAll(firstQuadrantAddedCells);

        return resultSet;
    }


    /**
     * 返回所有可能的输出cell
     * @param highProbabilityBorderCellIndexCollection
     * @param sizeD
     * @param sizeB
     * @param upperIndex45
     * @return
     */
    public static List<TwoDimensionalIntegerPoint> getNoiseIntegerPointTypeList(Collection<TwoDimensionalIntegerPoint> highProbabilityBorderCellIndexCollection, Integer sizeD, Integer sizeB, Integer upperIndex45) {
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
        for (TwoDimensionalIntegerPoint tempPair : highProbabilityBorderCellIndexCollection) {
            j = tempPair.getYIndex();
            tempX = tempPair.getXIndex();
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
    public static TreeSet<TwoDimensionalIntegerPoint> getResidualPureLowCellsByPoint(TwoDimensionalIntegerPoint realPoint, List<TwoDimensionalIntegerPoint> noiseIntegerPointTypeList, List<TwoDimensionalIntegerPoint> innerCellIndexList, List<TwoDimensionalIntegerPoint> outerCellIndexList, Integer sizeB, Integer upperIndex45) {
        Integer realPointX = realPoint.getXIndex();
        Integer realPointY = realPoint.getYIndex();

        /*
            1. 构建集合包含：2π范围内部点，四段高概率坐标轴(含原始点)，四段高概率45轴，四部分外边界点
         */
        List<TwoDimensionalIntegerPoint> list = new ArrayList<>();
        TwoDimensionalIntegerPoint tempPair;
        Integer tempX, tempY;
        // (1) 2π范围内部点
        for (int i = 0; i < innerCellIndexList.size(); i++) {
            tempPair = innerCellIndexList.get(i);
            tempX = tempPair.getXIndex();
            tempY = tempPair.getYIndex();
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
            tempX = tempPair.getXIndex();
            tempY = tempPair.getYIndex();
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


    public static ThreePartsStruct<TwoDimensionalIntegerPoint> getSplitCellsInInputArea(TwoDimensionalIntegerPoint centerCell, int sizeD, int sizeB) {
        ThreePartsStruct<TwoDimensionalIntegerPoint> resultStruct = new ThreePartsStruct<>();
        TwoDimensionalIntegerPoint tempInputCell, transformedCell;
        double tempShrinkAreaSize;
        for (int x = 0; x < sizeD; x++) {
            for (int y = 0; y < sizeD; y++) {
                tempInputCell = new TwoDimensionalIntegerPoint(x, y);
                transformedCell = DiscretizedDiskSchemeTool.transformToWithin45(centerCell, tempInputCell);
                tempShrinkAreaSize = DiscretizedDiskSchemeTool.getShrinkAreaSize(sizeB, transformedCell.getXIndex(), transformedCell.getYIndex());
                if (tempShrinkAreaSize >= 1.0) {
                    resultStruct.addHighProbabilityElement(tempInputCell);
                } else if (tempShrinkAreaSize <= 0.0) {
                    resultStruct.addLowProbabilityElement(tempInputCell);
                } else {
                    resultStruct.addMixProbabilityElement(tempInputCell, tempShrinkAreaSize);
                }
            }
        }
        return resultStruct;
    }

















}
