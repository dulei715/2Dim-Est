package ecnu.dll.construction.run.main_process.c_data_set_run;

import cn.edu.ecnu.constant_values.ConstantValues;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.io.write.ExperimentResultWrite;
import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.grid.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedSchemeTool;
import ecnu.dll.construction.run.main_process.b_comparision_run.basic.AlterParameterBRun;
import ecnu.dll.construction.run.main_process.b_comparision_run.basic.AlterParameterBudgetRun;
import ecnu.dll.construction.run.main_process.b_comparision_run.basic.AlterParameterGRun;
import ecnu.dll.construction.run.main_process.b_comparision_run.basic.AlterParameterKRun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("Duplicates")
public class DataSetRun {
    public static Map<String, Map<String, List<ExperimentResult>>> run(String dataSetPath, String datasetName, double xBound, double yBound, double inputSideLength) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {
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

        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, inputSideLength / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE);
        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE));
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

        Map<String, List<ExperimentResult>> alteringBResult = AlterParameterBRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        ExperimentResult.addPair(alteringBResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(alteringBResult, 0, Constant.dataSetNameKey, datasetName);
        Map<String, List<ExperimentResult>> alteringBudgetResult = AlterParameterBudgetRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        ExperimentResult.addPair(alteringBudgetResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(alteringBudgetResult, 0, Constant.dataSetNameKey, datasetName);
        Map<String, List<ExperimentResult>> alteringKResult = AlterParameterKRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        ExperimentResult.addPair(alteringKResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(alteringKResult, 0, Constant.dataSetNameKey, datasetName);
        Map<String, List<ExperimentResult>> alteringGResult = AlterParameterGRun.run(doublePointList, inputSideLength, xBound, yBound);
        ExperimentResult.addPair(alteringGResult, 0, Constant.dataSetNameKey, datasetName);

        Map<String, Map<String, List<ExperimentResult>>> datasetResult = new HashMap<>();
        datasetResult.put(Constant.alterBKey, alteringBResult);
        datasetResult.put(Constant.alterBudgetKey, alteringBudgetResult);
        datasetResult.put(Constant.alterKKey, alteringKResult);
        datasetResult.put(Constant.alterGKey, alteringGResult);

        return datasetResult;

    }
    public static Map<String, Map<String, List<ExperimentResult>>> runAndWrite(String dataSetPath, String datasetName, String outputDir, double xBound, double yBound, double inputSideLength) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {
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

        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, inputSideLength / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE);
        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE));
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

        String outputFileName;
        Map<String, List<ExperimentResult>> alteringBResult = AlterParameterBRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        ExperimentResult.addPair(alteringBResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(alteringBResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterBKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringBResult));

        Map<String, List<ExperimentResult>> alteringBudgetResult = AlterParameterBudgetRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        ExperimentResult.addPair(alteringBudgetResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(alteringBudgetResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterBudgetKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringBudgetResult));

        Map<String, List<ExperimentResult>> alteringKResult = AlterParameterKRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        ExperimentResult.addPair(alteringKResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(alteringKResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterKKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringKResult));

        Map<String, List<ExperimentResult>> alteringGResult = AlterParameterGRun.run(doublePointList, inputSideLength, xBound, yBound);
        ExperimentResult.addPair(alteringGResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterGKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringGResult));

        Map<String, Map<String, List<ExperimentResult>>> datasetResult = new HashMap<>();
        datasetResult.put(Constant.alterBKey, alteringBResult);
        datasetResult.put(Constant.alterBudgetKey, alteringBudgetResult);
        datasetResult.put(Constant.alterKKey, alteringKResult);
        datasetResult.put(Constant.alterGKey, alteringGResult);

        return datasetResult;

    }

    public static void main(String[] args) {
        
    }

}
