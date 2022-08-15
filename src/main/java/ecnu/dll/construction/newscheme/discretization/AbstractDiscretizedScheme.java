package ecnu.dll.construction.newscheme.discretization;


import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.basic.DiscretizedPlaneInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public abstract class AbstractDiscretizedScheme implements DiscretizedPlaneInterface {
    protected Double epsilon = null;
    protected Double kParameter = null;
    protected Double[] leftBorderArray = null;
    // 记录grid边长值
    protected Double gridLength = null;
    // 记录输入区域边长值
    protected Double inputLength = null;
    // 记录输入的正方形边长除以grid边长的大小
//    protected Integer sizeInputLen = null;
    protected Integer sizeB = null;
    // 记录输入长度除以grid长度的大小
    protected Integer sizeD = null;
    protected Double constP = null;
    protected Double constQ = null;

    protected Double[][] transformMatrix;

    // 顺序记录原始点
    protected List<TwoDimensionalIntegerPoint> rawIntegerPointTypeList = null;
    // 顺序记录噪声点
    protected List<TwoDimensionalIntegerPoint> noiseIntegerPointTypeList = null;



    public AbstractDiscretizedScheme(Double epsilon, Double gridLength, Double constB, Double inputLength, Double kParameter, Double xLeft, Double yLeft) {
        this.leftBorderArray = new Double[2];
        this.leftBorderArray[0] = xLeft;
        this.leftBorderArray[1] = yLeft;
        this.epsilon = epsilon;
        this.gridLength = gridLength;
        this.inputLength = inputLength;
        //假设向下取整 (为了提高精度，尽量让高概率部分小)
        this.sizeB = (int)Math.floor(constB / gridLength);
        //假设向上取整 (为了包含所有可能的点)
        this.sizeD = (int)Math.ceil(inputLength / gridLength);
        this.kParameter = kParameter;
//        this.setConstPQ();
//        this.setRawIntegerPointTypeList();
//        this.setNoiseIntegerPointTypeList();
//        this.setTransformMatrix();
    }

    public AbstractDiscretizedScheme(Double epsilon, Double gridLength, Double inputLength, Double kParameter, Double xLeft, Double yLeft) {
        this.leftBorderArray = new Double[2];
        this.leftBorderArray[0] = xLeft;
        this.leftBorderArray[1] = yLeft;
        this.epsilon = epsilon;
        this.gridLength = gridLength;
        this.inputLength = inputLength;
        //假设向上取整
        this.sizeB = this.getOptimalSizeB();
        //假设向上取整
        this.sizeD = (int)Math.ceil(inputLength / gridLength);
        this.kParameter = kParameter;
//        this.setConstPQ();
//        this.setRawIntegerPointTypeList();
//        this.setNoiseIntegerPointTypeList();
//        this.setTransformMatrix();
    }

    protected abstract void setConstPQ();

    public abstract Integer getOptimalSizeB();

    public void setRawIntegerPointTypeList() {
        int  totalSize = this.sizeD * this.sizeD;
        this.rawIntegerPointTypeList = new ArrayList<>(totalSize);
        for (int i = 0; i < sizeD; i++) {
            for (int j = 0; j < sizeD; j++) {
                this.rawIntegerPointTypeList.add(new TwoDimensionalIntegerPoint(i, j));
            }
        }
    }

    /**
     * 记录所有可能返回的cell的坐标
     */
    public abstract void setNoiseIntegerPointTypeList();

    /**
     * 记录输出cell和输入cell之间的概率转换矩阵
     * 横坐标为输出cell，纵坐标为输入cell
     */
    public abstract void setTransformMatrix();


    @Override
    public abstract TwoDimensionalIntegerPoint getNoiseValue(TwoDimensionalIntegerPoint originalPoint);

//    public TwoDimensionalIntegerPoint toIntegerPoint(TwoDimensionalDoublePoint doublePoint, double xLeft, double yLeft) {
//        double xIndex = doublePoint.getXIndex() - xLeft;
//        double yIndex = doublePoint.getYIndex() - yLeft;
//        int xIntIndex = (int) Math.floor(xIndex / this.gridLength);
//        int yIntIndex = (int) Math.floor(yIndex / this.gridLength);
//        return new TwoDimensionalIntegerPoint(xIntIndex, yIntIndex);
//    }

    public List<TwoDimensionalIntegerPoint> getNoiseValueList(List<TwoDimensionalIntegerPoint> originalPointList) {
        List<TwoDimensionalIntegerPoint> noiseIntegerPointList = new ArrayList<>(originalPointList.size());
        for (TwoDimensionalIntegerPoint integerPoint : originalPointList) {
            noiseIntegerPointList.add(this.getNoiseValue(integerPoint));
        }
        return noiseIntegerPointList;
    }

    /**
     * 给定原始Integer点，生成对应的noise Integer点，并将这些noise点打散到对应的cell中
     * @param originalPointList
     * @return
     */
    public List<TwoDimensionalDoublePoint> getRandomizedNoiseValueInIntegerCell(List<TwoDimensionalIntegerPoint> originalPointList) {
        List<TwoDimensionalIntegerPoint> noiseIntegerValue = this.getNoiseValueList(originalPointList);
        List<TwoDimensionalDoublePoint> resultPointList = Grid.randomizeInGrid(noiseIntegerValue);
        return resultPointList;
    }

    public List<TwoDimensionalDoublePoint> getNoiseDoubleValue(List<TwoDimensionalDoublePoint> originalPointList, boolean isCenter) {
        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(originalPointList, this.leftBorderArray, this.gridLength);
        List<TwoDimensionalIntegerPoint> noiseIntegerValue = this.getNoiseValueList(integerPointList);
        List<TwoDimensionalDoublePoint> resultPointList = Grid.toDoublePoint(noiseIntegerValue, this.leftBorderArray, this.gridLength, isCenter);
        return resultPointList;
    }

    public TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic(List<TwoDimensionalIntegerPoint> valueList) {
        return StatisticTool.countHistogramRatioMap(this.rawIntegerPointTypeList, valueList);
    }

    @Override
    public TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<TwoDimensionalIntegerPoint> valueList) {
        Integer[] noiseValueCountArray = StatisticTool.countHistogramNumber(this.noiseIntegerPointTypeList,valueList);
        Double[] initialValueCountArray = new Double[this.transformMatrix[0].length];
        double initialValue = 1.0 / initialValueCountArray.length;
        BasicArray.setDoubleArrayTo(initialValueCountArray, initialValue);
        Double[] resultRatio = StatisticTool.getTwoDimensionalExpectationMaximizationSmooth(this.transformMatrix, noiseValueCountArray, Constant.DEFAULT_STOP_VALUE_TAO, initialValueCountArray, this.kParameter, this.sizeD, this.sizeD);
        TreeMap<TwoDimensionalIntegerPoint, Double> resultMap = new TreeMap<>();
        for (int i = 0; i < this.rawIntegerPointTypeList.size(); i++) {
            resultMap.put(this.rawIntegerPointTypeList.get(i), resultRatio[i]);
        }
        return resultMap;
    }


    public Double[] getLeftBorderArray() {
        return leftBorderArray;
    }
}
