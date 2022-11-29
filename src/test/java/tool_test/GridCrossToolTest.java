package tool_test;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.struct.pair.IdentityPair;
import ecnu.dll.construction.specialtools.GridCrossTool;
import org.junit.Test;

import java.util.TreeSet;

public class GridCrossToolTest extends GridCrossTool {
    @Test
    public void fun1() {
        int sizeB = 2;
        boolean booleanResult;
        IdentityPair<Integer> centerPoint = new IdentityPair<>(0, 0);
        for (int i = -sizeB - 1; i <= sizeB + 1; i++) {
            for (int j = -sizeB - 1; j <= sizeB + 1; j++) {
                booleanResult = GridCrossTool.isInHighProbabilityArea(centerPoint, sizeB, new IdentityPair<Integer>(i, j));
                System.out.print("(" + i + ", " + j + "): " + booleanResult + "; ");
            }
            System.out.println();
        }
    }

    @Test
    public void fun2() {
        int sizeD = 6;
        int sizeB = 4;
//        IdentityPair<Integer> centerPoint = new IdentityPair<>(0, 0);
        IdentityPair<Integer> centerPoint = new IdentityPair<>(-2, 1);
        TreeSet<IdentityPair<Integer>> crossCellSet = GridCrossTool.getCrossCell(centerPoint, sizeD, sizeB);
        MyPrint.showCollection(crossCellSet, "; ");
        System.out.println(crossCellSet.size());
    }

}
