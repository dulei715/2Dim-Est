package mechanism_test;

import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.io.write.PointWrite;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.schemes.new_scheme.discretization.DiscretizedHybridUniformExponentialScheme;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class DiscretizedHybridUniformExponentialSchemeTest {
    @Test
    public void fun1() {
        double epsilon = 0.5;
        double gridLength = 1.0;
        double constB = 2.0;
        Double inputLength = 2.0;
        Double kParameter = 0.2;
        Double xLeft = 0.0;
        Double yLeft = 0.0;

        DiscretizedHybridUniformExponentialScheme scheme = new DiscretizedHybridUniformExponentialScheme(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
        scheme.initialize();



    }

    @Test
    public void fun2() {
        double epsilon = 0.5;
        double gridLength = 0.2;
        double constB = 1.6;
        Double inputLength = 1.0;
        Double kParameter = 0.2;
        Double xLeft = 0.0;
        Double yLeft = 0.0;

        DiscretizedHybridUniformExponentialScheme scheme = new DiscretizedHybridUniformExponentialScheme(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
        scheme.initialize();

        String inputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\two_uniform_dataset.txt";
        String outputCenterPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\output_test\\two_uniform_dataset_center.txt";
        String outputRandomCenterPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\output_test\\two_uniform_dataset_random_center.txt";
        List<TwoDimensionalDoublePoint> pointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputPath);
//        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);

        List<TwoDimensionalDoublePoint> noiseDoubleCentralValue = scheme.getNoiseDoubleValue(pointList, true);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputCenterPath);
        pointWrite.writePoint(noiseDoubleCentralValue);
        pointWrite.endWriting();

        List<TwoDimensionalDoublePoint> noiseDoubleRandomCentralValue = scheme.getNoiseDoubleValue(pointList, false);
        pointWrite = new PointWrite();
        pointWrite.startWriting(outputRandomCenterPath);
        pointWrite.writePoint(noiseDoubleRandomCentralValue);
        pointWrite.endWriting();

    }

    @Test
    public void fun3() {
        String inputCenterPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\output_test\\two_uniform_dataset_center.txt";
        String outputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\output_test\\two_uniform_dataset_center_count.txt";
        List<TwoDimensionalDoublePoint> pointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputCenterPath);
        Map<TwoDimensionalDoublePoint, Integer> countMap = StatisticTool.countHistogramNumber(pointList);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writeStatisticIntegerPoint(countMap);
        pointWrite.endWriting();
    }

    @Test
    public void fun4() {
        String outputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\test2\\single_point_test.txt";
        double epsilon = 0.5;
        double gridLength = 0.2;
        double constB = 1.6;
        Double inputLength = 1.0;
        Double kParameter = 0.2;
        Double xLeft = 0.0;
        Double yLeft = 0.0;

        DiscretizedHybridUniformExponentialScheme scheme = new DiscretizedHybridUniformExponentialScheme(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
        scheme.initialize();

        TwoDimensionalDoublePoint originalPoint = new TwoDimensionalDoublePoint(0, 0);
        List<TwoDimensionalDoublePoint> pointList = ListUtils.copyToListGivenElement(originalPoint, 50000);
//        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);


        List<TwoDimensionalDoublePoint> noiseDoubleRandomCentralValue = scheme.getNoiseDoubleValue(pointList, false);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writePoint(noiseDoubleRandomCentralValue);
        pointWrite.endWriting();
    }

    @Test
    public void fun5() {
        String outputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\test2\\single_point_center_test.txt";
        double epsilon = 0.5;
        double gridLength = 0.2;
        double constB = 1.6;
        Double inputLength = 1.0;
        Double kParameter = 0.2;
        Double xLeft = 0.0;
        Double yLeft = 0.0;

        DiscretizedHybridUniformExponentialScheme scheme = new DiscretizedHybridUniformExponentialScheme(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
        scheme.initialize();

        TwoDimensionalDoublePoint originalPoint = new TwoDimensionalDoublePoint(0, 0);
        List<TwoDimensionalDoublePoint> pointList = ListUtils.copyToListGivenElement(originalPoint, 50000);
//        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);


        List<TwoDimensionalDoublePoint> noiseDoubleRandomCentralValue = scheme.getNoiseDoubleValue(pointList, true);
        Map<TwoDimensionalDoublePoint, Integer> countMap = StatisticTool.countHistogramNumber(noiseDoubleRandomCentralValue);
        MyPrint.showMap(countMap);
//        PointWrite pointWrite = new PointWrite();
//        pointWrite.startWriting(outputPath);
//        pointWrite.writePoint(noiseDoubleRandomCentralValue);
//        pointWrite.endWriting();
    }

    @Test
    public void fun6() {
        String outputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\test2\\single_point_test2.txt";
        double epsilon = 1;
        double gridLength = 0.2;
        double constB = 1.6;
        Double inputLength = 1.0;
        Double kParameter = 0.2;
        Double xLeft = 0.0;
        Double yLeft = 0.0;

        DiscretizedHybridUniformExponentialScheme scheme = new DiscretizedHybridUniformExponentialScheme(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
        scheme.initialize();

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
    public void fun7() {
//        String outputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\test2\\single_point_center_test2.txt";
        double epsilon = 1;
        double gridLength = 0.2;
//        double constB = 1.6;
        double constB = 1.6;
//        Double inputLength = 1.0;
        Double inputLength = 10.0;
        Double kParameter = 0.2;
        Double xLeft = 0.0;
        Double yLeft = 0.0;

        DiscretizedHybridUniformExponentialScheme scheme = new DiscretizedHybridUniformExponentialScheme(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
        scheme.initialize();

        TwoDimensionalIntegerPoint originalPoint = new TwoDimensionalIntegerPoint(0, 0);
        List<TwoDimensionalIntegerPoint> pointList = ListUtils.copyToListGivenElement(originalPoint, 500000);


        List<TwoDimensionalIntegerPoint> noiseValue = scheme.getNoiseValueList(pointList);
        Map<TwoDimensionalIntegerPoint, Integer> countMap = StatisticTool.countHistogramNumber(noiseValue);
        MyPrint.showMap(countMap);
//        PointWrite pointWrite = new PointWrite();
//        pointWrite.startWriting(outputPath);
//        pointWrite.writePoint(noiseDoubleRandomCentralValue);
//        pointWrite.endWriting();
    }


}
