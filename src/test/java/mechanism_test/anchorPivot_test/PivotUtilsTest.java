package mechanism_test.anchorPivot_test;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.geometry.Line;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct.SectorAreas;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils.SectorAreasUtils;
import org.junit.Test;

import java.util.List;

public class PivotUtilsTest {
    @Test
    public void fun1() {
        TwoDimensionalDoublePoint pivotPoint = new TwoDimensionalDoublePoint(2, 3);
        TwoDimensionalDoublePoint targetPoint = new TwoDimensionalDoublePoint(4, 1);
        Integer sectorSize = 8;
        SectorAreas sectorAreas = new SectorAreas(pivotPoint, targetPoint, sectorSize);

        List<Line> sectorBorderSortedLineList = sectorAreas.getSectorBorderSortedLineList();
        MyPrint.showList(sectorBorderSortedLineList, ConstantValues.LINE_SPLIT);
        MyPrint.showSplitLine("*", 150);

        List<BasicPair<Integer, Integer>> areaList = sectorAreas.getAreaList();
        MyPrint.showList(areaList, ConstantValues.LINE_SPLIT);
        MyPrint.showSplitLine("*", 150);

        Integer targetPointExistingAreaIndex = sectorAreas.getTargetPointExistingAreaIndex();
        System.out.println(targetPointExistingAreaIndex);
        MyPrint.showSplitLine("*", 150);

        int[] lineIndexes = SectorAreasUtils.fromAreaIndexToLineIndexes(targetPointExistingAreaIndex, sectorSize / 2);
        MyPrint.showIntegerArray(lineIndexes);
        System.out.println(sectorBorderSortedLineList.get(lineIndexes[0]));
        System.out.println(sectorBorderSortedLineList.get(lineIndexes[1]));
        MyPrint.showSplitLine("*", 150);
    }
}
