package mechanism_test;

import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import org.junit.Test;

import java.util.List;

public class GridTest {
    @Test
    public void fun1() {
        Double[] bound = new Double[2];
        DataSetAreaInfo[] dataSetAreaInfos = Constant.crimeDataSetArray;
        bound[0] = dataSetAreaInfos[0].getxBound();
        bound[1] = dataSetAreaInfos[0].getyBound();
        MyPrint.showArray(bound);

        String dataSetPath = dataSetAreaInfos[0].getDataSetPath();
        System.out.println(dataSetPath);
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();
        System.out.println(doublePointList.size());
        for (TwoDimensionalDoublePoint point : doublePointList) {
            if (point.getXIndex() < bound[0] || point.getYIndex() < bound[1]) {
                MyPrint.showDoubleArray(point.getIndex());
            }
        }
    }
}
