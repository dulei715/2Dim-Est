package ecnu.dll.construction.run._2_trajectory_run.main_process.b_comparision_run;

import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.grid.Grid;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction._config.Initialized;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run._0_base_run._struct.ExperimentResultAndScheme;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.DAMRun;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.SubsetGeoITwoNormRun;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedSchemeTool;

import java.util.*;


public class AlterParameterGTrajectoryRun {
    public static Map<String, List<ExperimentResult>> run(final List<TwoDimensionalDoublePoint> doublePointList, double inputSideLength, double xBound, double yBound) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {

        int arraySize = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison.length;

        /*
            1. 设置cell大小的变化参数（同时也是设置整数input的边长大小）
         */
        double[] inputLengthSizeNumberArray = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison;
        double[] gridLengthArray = new double[arraySize];
        for (int i = 0; i < gridLengthArray.length; i++) {
            gridLengthArray[i] = inputSideLength / inputLengthSizeNumberArray[i];
        }

        /*
            2. 设置隐私预算budget
         */
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET_for_DAM_and_SubsetGeoI_Comparison;

        /*
            3. 根据inputIntegerSizeLengthArray，分别计算Rhombus和Disk方案对应的Optimal sizeB的取值
         */
        int[] sizeDArray = new int[arraySize];
        int[] alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizeDArray[i] = (int)Math.ceil(inputLengthSizeNumberArray[i]);
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, sizeDArray[i]);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;


        /*
            针对SubsetGeoI, MSW, Rhombus, Disk, Disk-non-Shrink, HUEM 分别计算对应grid下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String diskKey = Constant.diskSchemeKey, subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey;
        ExperimentResult tempDiskExperimentResult, tempSubsetGeoITwoNormExperimentResult;
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>(), subsetGeoITwoNormExperimentResultList = new ArrayList<>();
        List<TwoDimensionalIntegerPoint> integerPointList, integerPointTypeList;
        TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic;

        ExperimentResultAndScheme tempDiskExperimentResultAndScheme;
        double tempLocalPrivacy, transformedEpsilon;

        for (int i = 0; i < arraySize; i++) {

            System.out.println("Privacy Budget is " + epsilon + ", Input Length Size is " + inputLengthSizeNumberArray[i]);

            // 计算不同的gridLength对应的不同的integerPointList
            integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, gridLengthArray[i]);

            // 计算不同的gridLength对应的不同的rawStatistic
            integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList(sizeDArray[i]);
            rawDataStatistic = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

            // for DMA
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, alterDiskOptimalSizeB[i]*gridLengthArray[i], epsilon, kParameter, xBound, yBound);
//            tempDiskExperimentResult = tempDiskExperimentResultAndScheme.getExperimentResult();
            tempDiskExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            diskExperimentResultList.add(tempDiskExperimentResult);


            // for Subset-Geo-I-norm2
            // 根据相应的DAM，计算出对应的LP
//            tempLocalPrivacy = Initialized.damELPTable.getLocalPrivacyByEpsilon(inputLengthSizeNumberArray[i], epsilon);
            tempLocalPrivacy = Initialized.damELPExtendedTable.getLowerBoundLocalPrivacyByEpsilon(inputLengthSizeNumberArray[i], epsilon);
//            transformedEpsilon = Initialized.subGeoIELPTable.getEpsilonByLocalPrivacy(inputLengthSizeNumberArray[i], tempLocalPrivacy);
            transformedEpsilon = Initialized.subGeoIELPExtendedTable.getEpsilonByUpperBoundLocalPrivacy(inputLengthSizeNumberArray[i], tempLocalPrivacy);
            tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, transformedEpsilon, xBound, yBound);
            tempSubsetGeoITwoNormExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);


        }


        alterParameterMap.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);

        return alterParameterMap;

    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
//        DataSetAreaInfo dataSetInfo = Constant.nycDataSetArray[0];
        DataSetAreaInfo dataSetInfo = Constant.crimeDataSetArray[1];
        String dataSetPath = dataSetInfo.getDataSetPath();
        String dataSetName = dataSetInfo.getDataSetName();
        Double xBound = dataSetInfo.getxBound();
        Double yBound = dataSetInfo.getyBound();
        Double inputSideLength = dataSetInfo.getLength();
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();
//        Map<String, List<ExperimentResult>> alteringGResult = AlterParameterGExtendedRun.run(doublePointList, length, xBound, yBound);


        int arraySize = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison.length;
        double[] inputLengthSizeNumberArray = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison;
        double[] gridLengthArray = new double[arraySize];
        for (int i = 0; i < gridLengthArray.length; i++) {
            gridLengthArray[i] = inputSideLength / inputLengthSizeNumberArray[i];
        }
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET_for_DAM_and_SubsetGeoI_Comparison;
        int[] sizeDArray = new int[arraySize];
        int[] alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizeDArray[i] = (int)Math.ceil(inputLengthSizeNumberArray[i]);
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, sizeDArray[i]);
        }
        double kParameter = Constant.DEFAULT_K_PARAMETER;
        Map<String, List<ExperimentResult>> alteringGResult = new HashMap<>();
        String diskKey = Constant.diskSchemeKey, subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey;
        ExperimentResult tempDiskExperimentResult, tempSubsetGeoITwoNormExperimentResult;
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>(), subsetGeoITwoNormExperimentResultList = new ArrayList<>();
        List<TwoDimensionalIntegerPoint> integerPointList, integerPointTypeList;
        TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic;

        ExperimentResultAndScheme tempDiskExperimentResultAndScheme;
        double tempLocalPrivacy, transformedEpsilon;
        int i = 4;
        System.out.println("Privacy Budget is " + epsilon + ", Input Length Size is " + inputLengthSizeNumberArray[i]);

        // 计算不同的gridLength对应的不同的integerPointList
        integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, gridLengthArray[i]);

        // 计算不同的gridLength对应的不同的rawStatistic
        integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList(sizeDArray[i]);
        rawDataStatistic = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

        // for DMA
        tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, alterDiskOptimalSizeB[i]*gridLengthArray[i], epsilon, kParameter, xBound, yBound);
        tempDiskExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        diskExperimentResultList.add(tempDiskExperimentResult);
        alteringGResult.put(diskKey, diskExperimentResultList);


        // for Subset-Geo-I-norm2
        // 根据相应的DAM，计算出对应的LP
//        tempLocalPrivacy = Initialized.damELPExtendedTable.getLowerBoundLocalPrivacyByEpsilon(inputLengthSizeNumberArray[i], epsilon);
//        transformedEpsilon = Initialized.subGeoIELPExtendedTable.getEpsilonByUpperBoundLocalPrivacy(inputLengthSizeNumberArray[i], tempLocalPrivacy);
//        tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, transformedEpsilon, xBound, yBound);
//        tempSubsetGeoITwoNormExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
//        subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);
//        alteringGResult.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);



        ExperimentResult.addPair(alteringGResult, 0, Constant.dataSetNameKey, dataSetName);
        MyPrint.showMap(alteringGResult);
//        System.out.println(diskExperimentResultList.get(diskExperimentResultList.size()-2));
    }

}
