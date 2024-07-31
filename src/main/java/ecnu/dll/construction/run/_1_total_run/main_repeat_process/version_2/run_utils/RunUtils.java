package ecnu.dll.construction.run._1_total_run.main_repeat_process.version_2.run_utils;

import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.result.FileTool;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.grid.Grid;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.ConfigureUtils;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedSchemeTool;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

public class RunUtils {
    public static void runByThreads(String datasetTag, double[] epsilonArray, Double epsilonDefault, double[] sizeDArray, Double sizeDDefault, String basicOutputDirPath) {
        String datasetName, outputDirPath;
        List<TwoDimensionalIntegerPoint> integerPointList;
        Double inputSideLength, xBound, yBound, gridLength;
        TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic;

        String datasetPath;
        List<TwoDimensionalDoublePoint> doublePointList;
        List<TwoDimensionalIntegerPoint> integerPointTypeList;
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap;

        DataSetAreaInfo[] datasetInfoArray = ConfigureUtils.getDatasetInfoArray(Constant.basicDatasetPath, datasetTag);
        for (DataSetAreaInfo datasetInfo : datasetInfoArray) {
            CountDownLatch innerLatch = new CountDownLatch(epsilonArray.length + sizeDArray.length - 1);

            outputDirPath = FileTool.getPath(basicOutputDirPath, datasetInfo.getDataSetName());
            datasetName = datasetInfo.getDataSetName();
            datasetPath = datasetInfo.getDataSetPath();
            xBound = datasetInfo.getxBound();
            yBound = datasetInfo.getyBound();
            inputSideLength = datasetInfo.getLength();

            TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(datasetPath);
            pointRead.readPointWithFirstLineCount();
            doublePointList = pointRead.getPointList();

            gridLength = inputSideLength / sizeDDefault;

            integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, gridLength);
            integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(sizeDDefault));
            rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

            Thread thread;
            Runnable runnable;
            for (Double tempEpsilon : epsilonArray) {
                runnable = new BasicRun(datasetName, integerPointList, inputSideLength, rawStatisticMap, tempEpsilon, sizeDDefault, xBound, yBound, outputDirPath, innerLatch);
                thread = new Thread(runnable);
                thread.start();
            }

            for (Double tempSizeD : sizeDArray) {
                if (tempSizeD.equals(sizeDDefault)) {
                    continue;
                }
                gridLength = inputSideLength / tempSizeD;
                integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, gridLength);
                integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(tempSizeD));
                rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);
                runnable  = new BasicRun(datasetName, integerPointList, inputSideLength, rawStatisticMap, epsilonDefault, tempSizeD, xBound, yBound, outputDirPath, innerLatch);
                thread = new Thread(runnable);
                thread.start();
            }

            try {
                innerLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
