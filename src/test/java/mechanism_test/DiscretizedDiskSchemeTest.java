package mechanism_test;

import cn.edu.ecnu.collection.ListUtils;
import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.pair.IdentityPair;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class DiscretizedDiskSchemeTest {
    @Test
    public void fun1() {
//        Double epsilon = 0.5;
//        Double gridLength = 1.0;
//        Double constB = 2.0;
//        Double inputLength = 2.0;
//        Double kParameter = 0.2;
//        Double xLeft = 0.0;
//        Double yLeft = 0.0;
        Integer sizeB = 2;
        Integer sizeD = 2;


//        DiscretizedDiskScheme discretizedDiskScheme = new DiscretizedDiskScheme(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
        Integer index45 = DiscretizedDiskSchemeTool.calculate45EdgeIndex(sizeB).getXIndex();
        List<TwoDimensionalIntegerPoint> outerCellIndexList = DiscretizedDiskSchemeTool.calculateHighProbabilityBorderCellIndexList(sizeB);
        List<TwoDimensionalIntegerPoint> noiseIntegerPointTypeList = DiscretizedDiskSchemeTool.getNoiseIntegerPointTypeList(outerCellIndexList, sizeD, sizeB, index45);
        List<TwoDimensionalIntegerPoint> innerCellList = DiscretizedDiskSchemeTool.getInnerCell(outerCellIndexList);
        TreeSet<TwoDimensionalIntegerPoint> residualSet = DiscretizedDiskSchemeTool.getResidualPureLowCellsByPoint(new TwoDimensionalIntegerPoint(0, 0), noiseIntegerPointTypeList, innerCellList, outerCellIndexList, sizeB, index45);
        MyPrint.showCollection(residualSet);
    }

    @Test
    public void fun2() {
        String outputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\test2\\single_point_test3.txt";
        double epsilon = 1;
        double gridLength = 0.2;
        double constB = 1.6;
        Double inputLength = 1.0;
        Double kParameter = 0.2;
        Double xLeft = 0.0;
        Double yLeft = 0.0;

        DiscretizedDiskScheme scheme = new DiscretizedDiskScheme(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);

        TwoDimensionalIntegerPoint originalPoint = new TwoDimensionalIntegerPoint(0, 0);
        List<TwoDimensionalIntegerPoint> pointList = ListUtils.copyToListGivenElement(originalPoint, 50000);
//        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);


        List<TwoDimensionalDoublePoint> noiseValue = scheme.getRandomizedNoiseValueInIntegerCell(pointList);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writePoint(noiseValue);
        pointWrite.endWriting();
    }

    @Test
    public void fun3() {
        String outputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\test2\\single_point_center_test3.txt";
        double epsilon = 1;
        double gridLength = 0.2;
        double constB = 1.6;
        Double inputLength = 1.0;
        Double kParameter = 0.2;
        Double xLeft = 0.0;
        Double yLeft = 0.0;

        DiscretizedDiskScheme scheme = new DiscretizedDiskScheme(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);

        TwoDimensionalIntegerPoint originalPoint = new TwoDimensionalIntegerPoint(0, 0);
        List<TwoDimensionalIntegerPoint> pointList = ListUtils.copyToListGivenElement(originalPoint, 50000);
//        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);


        List<TwoDimensionalIntegerPoint> noiseValue = scheme.getNoiseValueList(pointList);
        Map<TwoDimensionalIntegerPoint, Integer> countMap = StatisticTool.countHistogramNumber(noiseValue);
        MyPrint.showMap(countMap);
//        PointWrite pointWrite = new PointWrite();
//        pointWrite.startWriting(outputPath);
//        pointWrite.writePoint(noiseDoubleRandomCentralValue);
//        pointWrite.endWriting();
    }
}
