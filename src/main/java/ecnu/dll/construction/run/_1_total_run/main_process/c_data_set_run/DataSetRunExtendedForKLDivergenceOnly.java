package ecnu.dll.construction.run._1_total_run.main_process.c_data_set_run;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.io.write.ExperimentResultWrite;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.grid.Grid;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedSchemeTool;
import ecnu.dll.construction.run._1_total_run.main_process.b_comparision_run.extended.AlterParameterBudgetExtendedExtendedOnlyForKLDivergenceRun;
import ecnu.dll.construction.run._1_total_run.main_process.b_comparision_run.extended.AlterParameterGExtendedExtendedOnlyForKLDivergenceRun;

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
        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, inputSideLength / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_for_KL_Divergence);
        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_for_KL_Divergence));
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);


        String outputFileName;
        Map<String, Map<String, List<ExperimentResult>>> datasetResult = new HashMap<>();

        Map<String, List<ExperimentResult>> alteringBudgetResult = AlterParameterBudgetExtendedExtendedOnlyForKLDivergenceRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        ExperimentResult.addPair(alteringBudgetResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(alteringBudgetResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterBudgetKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringBudgetResult));
        datasetResult.put(Constant.alterBudgetKey, alteringBudgetResult);


        Map<String, List<ExperimentResult>> alteringGResult = AlterParameterGExtendedExtendedOnlyForKLDivergenceRun.run(doublePointList, inputSideLength, xBound, yBound);
        ExperimentResult.addPair(alteringGResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterGKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringGResult));
        datasetResult.put(Constant.alterGKey, alteringGResult);

        return datasetResult;

    }

    public static void main(String[] args) {
        
    }

}
