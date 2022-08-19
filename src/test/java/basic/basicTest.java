package basic;

import Jama.Matrix;
import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.struct.pair.BasicPair;
import org.junit.Test;

import java.util.*;

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

    @Test
    public void fun4() {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(0,4);
        MyPrint.showList(list);
    }

    @Test
    public void fun5() {
        Double value = 9.45E-4;
        System.out.println(value);
        System.out.println(value*100000);
    }

    @Test
    public void fun6() {
        double[][] vectorA = new double[][]{{2,3}};
        double[][] factor = new double[][]{
                {4, 5},
                {6, 7}
        };
        Matrix matrixA = new Matrix(vectorA);
        Matrix matrixB = new Matrix(factor);
        Matrix resultMatrix = matrixA.times(matrixB);
        double[][] result = resultMatrix.getArray();
        MyPrint.show2DimensionDoubleArray(result);
    }

    @Test
    public void fun7() {
        double[][] vectorA = new double[][]{{26, 31}};
        double[][] factor = new double[][]{
                {4, 5},
                {6, 7}
        };
        Matrix matrixA = new Matrix(vectorA);
        Matrix matrixB = new Matrix(factor).inverse();
        Matrix resultMatrix = matrixA.times(matrixB);
        double[][] result = resultMatrix.getArray();
        MyPrint.show2DimensionDoubleArray(result);
    }

    @Test
    public void fun8() {
        double[][] vectorA = new double[][]{{1, 2}, {3, 4}};
        double[][] factor = new double[][]{{5, 6}, {7, 8}};
        Matrix matrixA = new Matrix(vectorA);
        Matrix matrixB = new Matrix(factor);
        Matrix resultMatrix = matrixA.times(matrixB);
        double[][] result = resultMatrix.getArray();
        MyPrint.show2DimensionDoubleArray(result);
    }

    @Test
    public void fun9() {
        double[][] vectorA = new double[][]{{19, 22}, {43, 50}};
        double[][] factor = new double[][]{{5, 6}, {7, 8}};
        Matrix matrixA = new Matrix(vectorA);
        Matrix matrixB = new Matrix(factor);
        Matrix resultMatrix = matrixA.arrayRightDivide(matrixB);
        double[][] result = resultMatrix.getArray();
        MyPrint.show2DimensionDoubleArray(result);
    }

    @Test
    public void fun10() {
        double[][] vectorA = new double[][]{{19, 22}, {43, 50}};
        double[][] factor = new double[][]{{5, 6}, {7, 8}};
        Matrix matrixA = new Matrix(vectorA);
        Matrix matrixB = new Matrix(factor).inverse();
        Matrix resultMatrix = matrixA.times(matrixB);
        double[][] result = resultMatrix.getArray();
        MyPrint.show2DimensionDoubleArray(result);
    }

}
