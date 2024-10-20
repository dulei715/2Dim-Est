package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot;

import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.sub_struct.CellNeighboring;

public class UserTrajectoryOneHotStruct {
    public OneHot<Integer> trajectoryLength;
    public OneHot<CellNeighboring> cellNeighboring;
    public OneHot<TwoDimensionalIntegerPoint> startIndex;
    public OneHot<TwoDimensionalIntegerPoint> endIndex;

    public UserTrajectoryOneHotStruct() {
    }

    public UserTrajectoryOneHotStruct(OneHot<Integer> trajectoryLength, OneHot<CellNeighboring> cellNeighboring, OneHot<TwoDimensionalIntegerPoint> startIndex, OneHot<TwoDimensionalIntegerPoint> endIndex) {
        this.trajectoryLength = trajectoryLength;
        this.cellNeighboring = cellNeighboring;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public OneHot<Integer> getTrajectoryLength() {
        return trajectoryLength;
    }

    public void setTrajectoryLength(OneHot<Integer> trajectoryLength) {
        this.trajectoryLength = trajectoryLength;
    }

    public OneHot<CellNeighboring> getCellNeighboring() {
        return cellNeighboring;
    }

    public void setCellNeighboring(OneHot<CellNeighboring> cellNeighboring) {
        this.cellNeighboring = cellNeighboring;
    }

    public OneHot<TwoDimensionalIntegerPoint> getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(OneHot<TwoDimensionalIntegerPoint> startIndex) {
        this.startIndex = startIndex;
    }

    public OneHot<TwoDimensionalIntegerPoint> getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(OneHot<TwoDimensionalIntegerPoint> endIndex) {
        this.endIndex = endIndex;
    }
}
