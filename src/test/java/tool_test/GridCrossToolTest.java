package tool_test;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.struct.pair.IdentityPair;
import org.junit.Test;

public class GridCrossToolTest extends GridCrossTool {


    @Test
    public void fun3() {
        int sizeD = 3;
        int sizeB = 2;
        IdentityPair<Integer> centerPoint = new IdentityPair<>(0, 0);
        RAMGridCrossDistance gridCrossDistanceTool = new RAMGridCrossDistance(sizeD, sizeB, RAMGridCrossDistance.ONE_NORM);
//        GridCrossDistanceTool gridCrossDistanceTool = new GridCrossDistanceTool(sizeD, sizeB, GridCrossDistanceTool.TWO_NORM);
        double[] totalDistance = gridCrossDistanceTool.getTotalDistance(centerPoint);
        MyPrint.showDoubleArray(totalDistance);
    }

}
