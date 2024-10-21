package mechanism_test.ldp_trace_test;

import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.one_hot.OneHot;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.sub_struct.CellNeighboring;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.CellNeighboringOneHot;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.struct_utils.CellNeighboringOneHotUtils;
import org.junit.Test;

public class CellNeighboringOneHotUtilsTest {
    @Test
    public void fun1() {
        int rowSize = 4;
        int colSize = 5;
        int rowIndex = 1, colIndex = 0;
        int[] directIndex = new int[]{1, 2};
        CellNeighboring cellNeighboring = new CellNeighboring(rowSize, colSize, rowIndex, colIndex, directIndex);
        System.out.println(cellNeighboring);
        MyPrint.showSplitLine("*", 150);

        CellNeighboringOneHot oneHot = new CellNeighboringOneHot(rowSize, colSize);
        oneHot.setElement(cellNeighboring);
        System.out.println(oneHot.toClassifiedString());
        MyPrint.showSplitLine("*", 150);

        int[] indexRange = CellNeighboringOneHotUtils.toOneHotDataIndexRange(cellNeighboring);
        System.out.println("left_index: " + indexRange[0] + ", right_index: " + indexRange[1]);
        MyPrint.showSplitLine("*", 150);

        int oneHotIndex = CellNeighboringOneHotUtils.toOneHotIndex(cellNeighboring);
        System.out.println(oneHotIndex);
        MyPrint.showSplitLine("*", 150);

        CellNeighboring originalData = CellNeighboringOneHotUtils.toOriginalData(oneHotIndex, rowSize, colSize);
        System.out.println(originalData);
        MyPrint.showSplitLine("*", 150);
        MyPrint.showIntegerArray(originalData.getDirectNeighboringInnerIndex());
    }
}
