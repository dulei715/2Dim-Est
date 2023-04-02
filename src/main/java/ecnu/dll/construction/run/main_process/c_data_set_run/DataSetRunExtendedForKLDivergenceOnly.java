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
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.AlterParameterGExtendedExtendedOnlyForKLDivergenceRun;
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.AlterParameterGExtendedRun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("Duplicates")
public class DataSetRunExtendedForKLDivergenceOnly {
    public static Map<String, Map<String, List<ExperimentResult>>> runAndWrite(String dataSetPath, String datasetName, String outputDir, double xBound, double yBound, double inputSideLength) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {
        /**
         *  1. 读取 DoublePointList
         *  2. 生成 IntegerPointList
         *  3. 统计 rawStatistics
         *  4. 调用 AlterParameterBudgetExtendedRun
         *  5. 调用 AlterParameterGExtendedRun
         */
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();

//        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

        String outputFileName;

//        Map<String, List<ExperimentResult>> alteringBudgetResult = AlterParameterBudgetExtendedRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
//        ExperimentResult.addPair(alteringBudgetResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
//        ExperimentResult.addPair(alteringBudgetResult, 0, Constant.dataSetNameKey, datasetName);
//        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterBudgetKey + ".csv";
//        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringBudgetResult));


        Map<String, List<ExperimentResult>> alteringGResult = AlterParameterGExtendedExtendedOnlyForKLDivergenceRun.run(doublePointList, inputSideLength, xBound, yBound);
        ExperimentResult.addPair(alteringGResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterGKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringGResult));

        Map<String, Map<String, List<ExperimentResult>>> datasetResult = new HashMap<>();
//        datasetResult.put(Constant.alterBudgetKey, alteringBudgetResult);
        datasetResult.put(Constant.alterGKey, alteringGResult);

        return datasetResult;

    }

    public static void main(String[] args) {
        
    }

}
