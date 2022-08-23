package ecnu.dll.construction.run.main_process.c_data_set_run;

import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedSchemeTool;
import ecnu.dll.construction.run.main_process.b_comparision_run.AlterParameterBRun;
import ecnu.dll.construction.run.main_process.b_comparision_run.AlterParameterBudgetRun;
import ecnu.dll.construction.run.main_process.b_comparision_run.AlterParameterGRun;
import ecnu.dll.construction.run.main_process.b_comparision_run.AlterParameterKRun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainDataSetRun {
    public static Map<String, Map<String, List<Double>>> run(String dataSetPath, double xBound, double yBound, double inputSideLength) {
        /**
         *  1. 读取 DoublePointList
         *  2. 生成 IntegerPointList
         *  3. 统计 rawStatistics
         *  4. 调用各个b_comparison_run (除了AlterParameterGRun)
         *  5. 调用 AlterParameterGRun
         */
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();

        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, inputSideLength);
        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawIntegerPointTypeList((int) Math.ceil(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE));
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

        Map<String, List<Double>> alteringBResult = AlterParameterBRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        Map<String, List<Double>> alteringBudgetResult = AlterParameterBudgetRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        Map<String, List<Double>> alteringKResult = AlterParameterKRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        Map<String, List<Double>> alteringGResult = AlterParameterGRun.run(doublePointList, inputSideLength, xBound, yBound);

        Map<String, Map<String, List<Double>>> result = new HashMap<>();
        result.put(Constant.alterBKey, alteringBResult);
        result.put(Constant.alterBudgetKey, alteringBudgetResult);
        result.put(Constant.alterKKey, alteringKResult);
        result.put(Constant.alterGKey, alteringGResult);

        return result;

    }
}
