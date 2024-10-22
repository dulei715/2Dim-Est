package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils;

import cn.edu.dll.geometry.Line;
import cn.edu.dll.geometry.LineUtils;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePointUtils;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct.SectorAreas;

import java.util.*;

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



    public static Set<TwoDimensionalDoublePoint> getPointSet(List<TwoDimensionalDoublePoint> totalPointSet, TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, int sectorSize, int perturbedAreaIndex) {
        SectorAreas sectorAreas = new SectorAreas(pivotPoint, targetPoint, sectorSize);
//        int targetPointAreaIndex = sectorAreas.getTargetPointExistingAreaIndex();
        Set<TwoDimensionalDoublePoint> resultSet = new HashSet<>();
        for (TwoDimensionalDoublePoint point : totalPointSet) {
            if (SectorAreasUtils.isInArea(sectorAreas, perturbedAreaIndex, point)) {
                resultSet.add(point);
            }
        }
        return resultSet;
    }
    public static Set<TwoDimensionalDoublePoint> getPointIntersectionSet(List<TwoDimensionalDoublePoint> totalPointSet, TwoDimensionalDoublePoint pivotPointA, TwoDimensionalDoublePoint pivotPointB, TwoDimensionalDoublePoint targetPoint, int sectorSize, int perturbedAreaIndexA, int perturbedAreaIndexB) {
        SectorAreas sectorAreasA = new SectorAreas(pivotPointA, targetPoint, sectorSize);
        SectorAreas sectorAreasB = new SectorAreas(pivotPointB, targetPoint, sectorSize);
//        int targetPointAreaIndex = sectorAreas.getTargetPointExistingAreaIndex();
        Set<TwoDimensionalDoublePoint> resultSet = new HashSet<>();
        for (TwoDimensionalDoublePoint point : totalPointSet) {
            if (SectorAreasUtils.isInArea(sectorAreasA, perturbedAreaIndexA, point) && SectorAreasUtils.isInArea(sectorAreasB, perturbedAreaIndexB, point)) {
                resultSet.add(point);
            }
        }
        return resultSet;
    }
}
