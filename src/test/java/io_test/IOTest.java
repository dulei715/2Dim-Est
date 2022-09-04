package io_test;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.read.CSVRead;
import ecnu.dll.construction._config.Constant;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class IOTest {
    @Test
    public void fun1() {
        String path = Constant.basicDatasetPath + "\\crime\\Chicago_Crimes_2022_01_06.csv";
        List<Map<String, String>> result = CSVRead.readData(path);
        MyPrint.showList(result, "\r\n");
    }

    @Test
    public void fun2() {
        String path = Constant.basicDatasetPath + "\\crime\\Chicago_Crimes_2022_01_06.csv";
        String keyLatitude = "Latitude", valueLatitude;
        String keyLongitude = "Longitude", valueLongitude;
        List<Map<String, String>> result = CSVRead.readData(path);
        for (Map<String, String> map : result) {
            valueLatitude = map.get(keyLatitude);
            valueLongitude = map.get(keyLongitude);
            if (valueLatitude != null && valueLongitude != null) {
                System.out.println(map.get(keyLatitude) + ", " + map.get(keyLongitude));
            }
        }
    }
}
