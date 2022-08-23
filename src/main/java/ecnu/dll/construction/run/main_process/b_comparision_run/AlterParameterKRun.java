package ecnu.dll.construction.run.main_process.b_comparision_run;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.run.main_process.a_single_scheme_run.*;

import java.util.*;

@SuppressWarnings("Duplicates")
public class AlterParameterKRun {
    public static Map<String, List<Double>> run(final List<TwoDimensionalIntegerPoint> integerPointList, double inputSideLength, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) {
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
        int rhombusOptimalSizeB, diskOptimalSizeB;
        rhombusOptimalSizeB = DiscretizedRhombusSchemeTool.getOptimalSizeBOfRhombusScheme(epsilon, inputIntegerLengthSize);
        diskOptimalSizeB = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, inputIntegerLengthSize);

        /*
            4. 设置邻居属性smooth的参与率
         */
        double[] kParameterArray = Constant.ALTER_K_PARAMETER_ARRAY;


        /*
            针对Rhombus, Disk, HUEM 分别计算对应grid下的估计并返回相应的wasserstein距离
         */
        Map<String, List<Double>> wassersteinDistanceMap = new HashMap<>();
        String rhombusKey = Constant.rhombusSchemeKey, diskKey = Constant.diskSchemeKey, hue = Constant.hybridUniformExponentialSchemeKey;
        Double tempRhombusValue, tempDiskValue, tempHUEM;
        List<Double> rhombusList = new ArrayList<>(), diskList = new ArrayList<>(), huemList = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            tempRhombusValue = RAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, rhombusOptimalSizeB, epsilon, kParameterArray[i], xBound, yBound);
            rhombusList.add(tempRhombusValue);
            tempDiskValue = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, diskOptimalSizeB, epsilon, kParameterArray[i], xBound, yBound);
            diskList.add(tempDiskValue);
            tempHUEM = HUEMSRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, diskOptimalSizeB, epsilon, kParameterArray[i], xBound, yBound);
            huemList.add(tempHUEM);
        }
        wassersteinDistanceMap.put(rhombusKey, rhombusList);
        wassersteinDistanceMap.put(diskKey, diskList);
        wassersteinDistanceMap.put(hue, huemList);
        return wassersteinDistanceMap;

    }
}
