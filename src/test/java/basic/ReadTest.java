package basic;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.read.CSVRead;
import com.csvreader.CsvReader;
import ecnu.dll.construction._config.Constant;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class ReadTest {
    @Test
    public void fun1() {
        String path = Constant.datasetPath + "\\crime\\Chicago_Crimes_2008_to_2011.csv";
        int lineSize = 10;
        List<Map<String, String>> result = CSVRead.readData(path, lineSize);
        MyPrint.showList(result, "\r\n");
    }

    @Test
    public void fun2() throws IOException {
        String path = Constant.datasetPath + "\\crime\\Chicago_Crimes_2008_to_2011.csv";
        CsvReader csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));
//        csvReader.readRecord();
//        String[] headers = csvReader.getHeaders();
//        MyPrint.showArray(headers);
        String header = csvReader.getHeader(1);
        System.out.println(header);

        int index = csvReader.getIndex("ID");
        System.out.println(index);

    }


    @Test
    public void fun3() throws IOException {
        String path = Constant.datasetPath + "\\crime\\Chicago_Crimes_2008_to_2011.csv";
        CsvReader csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));
        String keyLatitude = "Latitude", valueLatitude;
        String keyLongitude = "Longitude", valueLongitude;
        int i = 0;
        int max = 2;
        csvReader.readHeaders();
        String[] headArray = csvReader.getHeaders();
        MyPrint.showArray(headArray);
        while (csvReader.readRecord()) {
            ++i;
            if (i > max) {
                break;
            }
            MyPrint.showArray(csvReader.getValues(), ", ");
        }
    }

    @Test
    public void fun4() throws IOException {
        String path = Constant.datasetPath + "\\crime\\Chicago_Crimes_2008_to_2011.csv";
        CsvReader csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));

        String keyLatitude = "Latitude", valueLatitudeStr;
        String keyLongitude = "Longitude", valueLongitudeStr;
        double valueLatitude, valueLongitude;
        csvReader.readHeaders();
        String[] headArray = csvReader.getHeaders();
        while (csvReader.readRecord()) {
            valueLatitudeStr = csvReader.get(keyLatitude);
            valueLongitudeStr = csvReader.get(keyLongitude);
            if (valueLatitudeStr.isEmpty() || valueLongitudeStr.isEmpty()) {
                continue;
            }
            valueLatitude = Double.parseDouble(valueLatitudeStr);
            valueLongitude = Double.parseDouble(valueLongitudeStr);
//            valueLatitude = csvReader.get(0);
//            valueLongitude = csvReader.get(1);
//            System.out.println(valueLatitudeStr + ", " + valueLongitudeStr);
            if (valueLatitude < 40) {
                MyPrint.showArray(csvReader.getValues(), "; ");
            }
        }
    }

}
