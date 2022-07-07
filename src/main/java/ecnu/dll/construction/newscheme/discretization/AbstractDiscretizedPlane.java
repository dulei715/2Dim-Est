package ecnu.dll.construction.newscheme.discretization;


import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.basic.RandomUtil;
import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.basic.DiscretizedPlaneInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractDiscretizedPlane implements DiscretizedPlaneInterface {
    protected Double epsilon = null;
    protected Double kParameter = null;
    // 记录grid边长的大小
    protected Double gridLength = null;
    // 记录输入的正方形边长除以grid边长的大小
    protected Integer sizeInputLen = null;
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



    public AbstractDiscretizedPlane(Double epsilon, Double gridLength, Double constB, Double inputLength) {
        this.epsilon = epsilon;
        this.gridLength = gridLength;
        //todo: 假设向上取整
        this.sizeB = (int)Math.ceil(constB / gridLength);
        //todo: 假设向上取整
        this.sizeD = (int)Math.ceil(inputLength / gridLength);
        this.setConstPQ();
        this.setRawIntegerPointTypeList();
        this.setNoiseIntegerPointTypeList();
        this.setTransformMatrix();
    }

    public AbstractDiscretizedPlane(Double epsilon, Double gridLength, Double inputLength) {
        this.epsilon = epsilon;
        this.gridLength = gridLength;
        //todo: 假设向上取整
        this.sizeB = this.getOptimalSizeB();
        //todo: 假设向上取整
        this.sizeD = (int)Math.ceil(inputLength / gridLength);
        this.setConstPQ();
        this.setRawIntegerPointTypeList();
        this.setNoiseIntegerPointTypeList();
        this.setTransformMatrix();
    }

    protected abstract void setConstPQ();

    protected abstract Integer getOptimalSizeB();

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

    @Override
    public TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<TwoDimensionalIntegerPoint> valueList) {
        Integer[] noiseValueCountArray = StatisticTool.countHistogramNumber(this.noiseIntegerPointTypeList,valueList);
        Double[] initialValueCountArray = new Double[this.transformMatrix[0].length];
        BasicArray.setDoubleArrayToZero(initialValueCountArray);
        Double[] resultRatio = StatisticTool.getTwoDimensionalExpectationMaximizationSmooth(this.transformMatrix, noiseValueCountArray, Constant.DEFAULT_STOP_VALUE_TAO, initialValueCountArray, this.kParameter, this.sizeD, this.sizeD);
        TreeMap<TwoDimensionalIntegerPoint, Double> resultMap = new TreeMap<>();
        for (int i = 0; i < this.rawIntegerPointTypeList.size(); i++) {
            resultMap.put(this.rawIntegerPointTypeList.get(i), resultRatio[i]);
        }
        return resultMap;
    }


}
