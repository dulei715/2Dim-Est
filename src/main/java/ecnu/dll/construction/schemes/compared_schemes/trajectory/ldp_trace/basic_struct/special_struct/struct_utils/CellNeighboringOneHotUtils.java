package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.struct_utils;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.sub_struct.CellNeighboring;

public class CellNeighboringOneHotUtils {
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
    public static int[] toOneHotDataIndexRange(CellNeighboring cellNeighboring) {
        int type = cellNeighboring.getType();
        TwoDimensionalIntegerPoint cellValue = cellNeighboring.getCellIndex();
        int[] oneHotIndex = new int[2];
        int rowIndex = cellValue.getXIndex();
        int colIndex = cellValue.getYIndex();
        int rowSize = cellNeighboring.getRowSize();
        int colSize = cellNeighboring.getColSize();
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
                    oneHotIndex[0] = bias + (colSize-2) * 5 + (rowIndex - 1) * 5;
                } else if (colIndex > 0) {
                    oneHotIndex[0] = bias + (colSize + rowSize - 4) * 5 + (rowIndex - 1) * 5;
                } else {
                    oneHotIndex[0] = bias + (colSize + rowSize * 2 - 6) * 5 + (colIndex - 1) * 5;
                }
                oneHotIndex[1] = oneHotIndex[0] + 4;
                break;
            case CellNeighboring.Inner:
                bias = (rowSize + colSize) * 10 - 28;
                oneHotIndex[0] = bias + (rowIndex - 1) * (colSize - 2) * 8 + (colIndex - 1) * 8;
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

    public static int toOneHotIndex(CellNeighboring cellNeighboring) {
        int[] oneHotDataIndex = toOneHotDataIndexRange(cellNeighboring);
        int bias = getBias(cellNeighboring);
        return oneHotDataIndex[0] + bias;
    }



    public static CellNeighboring toOriginalData(int index) {

    }
}
