package ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run;

import cn.edu.dll.differential_privacy.accuracy.metrics.distance_quantities.KLDivergence;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.run._0_base_run._struct.ExperimentResultAndScheme;
import edu.ecnu.dll.cpl.expection.CPLException;
import tools.others.Sinkhorn;

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
        System.out.println("Statistic time is " + postProcessTime);

        // for test
//        System.out.println(BasicCalculation.getValueSum(rawDataStatistic));
//        System.out.println(BasicCalculation.getValueSum(estimationResult));

        // todo: for test
//        String outputPathIn = StringUtil.join(ConstantValues.FILE_SPLIT, "E:", "1.学习", "4.数据集", "2.dataset_for_spatial_estimation", "test_dataset", "test_for_crime2_data_original_dam.txt");
//        TestTool.writeDistribution(rawDataStatistic, outputPathIn);
//        String outputPathEst = StringUtil.join(ConstantValues.FILE_SPLIT, "E:", "1.学习", "4.数据集", "2.dataset_for_spatial_estimation", "test_dataset", "test_for_crime2_data_estimation_dam.txt");
//        TestTool.writeDistribution(estimationResult, outputPathEst);



        ExperimentResult experimentResult = null;
        //            Double wassersteinDistance1 = TwoDimensionalWassersteinDistance.getWassersteinDistanceByCPlex(rawDataStatistic, estimationResult, 1);
        Double wassersteinDistance1 = 0D;
//            Double wassersteinDistance2 = TwoDimensionalWassersteinDistance.getWassersteinDistanceBySinkhorn(rawDataStatistic, estimationResult, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND);
        startTime = System.currentTimeMillis();
        Double wassersteinDistance2 = Sinkhorn.getWassersteinDistanceBySinkhorn(estimationResult, rawDataStatistic, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND, Constant.SINKHORN_ITERATOR_UPPERBOUND);
        endTime = System.currentTimeMillis();
        long wDTime = endTime - startTime;
        System.out.println("Wasserstein 2 distantce time is " + wDTime);
//            Double klDivergence = KLDivergence.getKLDivergence(rawDataStatistic, estimationResult, Constant.DEFAULT_MINIMAL_DENOMINATOR);
        Double klDivergence = 0D;
//            Double meanDistance = Distance.getAbsMeanDifference(rawDataStatistic, estimationResult);
//            Double varianceDistance = Distance.getAbsVarianceDifference(rawDataStatistic, estimationResult);
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
        experimentResult.addPair(Constant.wassersteinDistance1Key, String.valueOf(wassersteinDistance1));
        experimentResult.addPair(Constant.wassersteinDistance2Key, String.valueOf(wassersteinDistance2));
        experimentResult.addPair(Constant.klDivergenceKey, String.valueOf(klDivergence));
//            experimentResult.addPair(Constant.meanDistanceKey, String.valueOf(meanDistance));
//            experimentResult.addPair(Constant.varianceDistanceKey, String.valueOf(varianceDistance));


        return experimentResult;

    }
    public static ExperimentResult runWithoutWassersteinDistance(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, double bLength, double epsilon, double kParameter, double xBound, double yBound) {
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




        ExperimentResult experimentResult = null;
        Double klDivergence = KLDivergence.getKLDivergence(rawDataStatistic, estimationResult, Constant.DEFAULT_MINIMAL_DENOMINATOR);
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
        experimentResult.addPair(Constant.klDivergenceKey, String.valueOf(klDivergence));

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
        //            Double wassersteinDistance1 = TwoDimensionalWassersteinDistance.getWassersteinDistanceByCPlex(rawDataStatistic, estimationResult, 1);
        Double wassersteinDistance1 = 0D;
        Double wassersteinDistance2 = Sinkhorn.getWassersteinDistanceBySinkhorn(rawDataStatistic, estimationResult, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND, Constant.SINKHORN_ITERATOR_UPPERBOUND);
        Double klDivergence = KLDivergence.getKLDivergence(rawDataStatistic, estimationResult, Constant.DEFAULT_MINIMAL_DENOMINATOR);
//            Double meanDistance = Distance.getAbsMeanDifference(rawDataStatistic, estimationResult);
//            Double varianceDistance = Distance.getAbsVarianceDifference(rawDataStatistic, estimationResult);
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
        experimentResult.addPair(Constant.wassersteinDistance1Key, String.valueOf(wassersteinDistance1));
        experimentResult.addPair(Constant.wassersteinDistance2Key, String.valueOf(wassersteinDistance2));
        experimentResult.addPair(Constant.klDivergenceKey, String.valueOf(klDivergence));
//            experimentResult.addPair(Constant.meanDistanceKey, String.valueOf(meanDistance));
//            experimentResult.addPair(Constant.varianceDistanceKey, String.valueOf(varianceDistance));


        return new ExperimentResultAndScheme(experimentResult, scheme);

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
