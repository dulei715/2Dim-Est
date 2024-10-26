package ecnu.dll.construction.common_tools;

import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

import java.util.ArrayList;
import java.util.List;

public class GridTools {

    /**
     * 给定一个二维Double点，返回它在网格中的位置
     * @param point
     * @param gridSideLength
     * @param leftBottomPoint
     * @param rightTopPoint
     * @return
     */
    public static int[] getIndex(TwoDimensionalDoublePoint point, Integer gridSideLength, TwoDimensionalDoublePoint leftBottomPoint, TwoDimensionalDoublePoint rightTopPoint) {
        Double leftBottomXIndex = leftBottomPoint.getXIndex();
        Double leftBottomYIndex = leftBottomPoint.getYIndex();
        double xUnit, yUnit;
        Double xSideLength = rightTopPoint.getXIndex() - leftBottomXIndex;
        xUnit = xSideLength / gridSideLength;
        Double ySizeLength = rightTopPoint.getYIndex() - leftBottomYIndex;
        yUnit = ySizeLength / gridSideLength;
        Double xDiffer = point.getXIndex() - leftBottomXIndex;
        Double yDiffer = point.getYIndex() - leftBottomYIndex;
        int xResultIndex = (int) Math.floor(xDiffer / xUnit);
        if (xResultIndex >= gridSideLength) {
            // 专门处理右边或上边边界情况
            xResultIndex = gridSideLength - 1;
        }
        int yResultIndex = (int) Math.floor(yDiffer / yUnit);
        if (yResultIndex >= gridSideLength) {
            yResultIndex = gridSideLength - 1;
        }
        return new int[]{xResultIndex, yResultIndex};
    }

    public static List<TwoDimensionalIntegerPoint> fromDoubleTrajectoryToGridTrajectory(List<TwoDimensionalDoublePoint> doubleTrajectory, Integer gridSideLength, TwoDimensionalDoublePoint leftBottomPoint, TwoDimensionalDoublePoint rightTopPoint) {
        List<TwoDimensionalIntegerPoint> result = new ArrayList<>(doubleTrajectory.size());
        TwoDimensionalIntegerPoint tempIntegerPoint;
        for (TwoDimensionalDoublePoint doublePoint : doubleTrajectory) {
            tempIntegerPoint = new TwoDimensionalIntegerPoint(getIndex(doublePoint, gridSideLength, leftBottomPoint, rightTopPoint));
            result.add(tempIntegerPoint);
        }
        return result;
    }

}
