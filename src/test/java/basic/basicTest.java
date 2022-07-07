package basic;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.struct.BasicPair;
import javafx.util.Pair;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class basicTest {
    @Test
    public void fun1() {
        Map<String, String> map = new HashMap<>();
        map.put("xxx", null);
        MyPrint.showMap(map);
    }

    @Test
    public void fun2() {
        Map<String, String> map = new HashMap<>();
        String str = "a,,b";
        String[] splitStrs = str.split(",");
        MyPrint.showArray(splitStrs);
        System.out.println(splitStrs.length);
        for (int i = 0; i < splitStrs.length; i++) {
            map.put((i+1)+"", splitStrs[i]);
        }
        MyPrint.showMap(map);
    }

    @Test
    public void fun3() {
        TreeSet<BasicPair<Integer, Integer>> treeSet = new TreeSet<>();
        treeSet.add(new BasicPair<>(2,3));
        treeSet.add(new BasicPair<>(4,3));
        treeSet.add(new BasicPair<>(4,6));
        treeSet.add(new BasicPair<>(2,5));
        treeSet.add(new BasicPair<>(2,5));
        MyPrint.showSet(treeSet);
    }

}
