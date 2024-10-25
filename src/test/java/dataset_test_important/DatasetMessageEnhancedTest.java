package dataset_test_important;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.io.write.PointWrite;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePointUtils;
import com.csvreader.CsvReader;
import ecnu.dll.construction._config.Constant;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DatasetMessageEnhancedTest {
    @Test
    public void testDatasetBorder() {
//        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "2_synthetic", "1_two_normal", "two_normal_point.txt");
//        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "2_synthetic", "2_two_zipf", "two_zipf_point.txt");
        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "2_synthetic", "3_two_normal_multiple_center", "two_normal_point_multiple_centers.txt");
        TwoDimensionalPointRead read = new TwoDimensionalPointRead(datasetPath);
        read.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> pointList = read.getPointList();
        List<Double> xIndexList = TwoDimensionalDoublePointUtils.getXIndexList(pointList);
        List<Double> yIndexList = TwoDimensionalDoublePointUtils.getYIndexList(pointList);
        double minimalXValue = ListUtils.getMinimalValue(xIndexList);
        double maximalXValue = ListUtils.getMaximalDoubleValue(xIndexList);
        double minimalYValue = ListUtils.getMinimalValue(yIndexList);
        double maximalYValue = ListUtils.getMaximalDoubleValue(yIndexList);
        System.out.printf("x: [%f, %f]", minimalXValue, maximalXValue);
        System.out.println();
        System.out.printf("y: [%f, %f]", minimalYValue, maximalYValue);
        System.out.println();

    }
}
