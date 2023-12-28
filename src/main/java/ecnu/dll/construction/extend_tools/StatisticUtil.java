package ecnu.dll.construction.extend_tools;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;

import java.util.List;
import java.util.TreeMap;

public class StatisticUtil {
    public static TreeMap<TwoDimensionalIntegerPoint, Integer> statisticPoint(List<TwoDimensionalIntegerPoint> pointData) {
        TreeMap<TwoDimensionalIntegerPoint, Integer> result = new TreeMap<>();
        Integer tempCount;
        for (TwoDimensionalIntegerPoint point : pointData) {
            tempCount = result.get(point);
            if (tempCount == null) {
                tempCount = 1;
            } else {
                ++ tempCount;
            }
            result.put(point, tempCount);
        }
        return result;
    }
}
