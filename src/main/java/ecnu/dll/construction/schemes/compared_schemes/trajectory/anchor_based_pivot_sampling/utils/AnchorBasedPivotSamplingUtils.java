package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.foImp.GeneralizedRandomizedResponse;
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


    @Deprecated
    public static Set<TwoDimensionalDoublePoint> getPointSet_before(List<TwoDimensionalDoublePoint> totalPointSet, TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, int sectorSize, int perturbedAreaIndex) {
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

    public static Set<TwoDimensionalDoublePoint> getPointSet(List<TwoDimensionalDoublePoint> totalPointSet, SectorAreas sectorAreas, int perturbedAreaIndex) {
        Set<TwoDimensionalDoublePoint> resultSet = new HashSet<>();
        for (TwoDimensionalDoublePoint point : totalPointSet) {
            if (SectorAreasUtils.isInArea(sectorAreas, perturbedAreaIndex, point)) {
                resultSet.add(point);
            }
        }
        return resultSet;
    }


    @Deprecated
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

    public static Set<TwoDimensionalDoublePoint> getPointIntersectionSet(List<TwoDimensionalDoublePoint> totalPointSet, SectorAreas sectorAreasA, SectorAreas sectorAreasB, int perturbedAreaIndexA, int perturbedAreaIndexB) {
        Set<TwoDimensionalDoublePoint> resultSet = new HashSet<>();
        for (TwoDimensionalDoublePoint point : totalPointSet) {
            if (SectorAreasUtils.isInArea(sectorAreasA, perturbedAreaIndexA, point) && SectorAreasUtils.isInArea(sectorAreasB, perturbedAreaIndexB, point)) {
                resultSet.add(point);
            }
        }
        return resultSet;
    }

    private static Double getPhiValue(Integer dI, Double thetaJ, Integer g) {
        double unitAngle = Math.PI / g;
        double[] dInterval = new double[]{(2 * dI - 1) * unitAngle, (2 * dI + 1) * unitAngle};
        double[] thetaJInterval = new double[]{-thetaJ, thetaJ};
        double[] intervalIntersection = BasicCalculation.getIntervalIntersection(dInterval, thetaJInterval);
        if (intervalIntersection == null) {
            return 0D;
        }
        return Math.abs(intervalIntersection[1] - intervalIntersection[0]) / (2 * Math.PI / g);
    }

    private static Double getLambdaValue(Integer dI, Integer d, Double epsilon, Integer g) {
        double tempValue = Math.exp(epsilon);
        if (dI.equals(d)) {
            return tempValue / (g - 1 + tempValue);
        }
        return 1.0 / (g - 1 + tempValue);
    }

    public static Double getOptimalSectorSize(Collection<Integer> candidateSectorSizeCollection, Double privacyBudget, Integer realDirectIndex) {
        GeneralizedRandomizedResponse<Integer> gRR;
        Integer[] tempDIArray;
        Double result = -1D, goalValue = -1D, tempGoalValue;
        Integer resultG = null;
        Collection<Double> bigTheta = new HashSet<>();
        for (Integer g : candidateSectorSizeCollection) {
            bigTheta.add(Math.PI / g);
        }
        Integer bigThetaSize = bigTheta.size();
        for (Integer g : candidateSectorSizeCollection) {
            tempDIArray = BasicArrayUtil.getIncreaseIntegerNumberArray(0, 1, g - 1);
            tempGoalValue = 0D;
            for (Double thetaJ : bigTheta) {
                for (Integer dI : tempDIArray) {
                    tempGoalValue += getPhiValue(dI, thetaJ, g) * getLambdaValue(dI, realDirectIndex, privacyBudget, g) / bigThetaSize;
                }
            }
            if (tempGoalValue > goalValue) {
                resultG = g;
                goalValue = tempGoalValue;
            }
        }
        return result;
    }
}
