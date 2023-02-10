package ecnu.dll.construction.run.main_process.b_comparision_run;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.analysis.e_to_lp.Norm2DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.lp_to_e.SubsetGeoITransformEpsilon;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskNonShrinkScheme;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.run._struct.ExperimentResultAndScheme;
import ecnu.dll.construction.run.main_process.a_single_scheme_run.*;

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


        /*
            针对SubsetGeoI, MSW, Rhombus, Disk, HUEM 分别计算对应budget下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String rhombusKey = Constant.rhombusSchemeKey, diskKey = Constant.diskSchemeKey, diskNonShrinkKey = Constant.diskNonShrinkSchemeKey, subsetGeoIOneNorm = Constant.subsetGeoIOneNormSchemeKey, subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey, mdsw = Constant.multiDimensionalSquareWaveSchemeKey, hue = Constant.hybridUniformExponentialSchemeKey;
        ExperimentResult tempRhombusExperimentResult, tempDiskExperimentResult, tempDiskNonShrinkExperimentResult, tempSubsetGeoIOneNormExperimentResult, tempSubsetGeoITwoNormExperimentResult, tempMdswExperimentResult, tempHUEMExperimentResult;
        List<ExperimentResult> rhombusExperimentResultList = new ArrayList<>(), diskExperimentResultList = new ArrayList<>(), diskNonShrinkExperimentResultList = new ArrayList<>(), subsetGeoIOneNormExperimentResultList = new ArrayList<>(), subsetGeoITwoNormExperimentResultList = new ArrayList<>(), mdswExperimentResultList = new ArrayList<>(), huemExperimentResultList = new ArrayList<>();

        ExperimentResultAndScheme tempDiskExperimentResultAndScheme;
        DiscretizedDiskScheme tempDiskScheme;
        DiscretizedDiskNonShrinkScheme tempDiskNonShrinkScheme;
        DiscretizedSubsetExponentialGeoI tempGeoIScheme;
        DAMLocalPrivacy damLocalPrivacy;
        SubsetGeoITransformEpsilon geoITransformEpsilon;
        double tempLocalPrivacy, transformedEpsilon;

        tempDiskScheme = new DiscretizedDiskScheme(epsilonArray[0], gridLength, inputSideLength, kParameter, xBound, yBound);
        tempDiskNonShrinkScheme = new DiscretizedDiskNonShrinkScheme(epsilonArray[0], gridLength, inputSideLength, kParameter, xBound, yBound);
        // todo: add for disk non shrink scheme: ... down
        damLocalPrivacy = new Norm2DAMLocalPrivacy(tempDiskScheme);
        tempGeoIScheme = new DiscretizedSubsetExponentialGeoI(epsilonArray[0], gridLength, inputSideLength, xBound, yBound, new TwoNormTwoDimensionalIntegerPointDistanceTor());
        geoITransformEpsilon = new SubsetGeoITransformEpsilon(Constant.FINE_GRIT_PRIVACY_BUDGET_ARRAY, tempGeoIScheme);

        for (int i = 0; i < arraySize; i++) {


            tempMdswExperimentResult = MDSWRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, epsilonArray[i], xBound, yBound);
            mdswExperimentResultList.add(tempMdswExperimentResult);

            tempRhombusExperimentResult = RAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterRhombusOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            rhombusExperimentResultList.add(tempRhombusExperimentResult);

            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            diskExperimentResultList.add(tempDiskExperimentResult);
            damLocalPrivacy.resetEpsilon(epsilonArray[i]);

            tempLocalPrivacy = damLocalPrivacy.getTransformLocalPrivacyValue();

            transformedEpsilon = geoITransformEpsilon.getEpsilonByLocalPrivacy(tempLocalPrivacy);
            tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, transformedEpsilon, xBound, yBound);
            subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);

            if (alterDiskOptimalSizeB[i] < 1) {
                tempHUEMExperimentResult = (ExperimentResult) tempDiskExperimentResult.clone();
                tempHUEMExperimentResult.setPair(Constant.schemeNameKey, Constant.hybridUniformExponentialSchemeKey);
            } else {
                tempHUEMExperimentResult = HUEMSRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            }
            huemExperimentResultList.add(tempHUEMExperimentResult);
        }


        alterParameterMap.put(mdsw, mdswExperimentResultList);
        alterParameterMap.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);
        alterParameterMap.put(rhombusKey, rhombusExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);
        alterParameterMap.put(hue, huemExperimentResultList);

        return alterParameterMap;

    }
}
