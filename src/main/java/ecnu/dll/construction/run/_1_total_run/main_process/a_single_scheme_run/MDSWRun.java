package ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run;

import cn.edu.dll.differential_privacy.accuracy.metrics.distance_quantities.KLDivergence;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.other_schemes.compared_schemes.msw_hdg.discretization.BucketizingMultiDimensionalSquareWave;
import tools.others.Sinkhorn;

import java.util.List;
import java.util.TreeMap;

@SuppressWarnings("ALL")
public class MDSWRun {
    public static ExperimentResult run(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, double epsilon, double xBound, double yBound) {
        BucketizingMultiDimensionalSquareWave scheme = new BucketizingMultiDimensionalSquareWave(epsilon, cellLength, inputLength, xBound, yBound);

        /**
         * 生成噪声数据
         */
        List<BasicPair<Integer, Integer>> pairList = scheme.getNoiseIndexList(integerPointList);
        //todo: ...
        long startTime = System.currentTimeMillis();
        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = scheme.statistic(pairList);
        long endTime = System.currentTimeMillis();
        long postProcessTime = endTime - startTime;
        // for test
//        System.out.println(BasicCalculation.getValueSum(rawDataStatistic));
//        System.out.println(BasicCalculation.getValueSum(estimationResult));

        ExperimentResult experimentResult = null;
        //            Double wassersteinDistance1 = TwoDimensionalWassersteinDistance.getWassersteinDistanceByCPlex(rawDataStatistic, estimationResult, 1);
        Double wassersteinDistance1 = 0D;
//            Double wassersteinDistance2 = TwoDimensionalWassersteinDistance.getWassersteinDistanceBySinkhorn(rawDataStatistic, estimationResult, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND);
        Double wassersteinDistance2 = Sinkhorn.getWassersteinDistanceBySinkhorn(estimationResult, rawDataStatistic, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND, Constant.SINKHORN_ITERATOR_UPPERBOUND);
        Double klDivergence = KLDivergence.getKLDivergence(rawDataStatistic, estimationResult, Constant.DEFAULT_MINIMAL_DENOMINATOR);
//            Double meanDistance = Distance.getAbsMeanDifference(rawDataStatistic, estimationResult);
//            Double varianceDistance = Distance.getAbsVarianceDifference(rawDataStatistic, estimationResult);
        experimentResult = new ExperimentResult();
        experimentResult.addPair(Constant.dataPointSizeKey, String.valueOf(integerPointList.size()));
        experimentResult.addPair(Constant.schemeNameKey, Constant.multiDimensionalSquareWaveSchemeKey);
        experimentResult.addPair(Constant.postProcessTimeKey, String.valueOf(postProcessTime));
        experimentResult.addPair(Constant.gridUnitSizeKey, String.valueOf(cellLength));
        experimentResult.addPair(Constant.dataTypeSizeKey, String.valueOf(scheme.getRawIntegerPointTypeList().size()));
        experimentResult.addPair(Constant.sizeDKey, String.valueOf(scheme.getSizeD()));
        experimentResult.addPair(Constant.sizeBKey, String.valueOf(scheme.getSizeB()));
        experimentResult.addPair(Constant.privacyBudgetKey, String.valueOf(epsilon));
        experimentResult.addPair(Constant.contributionKKey, String.valueOf(Constant.invalidValue));
        experimentResult.addPair(Constant.wassersteinDistance1Key, String.valueOf(wassersteinDistance1));
        experimentResult.addPair(Constant.wassersteinDistance2Key, String.valueOf(wassersteinDistance2));
        experimentResult.addPair(Constant.klDivergenceKey, String.valueOf(klDivergence));
//            experimentResult.addPair(Constant.meanDistanceKey, String.valueOf(meanDistance));
//            experimentResult.addPair(Constant.varianceDistanceKey, String.valueOf(varianceDistance));

        return experimentResult;

    }

    public static void main(String[] args) {
////        String inputPath = "F:\\dataset\\test\\real_dataset\\chicago_point_A.txt";
//        String inputPath = Constant.DEFAULT_INPUT_PATH;
//        List<TwoDimensionalDoublePoint> pointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputPath);
//        double cellLength = Constant.DEFAULT_INPUT_LENGTH / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
//        double inputLength = Constant.DEFAULT_INPUT_LENGTH;
////        double bLength = Constant.DEFAULT_B_LENGTH;
//        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET;
////        double kParameter = Constant.DEFAULT_K_PARAMETER;
//        double xBound = Constant.DEFAULT_X_BOUND;
//        double yBound = Constant.DEFAULT_Y_BOUND;
//
////        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);
//        MDSWRun.run0(pointList, cellLength, inputLength, epsilon, xBound, yBound);


    }
}
