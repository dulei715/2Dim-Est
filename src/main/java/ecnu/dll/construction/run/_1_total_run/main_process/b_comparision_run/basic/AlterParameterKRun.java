package ecnu.dll.construction.run._1_total_run.main_process.b_comparision_run.basic;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.DAMRun;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.HUEMSRun;

import java.util.*;

@SuppressWarnings("Duplicates")
public class AlterParameterKRun {
    public static Map<String, List<ExperimentResult>> run(final List<TwoDimensionalIntegerPoint> integerPointList, double inputSideLength, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) throws CloneNotSupportedException {
//        String inputDataPath = Constant.DEFAULT_INPUT_PATH;

        int arraySize = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE.length;

        /*
            1. 设置cell大小参数（同时也是设置整数input的边长大小）
         */
        double inputLengthSize = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
        double gridLength = inputSideLength / inputLengthSize;

        /*
            2. 设置隐私预算budget
         */
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET;

        /*
            3. inputIntegerLengthSize，分别计算Rhombus和Disk方案对应的Optimal sizeB的取值
         */
        int inputIntegerLengthSize = (int)Math.ceil(inputLengthSize);
        int rhombusOptimalSizeB;
        int diskOptimalSizeB;
        rhombusOptimalSizeB = DiscretizedRhombusSchemeTool.getOptimalSizeBOfRhombusScheme(epsilon, inputIntegerLengthSize);
        diskOptimalSizeB = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, inputIntegerLengthSize);

        /*
            4. 设置邻居属性smooth的参与率
         */
        double[] kParameterArray = Constant.ALTER_K_PARAMETER_ARRAY;


        /*
            针对Rhombus, Disk, HUEM 分别计算对应grid下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String diskKey = Constant.diskSchemeKey, hue = Constant.hybridUniformExponentialSchemeKey;
        ExperimentResult tempDiskExperimentResult, tempHUEMExperimentResult;
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>(), huemExperimentResultList = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, diskOptimalSizeB*gridLength, epsilon, kParameterArray[i], xBound, yBound);
            diskExperimentResultList.add(tempDiskExperimentResult);
            if (diskOptimalSizeB <= 0) {
                tempHUEMExperimentResult = (ExperimentResult) tempDiskExperimentResult.clone();
                tempHUEMExperimentResult.setPair(Constant.schemeNameKey, Constant.hybridUniformExponentialSchemeKey);
            } else {
                tempHUEMExperimentResult = HUEMSRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, diskOptimalSizeB*gridLength, epsilon, kParameterArray[i], xBound, yBound);
            }
            huemExperimentResultList.add(tempHUEMExperimentResult);
        }
        alterParameterMap.put(diskKey, diskExperimentResultList);
        alterParameterMap.put(hue, huemExperimentResultList);
        return alterParameterMap;

    }
}
