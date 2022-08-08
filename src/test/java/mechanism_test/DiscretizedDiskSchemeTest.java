package mechanism_test;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.struct.pair.IdentityPair;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import org.junit.Test;

import java.util.List;
import java.util.TreeSet;

public class DiscretizedDiskSchemeTest {
    @Test
    public void fun1() {
//        Double epsilon = 0.5;
//        Double gridLength = 1.0;
//        Double constB = 2.0;
//        Double inputLength = 2.0;
//        Double kParameter = 0.2;
//        Double xLeft = 0.0;
//        Double yLeft = 0.0;
        Integer sizeB = 2;
        Integer sizeD = 2;


//        DiscretizedDiskScheme discretizedDiskScheme = new DiscretizedDiskScheme(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
        Integer index45 = DiscretizedDiskSchemeTool.calculate45EdgeIndex(sizeB).getKey();
        List<IdentityPair<Integer>> outerCellIndexList = DiscretizedDiskSchemeTool.calculateOuterCellIndexList(sizeB);
        List<TwoDimensionalIntegerPoint> noiseIntegerPointTypeList = DiscretizedDiskSchemeTool.getNoiseIntegerPointTypeList(outerCellIndexList, sizeD, sizeB, index45);
        List<IdentityPair<Integer>> innerCellList = DiscretizedDiskSchemeTool.getInnerCell(outerCellIndexList);
        TreeSet<TwoDimensionalIntegerPoint> residualSet = DiscretizedDiskSchemeTool.getResidualPureLowCellsByPoint(new TwoDimensionalIntegerPoint(0, 0), noiseIntegerPointTypeList, innerCellList, outerCellIndexList, sizeB, index45);
        MyPrint.showSet(residualSet);
    }
}
