package ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run;

import cn.edu.dll.differential_privacy.accuracy.metrics.distance_quantities.KLDivergence;
import cn.edu.dll.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.schemes.compared_schemes.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import tools.others.Sinkhorn;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;


@SuppressWarnings("Duplicates")
public class SubsetGeoITwoNormRun {
    public static ExperimentResult run(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, double epsilon, double xBound, double yBound) {
        DiscretizedSubsetExponentialGeoI scheme = null;
        ExperimentResult experimentResult = null;
        try {
            scheme = new DiscretizedSubsetExponentialGeoI(epsilon, cellLength, inputLength, xBound, yBound, new TwoNormTwoDimensionalIntegerPointDistanceTor());

            /**
             * 生成噪声数据
             */
            List<Set<Integer>> noiseSubsetIndexList = scheme.getNoiseSubsetIndexList(integerPointList);
            //todo: ...
            long startTime = System.currentTimeMillis();
            TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = scheme.statistic(noiseSubsetIndexList);
            long endTime = System.currentTimeMillis();
            long postProcessTime = endTime - startTime;




            Double wassersteinDistance1 = 0D;
            Double wassersteinDistance2 = Sinkhorn.getWassersteinDistanceBySinkhorn(estimationResult, rawDataStatistic, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND, Constant.SINKHORN_ITERATOR_UPPERBOUND);
            Double klDivergence = 0D;
            experimentResult = new ExperimentResult();
            experimentResult.addPair(Constant.dataPointSizeKey, String.valueOf(integerPointList.size()));
            experimentResult.addPair(Constant.schemeNameKey, Constant.subsetGeoITwoNormSchemeKey);
            experimentResult.addPair(Constant.postProcessTimeKey, String.valueOf(postProcessTime));
            experimentResult.addPair(Constant.gridUnitSizeKey, String.valueOf(cellLength));
            experimentResult.addPair(Constant.dataTypeSizeKey, String.valueOf(scheme.getSortedInputPointList().size()));
            experimentResult.addPair(Constant.sizeDKey, String.valueOf(scheme.getSizeD()));
            experimentResult.addPair(Constant.sizeBKey, String.valueOf(Constant.invalidValue));
            experimentResult.addPair(Constant.privacyBudgetKey, String.valueOf(epsilon));
            experimentResult.addPair(Constant.contributionKKey, String.valueOf(Constant.invalidValue));
            experimentResult.addPair(Constant.wassersteinDistance1Key, String.valueOf(wassersteinDistance1));
            experimentResult.addPair(Constant.wassersteinDistance2Key, String.valueOf(wassersteinDistance2));
            experimentResult.addPair(Constant.klDivergenceKey, String.valueOf(klDivergence));
//                experimentResult.addPair(Constant.meanDistanceKey, String.valueOf(meanDistance));
//                experimentResult.addPair(Constant.varianceDistanceKey, String.valueOf(varianceDistance));

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return experimentResult;

    }
    @Deprecated
    public static ExperimentResult runWithoutWassersteinDistance(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, double epsilon, double xBound, double yBound) {
        DiscretizedSubsetExponentialGeoI scheme = null;
        ExperimentResult experimentResult = null;
        try {
            scheme = new DiscretizedSubsetExponentialGeoI(epsilon, cellLength, inputLength, xBound, yBound, new TwoNormTwoDimensionalIntegerPointDistanceTor());

            /**
             * 生成噪声数据
             */
            List<Set<Integer>> noiseSubsetIndexList = scheme.getNoiseSubsetIndexList(integerPointList);
            //todo: ...
            long startTime = System.currentTimeMillis();
            TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = scheme.statistic(noiseSubsetIndexList);
            long endTime = System.currentTimeMillis();
            long postProcessTime = endTime - startTime;


            Double klDivergence = KLDivergence.getKLDivergence(rawDataStatistic, estimationResult, Constant.DEFAULT_MINIMAL_DENOMINATOR);
            experimentResult = new ExperimentResult();
            experimentResult.addPair(Constant.dataPointSizeKey, String.valueOf(integerPointList.size()));
            experimentResult.addPair(Constant.schemeNameKey, Constant.subsetGeoITwoNormSchemeKey);
            experimentResult.addPair(Constant.postProcessTimeKey, String.valueOf(postProcessTime));
            experimentResult.addPair(Constant.gridUnitSizeKey, String.valueOf(cellLength));
            experimentResult.addPair(Constant.dataTypeSizeKey, String.valueOf(scheme.getSortedInputPointList().size()));
            experimentResult.addPair(Constant.sizeDKey, String.valueOf(scheme.getSizeD()));
            experimentResult.addPair(Constant.sizeBKey, String.valueOf(Constant.invalidValue));
            experimentResult.addPair(Constant.privacyBudgetKey, String.valueOf(epsilon));
            experimentResult.addPair(Constant.contributionKKey, String.valueOf(Constant.invalidValue));
            experimentResult.addPair(Constant.klDivergenceKey, String.valueOf(klDivergence));

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return experimentResult;

    }

}
