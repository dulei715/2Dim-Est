package dataset_test_important;

import cn.edu.ecnu.dataset.DatasetHandler;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import org.junit.Test;

import java.util.List;

public class ExtractDatasetTest {

    @Test
    public void extractCrimeDataset() {
        String inputPath = "F:\\dataset\\crime\\Chicago_Crimes_2022_01_06.csv";
        String outPath = "F:\\dataset\\test\\chicago_crimes_dataset.txt";
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(inputPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> pointList = pointRead.getPointList();
        TwoDimensionalDoublePoint leftBottomPoint = new TwoDimensionalDoublePoint(-1.5, -1.5);
        double xLength, yLength;
        xLength = yLength = 3.0;
        List<TwoDimensionalDoublePoint> extractDataList = DatasetHandler.extractDataPointInGivenSquareArea(pointList, leftBottomPoint, xLength, yLength);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outPath);
        pointWrite.writePoint(extractDataList);
        pointWrite.endWriting();
    }

    @Test
    public void extractNormDataset() {
        String inputPath = "F:\\dataset\\test\\synthetic_dataset\\two_normal_point.txt";
        String outPath = "F:\\dataset\\test\\synthetic_dataset\\two_normal_point_extract.txt";
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(inputPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> pointList = pointRead.getPointList();
        TwoDimensionalDoublePoint leftBottomPoint = new TwoDimensionalDoublePoint(-1.5, -1.5);
        double xLength, yLength;
        xLength = yLength = 3.0;
        List<TwoDimensionalDoublePoint> extractDataList = DatasetHandler.extractDataPointInGivenSquareArea(pointList, leftBottomPoint, xLength, yLength);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outPath);
        pointWrite.writePoint(extractDataList);
        pointWrite.endWriting();
    }
}
