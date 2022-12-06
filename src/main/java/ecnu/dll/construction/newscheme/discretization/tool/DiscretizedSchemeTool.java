package ecnu.dll.construction.newscheme.discretization.tool;

import cn.edu.ecnu.struct.pair.IdentityPair;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;

import java.util.ArrayList;
import java.util.List;

public class DiscretizedSchemeTool {
    public static List<TwoDimensionalIntegerPoint> getRawTwoDimensionalIntegerPointTypeList(Integer sizeD) {
        int  totalSize = sizeD * sizeD;
        List<TwoDimensionalIntegerPoint> rawIntegerPointTypeList = new ArrayList<>(totalSize);
        for (int i = 0; i < sizeD; i++) {
            for (int j = 0; j < sizeD; j++) {
                rawIntegerPointTypeList.add(new TwoDimensionalIntegerPoint(i, j));
            }
        }
        return rawIntegerPointTypeList;
    }


    public static List<IdentityPair<Integer>> getRawIntegerIdentityPairTypeList(Integer sizeD) {
        int  totalSize = sizeD * sizeD;
        List<IdentityPair<Integer>> rawIntegerPointTypeList = new ArrayList<>(totalSize);
        for (int i = 0; i < sizeD; i++) {
            for (int j = 0; j < sizeD; j++) {
                rawIntegerPointTypeList.add(new IdentityPair<Integer>(i, j));
            }
        }
        return rawIntegerPointTypeList;
    }


}
