package ecnu.dll.construction.run.main_process.a_single_scheme_run;

import cn.edu.ecnu.basic.BasicCalculation;
import cn.edu.ecnu.differential_privacy.accuracy.metrics.distance_quantities.TwoDimensionalWassersteinDistance;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.struct.grid.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
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
    public static ExperimentResult run(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, double bLength, double epsilon, double kParameter, double xBound, double yBound) {
        DiscretizedRhombusScheme scheme;
        if (bLength > 0) {
            scheme = new DiscretizedRhombusScheme(epsilon, cellLength, bLength, inputLength, kParameter, xBound, yBound);
        } else {
            scheme = new DiscretizedRhombusScheme(epsilon, cellLength, inputLength, kParameter, xBound, yBound);
        }

        /**
         * 生成噪声数据
         */
        List<TwoDimensionalIntegerPoint> noiseIntegerPointList = scheme.getNoiseValueList(integerPointList);
        //todo: ...
        long startTime = System.currentTimeMillis();
        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = scheme.statistic(noiseIntegerPointList);
        long endTime = System.currentTimeMillis();
        long postProcessTime = endTime - startTime;

        // for test
//        System.out.println(BasicCalculation.getValueSum(rawDataStatistic));
//        System.out.println(BasicCalculation.getValueSum(estimationResult));


        ExperimentResult experimentResult = null;
        try {
            Double wassersteinDistance = TwoDimensionalWassersteinDistance.getWassersteinDistance(rawDataStatistic, estimationResult, 2);
            experimentResult = new ExperimentResult();
            experimentResult.addPair(Constant.dataPointSizeKey, String.valueOf(integerPointList.size()));
            experimentResult.addPair(Constant.schemeNameKey, Constant.rhombusSchemeKey);
            experimentResult.addPair(Constant.postProcessTimeKey, String.valueOf(postProcessTime));
            experimentResult.addPair(Constant.gridUnitSizeKey, String.valueOf(cellLength));
            experimentResult.addPair(Constant.dataTypeSizeKey, String.valueOf(scheme.getRawIntegerPointTypeList().size()));
            experimentResult.addPair(Constant.sizeDKey, String.valueOf(scheme.getSizeD()));
            experimentResult.addPair(Constant.sizeBKey, String.valueOf(scheme.getSizeB()));
            experimentResult.addPair(Constant.privacyBudgetKey, String.valueOf(epsilon));
            experimentResult.addPair(Constant.contributionKKey, String.valueOf(kParameter));
            experimentResult.addPair(Constant.wassersteinDistanceKey, String.valueOf(wassersteinDistance));
        } catch (CPLException e) {
            e.printStackTrace();
        }

        return experimentResult;

    }
    public static void run0(List<TwoDimensionalDoublePoint> pointList, double cellLength, double inputLength, double bLength, double epsilon, double kParameter, double xBound, double yBound) {
        DiscretizedRhombusScheme ramScheme;
        if (bLength > 0) {
            ramScheme = new DiscretizedRhombusScheme(epsilon, cellLength, bLength, inputLength, kParameter, xBound, yBound);
        } else {
            ramScheme = new DiscretizedRhombusScheme(epsilon, cellLength, inputLength, kParameter, xBound, yBound);
        }
        List<TwoDimensionalIntegerPoint> twoDimensionalIntegerPointList = Grid.toIntegerPoint(pointList, ramScheme.getLeftBorderArray(), cellLength);
        /**
         * 相对的原始数据
         */
//        List<TwoDimensionalIntegerPoint> twoDimensionalIntegerPointList = TwoDimensionalIntegerPoint.valueOf(integerPointList);
        TreeMap<TwoDimensionalIntegerPoint, Double> realResult = ramScheme.rawDataStatistic(twoDimensionalIntegerPointList);

        /**
         * 生成噪声数据
         */
        List<TwoDimensionalIntegerPoint> noiseIntegerPointList = ramScheme.getNoiseValueList(twoDimensionalIntegerPointList);
        //todo: ...
        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = ramScheme.statistic(noiseIntegerPointList);


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
////        String inputPath = "F:\\dataset\\test\\real_dataset\\chicago_point_A.txt";
//        String inputPath = Constant.DEFAULT_INPUT_PATH;
//        List<TwoDimensionalDoublePoint> pointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputPath);
//        double cellLength = Constant.DEFAULT_INPUT_LENGTH / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
//        double inputLength = Constant.DEFAULT_INPUT_LENGTH;
//        double bLength = Constant.DEFAULT_B_LENGTH;
//        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET;
//        double kParameter = Constant.DEFAULT_K_PARAMETER;
//        double xBound = Constant.DEFAULT_X_BOUND;
//        double yBound = Constant.DEFAULT_Y_BOUND;
//
////        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);
//        RAMRun.run0(pointList, cellLength, inputLength, bLength, epsilon, kParameter, xBound, yBound);


    }

}
