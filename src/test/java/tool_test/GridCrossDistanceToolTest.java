package tool_test;

import cn.edu.ecnu.struct.pair.BasicPair;
import cn.edu.ecnu.struct.pair.IdentityPair;
import ecnu.dll.construction.specialtools.GridCrossDistanceTool;
import org.junit.Test;

public class GridCrossDistanceToolTest {
    @Test
    public void fun1() {
        int sizeB = 2;
        boolean booleanResult;
        for (int i = -sizeB - 1; i <= sizeB + 1; i++) {
            for (int j = -sizeB - 1; j <= sizeB + 1; j++) {
                booleanResult = GridCrossDistanceTool.isInHighProbabilityArea(new IdentityPair<Integer>(i, j), sizeB);
                System.out.print("(" + i + ", " + j + "): " + booleanResult + "; ");
            }
            System.out.println();
        }
    }
}
