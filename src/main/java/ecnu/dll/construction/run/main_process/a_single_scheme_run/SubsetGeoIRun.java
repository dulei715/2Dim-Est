package ecnu.dll.construction.run.main_process.a_single_scheme_run;

import cn.edu.ecnu.basic.BasicCalculation;
import cn.edu.ecnu.differential_privacy.accuracy.metrics.distance_quantities.TwoDimensionalWassersteinDistance;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.struct.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import edu.ecnu.dll.cpl.expection.CPLException;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;


@SuppressWarnings("Duplicates")
public class SubsetGeoIRun {
    public static Double run(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, double epsilon, double xBound, double yBound) {
        DiscretizedSubsetExponentialGeoI subsetGeoIScheme = null;
        Double wassersteinDistance = null;
        try {
            subsetGeoIScheme = new DiscretizedSubsetExponentialGeoI(epsilon, cellLength, inputLength, xBound, yBound);

            /**
             * 生成噪声数据
             */
            List<Set<Integer>> noiseSubsetIndexList = subsetGeoIScheme.getNoiseSubsetIndexList(integerPointList);
            //todo: ...
            TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = subsetGeoIScheme.statistic(noiseSubsetIndexList);


            // for test
//            System.out.println(BasicCalculation.getValueSum(rawDataStatistic));
//            System.out.println(BasicCalculation.getValueSum(estimationResult));

            try {
                wassersteinDistance = TwoDimensionalWassersteinDistance.getWassersteinDistance(rawDataStatistic, estimationResult, 2);

            } catch (CPLException e) {
                e.printStackTrace();
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return wassersteinDistance;

    }
    public static void run0(List<TwoDimensionalDoublePoint> pointList, double cellLength, double inputLength, double epsilon, double xBound, double yBound) {
        DiscretizedSubsetExponentialGeoI segiScheme = null;
        try {
            segiScheme = new DiscretizedSubsetExponentialGeoI(epsilon, cellLength, inputLength, xBound, yBound);
            List<TwoDimensionalIntegerPoint> twoDimensionalIntegerPointList = Grid.toIntegerPoint(pointList, segiScheme.getLeftBorderArray(), cellLength);
            /**
             * 相对的原始数据
             */
//        List<TwoDimensionalIntegerPoint> twoDimensionalIntegerPointList = TwoDimensionalIntegerPoint.valueOf(integerPointList);
            TreeMap<TwoDimensionalIntegerPoint, Double> realResult = segiScheme.rawDataStatistic(twoDimensionalIntegerPointList);

            /**
             * 生成噪声数据
             */
            List<Set<Integer>> noiseSubsetIndexList = segiScheme.getNoiseSubsetIndexList(twoDimensionalIntegerPointList);
            //todo: ...
            TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = segiScheme.statistic(noiseSubsetIndexList);


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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
//        String inputPath = "F:\\dataset\\test\\real_dataset\\chicago_point_A.txt";
        String inputPath = Constant.DEFAULT_INPUT_PATH;
        List<TwoDimensionalDoublePoint> pointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputPath);
        double cellLength = Constant.DEFAULT_INPUT_LENGTH / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
        double inputLength = Constant.DEFAULT_INPUT_LENGTH;
//        double bLength = Constant.DEFAULT_B_LENGTH;
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET;
//        double kParameter = Constant.DEFAULT_K_PARAMETER;
        double xBound = Constant.DEFAULT_X_BOUND;
        double yBound = Constant.DEFAULT_Y_BOUND;

//        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);
        SubsetGeoIRun.run0(pointList, cellLength, inputLength, epsilon, xBound, yBound);


    }
}
