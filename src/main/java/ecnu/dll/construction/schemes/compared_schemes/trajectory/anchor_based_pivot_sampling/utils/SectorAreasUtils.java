package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils;

import cn.edu.dll.geometry.Line;
import cn.edu.dll.struct.pair.BasicPair;

public class SectorAreasUtils {
    public static int[] fromAreaIndexToLineIndexes(int areaIndex, int lineSize) {
        int[] indexes = new int[2];
        indexes[0] = (areaIndex + lineSize - 1) % lineSize;
        indexes[1] = areaIndex % lineSize;
        return indexes;
    }


//    public static BasicPair<Integer, Integer> getAreaStatus(Line leftLine, Line rightLine) {
////        if ()
//    }
}
