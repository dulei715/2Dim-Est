package _2_extend_test._1_grid_statistic.basic_run;

import cn.edu.dll.io.write.CSVWrite;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.grid.Grid;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedSchemeTool;

import java.util.List;
import java.util.TreeMap;

public class GridStatistic implements Runnable {

    private List<TwoDimensionalDoublePoint> doublePointList;
    private String outputPath;
    private double xBound;
    private double yBound;
    private double inputSideLength;

    private double sideLengthNumberSize;

    public GridStatistic(List<TwoDimensionalDoublePoint> doublePointList, String outputPath, double xBound, double yBound, double inputSideLength, double sideLengthNumberSize) {
        this.doublePointList = doublePointList;
        this.outputPath = outputPath;
        this.xBound = xBound;
        this.yBound = yBound;
        this.inputSideLength = inputSideLength;
        this.sideLengthNumberSize = sideLengthNumberSize;
    }

    public void readAndGridDataAndWriteStatistic() {
        // 1. 处理数据集

        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, inputSideLength / sideLengthNumberSize);
        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison));
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

        // 2. 写数据
        CSVWrite csvWrite = new CSVWrite();
        csvWrite.startWriting(outputPath);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("integerPointTypeSize = " + integerPointList.size());
        stringBuilder.append("; inputSideLength = " + inputSideLength);
        stringBuilder.append("; sideLengthNumberSize = " + sideLengthNumberSize);
        csvWrite.writeTwoDimensionalIntegerPointWithTileOrder(rawStatisticMap, Constant.distributionTitle, stringBuilder.toString());
        csvWrite.endWriting();

    }

    @Override
    public void run() {
        this.readAndGridDataAndWriteStatistic();
    }
}
