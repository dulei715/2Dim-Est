package ecnu.dll.construction.comparedscheme.sem_geo_i.discretization;

import cn.edu.ecnu.DecimalTool;
import cn.edu.ecnu.comparator.TwoDimensionalIntegerPointComparator;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.grid.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.comparedscheme.sem_geo_i.SubsetExponentialGeoI;

import java.util.*;

public class DiscretizedSubsetExponentialGeoI implements Cloneable {

    private Double epsilon = null;
    private Double gridLength = null;
    private Double inputLength = null;
    private Double[] leftBorderArray = null;

    private Integer sizeD = null;
    private List<TwoDimensionalIntegerPoint> sortedInputPointList = null;

    private SubsetExponentialGeoI<TwoDimensionalIntegerPoint> subsetExponentialGeoI = null;

    private DistanceTor<TwoDimensionalIntegerPoint> distanceTor = null;



    public DiscretizedSubsetExponentialGeoI(Double epsilon, Double gridLength, Double inputLength, Double xLeft, Double yLeft, DistanceTor<TwoDimensionalIntegerPoint> distanceTor) throws InstantiationException, IllegalAccessException {
        this.epsilon = epsilon;
        this.gridLength = gridLength;
        this.inputLength = inputLength;
        this.leftBorderArray = new Double[2];
        this.leftBorderArray[0] = xLeft;
        this.leftBorderArray[1] = yLeft;
        this.distanceTor = distanceTor;
        this.sizeD = (int)Math.ceil(DecimalTool.round(inputLength / gridLength, Constant.eliminateDoubleErrorIndexSize));
        this.sortedInputPointList = Grid.generateTwoDimensionalIntegerPoint(this.sizeD, 0, 0);    //这里的后两个参数是整数(0,0),不是传入的xLeft和yLeft
        this.sortedInputPointList.sort(new TwoDimensionalIntegerPointComparator());
        this.subsetExponentialGeoI = new SubsetExponentialGeoI<>(this.epsilon, this.sortedInputPointList, this.distanceTor);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void resetEpsilon(Double epsilon) {
        this.epsilon = epsilon;
        this.subsetExponentialGeoI.resetEpsilon(epsilon);
    }

    public void resetGridLength(Double gridLength) {
        //todo:
        this.gridLength = gridLength;
        this.subsetExponentialGeoI.resetInputPointList();
    }

    /**
     * 专门针对矩形区域的输入，排列根据x坐标大小结合y坐标大小
     * @param point
     * @return
     */
    private int getTwoDimensionalIndex(TwoDimensionalIntegerPoint point) {
        Integer xIndex = point.getXIndex();
        Integer yIndex = point.getYIndex();
        return xIndex * this.sizeD + yIndex;
    }


    public Set<TwoDimensionalIntegerPoint> getNoiseSubset(TwoDimensionalIntegerPoint originalPoint) {
        int index = getTwoDimensionalIndex(originalPoint);
        Set<Integer> sampleSet = this.subsetExponentialGeoI.sampler(index);
        Set<TwoDimensionalIntegerPoint> resultSet = new HashSet<>();
        for (Integer tempIndex : sampleSet) {
            resultSet.add(this.sortedInputPointList.get(tempIndex));
        }
        return resultSet;
    }

    public Set<Integer> getNoiseIndexSubset(TwoDimensionalIntegerPoint originalPoint) {
        int index = getTwoDimensionalIndex(originalPoint);
        return this.subsetExponentialGeoI.sampler(index);
    }

    public List<Set<Integer>> getNoiseSubsetIndexList(List<TwoDimensionalIntegerPoint> inputList) {
        List<Set<Integer>> resultList = new ArrayList<>();
        Set<Integer> tempSet;
        for (TwoDimensionalIntegerPoint rawPoint : inputList) {
            tempSet = this.getNoiseIndexSubset(rawPoint);
            resultList.add(tempSet);
        }
        return resultList;
    }

    public TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<Set<Integer>> valueSetList) {
        double[] estimateRatio = this.subsetExponentialGeoI.estimator(valueSetList);
        TreeMap<TwoDimensionalIntegerPoint, Double> resultMap = new TreeMap<>();
        for (int i = 0; i < this.sortedInputPointList.size(); i++) {
            resultMap.put(this.sortedInputPointList.get(i), estimateRatio[i]);
        }
        return resultMap;
    }

    public Double[] getLeftBorderArray() {
        return leftBorderArray;
    }

    public TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic(List<TwoDimensionalIntegerPoint> valueList) {
        return StatisticTool.countHistogramRatioMap(this.sortedInputPointList, valueList);
    }

    public List<TwoDimensionalIntegerPoint> getSortedInputPointList() {
        return sortedInputPointList;
    }

    public Integer getSizeD() {
        return sizeD;
    }

    public Double[] getMassArray() {
        return this.subsetExponentialGeoI.getMassArray();
    }

    public Double getOmega() {
        return this.subsetExponentialGeoI.getOmega();
    }

    public Integer getSetSizeK() {
        return this.subsetExponentialGeoI.getSetSizeK();
    }

    public Double getEpsilon() {
        return epsilon;
    }
}
