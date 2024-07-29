package ecnu.dll.construction.run._1_total_run.main_process.b_comparision_run.extended;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.grid.Grid;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction._config.Initialized;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedSchemeTool;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.DAMRun;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.SubsetGeoITwoNormRun;

import java.util.*;

@Deprecated
public class AlterParameterGExtendedExtendedOnlyForKLDivergenceRun {
    public static Map<String, List<ExperimentResult>> run(final List<TwoDimensionalDoublePoint> doublePointList, double inputSideLength, double xBound, double yBound) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {

        int arraySize = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_for_KL_Divergence.length;

        /*
            1. 设置cell大小的变化参数（同时也是设置整数input的边长大小）
         */
        double[] inputLengthSizeNumberArray = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_for_KL_Divergence;
        double[] gridLengthArray = new double[arraySize];
        for (int i = 0; i < gridLengthArray.length; i++) {
            gridLengthArray[i] = inputSideLength / inputLengthSizeNumberArray[i];
        }

        /*
            2. 设置隐私预算budget
         */
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET_for_DAM_and_SubsetGeoI_Comparison_for_KL_Divergence;

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
            针对SubsetGeoI, Disk 分别计算对应grid下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String diskKey = Constant.diskSchemeKey, subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey;
        ExperimentResult tempDiskExperimentResult, tempSubsetGeoITwoNormExperimentResult;
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>(), subsetGeoITwoNormExperimentResultList = new ArrayList<>();
        List<TwoDimensionalIntegerPoint> integerPointList, integerPointTypeList;
        TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic;

        double tempLocalPrivacy, transformedEpsilon;

        for (int i = 0; i < arraySize; i++) {

            System.out.println("Privacy Budget is " + epsilon + ", Input Length Size is " + inputLengthSizeNumberArray[i]);

            // 计算不同的gridLength对应的不同的integerPointList
            integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, gridLengthArray[i]);

            // 计算不同的gridLength对应的不同的rawStatistic
            integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList(sizeDArray[i]);
            rawDataStatistic = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

            // for DMA
            tempDiskExperimentResult = DAMRun.runWithoutWassersteinDistance(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, alterDiskOptimalSizeB[i]*gridLengthArray[i], epsilon, kParameter, xBound, yBound);
            tempDiskExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            diskExperimentResultList.add(tempDiskExperimentResult);


            // for Subset-Geo-I-norm2
            // 根据相应的DAM，计算出对应的LP
            tempLocalPrivacy = Initialized.damELPKLTable.getLowerBoundLocalPrivacyByEpsilon(inputLengthSizeNumberArray[i], epsilon);
            transformedEpsilon = Initialized.subGeoIELPKLTable.getEpsilonByUpperBoundLocalPrivacy(inputLengthSizeNumberArray[i], tempLocalPrivacy);
            tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.runWithoutWassersteinDistance(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, transformedEpsilon, xBound, yBound);
            tempSubsetGeoITwoNormExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);


        }


        alterParameterMap.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);

        return alterParameterMap;

    }

    public static void main(String[] args) {

    }

}
