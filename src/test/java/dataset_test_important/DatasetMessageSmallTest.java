package dataset_test_important;

import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import com.csvreader.CsvReader;
import ecnu.dll.construction._config.Constant;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DatasetMessageSmallTest {

    public List<TwoDimensionalDoublePoint> pointList;

    public String path = Constant.datasetPath + "\\crime\\Chicago_Crimes_2008_to_2011.csv";
    public String keyLatitude = "Latitude";
    public String keyLongitude = "Longitude";
    public String outputPath = Constant.datasetPath + "\\test\\before\\chicago_point_small.txt";


    @Before
    public void preHandleData() throws IOException {


//        this.pointList = new ArrayList<>();
        this.pointList = new ArrayList<>();

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
            if (valueLatitude >= 41.72 && valueLatitude <= 41.73 && valueLongitude >= -87.68 && valueLongitude <= -87.67) {
                this.pointList.add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
            }
        }
    }

    /**
     * 将给定数据集中的位置坐标点输出到文件
     */
    @Test
    public void outputPointToFile() {
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(this.outputPath);
        pointWrite.writePoint(this.pointList);
        pointWrite.endWriting();

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
