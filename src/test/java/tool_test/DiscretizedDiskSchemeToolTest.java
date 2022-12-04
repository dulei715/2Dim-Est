package tool_test;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.struct.pair.IdentityPair;
import ecnu.dll.construction.newscheme.discretization.struct.ThreePartsStruct;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import org.junit.Test;

import java.util.List;
import java.util.TreeSet;

public class DiscretizedDiskSchemeToolTest {
    @Test
    public void fun1() {
//        int sizeB = 2;
        int sizeB = 2;
        int sizeD = 2;
        List<IdentityPair<Integer>> outerCellIndexList = DiscretizedDiskSchemeTool.calculateOuterCellIndexList(sizeB);
        MyPrint.showList(outerCellIndexList);
        MyPrint.showSplitLine("*", 150);
        IdentityPair<Integer> point45 = DiscretizedDiskSchemeTool.calculate45EdgeIndex(sizeB);
        Double point45Area = DiscretizedDiskSchemeTool.get45EdgeIndexSplitArea(sizeB);
        System.out.println(point45 + ": " + point45Area);
        MyPrint.showSplitLine("*", 150);
        List<IdentityPair<Integer>> borderCellList = DiscretizedDiskSchemeTool.getOutputBorderOuterCellList(outerCellIndexList, sizeD, sizeB);
        MyPrint.showList(borderCellList);
        MyPrint.showSplitLine("*", 150);
        TreeSet<IdentityPair<Integer>> rightAddedSet = DiscretizedDiskSchemeTool.getMoveRightIncrementPureLowProbabilityCellSet(outerCellIndexList, sizeD, sizeB);
        MyPrint.showCollection(rightAddedSet);
        MyPrint.showSplitLine("*", 150);
        TreeSet<IdentityPair<Integer>> fistTotalAddedSet = DiscretizedDiskSchemeTool.getMoveRightUpperIncrementPureLowProbabilityCellSet(outerCellIndexList, sizeD, sizeB, rightAddedSet);
        MyPrint.showCollection(fistTotalAddedSet);
        MyPrint.showSplitLine("*", 150);
        TreeSet<IdentityPair<Integer>> totalAddedSet = DiscretizedDiskSchemeTool.getResidualPureLowCells(outerCellIndexList, sizeD, sizeB);
        MyPrint.showCollection(totalAddedSet);
        MyPrint.showSplitLine("*", 150);

    }

    @Test
    public void fun2() {
        int sizeB = 7;

        int xIndex = 6;
        int yIndex = 5;
        double shrinkAreaSize = DiscretizedDiskSchemeTool.getShrinkAreaSize(sizeB, xIndex, yIndex);
        System.out.println(shrinkAreaSize);
    }

    @Test
    public void fun3() {
        IdentityPair<Integer> centerCell = new IdentityPair<>(2, 3);
        IdentityPair<Integer> judgeCell = new IdentityPair<>(4, 7);
        IdentityPair<Integer> relativeCell = DiscretizedDiskSchemeTool.transformToWithin45(centerCell, judgeCell);
        System.out.println(relativeCell);
    }


    @Test
    public void fun4() {
        IdentityPair<Integer> centerCell = new IdentityPair<>(0, 0);
        int sizeD = 6;
        int sizeB = 3;
        ThreePartsStruct<IdentityPair<Integer>> splitCellsInInputArea = DiscretizedDiskSchemeTool.getSplitCellsInInputArea(centerCell, sizeD, sizeB);
        System.out.println(splitCellsInInputArea);
    }

























}
