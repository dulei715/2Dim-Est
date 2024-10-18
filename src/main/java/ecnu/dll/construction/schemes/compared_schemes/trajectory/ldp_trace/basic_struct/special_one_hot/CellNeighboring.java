package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

public class CellNeighboring {
    private TwoDimensionalIntegerPoint[][] cellIndex;

    public CellNeighboring(int rowSize, int colSize, int rowIndex, int colIndex) {
        this.cellIndex = new TwoDimensionalIntegerPoint[3][3];
        this.cellIndex[1][1] = new TwoDimensionalIntegerPoint(rowIndex, colIndex);
        if (rowIndex - 1 >= 0) {
            this.cellIndex[0][1] = new TwoDimensionalIntegerPoint(rowIndex-1, colIndex);
            if (colIndex - 1 >= 0) {
                this.cellIndex[0][0] = new TwoDimensionalIntegerPoint(rowIndex-1, colIndex-1);
            }
            if (colIndex + 1 < colSize) {
                this.cellIndex[0][2] = new TwoDimensionalIntegerPoint(rowIndex-1, colIndex+1);
            }
        }
        if (rowIndex + 1 < rowSize) {
            this.cellIndex[2][1] = new TwoDimensionalIntegerPoint(rowIndex+1, colIndex);
            this.cellIndex[0][1] = new TwoDimensionalIntegerPoint(rowIndex-1, colIndex);
            if (colIndex - 1 >= 0) {
                this.cellIndex[2][0] = new TwoDimensionalIntegerPoint(rowIndex+1, colIndex-1);
            }
            if (colIndex + 1 < colSize) {
                this.cellIndex[2][2] = new TwoDimensionalIntegerPoint(rowIndex+1, colIndex+1);
            }
        }

    }

}
