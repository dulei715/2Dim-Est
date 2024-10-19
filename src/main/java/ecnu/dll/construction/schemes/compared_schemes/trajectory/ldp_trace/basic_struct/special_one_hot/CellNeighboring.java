package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

public class CellNeighboring {
    public static final int Angle = 0;
    public static final int Edge = 1;
    public static final int Inner = 2;
    public static final int[] LeftTop = new int[]{0, 0};
    public static final int[] Top = new int[]{0, 1};
    public static final int[] RightTop = new int[]{0, 2};
    public static final int[] Left = new int[]{1, 0};
    public static final int[] Right = new int[]{1, 2};
    public static final int[] LeftBottom = new int[]{2, 0};
    public static final int[] Bottom = new int[]{2, 1};
    public static final int[] RightBottom = new int[]{2, 2};

    private final int type;
    private final int rowSize;
    private final int colSize;
    private final TwoDimensionalIntegerPoint[][] cellNeighboringIndex;

    private int[] directNeighboringInnerIndex;

    public CellNeighboring(int rowSize, int colSize, int rowIndex, int colIndex, int[] directNeighboringInnerIndex) {
        if (rowIndex < 0 || rowIndex >= rowSize || colIndex < 0 || colIndex >= colSize) {
            throw new RuntimeException("Initializing error: out of bound!");
        }
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.cellNeighboringIndex = new TwoDimensionalIntegerPoint[3][3];
        this.cellNeighboringIndex[1][1] = new TwoDimensionalIntegerPoint(rowIndex, colIndex);

        if ((rowIndex == 0 || rowIndex == rowSize-1) && (colIndex == 0 || colIndex == colSize-1)) {
            this.type = Angle;
        } else if (rowIndex == 0 || rowIndex == rowSize-1 || colIndex == 0 || colIndex == colSize-1) {
            this.type = Edge;
        } else {
            this.type = Inner;
        }

        if (rowIndex - 1 >= 0) {
            this.cellNeighboringIndex[0][1] = new TwoDimensionalIntegerPoint(rowIndex-1, colIndex);
            if (colIndex - 1 >= 0) {
                this.cellNeighboringIndex[0][0] = new TwoDimensionalIntegerPoint(rowIndex-1, colIndex-1);
            }
            if (colIndex + 1 < colSize) {
                this.cellNeighboringIndex[0][2] = new TwoDimensionalIntegerPoint(rowIndex-1, colIndex+1);
            }
        }
        if (rowIndex + 1 < rowSize) {
            this.cellNeighboringIndex[2][1] = new TwoDimensionalIntegerPoint(rowIndex+1, colIndex);
            if (colIndex - 1 >= 0) {
                this.cellNeighboringIndex[2][0] = new TwoDimensionalIntegerPoint(rowIndex+1, colIndex-1);
            }
            if (colIndex + 1 < colSize) {
                this.cellNeighboringIndex[2][2] = new TwoDimensionalIntegerPoint(rowIndex+1, colIndex+1);
            }
        }

        if (colIndex - 1 >= 0) {
            this.cellNeighboringIndex[1][0] = new TwoDimensionalIntegerPoint(rowIndex, colIndex-1);
        }
        if (colIndex + 1 < colSize) {
            this.cellNeighboringIndex[1][2] = new TwoDimensionalIntegerPoint(rowIndex, colIndex+1);
        }

        if (this.cellNeighboringIndex[directNeighboringInnerIndex[0]][directNeighboringInnerIndex[1]] == null) {
            throw new RuntimeException("The given neighboring is error!");
        }
        this.directNeighboringInnerIndex = directNeighboringInnerIndex;

    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public TwoDimensionalIntegerPoint getCellIndex() {
        return cellNeighboringIndex[1][1];
    }

    public TwoDimensionalIntegerPoint[][] getCellNeighboringIndex() {
        return this.cellNeighboringIndex;
    }

    public int getType() {
        return this.type;
    }

    public int[] getDirectNeighboringInnerIndex() {
        return directNeighboringInnerIndex;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, j; i < cellNeighboringIndex.length; i++) {
            for (j = 0; j < cellNeighboringIndex[0].length - 1; j++) {
                if (cellNeighboringIndex[i][j] != null) {
                    stringBuilder.append("(").append(cellNeighboringIndex[i][j].getXIndex()).append(", ").append(cellNeighboringIndex[i][j].getYIndex()).append("), ");
                } else {
                    stringBuilder.append("null, ");
                }
            }
            if (cellNeighboringIndex[i][j] != null) {
                stringBuilder.append("(").append(cellNeighboringIndex[i][j].getXIndex()).append(", ").append(cellNeighboringIndex[i][j].getYIndex()).append(")").append(ConstantValues.LINE_SPLIT);
            } else {
                stringBuilder.append("null").append(ConstantValues.LINE_SPLIT);
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        int[] neighboringDirect = LeftBottom;
        CellNeighboring cellNeighboring = new CellNeighboring(5, 5, 0, 1, neighboringDirect);
        TwoDimensionalIntegerPoint value = cellNeighboring.getCellIndex();
        System.out.println(value);
//        value.setXIndex(7);
//        System.out.println(value);
        System.out.println(cellNeighboring);

    }
}
