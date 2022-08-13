package mechanism_test;

import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction.newscheme.discretization.DiscretizedHybridUniformExponentialScheme;
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

        String inputPath = "F:\\dataset\\test\\synthetic_dataset\\two_uniform_dataset.txt";
        String outputCenterPath = "F:\\dataset\\test\\synthetic_dataset\\output_test\\two_uniform_dataset_center.txt";
        String outputRandomCenterPath = "F:\\dataset\\test\\synthetic_dataset\\output_test\\two_uniform_dataset_random_center.txt";
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
        String inputCenterPath = "F:\\dataset\\test\\synthetic_dataset\\output_test\\two_uniform_dataset_center.txt";
        String outputPath = "F:\\dataset\\test\\synthetic_dataset\\output_test\\two_uniform_dataset_center_count.txt";
        List<TwoDimensionalDoublePoint> pointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputCenterPath);
        Map<TwoDimensionalDoublePoint, Integer> countMap = StatisticTool.countHistogramNumber(pointList);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writeStatisticIntegerPoint(countMap);
        pointWrite.endWriting();
    }

}
