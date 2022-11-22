package ecnu.dll.construction.specialtools;

import cn.edu.ecnu.struct.pair.BasicPair;

import java.util.TreeSet;

public class GridCrossDistanceTool {

    /**
     *
     * @param sizeD
     * @return
     */

    /********************************************************************************************************************/
    /*这里是一些基本功能函数，可能用不上*/





    /********************************************************************************************************************/

    public static Integer getSquareDistance(int sizeD) {
        return sizeD * (sizeD + 1) * (sizeD - 1) / 3 * 2 * sizeD * sizeD;
    }

    public static Integer getTotalDistance() {
        return null;
    }

    private static TreeSet<BasicPair<Integer, Integer>> getCrossCellSet() {
        return null;
    }

}
