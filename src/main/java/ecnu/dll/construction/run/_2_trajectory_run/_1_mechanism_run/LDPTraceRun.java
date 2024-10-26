package ecnu.dll.construction.run._2_trajectory_run._1_mechanism_run;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.common_tools.GridTools;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.LDPTrace;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.TrajectoryFO;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.UserTrajectoryOneHotStruct;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.UserTrajectoryOriginalStruct;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.utils.TrajectoryFOUtils;

import java.util.List;

public class LDPTraceRun {

    private static final Integer maxTravelDistance = Constant.TrajectorySamplingLengthUpperBound;

    protected List<Double[]> trajectoryDisturbAndCollection(List<List<TwoDimensionalDoublePoint>> trajectoryData, LDPTrace ldpTrace, TwoDimensionalDoublePoint leftBottomPoint, TwoDimensionalDoublePoint rightTopPoint) {
        List<TwoDimensionalIntegerPoint> tempGridTrajectory;
        UserTrajectoryOriginalStruct tempOriginalTrajectoryInfo;
        UserTrajectoryOneHotStruct tempTrajectoryOneHotStruct;
        Double totalPrivacyBudget = ldpTrace.getTotalPrivacyBudget();

        int rowSize = ldpTrace.getRowSize();
        int colSize = ldpTrace.getColSize();
        int gridSideLength;
        if (rowSize != colSize) {
            throw new RuntimeException("You need to make the row size and col size equal!");
        }
        gridSideLength = rowSize;

        TrajectoryFO trajectoryFO = new TrajectoryFO(totalPrivacyBudget, maxTravelDistance, rowSize, colSize);
        for (List<TwoDimensionalDoublePoint> trajectory : trajectoryData) {
            tempGridTrajectory = GridTools.fromDoubleTrajectoryToGridTrajectory(trajectory, gridSideLength, leftBottomPoint, rightTopPoint);
            tempOriginalTrajectoryInfo = TrajectoryFOUtils.extractOriginalTraceInformation(tempGridTrajectory, gridSideLength, gridSideLength);
            tempTrajectoryOneHotStruct = trajectoryFO.perturb(tempOriginalTrajectoryInfo);
            // todo: cxx
        }
    }

    public ExperimentResult runAndWrite(List<List<TwoDimensionalDoublePoint>> dataSet, Integer gridSideLength, Double epsilon) {
        LDPTrace ldpTrace = new LDPTrace(gridSideLength, gridSideLength, epsilon, maxTravelDistance);
//        ldpTrace.trajectorySynthesis()



        return null;
    }
}
