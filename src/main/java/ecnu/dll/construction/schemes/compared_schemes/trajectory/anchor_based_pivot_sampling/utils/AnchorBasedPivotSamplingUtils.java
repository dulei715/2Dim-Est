package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils;

import cn.edu.dll.geometry.Line;
import cn.edu.dll.geometry.LineUtils;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePointUtils;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct.SectorAreas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AnchorBasedPivotSamplingUtils {

    /**
     * 给定枢轴点，目标点和一个角度，返回以枢轴点与目标点所在直线为角平分线的该角度的两个边所在直线
     * @param pivotPoint
     * @param targetPoint
     * @param unitAngle
     * @return
     */
    protected static Line[] getBorderLine(TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, double unitAngle) {
        double pivotPointX = pivotPoint.getXIndex(), pivotPointY = pivotPoint.getYIndex();
        double targetPointX = targetPoint.getXIndex(), targetPointY = targetPoint.getYIndex();
        Line directionLine = new Line(pivotPointX, pivotPointY, targetPointX, targetPointY);
        Line lowBoundLine = LineUtils.getRoll(directionLine, pivotPointX, pivotPointY, -unitAngle / 2);
        Line highBoundLine = LineUtils.getRoll(directionLine, pivotPointX, pivotPointY, unitAngle / 2);
        return new Line[]{lowBoundLine, highBoundLine};
    }

    public static List<Line> getSeparateSortedSectorList(TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, int sectorSize) {
        Line[] resultLineArray = new Line[sectorSize];
        double pivotPointX = pivotPoint.getXIndex(), pivotPointY = pivotPoint.getYIndex();
        double targetPointX = targetPoint.getXIndex(), targetPointY = targetPoint.getYIndex();
        Line basicLine = new Line(pivotPointX, pivotPointY, targetPointX, targetPointY);
        double angle = 2 * Math.PI / sectorSize;
        Line tempLine = LineUtils.getRoll(basicLine, pivotPointX, pivotPointY, angle / 2);
        resultLineArray[0] = tempLine;
        for (int i = 1; i < sectorSize / 2; ++i) {
            tempLine = LineUtils.getRoll(tempLine, pivotPointX, pivotPointY, angle);
            resultLineArray[i] = tempLine;
        }
        Arrays.sort(resultLineArray);
        return Arrays.asList(resultLineArray);
    }

    /**
     * 针对排序好的直线组，返回其排序区域的直线一般式的值
     * @param lineSize
     * @return
     */
    public static List<BasicPair<Integer,Integer>> getSortedSeparateAreaStatusList(int lineSize) {
        List<BasicPair<Integer, Integer>> resultList = new ArrayList<>(lineSize * 2);
        // 处理第四象限到第一象限的区域
        // 处理一二象限
        resultList.add(new BasicPair<>(1, 1));
        for (int i = 1; i < lineSize; ++i) {
            resultList.add(new BasicPair<>(-1, 1));
        }
        // 处理第二象限到第三象限
        resultList.add(new BasicPair<>(-1, -1));
        for (int i = 1; i < lineSize; ++i) {
            resultList.add(new BasicPair<>(1, -1));
        }
        return resultList;
    }

    /**
     * 判断所给的点point是否在sectorAreas的第areaIndex区域内
     * @param sectorAreas
     * @param areaIndex
     * @param point
     * @return
     */
    public static boolean isInArea(SectorAreas sectorAreas, int areaIndex, TwoDimensionalDoublePoint point) {
        TwoDimensionalDoublePoint pivotPoint = sectorAreas.getPivotPoint();
        double pointX = point.getXIndex();
        double pointY = point.getYIndex();
        List<Line> sortedBorderLineList = sectorAreas.getSectorBorderSortedLineList();
        BasicPair<Integer, Integer> areaStatus = sectorAreas.getAreaList().get(areaIndex);
        int[] lineIndexes = SectorAreasUtils.fromAreaIndexToLineIndexes(areaIndex, sortedBorderLineList.size());
        Line lineLeft = sortedBorderLineList.get(lineIndexes[0]);
        Line lineRight = sortedBorderLineList.get(lineIndexes[1]);
        lineLeft.getLineValue()
        if (lineLeft.getLineValue())
    }

    public static Set<TwoDimensionalDoublePoint> getPointSet(TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, int directSize) {

    }
}
