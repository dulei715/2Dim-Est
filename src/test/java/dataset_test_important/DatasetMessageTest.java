package dataset_test_important;

import cn.edu.dll.io.write.PointWrite;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import com.csvreader.CsvReader;
import ecnu.dll.construction._config.Constant;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DatasetMessageTest {

    public List<TwoDimensionalDoublePoint>[] pointList;

    public String path = Constant.basicDatasetPath + "\\crime\\Chicago_Crimes_2022_01_06.csv";
    public String keyLatitude = "Latitude";
    public String keyLongitude = "Longitude";
    public String[] outputPathArray = new String[]{
            Constant.basicDatasetPath + "\\test\\chicago_point_A.txt",
            Constant.basicDatasetPath + "\\test\\chicago_point_B.txt",
            Constant.basicDatasetPath + "\\test\\chicago_point_C.txt"
    };

//    public String path = "F:\\dataset\\nyc\\2016_green_taxi_trip_data.csv";
//    public String keyLatitude = "Pickup_latitude";
//    public String keyLongitude = "Pickup_longitude";
//    public String[] outputPathArray = new String[]{
//            "F:\\dataset\\test\\nyc_point_A.txt",
//            "F:\\dataset\\test\\nyc_point_B.txt",
//            "F:\\dataset\\test\\nyc_point_C.txt"
//    };

    @Before
    public void preHandleData() throws IOException {


//        this.pointList = new ArrayList<>();
        this.pointList = new ArrayList[3];
        for (int i = 0; i < pointList.length; i++) {
            this.pointList[i] = new ArrayList<>();
        }

        CsvReader csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));

        String valueLatitudeStr;
        String valueLongitudeStr;
        csvReader.readHeaders();
        double valueLatitude, valueLongitude;
        String[] headArray = csvReader.getHeaders();
        while (csvReader.readRecord()) {
            valueLatitudeStr = csvReader.get(this.keyLatitude);
            valueLongitudeStr = csvReader.get(this.keyLongitude);
            if (valueLatitudeStr.isEmpty() || valueLongitudeStr.isEmpty()) {
                continue;
            }
            valueLatitude = Double.parseDouble(valueLatitudeStr);
            valueLongitude = Double.parseDouble(valueLongitudeStr);
            // chicago
            if (valueLatitude >= 41.72 && valueLatitude <= 41.81 && valueLongitude >= -87.68 && valueLongitude <= -87.59) {
                this.pointList[0].add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
            }
            if (valueLatitude >= 41.82 && valueLatitude <= 41.91 && valueLongitude >= -87.73 && valueLongitude <= -87.64) {
                this.pointList[1].add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
            }
            if (valueLatitude >= 41.92 && valueLatitude <= 41.99 && valueLongitude >= -87.77 && valueLongitude <= -87.70) {
                this.pointList[2].add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
            }

            // syn
//            if (valueLatitude >= 40.65 && valueLatitude <= 40.75 && valueLongitude >= -73.84 && valueLongitude <= -73.74) {
//                this.pointList[0].add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
//            }
//            if (valueLatitude >= 40.65 && valueLatitude <= 40.74 && valueLongitude >= -73.95 && valueLongitude <= -73.86) {
//                this.pointList[1].add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
//            }
//            if (valueLatitude >= 40.82 && valueLatitude <= 40.89 && valueLongitude >= -73.90 && valueLongitude <= -73.83) {
//                this.pointList[2].add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
//            }
        }
    }

    /**
     * 将给定数据集中的位置坐标点输出到文件
     */
    @Test
    public void outputPointToFile() {
        PointWrite pointWrite = new PointWrite();
        for (int i = 0; i < pointList.length; i++) {
            pointWrite.startWriting(this.outputPathArray[i]);
            pointWrite.writePoint(this.pointList[i]);
            pointWrite.endWriting();
        }
    }

    /**
     *  1. 统计给定数据集的边界
     *  2. 统计给定数据集的最小单元
     *  3. 统计给定数据集的分布
     */
    @Test
    public void statistic() {

    }
}
