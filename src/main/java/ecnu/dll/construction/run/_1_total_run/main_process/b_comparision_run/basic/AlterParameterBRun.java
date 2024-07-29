package ecnu.dll.construction.run._1_total_run.main_process.b_comparision_run.basic;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.DAMRun;

import java.util.*;

@SuppressWarnings("ALL")
public class AlterParameterBRun {
    public static Map<String, List<ExperimentResult>> run(final List<TwoDimensionalIntegerPoint> integerPointList, double inputSideLength, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) {
//        String inputDataPath = Constant.DEFAULT_INPUT_PATH;

        int arraySize = Constant.ALTER_B_LENGTH_RATIO_ARRAY.length;
        /*
            1. 设置cell大小的参数（同时也是设置整数input的边长大小）
         */
//        double inputLengthSize = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
        double inputLengthSize = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_b_change;
        double gridLength = inputSideLength / inputLengthSize;

        /*
            2. 设置隐私预算budget
         */
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET_for_b_change;

        /*
            3. 针对Disk方案计算变化的整数b值
         */
        double[] alterSizeBRatioArray = Constant.ALTER_B_LENGTH_RATIO_ARRAY;
        int diskOptimalSizeB;
        int inputIntegerLengthSize = (int)Math.ceil(inputLengthSize);
        diskOptimalSizeB = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, inputIntegerLengthSize);
        int[] alterRhombusSizeB = new int[arraySize], alterDiskSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            alterDiskSizeB[i] = (int)Math.round(diskOptimalSizeB * alterSizeBRatioArray[i]);
        }
//        System.out.println("D's best b is " + diskOptimalSizeB);
        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER_for_change_b;


        /*
            针对Disk执行在b变化时的估计，并返回对应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String rhombusKey = Constant.rhombusSchemeKey, diskKey = Constant.diskSchemeKey;
        ExperimentResult tempRhombusExperimentResult, tempDiskExperimentResult;
        List<ExperimentResult> rhombusExperimentResultList = new ArrayList<>(), diskExperimentResultList = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskSizeB[i]*gridLength, epsilon, kParameter, xBound, yBound);
            diskExperimentResultList.add(tempDiskExperimentResult);
        }
        alterParameterMap.put(diskKey, diskExperimentResultList);
        return alterParameterMap;

    }
}
