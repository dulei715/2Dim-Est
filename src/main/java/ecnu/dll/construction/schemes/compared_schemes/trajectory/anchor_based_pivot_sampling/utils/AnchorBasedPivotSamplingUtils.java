package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils;

import cn.edu.dll.geometry.Line;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;

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
        Line lowBoundLine = directionLine.roll(pivotPointX, pivotPointY, -unitAngle / 2);
        Line highBoundLine = directionLine.roll(pivotPointX, pivotPointY, unitAngle / 2);
        return new Line[]{lowBoundLine, highBoundLine};
    }

    protected Set<TwoDimensionalDoublePoint> 

    public static Set<TwoDimensionalDoublePoint> getPointSet(TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, int directSize) {

    }
}
