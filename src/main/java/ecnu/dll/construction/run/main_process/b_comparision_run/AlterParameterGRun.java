package ecnu.dll.construction.run.main_process.b_comparision_run;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.analysis.e_to_lp.Norm2DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.lp_to_e.SubsetGeoITransformEpsilon;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedSchemeTool;
import ecnu.dll.construction.run._struct.ExperimentResultAndScheme;
import ecnu.dll.construction.run.main_process.a_single_scheme_run.*;

import java.util.*;

@SuppressWarnings("Duplicates")
public class AlterParameterGRun {
    public static Map<String, List<ExperimentResult>> run(final List<TwoDimensionalDoublePoint> doublePointList, double inputSideLength, double xBound, double yBound) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
//        String inputDataPath = Constant.DEFAULT_INPUT_PATH;

        int arraySize = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE.length;

        /*
            1. 设置cell大小的变化参数（同时也是设置整数input的边长大小）
         */
        double[] inputLengthSizeNumberArray = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE;
        double[] gridLengthArray = new double[arraySize];
        for (int i = 0; i < gridLengthArray.length; i++) {
            gridLengthArray[i] = inputSideLength / inputLengthSizeNumberArray[i];
        }

        /*
            2. 设置隐私预算budget
         */
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET;

        /*
            3. 根据inputIntegerSizeLengthArray，分别计算Rhombus和Disk方案对应的Optimal sizeB的取值
         */
        int[] sizeDArray = new int[arraySize];
        int[] alterRhombusOptimalSizeB = new int[arraySize], alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizeDArray[i] = (int)Math.ceil(inputLengthSizeNumberArray[i]);
            alterRhombusOptimalSizeB[i] = DiscretizedRhombusSchemeTool.getOptimalSizeBOfRhombusScheme(epsilon, sizeDArray[i]);
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, sizeDArray[i]);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;


        /*
            针对SubsetGeoI, MSW, Rhombus, Disk, HUEM 分别计算对应grid下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String rhombusKey = Constant.rhombusSchemeKey, diskKey = Constant.diskSchemeKey, subsetGeoIOneNorm = Constant.subsetGeoIOneNormSchemeKey, subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey, mdsw = Constant.multiDimensionalSquareWaveSchemeKey, hue = Constant.hybridUniformExponentialSchemeKey;
        ExperimentResult tempRhombusExperimentResult, tempDiskExperimentResult, tempSubsetGeoIOneNormExperimentResult, tempSubsetGeoITwoNormExperimentResult, tempMdswExperimentResult, tempHUEMExperimentResult;
        List<ExperimentResult> rhombusExperimentResultList = new ArrayList<>(), diskExperimentResultList = new ArrayList<>(), subsetGeoIOneNormExperimentResultList = new ArrayList<>(), subsetGeoITwoNormExperimentResultList = new ArrayList<>(), mdswExperimentResultList = new ArrayList<>(), huemExperimentResultList = new ArrayList<>();
        List<TwoDimensionalIntegerPoint> integerPointList, integerPointTypeList;
        TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic;

        ExperimentResultAndScheme tempDiskExperimentResultAndScheme;
        DiscretizedDiskScheme tempDiskScheme;
        DiscretizedSubsetExponentialGeoI tempGeoIScheme;
        DAMLocalPrivacy damLocalPrivacy;
        SubsetGeoITransformEpsilon geoITransformEpsilon;
        double tempLocalPrivacy, transformedEpsilon;

        for (int i = 0; i < arraySize; i++) {

            // 计算不同的gridLength对应的不同的integerPointList
            integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, gridLengthArray[i]);

            // 计算不同的gridLength对应的不同的rawStatistic
            integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList(sizeDArray[i]);
            rawDataStatistic = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);


            tempMdswExperimentResult = MDSWRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, epsilon, xBound, yBound);
            tempMdswExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            mdswExperimentResultList.add(tempMdswExperimentResult);

            tempRhombusExperimentResult = RAMRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, alterRhombusOptimalSizeB[i]*gridLengthArray[i], epsilon, kParameter, xBound, yBound);
            tempRhombusExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            rhombusExperimentResultList.add(tempRhombusExperimentResult);


            tempDiskExperimentResultAndScheme = DAMRun.runEnhanced(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, alterDiskOptimalSizeB[i]*gridLengthArray[i], epsilon, kParameter, xBound, yBound);
            tempDiskExperimentResult = tempDiskExperimentResultAndScheme.getExperimentResult();
            tempDiskScheme = (DiscretizedDiskScheme) tempDiskExperimentResultAndScheme.getAbstractDiscretizedScheme();
            tempDiskExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            diskExperimentResultList.add(tempDiskExperimentResult);
            // todo: 根据相应的DAM，计算出对应的LP
            damLocalPrivacy = new Norm2DAMLocalPrivacy(tempDiskScheme);
            tempLocalPrivacy = damLocalPrivacy.getTransformLocalPrivacyValue();



//            tempSubsetGeoIOneNormExperimentResult = SubsetGeoIOneNormRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, epsilon, xBound, yBound);
//            tempSubsetGeoIOneNormExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
//            subsetGeoIOneNormExperimentResultList.add(tempSubsetGeoIOneNormExperimentResult);

            // todo: geoI - two norm: 根据相应的DAM，计算出对应的LP，根据LP，计算出Geo-I对应的epsilon
            tempGeoIScheme = new DiscretizedSubsetExponentialGeoI(epsilon, gridLengthArray[i], inputSideLength, xBound, yBound, new TwoNormTwoDimensionalIntegerPointDistanceTor());
            geoITransformEpsilon = new SubsetGeoITransformEpsilon(Constant.FINE_GRIT_PRIVACY_BUDGET_ARRAY, tempGeoIScheme);
            transformedEpsilon = geoITransformEpsilon.getEpsilonByLocalPrivacy(tempLocalPrivacy);
            tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, transformedEpsilon, xBound, yBound);
            tempSubsetGeoITwoNormExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);

            tempHUEMExperimentResult = HUEMSRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, alterDiskOptimalSizeB[i]*gridLengthArray[i], epsilon, kParameter, xBound, yBound);
            tempHUEMExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            huemExperimentResultList.add(tempHUEMExperimentResult);
        }
//        alterParameterMap.put(subsetGeoIOneNorm, subsetGeoIOneNormExperimentResultList);
        alterParameterMap.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);
        alterParameterMap.put(mdsw, mdswExperimentResultList);
        alterParameterMap.put(rhombusKey, rhombusExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);
        alterParameterMap.put(hue, huemExperimentResultList);
        return alterParameterMap;

    }

    public static void main(String[] args) {

    }

}
