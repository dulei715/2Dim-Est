package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.sub_struct.CellNeighboring;

public class CellNeighboringOneHot extends OneHot<CellNeighboring> {

    protected int rowSize;
    protected int colSize;

    public CellNeighboringOneHot(int rowSize, int colSize) {
        // 8ab-6a-6b+4个单元
        super(8*rowSize*colSize-6*(rowSize+colSize)+4);
        this.rowSize = rowSize;
        this.colSize = colSize;
    }

    protected CellNeighboringOneHot(boolean... booleans) {
        super(booleans);
    }

    @Override
    public OneHot<CellNeighboring> getInstance(boolean... booleans) {
        CellNeighboringOneHot newInstance =  new CellNeighboringOneHot(booleans);
        newInstance.rowSize = this.rowSize;
        newInstance.colSize = this.colSize;
        return newInstance;
    }

    /*
        当 a = b = n 时
        CellNeighboring 可以分为三类：
            1. 四个角(4个元素)：邻居各占3个单元
            2. 四条边(4n-8个元素)：邻居各占5个单元
            3. 内部节点(n^2-4n+4个元素)：邻居各占8个单元
         因此总共需要 12+20n-40+8n^2-32n+32 = 8n^2-12n+4个单元
         OneHot设定为：
            1. 12个角单元
            2. 20n-40个边单元
            3. 8n^2-32n+32个内部单元

         当 a不等于b时
         CellNeighboring 可以分为三类：
            1. 四个角(4个元素)：邻居各占3个单元
            2. 四条边(2a+2b-8个元素)：邻居各占5个单元
            3. 内部节点(a*b-2a-2b+4个元素)：邻居各占8个单元
         因此总共需要 12+10a+10b-40+8ab-16a-16b+32 = 8ab-6a-6b+4个单元
         OneHot设定为：
            1. 12个角单元
            2. 10a+10b-40个边单元
            3. 8ab-16a-16b+32个内部单元

     */

    /**
     * 这里的xIndex为rowIndex，x周向下，y轴向右
     * @param cellNeighboring
     * @return
     */
    protected int[] toOneHotDataIndex(CellNeighboring cellNeighboring) {
        int type = cellNeighboring.getType();
        TwoDimensionalIntegerPoint cellValue = cellNeighboring.getCellIndex();
        int[] oneHotIndex = new int[2];
        int rowIndex = cellValue.getXIndex();
        int colIndex = cellValue.getYIndex();
        int bias;
        switch (type) {
            case CellNeighboring.Angle:
                oneHotIndex[0] = (rowIndex == 0 ? 0 : 1) * 6 + (colIndex == 0 ? 0 : 1) * 3;
                oneHotIndex[1] = oneHotIndex[0] + 2;
                break;
            case CellNeighboring.Edge:
                bias = 12;
                if (rowIndex == 0) {
                    oneHotIndex[0] = bias + (colIndex - 1) * 5;
                } else if (colIndex == 0) {
                    oneHotIndex[0] = bias + (this.colSize-2) * 5 + (rowIndex - 1) * 5;
                } else if (colIndex > 0) {
                    oneHotIndex[0] = bias + (this.colSize + this.rowSize - 4) * 5 + (rowIndex - 1) * 5;
                } else {
                    oneHotIndex[0] = bias + (this.colSize + this.rowSize * 2 - 6) * 5 + (colIndex - 1) * 5;
                }
                oneHotIndex[1] = oneHotIndex[0] + 4;
                break;
            case CellNeighboring.Inner:
                bias = (this.rowSize + this.colSize) * 10 - 28;
                oneHotIndex[0] = bias + (rowIndex - 1) * (this.colSize - 2) * 8 + (colIndex - 1) * 8;
                oneHotIndex[1] = oneHotIndex[0] + 7;
                break;
        }
        return oneHotIndex;
    }

    protected static int getBias(CellNeighboring cellNeighboring) {
        TwoDimensionalIntegerPoint[][] cellNeighboringIndex = cellNeighboring.getCellNeighboringIndex();
        int[] directNeighboringInnerIndex = cellNeighboring.getDirectNeighboringInnerIndex();
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1 || cellNeighboringIndex[i][j] == null) {
                    continue;
                }
                if (directNeighboringInnerIndex[0] == i && directNeighboringInnerIndex[1] == j) {
                    return k;
                }
                ++k;
            }
        }
        throw new RuntimeException("Not found right direct neighboring!");
    }

    @Override
    public void setElement(CellNeighboring cellNeighboring) {
        int[] oneHotDataIndex = this.toOneHotDataIndex(cellNeighboring);
//        for (int i = oneHotDataIndex[0]; i <= oneHotDataIndex[1]; i++) {
//            this.data[i] = ONE;
//        }
        int bias = getBias(cellNeighboring);
        this.data[oneHotDataIndex[0]+bias] = ONE;
    }

    public String toClassifiedString() {
        StringBuilder stringBuilder = new StringBuilder();

        int k = 0;
        for (int i = 0; i < 4; i++) {
            stringBuilder.append("(");
            for (int j = 0; j < 3; j++) {
                stringBuilder.append(this.data[k++] ? "1" : "0").append(j < 2 ? ", " : ")");
            }
            stringBuilder.append(", ");
            if (i == 3) {
                stringBuilder.append(ConstantValues.LINE_SPLIT);
            }
        }
        int tempCellSize = 2 * (this.rowSize + this.colSize) - 8;
        for (int i = 0; i < tempCellSize; i++) {
            if (i % (this.colSize - 2) == 0) {
                stringBuilder.append("[");
            }
            stringBuilder.append("(");
            for (int j = 0; j < 5; j++) {
                stringBuilder.append(this.data[k++] ? "1" : "0").append(j < 4 ? ", " : ")");
            }
            if (i % (this.colSize - 2) == this.colSize - 3) {
                stringBuilder.append("]");
            }
            stringBuilder.append(", ");
            if (i == tempCellSize - 1) {
                stringBuilder.append(ConstantValues.LINE_SPLIT);
            }
        }
        tempCellSize = (this.rowSize - 2) * (this.colSize - 2);
        for (int i = 0; i < tempCellSize; i++) {
            if (i % (this.colSize - 2) == 0) {
                stringBuilder.append("[");
            }
            stringBuilder.append("(");
            for (int j = 0; j < 8; j++) {
                stringBuilder.append(this.data[k++] ? "1" : "0").append(j < 7 ? ", " : ")");
            }
            if (i % (this.colSize - 2) == this.colSize - 3) {
                stringBuilder.append("]");
            }
            if (i < tempCellSize - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        int rowSize = 5;
        int colSize = 5;
        CellNeighboringOneHot cellNeighboringOneHot = new CellNeighboringOneHot(rowSize, colSize);
        System.out.println(cellNeighboringOneHot);
        MyPrint.showSplitLine("*", 150);

        int[] directNeighboring = CellNeighboring.Bottom;
        CellNeighboring cellNeighboring = new CellNeighboring(rowSize, colSize, 1, 1, directNeighboring);
        cellNeighboringOneHot.setElement(cellNeighboring);
        System.out.println(cellNeighboringOneHot.toClassifiedString());
    }
}
