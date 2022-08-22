package ecnu.dll.construction.run.main_process.b_comparision_run;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.run.main_process.a_single_scheme_run.DAMRun;
import ecnu.dll.construction.run.main_process.a_single_scheme_run.RAMRun;

import java.util.*;

public class AlterParameterBRun {
    public static Map<String, List<Double>> run(final List<TwoDimensionalIntegerPoint> integerPointList, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) {
//        String inputDataPath = Constant.DEFAULT_INPUT_PATH;

        double inputLength = Constant.DEFAULT_INPUT_LENGTH;
        double inputLengthSize = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
        double gridLength = inputLength / inputLengthSize;

        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET;

        /*
            分别针对Rhombus和Disk方案计算变化的整数b值
         */
        double[] alterSizeBRatioArray = Constant.ALTER_B_LENGTH_RATIO_ARRAY;
        int rhombusOptimalSizeB, diskOptimalSizeB;
        int inputIntegerLengthSize = (int)Math.floor(inputLengthSize);
        rhombusOptimalSizeB = DiscretizedRhombusSchemeTool.getOptimalSizeBOfRhombusScheme(epsilon, inputIntegerLengthSize);
        diskOptimalSizeB = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, inputIntegerLengthSize);
        int[] alterRhombusSizeB = new int[5], alterDiskSizeB = new int[5];
        for (int i = 0; i < alterSizeBRatioArray.length; i++) {
            alterRhombusSizeB[i] = (int)Math.floor(rhombusOptimalSizeB * alterSizeBRatioArray[i]);
            alterDiskSizeB[i] = (int)Math.floor(diskOptimalSizeB * alterSizeBRatioArray[i]);
        }

        double kParameter = Constant.DEFAULT_K_PARAMETER;


        Map<String, List<Double>> wassersteinDistanceMap = new HashMap<>();
        String rhombusKey = "rhombus", diskKey = "disk";
        Double tempRhombusValue, tempDiskValue;
        List<Double> rhombusList = new ArrayList<>(), diskList = new ArrayList<>();
        for (int i = 0; i < alterSizeBRatioArray.length; i++) {
            tempRhombusValue = RAMRun.run(integerPointList, rawDataStatistic, gridLength, inputLength, alterRhombusSizeB[i], epsilon, kParameter, xBound, yBound);
            rhombusList.add(tempRhombusValue);
            tempDiskValue = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputLength, alterDiskSizeB[i], epsilon, kParameter, xBound, yBound);
            diskList.add(tempDiskValue);
        }
        wassersteinDistanceMap.put(rhombusKey, rhombusList);
        wassersteinDistanceMap.put(diskKey, diskList);
        return wassersteinDistanceMap;

    }
}
