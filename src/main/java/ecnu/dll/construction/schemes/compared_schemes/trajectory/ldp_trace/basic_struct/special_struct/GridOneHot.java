package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct;

import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.CellNeighboring;

public class GridOneHot extends OneHot<CellNeighboring> {

    protected int rowSize;
    protected int colSize;

    public GridOneHot(int rowSize, int colSize) {
        // 8ab-6a-6b+4个单元
        super(8*rowSize*colSize-6*(rowSize+colSize)+4);
        this.rowSize = rowSize;
        this.colSize = colSize;
    }

    protected GridOneHot(boolean... booleans) {
        super(booleans);
    }

    @Override
    public OneHot<CellNeighboring> getInstance(boolean... booleans) {
        return new GridOneHot(booleans);
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

    protected int[] toOneHotDataIndex(CellNeighboring cellNeighboring) {
        int type = cellNeighboring.getType();
        TwoDimensionalIntegerPoint cellValue = cellNeighboring.getCellIndex();
        int[] oneHotIndex = new int[2];
        int xIndex = cellValue.getXIndex();
        int yIndex = cellValue.getYIndex();
        int bias = 0;
        switch (type) {
            case CellNeighboring.Angle:
                oneHotIndex[0] = (xIndex == 0 ? 0 : 1) * 6 + (yIndex == 0 ? 0 : 1) * 3;
                oneHotIndex[1] = oneHotIndex[0] + 2;
                break;
            case CellNeighboring.Edge:
                bias = 12;
                if (xIndex == 0) {
                    oneHotIndex[0] = bias + (yIndex - 1) * 5;
                } else if (yIndex == 0) {
                    oneHotIndex[0] = bias + (this.colSize-2) * 5 + (xIndex - 1) * 5;
                } else if (yIndex > 0) {
                    oneHotIndex[0] = bias + (this.colSize + this.rowSize - 4) * 5 + (xIndex - 1) * 5;
                } else {
                    oneHotIndex[0] = bias + (this.colSize + this.rowSize * 2 - 6) * 5 + (yIndex - 1) * 5;
                }
                oneHotIndex[1] = oneHotIndex[0] + 4;
                break;
            case CellNeighboring.Inner:
                bias = (this.rowSize + this.colSize) * 10 - 28;
                oneHotIndex[0] = bias + (xIndex - 1) * (this.colSize - 2) * 8 + (yIndex - 1) * 8;
                oneHotIndex[1] = oneHotIndex[0] + 7;
                break;
        }
        return oneHotIndex;
    }
    @Override
    public void setElement(CellNeighboring cellNeighboring) {
        int[] oneHotDataIndex = this.toOneHotDataIndex(cellNeighboring);
        for (int i = oneHotDataIndex[0]; i <= oneHotDataIndex[1]; i++) {
            this.data[i] = ONE;
        }
    }

    public static void main(String[] args) {
        GridOneHot gridOneHot = new GridOneHot(5, 5);
    }
}
