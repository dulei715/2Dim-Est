package ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.schemes.new_scheme.discretization.DiscretizedHybridUniformExponentialScheme;
import tools.others.Sinkhorn;

import java.util.List;
import java.util.TreeMap;

@SuppressWarnings("Duplicates")
public class HUEMSRun {
    public static ExperimentResult run(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, double bLength, double epsilon, double kParameter, double xBound, double yBound) {
        DiscretizedHybridUniformExponentialScheme scheme = new DiscretizedHybridUniformExponentialScheme(epsilon, cellLength, bLength, inputLength, kParameter, xBound, yBound);

        /**
         * 生成噪声数据
         */
        List<TwoDimensionalIntegerPoint> noiseIntegerPointList = scheme.getNoiseValueList(integerPointList);

        long startTime = System.currentTimeMillis();
        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = scheme.statistic(noiseIntegerPointList);
        long endTime = System.currentTimeMillis();
        long postProcessTime = endTime - startTime;



        ExperimentResult experimentResult = null;
        Double wassersteinDistance1 = 0D;
        Double wassersteinDistance2 = Sinkhorn.getWassersteinDistanceBySinkhorn(estimationResult, rawDataStatistic, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND, Constant.SINKHORN_ITERATOR_UPPERBOUND);
//        Double klDivergence = KLDivergence.getKLDivergence(rawDataStatistic, estimationResult, Constant.DEFAULT_MINIMAL_DENOMINATOR);
        Double klDivergence = 0D;
//            Double meanDistance = Distance.getAbsMeanDifference(rawDataStatistic, estimationResult);
//            Double varianceDistance = Distance.getAbsVarianceDifference(rawDataStatistic, estimationResult);
        experimentResult = new ExperimentResult();
        experimentResult.addPair(Constant.dataPointSizeKey, String.valueOf(integerPointList.size()));
        experimentResult.addPair(Constant.schemeNameKey, Constant.hybridUniformExponentialSchemeKey);
        experimentResult.addPair(Constant.postProcessTimeKey, String.valueOf(postProcessTime));
        experimentResult.addPair(Constant.gridUnitSizeKey, String.valueOf(cellLength));
        experimentResult.addPair(Constant.dataTypeSizeKey, String.valueOf(scheme.getRawIntegerPointTypeList().size()));
        experimentResult.addPair(Constant.sizeDKey, String.valueOf(scheme.getSizeD()));
        experimentResult.addPair(Constant.sizeBKey, String.valueOf(scheme.getSizeB()));
        experimentResult.addPair(Constant.privacyBudgetKey, String.valueOf(epsilon));
        experimentResult.addPair(Constant.contributionKKey, String.valueOf(kParameter));
        experimentResult.addPair(Constant.wassersteinDistance1Key, String.valueOf(wassersteinDistance1));
        experimentResult.addPair(Constant.wassersteinDistance2Key, String.valueOf(wassersteinDistance2));
        experimentResult.addPair(Constant.klDivergenceKey, String.valueOf(klDivergence));
//            experimentResult.addPair(Constant.meanDistanceKey, String.valueOf(meanDistance));
//            experimentResult.addPair(Constant.varianceDistanceKey, String.valueOf(varianceDistance));

        return experimentResult;

    }


    public static void main(String[] args) {
//        String inputPath = Constant.twoNormalFilePath;
//        List<TwoDimensionalDoublePoint> pointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputPath);
//        double cellLength = Constant.DEFAULT_INPUT_LENGTH / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
//        double inputLength = Constant.DEFAULT_INPUT_LENGTH;
//        double bLength = 1;
//        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET;
////        double kParameter = Constant.DEFAULT_K_PARAMETER;
//        double kParameter = 1;
//        double xBound = Constant.DEFAULT_X_BOUND;
//        double yBound = Constant.DEFAULT_Y_BOUND;
//
////        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);
//        HUEMSRun.run(pointList, cellLength, inputLength, bLength, epsilon, kParameter, xBound, yBound);
    }
}
