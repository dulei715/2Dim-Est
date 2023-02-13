package ecnu.dll.construction.run.main_process.a_single_scheme_run;

import cn.edu.ecnu.basic.BasicCalculation;
import cn.edu.ecnu.differential_privacy.accuracy.metrics.distance_quantities.Distance;
import cn.edu.ecnu.differential_privacy.accuracy.metrics.distance_quantities.TwoDimensionalWassersteinDistance;
import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.struct.grid.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.run._struct.ExperimentResultAndScheme;
import edu.ecnu.dll.cpl.expection.CPLException;

import java.util.List;
import java.util.TreeMap;

@SuppressWarnings("ALL")
public class DAMRun {
    public static ExperimentResult run(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, double bLength, double epsilon, double kParameter, double xBound, double yBound) {
        DiscretizedDiskScheme scheme;
        if (bLength > 0) {
            scheme = new DiscretizedDiskScheme(epsilon, cellLength, bLength, inputLength, kParameter, xBound, yBound);
        } else {
            scheme = new DiscretizedDiskScheme(epsilon, cellLength, inputLength, kParameter, xBound, yBound);
        }

        /**
         * 生成噪声数据
         */
        List<TwoDimensionalIntegerPoint> noiseIntegerPointList = scheme.getNoiseValueList(integerPointList);

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
            Double meanDistance = Distance.getAbsMeanDifference(rawDataStatistic, estimationResult);
            Double varianceDistance = Distance.getAbsVarianceDifference(rawDataStatistic, estimationResult);
            experimentResult = new ExperimentResult();
            experimentResult.addPair(Constant.dataPointSizeKey, String.valueOf(integerPointList.size()));
            experimentResult.addPair(Constant.schemeNameKey, Constant.diskSchemeKey);
            experimentResult.addPair(Constant.postProcessTimeKey, String.valueOf(postProcessTime));
            experimentResult.addPair(Constant.gridUnitSizeKey, String.valueOf(cellLength));
            experimentResult.addPair(Constant.dataTypeSizeKey, String.valueOf(scheme.getRawIntegerPointTypeList().size()));
            experimentResult.addPair(Constant.sizeDKey, String.valueOf(scheme.getSizeD()));
            experimentResult.addPair(Constant.sizeBKey, String.valueOf(scheme.getSizeB()));
            experimentResult.addPair(Constant.privacyBudgetKey, String.valueOf(epsilon));
            experimentResult.addPair(Constant.contributionKKey, String.valueOf(kParameter));
            experimentResult.addPair(Constant.wassersteinDistanceKey, String.valueOf(wassersteinDistance));
            experimentResult.addPair(Constant.meanDistanceKey, String.valueOf(meanDistance));
            experimentResult.addPair(Constant.varianceDistanceKey, String.valueOf(varianceDistance));
        } catch (CPLException e) {
            e.printStackTrace();
        }


        return experimentResult;

    }
    public static ExperimentResultAndScheme runEnhanced(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, double bLength, double epsilon, double kParameter, double xBound, double yBound) {
        DiscretizedDiskScheme scheme;
        if (bLength > 0) {
            scheme = new DiscretizedDiskScheme(epsilon, cellLength, bLength, inputLength, kParameter, xBound, yBound);
        } else {
            scheme = new DiscretizedDiskScheme(epsilon, cellLength, inputLength, kParameter, xBound, yBound);
        }

        /**
         * 生成噪声数据
         */
        List<TwoDimensionalIntegerPoint> noiseIntegerPointList = scheme.getNoiseValueList(integerPointList);

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
            Double meanDistance = Distance.getAbsMeanDifference(rawDataStatistic, estimationResult);
            Double varianceDistance = Distance.getAbsVarianceDifference(rawDataStatistic, estimationResult);
            experimentResult = new ExperimentResult();
            experimentResult.addPair(Constant.dataPointSizeKey, String.valueOf(integerPointList.size()));
            experimentResult.addPair(Constant.schemeNameKey, Constant.diskSchemeKey);
            experimentResult.addPair(Constant.postProcessTimeKey, String.valueOf(postProcessTime));
            experimentResult.addPair(Constant.gridUnitSizeKey, String.valueOf(cellLength));
            experimentResult.addPair(Constant.dataTypeSizeKey, String.valueOf(scheme.getRawIntegerPointTypeList().size()));
            experimentResult.addPair(Constant.sizeDKey, String.valueOf(scheme.getSizeD()));
            experimentResult.addPair(Constant.sizeBKey, String.valueOf(scheme.getSizeB()));
            experimentResult.addPair(Constant.privacyBudgetKey, String.valueOf(epsilon));
            experimentResult.addPair(Constant.contributionKKey, String.valueOf(kParameter));
            experimentResult.addPair(Constant.wassersteinDistanceKey, String.valueOf(wassersteinDistance));
            experimentResult.addPair(Constant.meanDistanceKey, String.valueOf(meanDistance));
            experimentResult.addPair(Constant.varianceDistanceKey, String.valueOf(varianceDistance));
        } catch (CPLException e) {
            e.printStackTrace();
        }


        return new ExperimentResultAndScheme(experimentResult, scheme);

    }
    public static void run0(List<TwoDimensionalDoublePoint> pointList, double cellLength, double inputLength, double bLength, double epsilon, double kParameter, double xBound, double yBound) {
        DiscretizedDiskScheme damScheme;
        if (bLength > 0) {
            damScheme = new DiscretizedDiskScheme(epsilon, cellLength, bLength, inputLength, kParameter, xBound, yBound);
        } else {
            damScheme = new DiscretizedDiskScheme(epsilon, cellLength, inputLength, kParameter, xBound, yBound);
        }
        List<TwoDimensionalIntegerPoint> twoDimensionalIntegerPointList = Grid.toIntegerPoint(pointList, damScheme.getLeftBorderArray(), cellLength);
        /**
         * 相对的原始数据
         */
//        List<TwoDimensionalIntegerPoint> twoDimensionalIntegerPointList = TwoDimensionalIntegerPoint.valueOf(integerPointList);
        TreeMap<TwoDimensionalIntegerPoint, Double> realResult = damScheme.rawDataStatistic(twoDimensionalIntegerPointList);

        /**
         * 生成噪声数据
         */
        List<TwoDimensionalIntegerPoint> noiseIntegerPointList = damScheme.getNoiseValueList(twoDimensionalIntegerPointList);

        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = damScheme.statistic(noiseIntegerPointList);

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
////        double kParameter = 0;
//        double xBound = Constant.DEFAULT_X_BOUND;
//        double yBound = Constant.DEFAULT_Y_BOUND;
//
////        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);
//        DAMRun.run0(pointList, cellLength, inputLength, bLength, epsilon, kParameter, xBound, yBound);


    }

}
