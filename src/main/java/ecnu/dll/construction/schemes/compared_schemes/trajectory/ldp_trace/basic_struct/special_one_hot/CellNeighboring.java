package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

public class CellNeighboring {
    public static final int Angle = 0;
    public static final int Edge = 1;
    public static final int Inner = 2;

    private final int type;
    private final int rowSize;
    private final int colSize;
    private final TwoDimensionalIntegerPoint[][] cellIndex;

    public CellNeighboring(int rowSize, int colSize, int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= rowSize || colIndex < 0 || colIndex >= colSize) {
            throw new RuntimeException("Initializing error: out of bound!");
        }
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.cellIndex = new TwoDimensionalIntegerPoint[3][3];
        this.cellIndex[1][1] = new TwoDimensionalIntegerPoint(rowIndex, colIndex);

        if ((rowIndex == 0 || rowIndex == rowSize-1) && (colIndex == 0 || colIndex == colSize-1)) {
            this.type = Angle;
        } else if (rowIndex == 0 || rowIndex == rowSize-1 || colIndex == 0 || colIndex == colSize-1) {
            this.type = Edge;
        } else {
            this.type = Inner;
        }

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
            if (colIndex - 1 >= 0) {
                this.cellIndex[2][0] = new TwoDimensionalIntegerPoint(rowIndex+1, colIndex-1);
            }
            if (colIndex + 1 < colSize) {
                this.cellIndex[2][2] = new TwoDimensionalIntegerPoint(rowIndex+1, colIndex+1);
            }
        }

        if (colIndex - 1 >= 0) {
            this.cellIndex[1][0] = new TwoDimensionalIntegerPoint(rowIndex, colIndex-1);
        }
        if (colIndex + 1 < colSize) {
            this.cellIndex[1][2] = new TwoDimensionalIntegerPoint(rowIndex, colIndex+1);
        }


    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public TwoDimensionalIntegerPoint getCellIndex() {
        return cellIndex[2][2];
    }

    public int getType() {
        return this.type;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, j; i < cellIndex.length; i++) {
            for (j = 0; j < cellIndex[0].length - 1; j++) {
                if (cellIndex[i][j] != null) {
                    stringBuilder.append("(").append(cellIndex[i][j].getXIndex()).append(", ").append(cellIndex[i][j].getYIndex()).append("), ");
                } else {
                    stringBuilder.append("null, ");
                }
            }
            if (cellIndex[i][j] != null) {
                stringBuilder.append("(").append(cellIndex[i][j].getXIndex()).append(", ").append(cellIndex[i][j].getYIndex()).append(")").append(ConstantValues.LINE_SPLIT);
            } else {
                stringBuilder.append("null").append(ConstantValues.LINE_SPLIT);
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        CellNeighboring cellNeighboring = new CellNeighboring(5, 5, 0, 1);
        TwoDimensionalIntegerPoint value = cellNeighboring.getCellIndex();
        System.out.println(value);
//        value.setXIndex(7);
//        System.out.println(value);
        System.out.println(cellNeighboring);
    }
}
