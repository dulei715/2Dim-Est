package dataset_test_important;

import cn.edu.ecnu.dataset.DatasetHandler;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import org.junit.Test;

import java.util.List;

public class ExtractDatasetTest {
    @Test
    public void extractNormDataset() {
        String inputPath = "F:\\dataset\\test\\synthetic_dataset\\test3\\two_dim_norm.txt";
        String outPath = "F:\\dataset\\test\\synthetic_dataset\\test3\\two_dim_norm_extract.txt";
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(inputPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> pointList = pointRead.getPointList();
        TwoDimensionalDoublePoint leftBottomPoint = new TwoDimensionalDoublePoint(-1.0, -1.0);
        double xLength, yLength;
        xLength = yLength = 2.0;
        List<TwoDimensionalDoublePoint> extractDataList = DatasetHandler.extractDataPointInGivenSquareArea(pointList, leftBottomPoint, xLength, yLength);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outPath);
        pointWrite.writePoint(extractDataList);
        pointWrite.endWriting();
    }
}
