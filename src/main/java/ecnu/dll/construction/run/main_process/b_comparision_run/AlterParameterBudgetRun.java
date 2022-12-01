package ecnu.dll.construction.run.main_process.b_comparision_run;

import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.run.main_process.a_single_scheme_run.*;

import java.util.*;

@SuppressWarnings("ALL")
public class AlterParameterBudgetRun {
    public static Map<String, List<ExperimentResult>> run(final List<TwoDimensionalIntegerPoint> integerPointList, double inputSideLength, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) {
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
        String rhombusKey = Constant.rhombusSchemeKey, diskKey = Constant.diskSchemeKey, subsetGeoIOneNorm = Constant.subsetGeoIOneNormSchemeKey, subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey, mdsw = Constant.multiDimensionalSquareWaveSchemeKey, hue = Constant.hybridUniformExponentialSchemeKey;
        ExperimentResult tempRhombusExperimentResult, tempDiskExperimentResult, tempSubsetGeoIOneNormExperimentResult, tempSubsetGeoITwoNormExperimentResult, tempMdswExperimentResult, tempHUEMExperimentResult;
        List<ExperimentResult> rhombusExperimentResultList = new ArrayList<>(), diskExperimentResultList = new ArrayList<>(), subsetGeoIOneNormExperimentResultList = new ArrayList<>(), subsetGeoITwoNormExperimentResultList = new ArrayList<>(), mdswExperimentResultList = new ArrayList<>(), huemExperimentResultList = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            tempSubsetGeoIOneNormExperimentResult = SubsetGeoIOneNormRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, epsilonArray[i], xBound, yBound);
            subsetGeoIOneNormExperimentResultList.add(tempSubsetGeoIOneNormExperimentResult);
            tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, epsilonArray[i], xBound, yBound);
            subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);
            tempMdswExperimentResult = MDSWRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, epsilonArray[i], xBound, yBound);
            mdswExperimentResultList.add(tempMdswExperimentResult);
            tempRhombusExperimentResult = RAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterRhombusOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            rhombusExperimentResultList.add(tempRhombusExperimentResult);
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            diskExperimentResultList.add(tempDiskExperimentResult);
            tempHUEMExperimentResult = HUEMSRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            huemExperimentResultList.add(tempHUEMExperimentResult);
        }
        alterParameterMap.put(subsetGeoIOneNorm, subsetGeoIOneNormExperimentResultList);
        alterParameterMap.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);
        alterParameterMap.put(mdsw, mdswExperimentResultList);
        alterParameterMap.put(rhombusKey, rhombusExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);
        alterParameterMap.put(hue, huemExperimentResultList);
        return alterParameterMap;

    }
}
