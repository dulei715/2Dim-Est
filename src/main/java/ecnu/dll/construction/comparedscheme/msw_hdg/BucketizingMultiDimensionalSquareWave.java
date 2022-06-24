package ecnu.dll.construction.comparedscheme.msw_hdg;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.basicscheme.square_wave.discretization.BucketizingOptimalSquareWave;
import javafx.util.Pair;

import java.util.*;

public class BucketizingMultiDimensionalSquareWave {
    private BucketizingOptimalSquareWave xSquareWave = null;
    private BucketizingOptimalSquareWave ySquareWave = null;

    public BucketizingMultiDimensionalSquareWave(BucketizingOptimalSquareWave xSquareWave, BucketizingOptimalSquareWave ySquareWave) {
        this.xSquareWave = xSquareWave;
        this.ySquareWave = ySquareWave;
    }

    public BucketizingMultiDimensionalSquareWave(Double epsilon, Integer xInputSize, Integer yInputSize) {
        this.xSquareWave = new BucketizingOptimalSquareWave(epsilon, xInputSize);
        this.ySquareWave = new BucketizingOptimalSquareWave(epsilon, yInputSize);
    }

    private Integer getRandomIndexFlag() {
        double part = Math.random();
        if (part < 0.5) {
            return IndexFlag.X;
        }
        return IndexFlag.Y;
    }

    /**
     * Pair的第一个参数标志着坐标类别，第二个参数标志着坐标的值
     * @param point
     * @return
     */
    public Pair<Integer, Integer> getNoiseIndex(TwoDimensionalIntegerPoint point) {
        Integer indexFlagValue = this.getRandomIndexFlag();
        if (IndexFlag.X.equals(indexFlagValue)) {
            return new Pair<>(indexFlagValue, this.xSquareWave.getNoiseIndex(point.getXIndex()));
        }
        return new Pair<>(indexFlagValue, this.ySquareWave.getNoiseIndex(point.getYIndex()));
    }

    private static Map<Integer, List<Integer>> splitCountByIndexFlag(List<Pair<Integer, Integer>> valueList) {
        List<Integer> xIndexList = new ArrayList<>();
        List<Integer> yIndexList = new ArrayList<>();
        Integer indexFlagValue;
        Map<Integer, List<Integer>> result = new HashMap<>();
        for (Pair<Integer, Integer> pair : valueList) {
            indexFlagValue = pair.getKey();
            if (IndexFlag.X.equals(indexFlagValue)) {
                xIndexList.add(pair.getValue());
            } else {
                yIndexList.add(pair.getValue());
            }
        }
        result.put(IndexFlag.X, xIndexList);
        result.put(IndexFlag.Y, yIndexList);
        return result;
    }

    /**
     * 这里的二维整数点是元素坐标点的二元组合
     * @param valueList
     * @return
     */
    public TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<Pair<Integer, Integer>> valueList) {
        Map<Integer, List<Integer>> valueListMap = splitCountByIndexFlag(valueList);
        TreeMap<Integer, Double> xStatistic = this.xSquareWave.statistic(valueListMap.get(IndexFlag.X));
        TreeMap<Integer, Double> yStatistic = this.ySquareWave.statistic(valueListMap.get(IndexFlag.Y));
        TreeMap<TwoDimensionalIntegerPoint, Double> resultMap = new TreeMap<>();
        TwoDimensionalIntegerPoint point;
        Double ratio;
        for (Map.Entry<Integer, Double> xEntry : xStatistic.entrySet()) {
            for (Map.Entry<Integer, Double> yEntry : yStatistic.entrySet()) {
                point = new TwoDimensionalIntegerPoint(xEntry.getKey(), yEntry.getKey());
                ratio = xEntry.getValue() * yEntry.getValue();
                resultMap.put(point, ratio);
            }
        }
        return resultMap;
    }


}
