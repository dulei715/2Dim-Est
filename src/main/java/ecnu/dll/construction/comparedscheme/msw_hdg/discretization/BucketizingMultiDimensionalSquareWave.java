package ecnu.dll.construction.comparedscheme.msw_hdg.discretization;

import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.basicscheme.square_wave.discretization.BucketizingOptimalSquareWave;
import ecnu.dll.construction.comparedscheme.msw_hdg.IndexFlag;
import javafx.util.Pair;

import java.util.*;

public class BucketizingMultiDimensionalSquareWave {

    private Double epsilon = null;
//    private Double kParameter = null;
    private Double[] leftBorderArray = null;
    // 记录grid边长值
    private Double gridLength = null;
    // 记录输入区域边长值
    private Double inputLength = null;

    private List<TwoDimensionalIntegerPoint> rawIntegerPointTypeList = null;

    private BucketizingOptimalSquareWave xSquareWave = null;
    private BucketizingOptimalSquareWave ySquareWave = null;

//    public BucketizingMultiDimensionalSquareWave(BucketizingOptimalSquareWave xSquareWave, BucketizingOptimalSquareWave ySquareWave) {
//        this.xSquareWave = xSquareWave;
//        this.ySquareWave = ySquareWave;
//    }

    public BucketizingMultiDimensionalSquareWave(Double epsilon, Double gridLength, Double inputLength, Double xLeft, Double yLeft) {
        this.epsilon = epsilon;
        this.gridLength = gridLength;
        this.inputLength = inputLength;
        this.leftBorderArray = new Double[2];
        this.leftBorderArray[0] = xLeft;
        this.leftBorderArray[1] = yLeft;
        Integer xInputSize, yInputSize;
        xInputSize = yInputSize = (int)Math.ceil(inputLength / gridLength);
        this.xSquareWave = new BucketizingOptimalSquareWave(epsilon, xInputSize);
        this.ySquareWave = new BucketizingOptimalSquareWave(epsilon, yInputSize);
        this.rawIntegerPointTypeList = Grid.generateTwoDimensionalIntegerPoint(xInputSize, 0, 0);
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

    public List<Pair<Integer, Integer>> getNoiseIndexList(List<TwoDimensionalIntegerPoint> pointList) {
        List<Pair<Integer, Integer>> resultList = new ArrayList<>(pointList.size());
        Pair<Integer, Integer> tempPair;
        for (TwoDimensionalIntegerPoint point : pointList) {
            tempPair = getNoiseIndex(point);
            resultList.add(tempPair);
        }
        return resultList;
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
     * @param valueList 里面的的每个元素是二元对，第一个元素是坐标类别，第二个元素是该坐标下对应的值
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

    public Double[] getLeftBorderArray() {
        return leftBorderArray;
    }
    public TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic(List<TwoDimensionalIntegerPoint> valueList) {
        return StatisticTool.countHistogramRatioMap(this.rawIntegerPointTypeList, valueList);
    }
}