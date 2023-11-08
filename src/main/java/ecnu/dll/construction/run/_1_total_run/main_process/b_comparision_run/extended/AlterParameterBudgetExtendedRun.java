package ecnu.dll.construction.run._1_total_run.main_process.b_comparision_run.extended;

import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction._config.Initialized;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.DAMRun;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.SubsetGeoITwoNormRun;

import java.util.*;


public class AlterParameterBudgetExtendedRun {
    public static Map<String, List<ExperimentResult>> run(final List<TwoDimensionalIntegerPoint> integerPointList, double inputSideLength, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
//        String inputDataPath = Constant.DEFAULT_INPUT_PATH;

        int arraySize = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison.length;
        /*
            1. 设置cell大小的参数（同时也是设置整数input的边长大小）
         */
        double inputLengthSize = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison;
        double gridLength = inputSideLength / inputLengthSize;

        /*
            2. 设置隐私预算budget的变化数组
         */
        double[] epsilonArray = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison;

        /*
            3. 根据budget，计算Disk方案对应的Optimal sizeB的取值
         */
        int inputIntegerLengthSize = (int)Math.ceil(inputLengthSize);
        int[] alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilonArray[i], inputIntegerLengthSize);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;


        /*
            针对SubsetGeoI, MSW, Rhombus, Disk, Disk-non-Shrink, HUEM 分别计算对应budget下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String diskKey = Constant.diskSchemeKey, subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey;
        ExperimentResult tempDiskExperimentResult, tempSubsetGeoITwoNormExperimentResult;
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>(), subsetGeoITwoNormExperimentResultList = new ArrayList<>();


        double tempLocalPrivacy, transformedEpsilon;


        for (int i = 0; i < arraySize; i++) {

            System.out.println("Privacy Budget is " + epsilonArray[i] + ", Input Length Size is " + inputLengthSize);

            // for DAM
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            diskExperimentResultList.add(tempDiskExperimentResult);


            // for Subset-Geo-I-norm2
//            tempLocalPrivacy = Initialized.damELPTable.getLocalPrivacyByEpsilon(inputLengthSize, epsilonArray[i]);
            tempLocalPrivacy = Initialized.damELPExtendedTable.getLowerBoundLocalPrivacyByEpsilon(inputLengthSize, epsilonArray[i]);
//            transformedEpsilon = Initialized.subGeoIELPTable.getEpsilonByLocalPrivacy(inputLengthSize, tempLocalPrivacy);
            transformedEpsilon = Initialized.subGeoIELPExtendedTable.getEpsilonByUpperBoundLocalPrivacy(inputLengthSize, tempLocalPrivacy);
            tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, transformedEpsilon, xBound, yBound);
            subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);

        }


        alterParameterMap.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);

        return alterParameterMap;

    }
}
