package ecnu.dll.construction.specialtools;

import cn.edu.ecnu.struct.pair.BasicPair;
import cn.edu.ecnu.struct.pair.IdentityPair;

import java.util.TreeSet;

public class GridCrossDistanceTool {

    /**
     *
     * @param sizeD
     * @return
     */

    /********************************************************************************************************************/
    /*这里是一些基本功能函数，可能用不上*/
    public static boolean isInHighProbabilityArea(IdentityPair<Integer> centerPoint, int sizeB) {
        return Math.abs(centerPoint.getKey()) + Math.abs(centerPoint.getValue()) <= sizeB ? true : false;
    }

    



    /********************************************************************************************************************/

    public static Integer getSquareDistance(int sizeD) {
        return sizeD * (sizeD + 1) * (sizeD - 1) / 3 * 2 * sizeD * sizeD;
    }

    public static Integer getTotalDistance() {
        return null;
    }

    private static TreeSet<IdentityPair<Integer>> getCrossCellSet() {
        return null;
    }

}
