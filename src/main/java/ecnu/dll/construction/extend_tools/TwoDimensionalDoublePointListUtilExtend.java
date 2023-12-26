package ecnu.dll.construction.extend_tools;

import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;

import java.util.ArrayList;
import java.util.List;


public class TwoDimensionalDoublePointListUtilExtend {
    public static List<TwoDimensionalDoublePoint> getTwoBorderPoints(List<TwoDimensionalDoublePoint> dataList) {
        double minimalLeft = Double.MAX_VALUE, minimalBottom = Double.MAX_VALUE, maximalRight = -Double.MAX_VALUE, maximalTop=-Double.MAX_VALUE;
        double[] index;
        TwoDimensionalDoublePoint leftBottom, rightTop;
        List<TwoDimensionalDoublePoint> result = new ArrayList<>();
        for (TwoDimensionalDoublePoint point : dataList) {
            index = point.getIndex();
            if (index[0] < minimalLeft) {
                minimalLeft = index[0];
            } else if (index[0] > maximalRight) {
                maximalRight = index[0];
            }
            if (index[1] < minimalBottom) {
                minimalBottom = index[1];
            } else if (index[1] > maximalTop) {
                maximalTop = index[1];
            }
        }
        leftBottom = new TwoDimensionalDoublePoint(minimalLeft, minimalBottom);
        rightTop = new TwoDimensionalDoublePoint(maximalRight, maximalTop);
        result.add(leftBottom);
        result.add(rightTop);
        return result;
    }
}
