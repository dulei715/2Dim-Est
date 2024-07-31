package ecnu.dll.construction.run._1_total_run.main_process.b_comparision_run.basic;

import cn.edu.dll.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction._config.Initialized;
import ecnu.dll.construction.analysis.e_to_lp.Norm2DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.lp_to_e.version_1.SubsetGeoITransformEpsilon;
import ecnu.dll.construction.schemes.compared_schemes.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import ecnu.dll.construction.schemes.new_scheme.discretization.DiscretizedDiskNonShrinkScheme;
import ecnu.dll.construction.schemes.new_scheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.schemes.new_scheme.discretization.DiscretizedRhombusScheme;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.run._0_base_run._struct.ExperimentResultAndScheme;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.*;

import java.util.*;

@SuppressWarnings("ALL")
public class AlterParameterBudgetRun {
    public static Map<String, List<ExperimentResult>> run(final List<TwoDimensionalIntegerPoint> integerPointList, double inputSideLength, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
//        String inputDataPath = Constant.DEFAULT_INPUT_PATH;

        int arraySize = Constant.ALTER_PRIVACY_BUDGET_ARRAY.length;
        /*
            1. 设置cell大小的参数（同时也是设置整数input的边长大小）
         */
        double inputLengthSize = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
        double gridLength = inputSideLength / inputLengthSize;

        /*
            2. 设置隐私预算budget的变化数组
         */
        double[] epsilonArray = Constant.ALTER_PRIVACY_BUDGET_ARRAY;

        /*
            3. 根据budget，分别计算Rhombus和Disk方案对应的Optimal sizeB的取值
         */
        int inputIntegerLengthSize = (int)Math.ceil(inputLengthSize);
        int[] alterRhombusOptimalSizeB = new int[arraySize], alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            alterRhombusOptimalSizeB[i] = DiscretizedRhombusSchemeTool.getOptimalSizeBOfRhombusScheme(epsilonArray[i], inputIntegerLengthSize);
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilonArray[i], inputIntegerLengthSize);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;
        double kParameterForRAM = Constant.DEFAULT_K_PARAMETER_for_RAM;


        /*
            针对SubsetGeoI, MSW, Rhombus, Disk, Disk-non-Shrink, HUEM 分别计算对应budget下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String rhombusKey = Constant.rhombusSchemeKey, diskKey = Constant.diskSchemeKey, diskNonShrinkKey = Constant.diskNonShrinkSchemeKey, subsetGeoIOneNorm = Constant.subsetGeoIOneNormSchemeKey, subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey, mdsw = Constant.multiDimensionalSquareWaveSchemeKey, hue = Constant.hybridUniformExponentialSchemeKey;
        ExperimentResult tempRhombusExperimentResult;
        ExperimentResult tempDiskExperimentResult, tempDiskNonShrinkExperimentResult;
        ExperimentResult tempSubsetGeoIOneNormExperimentResult;
        ExperimentResult tempSubsetGeoITwoNormExperimentResult, tempMdswExperimentResult, tempHUEMExperimentResult;
        List<ExperimentResult> rhombusExperimentResultList = new ArrayList<>();
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>(), diskNonShrinkExperimentResultList = new ArrayList<>();
        List<ExperimentResult> subsetGeoIOneNormExperimentResultList = new ArrayList<>();
        List<ExperimentResult> subsetGeoITwoNormExperimentResultList = new ArrayList<>(), mdswExperimentResultList = new ArrayList<>(), huemExperimentResultList = new ArrayList<>();

        ExperimentResultAndScheme tempDiskExperimentResultAndScheme;
        DiscretizedRhombusScheme tempRhombusScheme;
        DiscretizedDiskScheme tempDiskScheme;
        DiscretizedDiskNonShrinkScheme tempDiskNonShrinkScheme;
//        DiscretizedSubsetExponentialGeoI tempGeoISchemeNorm1;
        DiscretizedSubsetExponentialGeoI tempGeoISchemeNorm2;
//        RAMLocalPrivacy ramLocalPrivacy;
        DAMLocalPrivacy damLocalPrivacy;
//        SubsetGeoITransformEpsilon geoITransformEpsilonNorm1;
        SubsetGeoITransformEpsilon geoITransformEpsilonNorm2;

        double tempLocalPrivacy, transformedEpsilon;

        tempRhombusScheme = new DiscretizedRhombusScheme(epsilonArray[0], gridLength, inputSideLength, kParameter, xBound, yBound);
        tempDiskScheme = new DiscretizedDiskScheme(epsilonArray[0], gridLength, inputSideLength, kParameter, xBound, yBound);
        tempDiskNonShrinkScheme = new DiscretizedDiskNonShrinkScheme(epsilonArray[0], gridLength, inputSideLength, kParameter, xBound, yBound);

//        ramLocalPrivacy = new Norm1RAMLocalPrivacy(tempRhombusScheme);
        damLocalPrivacy = new Norm2DAMLocalPrivacy(tempDiskScheme);
//        tempGeoISchemeNorm1 = new DiscretizedSubsetExponentialGeoI(epsilonArray[0], gridLength, inputSideLength, xBound, yBound, new OneNormTwoDimensionalIntegerPointDistanceTor());
        tempGeoISchemeNorm2 = new DiscretizedSubsetExponentialGeoI(epsilonArray[0], gridLength, inputSideLength, xBound, yBound, new TwoNormTwoDimensionalIntegerPointDistanceTor());
//        geoITransformEpsilonNorm1 = new SubsetGeoITransformEpsilon(Constant.FINE_GRIT_PRIVACY_BUDGET_ARRAY, tempGeoISchemeNorm1, SubsetGeoITransformEpsilon.Local_Privacy_Distance_Norm_One);
        geoITransformEpsilonNorm2 = new SubsetGeoITransformEpsilon(Constant.FINE_GRIT_PRIVACY_BUDGET_ARRAY, tempGeoISchemeNorm2, SubsetGeoITransformEpsilon.Local_Privacy_Distance_Norm_Two);

        for (int i = 0; i < arraySize; i++) {

            // for MDSW
            tempMdswExperimentResult = MDSWRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, epsilonArray[i], xBound, yBound);
            mdswExperimentResultList.add(tempMdswExperimentResult);

            // for RAM (没啥用，数据占位)
//            tempRhombusExperimentResult = RAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterRhombusOptimalSizeB[i]*gridLength, epsilonArray[i], kParameterForRAM, xBound, yBound);
//            tempRhombusExperimentResult = RAMRun.generateDefaultRunResult();
//            rhombusExperimentResultList.add(tempRhombusExperimentResult);

            // for DAM-non-shrink
            tempDiskNonShrinkExperimentResult = DAMNonShrinkRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            diskNonShrinkExperimentResultList.add(tempDiskNonShrinkExperimentResult);

            // for DAM
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            diskExperimentResultList.add(tempDiskExperimentResult);

            // for Subset-Geo-I-norm1 (没啥用，数据占位)
//            ramLocalPrivacy.resetEpsilon(epsilonArray[i]);
//            tempLocalPrivacy = ramLocalPrivacy.getTransformLocalPrivacyValue();
//            transformedEpsilon = geoITransformEpsilonNorm1.getEpsilonByLocalPrivacy(tempLocalPrivacy);
//            tempSubsetGeoIOneNormExperimentResult = SubsetGeoIOneNormRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, transformedEpsilon, xBound, yBound);
//            subsetGeoIOneNormExperimentResultList.add(tempSubsetGeoIOneNormExperimentResult);

//            tempSubsetGeoIOneNormExperimentResult = SubsetGeoIOneNormRun.generateDefaultRunResult();
//            subsetGeoIOneNormExperimentResultList.add(tempSubsetGeoIOneNormExperimentResult);

            // for Subset-Geo-I-norm2
            // todo: 这里修改了Localprivacy的转化为表格转化
//            damLocalPrivacy.resetEpsilon(epsilonArray[i]);
//            tempLocalPrivacy = damLocalPrivacy.getTransformLocalPrivacyValue();
            tempLocalPrivacy = Initialized.damELPBasicTable.getLowerBoundLocalPrivacyByEpsilon(inputLengthSize, epsilonArray[i]);
//            transformedEpsilon = geoITransformEpsilonNorm2.getEpsilonByLocalPrivacy(tempLocalPrivacy);
            transformedEpsilon = Initialized.subGeoIELPBasicTable.getEpsilonByUpperBoundLocalPrivacy(inputLengthSize, tempLocalPrivacy);
            tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, transformedEpsilon, xBound, yBound);
            subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);

            // for HUEM
            if (alterDiskOptimalSizeB[i] < 1) {
                tempHUEMExperimentResult = (ExperimentResult) tempDiskExperimentResult.clone();
                tempHUEMExperimentResult.setPair(Constant.schemeNameKey, Constant.hybridUniformExponentialSchemeKey);
            } else {
                tempHUEMExperimentResult = HUEMSRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            }
            huemExperimentResultList.add(tempHUEMExperimentResult);
        }


        alterParameterMap.put(mdsw, mdswExperimentResultList);
//        alterParameterMap.put(subsetGeoIOneNorm, subsetGeoIOneNormExperimentResultList);
        alterParameterMap.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);
//        alterParameterMap.put(rhombusKey, rhombusExperimentResultList);
        alterParameterMap.put(diskNonShrinkKey, diskNonShrinkExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);
        alterParameterMap.put(hue, huemExperimentResultList);

        return alterParameterMap;

    }
}
