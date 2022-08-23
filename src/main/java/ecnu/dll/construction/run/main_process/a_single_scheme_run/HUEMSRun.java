package ecnu.dll.construction.run.main_process.a_single_scheme_run;

import cn.edu.ecnu.basic.BasicCalculation;
import cn.edu.ecnu.differential_privacy.accuracy.metrics.distance_quantities.TwoDimensionalWassersteinDistance;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.struct.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.DiscretizedHybridUniformExponentialScheme;
import edu.ecnu.dll.cpl.expection.CPLException;

import java.util.List;
import java.util.TreeMap;

@SuppressWarnings("Duplicates")
public class HUEMSRun {
    public static Double run(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, double bLength, double epsilon, double kParameter, double xBound, double yBound) {
        DiscretizedHybridUniformExponentialScheme huemScheme = new DiscretizedHybridUniformExponentialScheme(epsilon, cellLength, bLength, inputLength, kParameter, xBound, yBound);

        /**
         * 生成噪声数据
         */
        List<TwoDimensionalIntegerPoint> noiseIntegerPointList = huemScheme.getNoiseValueList(integerPointList);

        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = huemScheme.statistic(noiseIntegerPointList);


        // for test
//        System.out.println(BasicCalculation.getValueSum(rawDataStatistic));
//        System.out.println(BasicCalculation.getValueSum(estimationResult));

        Double wassersteinDistance = null;

        try {
            wassersteinDistance = TwoDimensionalWassersteinDistance.getWassersteinDistance(rawDataStatistic, estimationResult, 2);

        } catch (CPLException e) {
            e.printStackTrace();
        }

        return wassersteinDistance;

    }
    public static void run0(List<TwoDimensionalDoublePoint> pointList, double cellLength, double inputLength, double bLength, double epsilon, double kParameter, double xBound, double yBound) {
        DiscretizedHybridUniformExponentialScheme huemScheme = new DiscretizedHybridUniformExponentialScheme(epsilon, cellLength, bLength, inputLength, kParameter, xBound, yBound);
        List<TwoDimensionalIntegerPoint> twoDimensionalIntegerPointList = Grid.toIntegerPoint(pointList, huemScheme.getLeftBorderArray(), cellLength);
        /**
         * 相对的原始数据
         */
//        List<TwoDimensionalIntegerPoint> twoDimensionalIntegerPointList = TwoDimensionalIntegerPoint.valueOf(integerPointList);
        TreeMap<TwoDimensionalIntegerPoint, Double> realResult = huemScheme.rawDataStatistic(twoDimensionalIntegerPointList);

        /**
         * 生成噪声数据
         */
        List<TwoDimensionalIntegerPoint> noiseIntegerPointList = huemScheme.getNoiseValueList(twoDimensionalIntegerPointList);

        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = huemScheme.statistic(noiseIntegerPointList);


        // for test
        System.out.println(BasicCalculation.getValueSum(realResult));
        System.out.println(BasicCalculation.getValueSum(estimationResult));

        double wassersteinDistance = -1;

        try {
            wassersteinDistance = TwoDimensionalWassersteinDistance.getWassersteinDistance(realResult, estimationResult, 2);

        } catch (CPLException e) {
            e.printStackTrace();
        }

        System.out.println(wassersteinDistance);

    }

    public static void main(String[] args) {
        String inputPath = Constant.DEFAULT_INPUT_PATH;
        List<TwoDimensionalDoublePoint> pointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputPath);
        double cellLength = Constant.DEFAULT_INPUT_LENGTH / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
        double inputLength = Constant.DEFAULT_INPUT_LENGTH;
        double bLength = 1;
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET;
        double kParameter = Constant.DEFAULT_K_PARAMETER;
        double xBound = Constant.DEFAULT_X_BOUND;
        double yBound = Constant.DEFAULT_Y_BOUND;

//        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);
        HUEMSRun.run0(pointList, cellLength, inputLength, bLength, epsilon, kParameter, xBound, yBound);
    }
}
