package ecnu.dll.construction.run.main_process.b_comparision_run;

import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.run.main_process.a_single_scheme_run.DAMRun;
import ecnu.dll.construction.run.main_process.a_single_scheme_run.RAMRun;

import java.util.*;

@SuppressWarnings("ALL")
public class AlterParameterBRun {
    public static Map<String, List<ExperimentResult>> run(final List<TwoDimensionalIntegerPoint> integerPointList, double inputSideLength, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) {
//        String inputDataPath = Constant.DEFAULT_INPUT_PATH;

        int arraySize = Constant.ALTER_B_LENGTH_RATIO_ARRAY.length;
        /*
            1. 设置cell大小的参数（同时也是设置整数input的边长大小）
         */
        double inputLengthSize = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
        double gridLength = inputSideLength / inputLengthSize;

        /*
            2. 设置隐私预算budget
         */
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET;

        /*
            3. 分别针对Rhombus和Disk方案计算变化的整数b值
         */
        double[] alterSizeBRatioArray = Constant.ALTER_B_LENGTH_RATIO_ARRAY;
        int rhombusOptimalSizeB, diskOptimalSizeB;
        int inputIntegerLengthSize = (int)Math.ceil(inputLengthSize);
        rhombusOptimalSizeB = DiscretizedRhombusSchemeTool.getOptimalSizeBOfRhombusScheme(epsilon, inputIntegerLengthSize);
        diskOptimalSizeB = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, inputIntegerLengthSize);
        int[] alterRhombusSizeB = new int[arraySize], alterDiskSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            alterRhombusSizeB[i] = (int)Math.floor(rhombusOptimalSizeB * alterSizeBRatioArray[i]);
            alterDiskSizeB[i] = (int)Math.floor(diskOptimalSizeB * alterSizeBRatioArray[i]);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;


        /*
            针对Rhombus和Disk分别执行在b变化时的估计，并返回对应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String rhombusKey = Constant.rhombusSchemeKey, diskKey = Constant.diskSchemeKey;
        ExperimentResult tempRhombusExperimentResult, tempDiskExperimentResult;
        List<ExperimentResult> rhombusExperimentResultList = new ArrayList<>(), diskExperimentResultList = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            tempRhombusExperimentResult = RAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterRhombusSizeB[i], epsilon, kParameter, xBound, yBound);
            rhombusExperimentResultList.add(tempRhombusExperimentResult);
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskSizeB[i], epsilon, kParameter, xBound, yBound);
            diskExperimentResultList.add(tempDiskExperimentResult);
        }
        alterParameterMap.put(rhombusKey, rhombusExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);
        return alterParameterMap;

    }
}
