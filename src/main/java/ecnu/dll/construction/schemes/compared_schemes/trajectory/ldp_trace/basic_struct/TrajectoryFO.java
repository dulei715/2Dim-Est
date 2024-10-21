package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct;

import cn.edu.dll.differential_privacy.ldp.frequency_oracle.FrequencyOracle;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.foImp.OptimizedUnaryEncoding;
import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.UserTrajectoryOneHotStruct;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.UserTrajectoryOriginalStruct;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.sub_struct.CellNeighboring;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.AbsolutePositionOneHot;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.CellNeighboringOneHot;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.TrajectoryLengthOneHot;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.utils.TrajectoryFOUtils;

import java.util.Collection;
import java.util.TreeMap;

public class TrajectoryFO implements FrequencyOracle<UserTrajectoryOriginalStruct, UserTrajectoryOneHotStruct> {
    private double totalPrivacyBudget;
    private int maxTrajectoryLength;
    private int gridRowSize;
    private int gridColSize;

    private OptimizedUnaryEncoding<Integer> trajectoryLengthOUE;
    private OptimizedUnaryEncoding<CellNeighboring> innerPointOUE;
    private OptimizedUnaryEncoding<TwoDimensionalIntegerPoint> startPointOUE;
    private OptimizedUnaryEncoding<TwoDimensionalIntegerPoint> endPointOUE;



    public TrajectoryFO (double totalPrivacyBudget, int maxTrajectoryLength, int gridRowSize, int gridColSize) {
        this.totalPrivacyBudget = totalPrivacyBudget;
        this.maxTrajectoryLength = maxTrajectoryLength;
        this.gridRowSize = gridRowSize;
        this.gridColSize = gridColSize;

        double[] privacyBudgetSplitArray = TrajectoryFOUtils.getSplitPrivacyBudget(this.totalPrivacyBudget);
        this.trajectoryLengthOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[0]);
        this.innerPointOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[1]);
        this.startPointOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[2]/2);
        this.endPointOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[2]/2);
    }

    @Override
    public UserTrajectoryOneHotStruct perturb(UserTrajectoryOriginalStruct userTrajectoryOriginalStruct) {
        OneHot<Integer> trajectoryLengthOneHot = new TrajectoryLengthOneHot(this.maxTrajectoryLength);
        OneHot<CellNeighboring> cellNeighboringOneHot = new CellNeighboringOneHot(this.gridRowSize, this.gridColSize);
        OneHot<TwoDimensionalIntegerPoint> startCellOneHot = new AbsolutePositionOneHot(this.gridRowSize, this.gridColSize);
        OneHot<TwoDimensionalIntegerPoint> endCellOneHot = new AbsolutePositionOneHot(this.gridRowSize, this.gridColSize);

        trajectoryLengthOneHot.setElement(userTrajectoryOriginalStruct.trajectoryLength);
        cellNeighboringOneHot.setElement(userTrajectoryOriginalStruct.cellNeighboring);
        startCellOneHot.setElement(userTrajectoryOriginalStruct.startIndex);
        endCellOneHot.setElement(userTrajectoryOriginalStruct.endIndex);

        OneHot<Integer> newTrajectoryOneHot = this.trajectoryLengthOUE.perturb(trajectoryLengthOneHot);
        OneHot<CellNeighboring> newCellNeighboringOneHot = this.innerPointOUE.perturb(cellNeighboringOneHot);
        OneHot<TwoDimensionalIntegerPoint> newStartOneHot = this.startPointOUE.perturb(startCellOneHot);
        OneHot<TwoDimensionalIntegerPoint> newEndOneHot = this.startPointOUE.perturb(endCellOneHot);

        UserTrajectoryOneHotStruct newTrajectoryStruct = new UserTrajectoryOneHotStruct(newTrajectoryOneHot, newCellNeighboringOneHot, newStartOneHot, newEndOneHot);
        return newTrajectoryStruct;
    }

    @Deprecated
    @Override
    public double aggregate(int targetNoiseEstimationCount, int userSize) {
        /*
            1. trajectory length 和用户一一对应，他独立于其他两个分布
            2. cell neighboring 和 start/end point 用于构建转移状态函数，因此需要获取每一个cell到其周围节点的统计量
         */
        /**
         * 需要写三个函数用于对perturb之后的
         */
        throw new RuntimeException("You are not use this function, use other aggregating function instead!");
    }

    public Double[] aggregateTrajectoryLength(Collection<OneHot<Integer>> trajectoryLengthDataCollection) {
        int userSize = trajectoryLengthDataCollection.size();
        Integer[] trajectoryLengthCount = OptimizedUnaryEncoding.count(trajectoryLengthDataCollection);
        Double[] estimationResult = this.trajectoryLengthOUE.unbias(trajectoryLengthCount, userSize);
        return estimationResult;
    }
//    public double[]

    public Double[] aggregateCellNeighboring(Collection<OneHot<CellNeighboring>> cellNeighboringDataCollection) {
        int userSize = cellNeighboringDataCollection.size();
        Integer[] cellNeighboringCount = OptimizedUnaryEncoding.count(cellNeighboringDataCollection);
        Double[] estimationResult = this.innerPointOUE.unbias(cellNeighboringCount, userSize);
        return estimationResult;
    }

    public Double[] aggregateStartCell(Collection<OneHot<TwoDimensionalIntegerPoint>> cellStartDataCollection) {
        int userSize = cellStartDataCollection.size();
        Integer[] cellStartCount = OptimizedUnaryEncoding.count(cellStartDataCollection);
        Double[] estimationResult = this.startPointOUE.unbias(cellStartCount, userSize);
        return estimationResult;
    }
    public Double[] aggregateEndCell(Collection<OneHot<TwoDimensionalIntegerPoint>> cellEndDataCollection) {
        int userSize = cellEndDataCollection.size();
        Integer[] cellEndCount = OptimizedUnaryEncoding.count(cellEndDataCollection);
        Double[] estimationResult = this.endPointOUE.unbias(cellEndCount, userSize);
        return estimationResult;
    }


}
