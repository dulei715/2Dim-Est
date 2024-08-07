package ecnu.dll.construction.schemes.new_scheme.basic;



import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

import java.util.List;
import java.util.TreeMap;

public interface DiscretizedPlaneInterface {
    TwoDimensionalIntegerPoint getNoiseValue(TwoDimensionalIntegerPoint originalPoint);
//    TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<Pair<Integer, Integer>> valueList);
    TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<TwoDimensionalIntegerPoint> valueList);
}
