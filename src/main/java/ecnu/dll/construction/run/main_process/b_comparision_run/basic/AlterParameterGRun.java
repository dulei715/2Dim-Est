package ecnu.dll.construction.run.main_process.b_comparision_run.basic;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.OneNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.grid.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction._config.Initialized;
import ecnu.dll.construction.analysis.e_to_lp.Norm1RAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.Norm2DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.RAMLocalPrivacy;
import ecnu.dll.construction.analysis.lp_to_e.version_1.SubsetGeoITransformEpsilon;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.DiscretizedRhombusScheme;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
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
        int[] alterRhombusOptimalSizeB = new int[arraySize];
        int[] alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizeDArray[i] = (int)Math.ceil(inputLengthSizeNumberArray[i]);
//            alterRhombusOptimalSizeB[i] = DiscretizedRhombusSchemeTool.getOptimalSizeBOfRhombusScheme(epsilon, sizeDArray[i]);
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, sizeDArray[i]);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;
        double kParameterForRAM = Constant.DEFAULT_K_PARAMETER_for_RAM;


        /*
            针对SubsetGeoI, MSW, Rhombus, Disk, Disk-non-Shrink, HUEM 分别计算对应grid下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String rhombusKey = Constant.rhombusSchemeKey;
        String diskKey = Constant.diskSchemeKey, diskNonShrinkKey = Constant.diskNonShrinkSchemeKey;
        String subsetGeoIOneNorm = Constant.subsetGeoIOneNormSchemeKey;
        String subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey, mdsw = Constant.multiDimensionalSquareWaveSchemeKey, hue = Constant.hybridUniformExponentialSchemeKey;
        ExperimentResult tempRhombusExperimentResult;
        ExperimentResult tempDiskExperimentResult, tempDiskNonShrinkExperimentResult;
        ExperimentResult tempSubsetGeoIOneNormExperimentResult;
        ExperimentResult tempSubsetGeoITwoNormExperimentResult, tempMdswExperimentResult, tempHUEMExperimentResult;
        List<ExperimentResult> rhombusExperimentResultList = new ArrayList<>();
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>(), diskNonShrinkExperimentResultList = new ArrayList<>();
        List<ExperimentResult> subsetGeoIOneNormExperimentResultList = new ArrayList<>();
        List<ExperimentResult> subsetGeoITwoNormExperimentResultList = new ArrayList<>(), mdswExperimentResultList = new ArrayList<>(), huemExperimentResultList = new ArrayList<>();
        List<TwoDimensionalIntegerPoint> integerPointList, integerPointTypeList;
        TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic;

        ExperimentResultAndScheme tempRhombusExperimentResultAndScheme;
        ExperimentResultAndScheme tempDiskExperimentResultAndScheme;
        DiscretizedRhombusScheme tempRhombusScheme;
        DiscretizedDiskScheme tempDiskScheme, tempDiskNonShrinkScheme;
        DiscretizedSubsetExponentialGeoI tempGeoIScheme;
        RAMLocalPrivacy ramLocalPrivacy;
        DAMLocalPrivacy damLocalPrivacy;
        SubsetGeoITransformEpsilon geoITransformEpsilonNorm1;
        SubsetGeoITransformEpsilon geoITransformEpsilonNorm2;
        double tempLocalPrivacy, transformedEpsilon;

        for (int i = 0; i < arraySize; i++) {

//            System.out.println("grid Length: " + gridLengthArray[i] + "; gridSize: " + inputLengthSizeNumberArray[i]);


            // 计算不同的gridLength对应的不同的integerPointList
            integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, gridLengthArray[i]);

            // 计算不同的gridLength对应的不同的rawStatistic
            integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList(sizeDArray[i]);
            rawDataStatistic = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);


            // for MDSW
            tempMdswExperimentResult = MDSWRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, epsilon, xBound, yBound);
            tempMdswExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            mdswExperimentResultList.add(tempMdswExperimentResult);

            // for RAM (没啥用，数据占位)
            tempRhombusExperimentResultAndScheme = RAMRun.runEnhanced(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, alterRhombusOptimalSizeB[i]*gridLengthArray[i], epsilon, kParameterForRAM, xBound, yBound);
            tempRhombusExperimentResult = tempRhombusExperimentResultAndScheme.getExperimentResult();
            tempRhombusScheme = (DiscretizedRhombusScheme)tempRhombusExperimentResultAndScheme.getAbstractDiscretizedScheme();
            tempRhombusExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            rhombusExperimentResultList.add(tempRhombusExperimentResult);

            // for DAM-non-Shrink
            tempDiskNonShrinkExperimentResult = DAMNonShrinkRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, alterDiskOptimalSizeB[i]*gridLengthArray[i], epsilon, kParameter, xBound, yBound);
            tempDiskNonShrinkExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            diskNonShrinkExperimentResultList.add(tempDiskNonShrinkExperimentResult);

            // for DMA
            tempDiskExperimentResultAndScheme = DAMRun.runEnhanced(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, alterDiskOptimalSizeB[i]*gridLengthArray[i], epsilon, kParameter, xBound, yBound);
            tempDiskExperimentResult = tempDiskExperimentResultAndScheme.getExperimentResult();
            tempDiskScheme = (DiscretizedDiskScheme) tempDiskExperimentResultAndScheme.getAbstractDiscretizedScheme();
            tempDiskExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            diskExperimentResultList.add(tempDiskExperimentResult);

            // for Subset-Geo-I-norm1 (没啥用，数据占位)
            // 根据相应的DAM，计算出对应的LP
            ramLocalPrivacy = new Norm1RAMLocalPrivacy(tempRhombusScheme);
            tempLocalPrivacy = ramLocalPrivacy.getTransformLocalPrivacyValue();
            // geoI - two norm: 根据相应的DAM，计算出对应的LP，根据LP，计算出Geo-I对应的epsilon
            tempGeoIScheme = new DiscretizedSubsetExponentialGeoI(epsilon, gridLengthArray[i], inputSideLength, xBound, yBound, new OneNormTwoDimensionalIntegerPointDistanceTor());
            geoITransformEpsilonNorm1 = new SubsetGeoITransformEpsilon(Constant.FINE_GRIT_PRIVACY_BUDGET_ARRAY, tempGeoIScheme, SubsetGeoITransformEpsilon.Local_Privacy_Distance_Norm_One);
            transformedEpsilon = geoITransformEpsilonNorm1.getEpsilonByLocalPrivacy(tempLocalPrivacy);
            tempSubsetGeoIOneNormExperimentResult = SubsetGeoIOneNormRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, transformedEpsilon, xBound, yBound);
            tempSubsetGeoIOneNormExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            subsetGeoIOneNormExperimentResultList.add(tempSubsetGeoIOneNormExperimentResult);


            // for Subset-Geo-I-norm2 todo: 这里修改为直接查表（取消）
            // 根据相应的DAM，计算出对应的LP
            damLocalPrivacy = new Norm2DAMLocalPrivacy(tempDiskScheme);
            tempLocalPrivacy = damLocalPrivacy.getTransformLocalPrivacyValue();
//            tempLocalPrivacy = Initialized.damELPTable.getLowerBoundLocalPrivacyByEpsilon(inputLengthSizeNumberArray[i], epsilon);
            // geoI - two norm: 根据相应的DAM，计算出对应的LP，根据LP，计算出Geo-I对应的epsilon
            tempGeoIScheme = new DiscretizedSubsetExponentialGeoI(epsilon, gridLengthArray[i], inputSideLength, xBound, yBound, new TwoNormTwoDimensionalIntegerPointDistanceTor());
            geoITransformEpsilonNorm2 = new SubsetGeoITransformEpsilon(Constant.FINE_GRIT_PRIVACY_BUDGET_ARRAY, tempGeoIScheme, SubsetGeoITransformEpsilon.Local_Privacy_Distance_Norm_Two);
            transformedEpsilon = geoITransformEpsilonNorm2.getEpsilonByLocalPrivacy(tempLocalPrivacy);
//            transformedEpsilon = Initialized.subGeoIELPTable.getEpsilonByUpperBoundLocalPrivacy(inputLengthSizeNumberArray[i], tempLocalPrivacy);
            tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, transformedEpsilon, xBound, yBound);
            tempSubsetGeoITwoNormExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);

            // for HUEM
            if (alterDiskOptimalSizeB[i] < 1) {
                tempHUEMExperimentResult = (ExperimentResult) tempDiskExperimentResult.clone();
                tempHUEMExperimentResult.setPair(Constant.schemeNameKey, Constant.hybridUniformExponentialSchemeKey);
            } else {
                tempHUEMExperimentResult = HUEMSRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, alterDiskOptimalSizeB[i]*gridLengthArray[i], epsilon, kParameter, xBound, yBound);
                tempHUEMExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            }
            huemExperimentResultList.add(tempHUEMExperimentResult);

        }


        alterParameterMap.put(mdsw, mdswExperimentResultList);
        // todo: 不计算ram相关的了，用dam替换，保证输出结构的一致性（取消）
        alterParameterMap.put(subsetGeoIOneNorm, subsetGeoIOneNormExperimentResultList);
        alterParameterMap.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);
        // todo: 不计算ram相关的了，用dam替换，保证输出结构的一致性（取消）
        alterParameterMap.put(rhombusKey, rhombusExperimentResultList);
        alterParameterMap.put(diskNonShrinkKey, diskNonShrinkExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);
        alterParameterMap.put(hue, huemExperimentResultList);

        return alterParameterMap;

    }

    public static void main(String[] args) {

    }

}
