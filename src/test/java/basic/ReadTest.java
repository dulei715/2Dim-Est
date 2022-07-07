package basic;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.read.CSVRead;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ReadTest {
    @Test
    public void fun1() {
        String path = "F:\\dataset\\crime\\Chicago_Crimes_2008_to_2011.csv";
        int lineSize = 10;
        List<Map<String, String>> result = CSVRead.readData(path, lineSize);
        MyPrint.showList(result, "\r\n");
    }
}
