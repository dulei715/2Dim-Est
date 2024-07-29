package run_test;

import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.grid.Grid;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.extend_tools.StatisticUtil;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedRhombusSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedSchemeTool;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.*;
import org.junit.Test;

import java.util.*;

public class AlterParameterBudgetTest {
    public static Map<String, List<ExperimentResult>> alterBudgetRun(final List<TwoDimensionalIntegerPoint> integerPointList, double inputSideLength, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) {
//        String inputDataPath = Constant.DEFAULT_INPUT_PATH;

        int arraySize = Constant.ALTER_PRIVACY_BUDGET_ARRAY.length;
        /*
            1. 设置cell大小的参数（同时也是设置整数input的边长大小）
         */
        double inputLengthSize = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
        double gridLength = inputSideLength / inputLengthSize;

        /*
            2. 设置隐私预算budget的变化数组
         */
        double[] epsilonArray = Constant.ALTER_PRIVACY_BUDGET_ARRAY;

        /*
            3. 根据budget，分别计算Rhombus和Disk方案对应的Optimal sizeB的取值
         */
        int inputIntegerLengthSize = (int)Math.ceil(inputLengthSize);
        int[] alterRhombusOptimalSizeB = new int[arraySize], alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            alterRhombusOptimalSizeB[i] = DiscretizedRhombusSchemeTool.getOptimalSizeBOfRhombusScheme(epsilonArray[i], inputIntegerLengthSize);
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilonArray[i], inputIntegerLengthSize);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */


        /*
            针对SubsetGeoI, MSW, Rhombus, Disk, Disk-non-Shrink, HUEM 分别计算对应budget下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String mdsw = Constant.multiDimensionalSquareWaveSchemeKey;
        ExperimentResult tempMdswExperimentResult;
        List<ExperimentResult> mdswExperimentResultList = new ArrayList<>();


        for (int i = 0; i < arraySize; i++) {

            // for MDSW
            tempMdswExperimentResult = MDSWRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, epsilonArray[i], xBound, yBound);
            mdswExperimentResultList.add(tempMdswExperimentResult);


        }


        alterParameterMap.put(mdsw, mdswExperimentResultList);

        return alterParameterMap;

    }
    @Test
    public void fun1() {
        DataSetAreaInfo dataSetAreaInfo = Constant.nycDataSetArray[0];
        String dataSetPath = dataSetAreaInfo.getDataSetPath();
        Double xBound = dataSetAreaInfo.getxBound();
        Double yBound = dataSetAreaInfo.getyBound();
        Double inputSideLength = dataSetAreaInfo.getLength();
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();

        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, inputSideLength / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE);
        TreeMap<TwoDimensionalIntegerPoint, Integer> testResult = StatisticUtil.statisticPoint(integerPointList);
        MyPrint.showMap(testResult);

        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE));
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

        Map<String, List<ExperimentResult>> result = alterBudgetRun(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        MyPrint.showMap(result);


    }
    @Test
    public void fun2() {
        DataSetAreaInfo dataSetAreaInfo = Constant.twoDimNormalDataSet;
        String dataSetPath = dataSetAreaInfo.getDataSetPath();
        Double xBound = dataSetAreaInfo.getxBound();
        Double yBound = dataSetAreaInfo.getyBound();
        Double inputSideLength = dataSetAreaInfo.getLength();
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();

        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, inputSideLength / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE);
        TreeMap<TwoDimensionalIntegerPoint, Integer> testResult = StatisticUtil.statisticPoint(integerPointList);
        MyPrint.showMap(testResult);

        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE));
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

        Map<String, List<ExperimentResult>> result = alterBudgetRun(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        MyPrint.showMap(result);


    }
}
