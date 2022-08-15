package ecnu.dll.construction.newscheme.basic;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;

import java.util.List;
import java.util.TreeMap;

public interface DiscretizedPlaneInterface {
    TwoDimensionalIntegerPoint getNoiseValue(TwoDimensionalIntegerPoint originalPoint);
//    TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<Pair<Integer, Integer>> valueList);
    TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<TwoDimensionalIntegerPoint> valueList);
}
