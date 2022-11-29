package ecnu.dll.construction.specialtools;

import cn.edu.ecnu.struct.pair.BasicPair;
import cn.edu.ecnu.struct.pair.IdentityPair;

import java.util.TreeSet;

public class GridCrossDistanceTool extends GridCrossTool {

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

    private Integer getInnerTotalDistance() {
        return null;
    }
    public Integer getTotalDistance() {
//        TreeSet<IdentityPair<Integer>> innerSet = super.getCrossCell()
        return null;
    }



}
