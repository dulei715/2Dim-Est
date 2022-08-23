package ecnu.dll.construction.newscheme.discretization.tool;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;

import java.util.ArrayList;
import java.util.List;

public class DiscretizedSchemeTool {
    public static List<TwoDimensionalIntegerPoint> getRawIntegerPointTypeList(Integer sizeD) {
        int  totalSize = sizeD * sizeD;
        List<TwoDimensionalIntegerPoint> rawIntegerPointTypeList = new ArrayList<>(totalSize);
        for (int i = 0; i < sizeD; i++) {
            for (int j = 0; j < sizeD; j++) {
                rawIntegerPointTypeList.add(new TwoDimensionalIntegerPoint(i, j));
            }
        }
        return rawIntegerPointTypeList;
    }
}
