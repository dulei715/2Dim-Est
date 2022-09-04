package dataset_test_important;

import cn.edu.ecnu.basic.RandomUtil;
import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.statistic.DistributionUtil;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction._config.Constant;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenerateDatasetTest {
    @Test
    public void generateZipf() {
//        String outputPath = "F:\\dataset\\test\\synthetic_dataset\\two_zipf_dataset.txt";
        String outputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\two_zipf_point.txt";
        int size = 300000;
        double randomValueX, randomValueY;
        double[] doubleArrX, doubleArrY;
        TwoDimensionalDoublePoint point;
        List<TwoDimensionalDoublePoint> pointList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            randomValueX = RandomUtil.getRandomDouble(0.0, 1.0);
            doubleArrX = DistributionUtil.getSkewZipfRandomPoint(randomValueX);

            randomValueY = RandomUtil.getRandomDouble(0.0, 1.0);
            doubleArrY = DistributionUtil.getSkewZipfRandomPoint(randomValueY);

            point = new TwoDimensionalDoublePoint(doubleArrX[0], doubleArrY[0]);

            pointList.add(point);
        }
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writePoint(pointList);
        pointWrite.endWriting();

    }

    @Test
    public void generateUniform() {
        String outputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\two_uniform_dataset.txt";
        int size = 10000;
        double randomValueX, randomValueY;
        TwoDimensionalDoublePoint point;
        List<TwoDimensionalDoublePoint> pointList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            randomValueX = RandomUtil.getRandomDouble(0.0, 1.0);

            randomValueY = RandomUtil.getRandomDouble(0.0, 1.0);

            point = new TwoDimensionalDoublePoint(randomValueX, randomValueY);

            pointList.add(point);
        }
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writePoint(pointList);
        pointWrite.endWriting();
    }

    @Test
    public void generateNorm() {
        String outputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\two_norm_dataset.txt";
        int size = 10000;
        double randomValueX, randomValueY;
        TwoDimensionalDoublePoint point;
        List<TwoDimensionalDoublePoint> pointList = new ArrayList<>();
    }

}
