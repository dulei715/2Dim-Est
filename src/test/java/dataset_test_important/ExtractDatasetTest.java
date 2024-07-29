package dataset_test_important;

import cn.edu.dll.dataset.DatasetHandler;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.io.write.PointWrite;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction._config.Constant;
import org.junit.Test;

import java.util.List;

public class ExtractDatasetTest {

    @Test
    public void extractCrimeDataset() {
        String inputPath = Constant.basicDatasetPath + "\\crime\\Chicago_Crimes_2022_01_06.csv";
        String outPath = Constant.basicDatasetPath + "\\test\\chicago_crimes_dataset.txt";
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
        String inputPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\two_normal_point.txt";
        String outPath = Constant.basicDatasetPath + "\\test\\synthetic_dataset\\two_normal_point_extract.txt";
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
    public void extractZipFDatasetForPicture() {
        String inputPath = Constant.basicDatasetPath + "\\2_synthetic\\2_two_zipf\\two_zipf_point.txt";
        String outputPath = Constant.basicDatasetPath + "\\2_synthetic\\2_two_zipf\\two_zipf_point_part.txt";
        int shrinkUnit = 10, offset = 0;
        List<TwoDimensionalDoublePoint> rawPointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputPath);
        List<TwoDimensionalDoublePoint> extractPointList = DatasetHandler.extractDataPointGivenShrinkUnitAndOffset(rawPointList, shrinkUnit, offset);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writePoint(extractPointList);
        pointWrite.endWriting();
    }

    @Test
    public void extractNormalDatasetForPicture() {
        String inputPath = Constant.basicDatasetPath + "\\2_synthetic\\1_two_normal\\two_normal_point.txt";
        String outputPath = Constant.basicDatasetPath + "\\2_synthetic\\1_two_normal\\two_normal_point_extract_for_picture.txt";
        int shrinkUnit = 10, offset = 0;
        List<TwoDimensionalDoublePoint> rawPointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputPath);
        List<TwoDimensionalDoublePoint> extractPointList = DatasetHandler.extractDataPointGivenShrinkUnitAndOffset(rawPointList, shrinkUnit, offset);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writePoint(extractPointList);
        pointWrite.endWriting();
    }

    @Test
    public void extractNormalDatasetWithMultipleCenters() {
        String inputPath = Constant.basicDatasetPath + "\\2_synthetic\\3_two_normal_multiple_center\\two_normal_point_multiple_centers.txt";
        String outPath = Constant.basicDatasetPath + "\\2_synthetic\\3_two_normal_multiple_center\\two_normal_point_multiple_centers_extract.txt";
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(inputPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> pointList = pointRead.getPointList();
        TwoDimensionalDoublePoint leftBottomPoint = new TwoDimensionalDoublePoint(-1.5, -2);
        double xLength, yLength;
        xLength = yLength = 5.0;
        List<TwoDimensionalDoublePoint> extractDataList = DatasetHandler.extractDataPointInGivenSquareArea(pointList, leftBottomPoint, xLength, yLength);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outPath);
        pointWrite.writePoint(extractDataList);
        pointWrite.endWriting();
    }
}


