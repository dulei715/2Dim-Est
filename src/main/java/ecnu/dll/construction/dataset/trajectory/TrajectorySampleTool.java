package ecnu.dll.construction.dataset.trajectory;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePointUtils;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.common_tools.GridTools;

import java.util.*;

public class TrajectorySampleTool {
    // 表示将平面划分成 sampleGridSizeLength * sampleGridSizeLength 的网格
    private Integer sampleGridSizeLength;

    public TrajectorySampleTool(Integer sampleGridSizeLength) {
        this.sampleGridSizeLength = sampleGridSizeLength;
    }

    /**
     *      这里的轨迹长度是指轨迹中关键节点的个数
     *      步骤
     *          1. 读取原始数据集，根据经纬度，选定所有点的取值范围
     *          2. 确定选择粒度
     *          3. 根据粒度对平面进行网格划分
     *          4. 建立网格ID到所在点的映射
     *          5. 随机选取起点网格和长度
     *          6. 根据长度和起点，不断地根据邻居节点的密度随机选择邻居节点
     *          7. 输出最终轨迹
     */
    public List<List<TwoDimensionalDoublePoint>> sampleTrajectory(List<TwoDimensionalDoublePoint> totalPoint, Integer trajectoryLengthLowerBound, Integer trajectoryLengthUpperBound, Integer sampleSize) {
        List<List<TwoDimensionalDoublePoint>> result = new ArrayList<>();
        // 1. 读取原始数据集，选定所有点的取值范围
        TwoDimensionalDoublePoint leftBottomPoint, rightTopPoint;
        TwoDimensionalDoublePoint[] border = getBorder(totalPoint);
        leftBottomPoint = border[0];
        rightTopPoint = border[1];

        // 2. 选择粒度（见静态变量 sampleGridSizeLength）

        // 3. 根据粒度对平面进行网格划分
        List<TwoDimensionalIntegerPoint> gridList = generateGrid(sampleGridSizeLength);

        // 4. 建立网格ID到所在点的映射
        Map<TwoDimensionalIntegerPoint, List<TwoDimensionalDoublePoint>> gridToPointMap = getGridToPoint(sampleGridSizeLength, totalPoint, leftBottomPoint, rightTopPoint);

        // 5. 随机选取起点(在gridList中的位置)网格和长度
        List<Integer> trajectoryLengthList = Arrays.asList(RandomUtil.getRandomIntegerArray(trajectoryLengthLowerBound, trajectoryLengthUpperBound, sampleSize));
//        List<Integer> startIndexList = Arrays.asList(RandomUtil.getRandomIntegerArray(0, gridList.size(), sampleSize));
//        int startIndex = RandomUtil.getRandomInteger(0, gridList.size() - 1);
        List<TwoDimensionalIntegerPoint> startPointList = generateRandomNotNullCell(sampleSize, gridList, gridToPointMap);
        TwoDimensionalIntegerPoint startCell;

        List<TwoDimensionalDoublePoint> tempTrajectory;
        for (int i = 0; i < sampleSize; ++i) {
            startCell = startPointList.get(i);
            // 6. 根据长度和起点，不断地根据邻居节点的密度随机选择邻居节点
            tempTrajectory = generateTrajectory(startCell, trajectoryLengthList.get(i), gridToPointMap, sampleGridSizeLength, sampleGridSizeLength);
            result.add(tempTrajectory);
        }



        return result;
    }

    // 6. 根据长度和起点，不断地根据邻居节点的密度随机选择邻居节点
    public List<TwoDimensionalDoublePoint> generateTrajectory(TwoDimensionalIntegerPoint startCell, Integer trajectoryLength, Map<TwoDimensionalIntegerPoint, List<TwoDimensionalDoublePoint>> gridToPointMap, Integer xGridLength, Integer yGridLength) {
        Random random = new Random();
        TwoDimensionalIntegerPoint currentCell, tempNeighboring;
        List<TwoDimensionalDoublePoint> currentPointList;
        List<TwoDimensionalIntegerPoint> currentNeighboringList;
        List<TwoDimensionalDoublePoint> result = new ArrayList<>();

        int i, tempIndex;
        for (i = 0, currentCell = startCell; i < trajectoryLength; ++i) {
            // 这三步是用来在网格中随机选点
            currentPointList = gridToPointMap.get(currentCell);
            tempIndex = random.nextInt(currentPointList.size());
            result.add(currentPointList.get(tempIndex));
            // 接下来按照邻居点的密度概率选邻居
            currentNeighboringList = getNeighboring(currentCell, xGridLength, yGridLength);
            Integer[] neighboringCountArray = new Integer[currentNeighboringList.size()];

            // for test
            int checkSize = 0;

            for (int j = 0; j < currentNeighboringList.size(); ++j) {
                tempNeighboring = currentNeighboringList.get(j);
                neighboringCountArray[j] = gridToPointMap.get(tempNeighboring).size();
                // for test
                checkSize += neighboringCountArray[j];
            }
//            System.out.println("checkSize: " + checkSize);
            if (checkSize <= 0 && currentCell == startCell) {
                System.out.println("This start cell is an isolated point, the trajectory is reduced to 0 dimensions!");
                break;
            }

            Integer chosenNeighboringInnerIndex;
            do {
                chosenNeighboringInnerIndex = RandomUtil.getRandomIndexGivenStatisticPoint(neighboringCountArray);
            } while (chosenNeighboringInnerIndex >= currentNeighboringList.size());
            currentCell = currentNeighboringList.get(chosenNeighboringInnerIndex);
        }
        //todo: for test
        System.out.printf("The (expectation, real) trajectory length: (%d, %d)" + ConstantValues.LINE_SPLIT, trajectoryLength, result.size());
        return result;
    }

    private static List<TwoDimensionalIntegerPoint> getNeighboring(TwoDimensionalIntegerPoint currentPoint, Integer xGridLength, Integer yGridLength) {
        List<TwoDimensionalIntegerPoint> neighboringList = new ArrayList<>();
        Integer xIndex = currentPoint.getXIndex();
        Integer yIndex = currentPoint.getYIndex();
        for (int i = xIndex<=0 ? 0 : xIndex - 1; i <= (xIndex >= xGridLength - 1 ? xGridLength - 1 : xIndex + 1); ++i) {
            for (int j = yIndex<=0 ? 0 : yIndex - 1; j <= (yIndex >= yGridLength - 1 ? yGridLength - 1 : yIndex + 1); ++j) {
                if (i == xIndex && j == yIndex) {
                    continue;
                }
                neighboringList.add(new TwoDimensionalIntegerPoint(i, j));
            }
        }
        return neighboringList;
    }

    // 5. 随机选起始cell，要求所选cell中的点不能是空
    List<TwoDimensionalIntegerPoint> generateRandomNotNullCell(Integer sampleSize, List<TwoDimensionalIntegerPoint> gridList, Map<TwoDimensionalIntegerPoint, List<TwoDimensionalDoublePoint>> gridToPointMap) {
        List<TwoDimensionalIntegerPoint> result = new ArrayList<>();
        Integer totalCellSize = gridList.size();
        Integer chosenIndex;
        for (int i = 0; i < sampleSize; ++i) {
            do {
                chosenIndex = RandomUtil.getRandomInteger(0, totalCellSize - 1);
            } while (gridToPointMap.get(gridList.get(chosenIndex)).size() == 0);
            result.add(gridList.get(chosenIndex));
        }
        return result;
    }

    // 4. 建立网格ID到所在点的映射
    public static Map<TwoDimensionalIntegerPoint, List<TwoDimensionalDoublePoint>> getGridToPoint(Integer gridSideLength, List<TwoDimensionalDoublePoint> totalPoint, TwoDimensionalDoublePoint leftBorderPoint, TwoDimensionalDoublePoint rightTopPoint) {
        Map<TwoDimensionalIntegerPoint, List<TwoDimensionalDoublePoint>> result = new TreeMap<>();
        for (int i = 0; i < gridSideLength; ++i) {
            for (int j = 0; j < gridSideLength; ++j) {
                result.put(new TwoDimensionalIntegerPoint(i, j), new ArrayList<>());
            }
        }
        Double tempX, tempY;
        TwoDimensionalIntegerPoint tempKey;
        for (TwoDimensionalDoublePoint point : totalPoint) {
            int[] indexes = GridTools.getIndex(point, gridSideLength, leftBorderPoint, rightTopPoint);
            tempKey = new TwoDimensionalIntegerPoint(indexes);
            result.get(tempKey).add(point);
        }
        return result;
    }




    // 步骤3
    public static List<TwoDimensionalIntegerPoint> generateGrid(Integer gridLength) {
        TreeSet<TwoDimensionalIntegerPoint> set = new TreeSet<>();
        for (int i = 0; i < gridLength; ++i) {
            for (int j = 0; j < gridLength; ++j) {
                set.add(new TwoDimensionalIntegerPoint(i, j));
            }
        }
        return new ArrayList<>(set);
    }

    // 步骤1
    public static TwoDimensionalDoublePoint[] getBorder(List<TwoDimensionalDoublePoint> totalPoint) {
        List<Double> xIndexList = TwoDimensionalDoublePointUtils.getXIndexList(totalPoint);
        List<Double> yIndexList = TwoDimensionalDoublePointUtils.getYIndexList(totalPoint);
        double minimalXValue = ListUtils.getMinimalValue(xIndexList);
        double maximalXValue = ListUtils.getMaximalDoubleValue(xIndexList);
        double minimalYValue = ListUtils.getMinimalValue(yIndexList);
        double maximalYValue = ListUtils.getMaximalDoubleValue(yIndexList);
        TwoDimensionalDoublePoint leftBottom = new TwoDimensionalDoublePoint(minimalXValue, minimalYValue);
        TwoDimensionalDoublePoint rightTop = new TwoDimensionalDoublePoint(maximalXValue, maximalYValue);
        return new TwoDimensionalDoublePoint[]{leftBottom, rightTop};
    }


    public static void main(String[] args) {
//        TwoDimensionalIntegerPoint point = new TwoDimensionalIntegerPoint(2, 3);
//        TwoDimensionalIntegerPoint point = new TwoDimensionalIntegerPoint(0, 0);
        TwoDimensionalIntegerPoint point = new TwoDimensionalIntegerPoint(2, 5);
        Integer gridXSize = 5;
        Integer gridYSize = 6;
        List<TwoDimensionalIntegerPoint> result = getNeighboring(point, gridXSize, gridYSize);
        MyPrint.showList(result, ConstantValues.LINE_SPLIT);
    }

}
