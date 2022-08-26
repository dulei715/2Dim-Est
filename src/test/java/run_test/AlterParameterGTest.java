package run_test;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedSchemeTool;
import ecnu.dll.construction.run.main_process.a_single_scheme_run.*;
import ecnu.dll.construction.run.main_process.b_comparision_run.AlterParameterGRun;
import ecnu.dll.construction.run.main_process.c_data_set_run.DataSetRun;
import org.junit.Test;

import java.util.*;

@SuppressWarnings("Duplicates")
public class AlterParameterGTest {

    public Map<String, List<ExperimentResult>> alterParameterGRunRun(final List<TwoDimensionalDoublePoint> doublePointList, double inputSideLength, double xBound, double yBound) {
        int arraySize = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE.length;

        /*
            1. 设置cell大小的变化参数（同时也是设置整数input的边长大小）
         */
        double[] inputLengthSizeNumberArray = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE;
        double[] gridLengthArray = new double[arraySize];
        for (int i = 0; i < gridLengthArray.length; i++) {
            gridLengthArray[i] = inputSideLength / inputLengthSizeNumberArray[i];
        }

        /*
            2. 设置隐私预算budget
         */
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET;

        /*
            3. 计算sizeDArray
         */
        int[] sizeDArray = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizeDArray[i] = (int)Math.ceil(inputLengthSizeNumberArray[i]);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
//        double kParameter = Constant.DEFAULT_K_PARAMETER;


        /*
            针对SubsetGeoI计算对应grid下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String subsetGeoI = Constant.subsetGeoISchemeKey;
        ExperimentResult tempSubsetGeoIExperimentResult;
        List<ExperimentResult> subsetGeoIExperimentResultList = new ArrayList<>();
        List<TwoDimensionalIntegerPoint> integerPointList, integerPointTypeList;
        TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic;
        for (int i = 0; i < arraySize; i++) {

            // 计算不同的gridLength对应的不同的integerPointList
            integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, gridLengthArray[i]);

            // 计算不同的gridLength对应的不同的rawStatistic
            integerPointTypeList = DiscretizedSchemeTool.getRawIntegerPointTypeList(sizeDArray[i]);
            rawDataStatistic = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

            tempSubsetGeoIExperimentResult = SubsetGeoIRun.run(integerPointList, rawDataStatistic, gridLengthArray[i], inputSideLength, epsilon, xBound, yBound);
            tempSubsetGeoIExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            subsetGeoIExperimentResultList.add(tempSubsetGeoIExperimentResult);

        }
        alterParameterMap.put(subsetGeoI, subsetGeoIExperimentResultList);
        return alterParameterMap;
    }

    public Map<String, Map<String, List<ExperimentResult>>> datasetRunRun(String dataSetPath, String datasetName, double xBound, double yBound, double inputSideLength) {
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();

        Map<String, List<ExperimentResult>> alteringGResult = alterParameterGRunRun(doublePointList, inputSideLength, xBound, yBound);
        ExperimentResult.addPair(alteringGResult, 0, Constant.dataSetNameKey, datasetName);

        Map<String, Map<String, List<ExperimentResult>>> datasetResult = new HashMap<>();
        datasetResult.put(Constant.alterGKey, alteringGResult);

        return datasetResult;
    }

    @Test
    public void subsetGeoITest() {
        // 测试Crime三个数据集下，AlterParameterGRun中SubsetGeoIRun
        String dataSetPath, dataSetName;
        Double xBound, yBound, length;
//        ExperimentResult tempDataSetResult;
        Map<String, Map<String, List<ExperimentResult>>> tempDataSetResult;
        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = new HashMap<>();

        for (DataSetAreaInfo dataSetInfo : Constant.crimeDataSetArray) {
            dataSetPath = dataSetInfo.getDataSetPath();
            dataSetName = dataSetInfo.getDataSetName();
            xBound = dataSetInfo.getxBound();
            yBound = dataSetInfo.getyBound();
            length = dataSetInfo.getLength();
            tempDataSetResult = datasetRunRun(dataSetPath, dataSetName, xBound, yBound, length);
            resultMap.put(dataSetName, tempDataSetResult);
        }
        MyPrint.showMap(resultMap);
    }


}
