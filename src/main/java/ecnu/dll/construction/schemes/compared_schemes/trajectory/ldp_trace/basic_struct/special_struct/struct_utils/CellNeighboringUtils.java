package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.struct_utils;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.sub_struct.CellNeighboring;

public class CellNeighboringUtils {
    public static TwoDimensionalIntegerPoint[][] getCellNeighboringIndex(int rowSize, int colSize, int rowIndex, int colIndex) {
        TwoDimensionalIntegerPoint[][] cellNeighboringIndex = new TwoDimensionalIntegerPoint[3][3];
        cellNeighboringIndex[1][1] = new TwoDimensionalIntegerPoint(rowIndex, colIndex);

        if (rowIndex - 1 >= 0) {
            cellNeighboringIndex[0][1] = new TwoDimensionalIntegerPoint(rowIndex -1, colIndex);
            if (colIndex - 1 >= 0) {
                cellNeighboringIndex[0][0] = new TwoDimensionalIntegerPoint(rowIndex -1, colIndex -1);
            }
            if (colIndex + 1 < colSize) {
                cellNeighboringIndex[0][2] = new TwoDimensionalIntegerPoint(rowIndex -1, colIndex +1);
            }
        }
        if (rowIndex + 1 < rowSize) {
            cellNeighboringIndex[2][1] = new TwoDimensionalIntegerPoint(rowIndex +1, colIndex);
            if (colIndex - 1 >= 0) {
                cellNeighboringIndex[2][0] = new TwoDimensionalIntegerPoint(rowIndex +1, colIndex -1);
            }
            if (colIndex + 1 < colSize) {
                cellNeighboringIndex[2][2] = new TwoDimensionalIntegerPoint(rowIndex +1, colIndex +1);
            }
        }

        if (colIndex - 1 >= 0) {
            cellNeighboringIndex[1][0] = new TwoDimensionalIntegerPoint(rowIndex, colIndex -1);
        }
        if (colIndex + 1 < colSize) {
            cellNeighboringIndex[1][2] = new TwoDimensionalIntegerPoint(rowIndex, colIndex +1);
        }

        return cellNeighboringIndex;
    }

    public static int getType(int rowSize, int colSize, int rowIndex, int colIndex) {
        int type;
        if ((rowIndex == 0 || rowIndex == rowSize -1) && (colIndex == 0 || colIndex == colSize -1)) {
            type = CellNeighboring.Angle;
        } else if (rowIndex == 0 || rowIndex == rowSize -1 || colIndex == 0 || colIndex == colSize -1) {
            type = CellNeighboring.Edge;
        } else {
            type = CellNeighboring.Inner;
        }
        return type;
    }
}
