package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.sub_struct.CellNeighboring;

public class UserTrajectoryOriginalStruct {
    public Integer trajectoryLength;
    public CellNeighboring cellNeighboring;
    public TwoDimensionalIntegerPoint startIndex;
    public TwoDimensionalIntegerPoint endIndex;

    public UserTrajectoryOriginalStruct() {
    }

    public UserTrajectoryOriginalStruct(Integer trajectoryLength, CellNeighboring cellNeighboring, TwoDimensionalIntegerPoint startIndex, TwoDimensionalIntegerPoint endIndex) {
        this.trajectoryLength = trajectoryLength;
        this.cellNeighboring = cellNeighboring;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public Integer getTrajectoryLength() {
        return trajectoryLength;
    }

    public void setTrajectoryLength(Integer trajectoryLength) {
        this.trajectoryLength = trajectoryLength;
    }

    public CellNeighboring getCellNeighboring() {
        return cellNeighboring;
    }

    public void setCellNeighboring(CellNeighboring cellNeighboring) {
        this.cellNeighboring = cellNeighboring;
    }

    public TwoDimensionalIntegerPoint getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(TwoDimensionalIntegerPoint startIndex) {
        this.startIndex = startIndex;
    }

    public TwoDimensionalIntegerPoint getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(TwoDimensionalIntegerPoint endIndex) {
        this.endIndex = endIndex;
    }


}
