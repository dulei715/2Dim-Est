package ecnu.dll.construction.comparedscheme.msw_hdg.discretization;

import cn.edu.dll.DecimalTool;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.grid.Grid;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.basicscheme.square_wave.discretization.BucketizingOptimalSquareWave;
import ecnu.dll.construction.comparedscheme.msw_hdg.IndexFlag;

import java.util.*;

public class BucketizingMultiDimensionalSquareWave {

    private Double epsilon = null;
//    private Double kParameter = null;
    private Double[] leftBorderArray = null;
    // 记录grid边长值
    private Double gridLength = null;
    // 记录输入区域边长值
    private Double inputLength = null;

    private Integer sizeD = null;

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
//        Integer xInputSize, yInputSize;
        this.sizeD = (int)Math.ceil(DecimalTool.round(inputLength / gridLength, Constant.eliminateDoubleErrorIndexSize));
        this.xSquareWave = new BucketizingOptimalSquareWave(epsilon, this.sizeD);
        this.ySquareWave = new BucketizingOptimalSquareWave(epsilon, this.sizeD);
        this.rawIntegerPointTypeList = Grid.generateTwoDimensionalIntegerPoint(this.sizeD, 0, 0);
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
    public BasicPair<Integer, Integer> getNoiseIndex(TwoDimensionalIntegerPoint point) {
        Integer indexFlagValue = this.getRandomIndexFlag();
        if (IndexFlag.X.equals(indexFlagValue)) {
            return new BasicPair<>(indexFlagValue, this.xSquareWave.getNoiseIndex(point.getXIndex()));
        }
        return new BasicPair<>(indexFlagValue, this.ySquareWave.getNoiseIndex(point.getYIndex()));
    }

    public List<BasicPair<Integer, Integer>> getNoiseIndexList(List<TwoDimensionalIntegerPoint> pointList) {
        List<BasicPair<Integer, Integer>> resultList = new ArrayList<>(pointList.size());
        BasicPair<Integer, Integer> tempPair;
        for (TwoDimensionalIntegerPoint point : pointList) {
//            if (!point.getXIndex().equals(2) || !point.getYIndex().equals(0)) {
//                System.out.println("hahaha");
//            }
            tempPair = getNoiseIndex(point);
            resultList.add(tempPair);
        }
        return resultList;
    }

    private static Map<Integer, List<Integer>> splitCountByIndexFlag(List<BasicPair<Integer, Integer>> valueList) {
        List<Integer> xIndexList = new ArrayList<>();
        List<Integer> yIndexList = new ArrayList<>();
        Integer indexFlagValue;
        Map<Integer, List<Integer>> result = new HashMap<>();
        for (BasicPair<Integer, Integer> pair : valueList) {
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
    public TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<BasicPair<Integer, Integer>> valueList) {
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

    public List<TwoDimensionalIntegerPoint> getRawIntegerPointTypeList() {
        return rawIntegerPointTypeList;
    }

    public Integer getSizeD() {
        return sizeD;
    }

    public Integer getSizeB() {
        // 这里默认xSquareWave和ySquareWave的结构一样
        return this.xSquareWave.getB();
    }

}
