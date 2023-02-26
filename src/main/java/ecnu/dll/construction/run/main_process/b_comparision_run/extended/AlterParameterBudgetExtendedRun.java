package ecnu.dll.construction.run.main_process.b_comparision_run.extended;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.OneNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction._config.Initialized;
import ecnu.dll.construction.analysis.e_to_lp.Norm1RAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.Norm2DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.RAMLocalPrivacy;
import ecnu.dll.construction.analysis.lp_to_e.version_1.SubsetGeoITransformEpsilon;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskNonShrinkScheme;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.DiscretizedRhombusScheme;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.run._struct.ExperimentResultAndScheme;
import ecnu.dll.construction.run.main_process.a_single_scheme_run.*;

import java.util.*;

@SuppressWarnings("ALL")
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

        ExperimentResultAndScheme tempDiskExperimentResultAndScheme;
        DiscretizedDiskScheme tempDiskScheme;
        DiscretizedSubsetExponentialGeoI tempGeoISchemeNorm2;
        DAMLocalPrivacy damLocalPrivacy;
        SubsetGeoITransformEpsilon geoITransformEpsilonNorm1, geoITransformEpsilonNorm2;

        double tempLocalPrivacy, transformedEpsilon;

        tempDiskScheme = new DiscretizedDiskScheme(epsilonArray[0], gridLength, inputSideLength, kParameter, xBound, yBound);

        damLocalPrivacy = new Norm2DAMLocalPrivacy(tempDiskScheme);
        tempGeoISchemeNorm2 = new DiscretizedSubsetExponentialGeoI(epsilonArray[0], gridLength, inputSideLength, xBound, yBound, new TwoNormTwoDimensionalIntegerPointDistanceTor());
        geoITransformEpsilonNorm2 = new SubsetGeoITransformEpsilon(Constant.FINE_GRIT_PRIVACY_BUDGET_ARRAY, tempGeoISchemeNorm2, SubsetGeoITransformEpsilon.Local_Privacy_Distance_Norm_Two);

        for (int i = 0; i < arraySize; i++) {


            // for DAM
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            diskExperimentResultList.add(tempDiskExperimentResult);


            // for Subset-Geo-I-norm2
//            damLocalPrivacy.resetEpsilon(epsilonArray[i]);
//            tempLocalPrivacy = damLocalPrivacy.getTransformLocalPrivacyValue();
//            transformedEpsilon = geoITransformEpsilonNorm2.getEpsilonByLocalPrivacy(tempLocalPrivacy);
            tempLocalPrivacy = Initialized.damELPTable.getLocalPrivacy(inputLengthSize, epsilonArray[i]);
            transformedEpsilon = Initialized.subGeoIELPTable.getEpsilonByLocalPrivacy(inputLengthSize, tempLocalPrivacy);
            tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, transformedEpsilon, xBound, yBound);
            subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);

        }


        alterParameterMap.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);

        return alterParameterMap;

    }
}
