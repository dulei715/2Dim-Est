package ecnu.dll.construction.run._2_trajectory_run.main_process.a_single_scheme_run;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePointUtils;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPointUtils;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.TrajectoryCommonTool;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.AnchorBasedPivotSampling;
import tools.others.Sinkhorn;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class PivotTraceRun {
    public ExperimentResult run(List<List<TwoDimensionalIntegerPoint>> trajectoryDataSet, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, Integer gridSideLength, Double epsilon, TwoDimensionalDoublePoint leftBottomPoint, TwoDimensionalDoublePoint rightTopPoint) {
        List<List<TwoDimensionalIntegerPoint>> estimationTrajectoryList = new ArrayList<>(trajectoryDataSet.size());
        List<TwoDimensionalDoublePoint> tempDoubleTrajectory;
        List<TwoDimensionalIntegerPoint> basicIntegerPointList = new ArrayList<>(rawDataStatistic.keySet());
        List<TwoDimensionalDoublePoint> basicDoublePointList = TwoDimensionalDoublePointUtils.toSimpleDoublePoint(basicIntegerPointList);
        List<TwoDimensionalDoublePoint> tempEstimationDoubleTrajectory;
        List<TwoDimensionalIntegerPoint> tempEstimationIntegerTrajectory;
        long startTime = System.currentTimeMillis();
        AnchorBasedPivotSampling pivotTrace = new AnchorBasedPivotSampling(basicDoublePointList);
        for (List<TwoDimensionalIntegerPoint> integerTrajectory : trajectoryDataSet) {
            tempDoubleTrajectory = TwoDimensionalDoublePointUtils.toSimpleDoublePoint(integerTrajectory);
            tempEstimationDoubleTrajectory = pivotTrace.execute(tempDoubleTrajectory, epsilon);
            tempEstimationIntegerTrajectory = TwoDimensionalIntegerPointUtils.toSimpleIntegerPoint(tempEstimationDoubleTrajectory);
            estimationTrajectoryList.add(tempEstimationIntegerTrajectory);
        }
        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = TrajectoryCommonTool.statistic(estimationTrajectoryList, rawDataStatistic.keySet());
        long endTime = System.currentTimeMillis();
        long postProcessTime = endTime - startTime;
        System.out.println("Total process time is " + postProcessTime);

        ExperimentResult experimentResult = null;
        Double wassersteinDistance1 = 0D;
        Double wassersteinDistance2 = Sinkhorn.getWassersteinDistanceBySinkhorn(estimationResult, rawDataStatistic, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND, Constant.SINKHORN_ITERATOR_UPPERBOUND);
        Double klDivergence = 0D;
        experimentResult = new ExperimentResult();
        // 这里实际上是trajectorySize，变量名和Excel头就不改了
        experimentResult.addPair(Constant.dataPointSizeKey, String.valueOf(trajectoryDataSet.size()));
        experimentResult.addPair(Constant.schemeNameKey, Constant.diskSchemeKey);
        experimentResult.addPair(Constant.postProcessTimeKey, String.valueOf(postProcessTime));
        experimentResult.addPair(Constant.gridUnitSizeKey, String.valueOf(cellLength));
        experimentResult.addPair(Constant.dataTypeSizeKey, String.valueOf(inputLength * inputLength));
        experimentResult.addPair(Constant.sizeDKey, String.valueOf(0));
        experimentResult.addPair(Constant.sizeBKey, String.valueOf(0));
        experimentResult.addPair(Constant.privacyBudgetKey, String.valueOf(epsilon));
        experimentResult.addPair(Constant.contributionKKey, String.valueOf(0));
        experimentResult.addPair(Constant.wassersteinDistance1Key, String.valueOf(wassersteinDistance1));
        experimentResult.addPair(Constant.wassersteinDistance2Key, String.valueOf(wassersteinDistance2));
        experimentResult.addPair(Constant.klDivergenceKey, String.valueOf(klDivergence));
        return experimentResult;
    }
}
