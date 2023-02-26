package mechanism_test;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.result.ExperimentResult;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.grid.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedSchemeTool;
import ecnu.dll.construction.run.main_process.b_comparision_run.basic.AlterParameterBudgetRun;
import ecnu.dll.construction.run.main_process.b_comparision_run.basic.AlterParameterGRun;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CompareTest {

    public static String dataSetPath, datasetName;
    public static double inputSideLength;
    public static double xBound, yBound;
    private List<TwoDimensionalDoublePoint> doublePointList;
    private List<TwoDimensionalIntegerPoint> integerPointList;

    private TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap;

    @BeforeClass
    public static void initializeClass() {

        DataSetAreaInfo twoDimNormalDataSet = Constant.twoDimNormalDataSet;

        dataSetPath = twoDimNormalDataSet.getDataSetPath();
        datasetName = twoDimNormalDataSet.getDataSetName();
//        inputSideLength = twoDimNormalDataSet.getLength();
        inputSideLength = twoDimNormalDataSet.getLength();
        xBound = twoDimNormalDataSet.getxBound();
        yBound = twoDimNormalDataSet.getyBound();
    }

    @Before
    public void initialize() {
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        doublePointList = pointRead.getPointList();
        integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, inputSideLength);
        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE));
        rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);
    }

    @Test
    public void mdswAlteringGTest() throws InstantiationException, IllegalAccessException, CloneNotSupportedException {



        Map<String, List<ExperimentResult>> alteringGResult = AlterParameterGRun.run(doublePointList, inputSideLength, xBound, yBound);
        ExperimentResult.addPair(alteringGResult, 0, Constant.dataSetNameKey, datasetName);
        MyPrint.showMap(alteringGResult);
    }

    @Test
    public void mdswAlteringBudgetTest() throws InstantiationException, IllegalAccessException, CloneNotSupportedException {

        Map<String, List<ExperimentResult>> alteringBudgetResult = AlterParameterBudgetRun.run(integerPointList, inputSideLength, rawStatisticMap, xBound, yBound);
        ExperimentResult.addPair(alteringBudgetResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(alteringBudgetResult, 0, Constant.dataSetNameKey, datasetName);
        MyPrint.showMap(alteringBudgetResult);
    }
}
