package ecnu.dll.construction.run.single_scheme_run;

import cn.edu.ecnu.differential_privacy.accuracy.metrics.distance_quantities.TwoDimensionalWassersteinDistance;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.struct.Grid;
import cn.edu.ecnu.struct.point.IntegerPoint;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.newscheme.discretization.DiscretizedRhombusScheme;
import edu.ecnu.dll.cpl.expection.CPLException;

import java.util.List;
import java.util.TreeMap;

/**
 * 运行RAM方案
 */
@SuppressWarnings("Duplicates")
public class RAMRun {
    /**
     * 根据参数执行响应的 RAM 方案
     *
     */
    public static void run(List<TwoDimensionalDoublePoint> pointList, double cellLength, double inputLength, double bLength, double epsilon, double kParameter, double xBound, double yBound) {
        DiscretizedRhombusScheme ramScheme = new DiscretizedRhombusScheme(epsilon, cellLength, bLength, inputLength, kParameter, xBound, yBound);
        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(pointList, ramScheme.getLeftBorderArray(), cellLength);
        /**
         * 相对的原始数据
         */
        List<TwoDimensionalIntegerPoint> twoDimensionalIntegerPointList = TwoDimensionalIntegerPoint.valueOf(integerPointList);
        TreeMap<TwoDimensionalIntegerPoint, Double> realResult = ramScheme.rawDataStatistic(twoDimensionalIntegerPointList);

        /**
         * 生成噪声数据
         */
        List<TwoDimensionalIntegerPoint> noiseIntegerPointList = ramScheme.getNoiseValueList(twoDimensionalIntegerPointList);
        //todo: ...
        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = ramScheme.statistic(noiseIntegerPointList);



        double wassersteinDistance = -1;

        try {
            wassersteinDistance = TwoDimensionalWassersteinDistance.getWassersteinDistance(realResult, estimationResult, 2);

        } catch (CPLException e) {
            e.printStackTrace();
        }

        System.out.println(wassersteinDistance);

    }

    public static void main(String[] args) {
//        String inputPath = "F:\\dataset\\test\\real_dataset\\chicago_point_A.txt";
        String inputPath = "F:\\dataset\\test\\chicago_point_small.txt";
        List<TwoDimensionalDoublePoint> pointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputPath);
        double cellLength = 0.005;
//        double inputLength = 0.09;
        double inputLength = 0.01;
        double bLength = 0.005;
        double epsilon = 0.5;
        double kParameter = 0.01;
        double xBound = 41.72;
        double yBound = -87.68;

//        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);
        RAMRun.run(pointList, cellLength, inputLength, bLength, epsilon, kParameter, xBound, yBound);


    }

}
