package basic;

import Jama.Matrix;
import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.struct.pair.BasicPair;
import cn.edu.ecnu.struct.pair.IdentityPair;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePointUtils;
import ecnu.dll.construction.extend_tools.TwoDimensionalDoublePointListUtilExtend;
import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.thirdparty.protobuf.Internal;
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
        MyPrint.showCollection(treeSet);
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

    @Test
    public void fun11() {
        TreeSet<IdentityPair<Integer>> setA = new TreeSet<>();
        TreeSet<IdentityPair<Integer>> setB = new TreeSet<>();


        setA.add(new IdentityPair<>(1, 2));
        setA.add(new IdentityPair<>(3, 4));
        setA.add(new IdentityPair<>(5, 6));
        setA.add(new IdentityPair<>(7, 8));

        setB.add(new IdentityPair<>(3, 4));
        setB.add(new IdentityPair<>(7, 8));
        setB.add(new IdentityPair<>(9, 10));

        Collection resultSet = CollectionUtils.disjunction(setA, setB);
        MyPrint.showCollection(resultSet);

        MyPrint.showSplitLine("*", 150);

        Collection subtract = CollectionUtils.subtract(setA, setB);
        MyPrint.showCollection(subtract);

    }


    @Test
    public void fun12() {
        double[] arr = new double[] {
                23.1, 22.2, 18.4, 16.5, 13.2
        };
        int index = ArraysUtils.binaryDescendSearch(arr, 18.3);
        System.out.println(index);
    }

    @Test
    public void fun13() {
        double valueA = 0.0;
        double valueB = 0.0;
        double valueC = valueA / valueB;
        System.out.println(valueC);
        System.out.println(Float.valueOf("NaN").equals(valueC));
    }

    @Test
    public void fun14() {
//        String path = "/Users/admin/MainFiles/1.Research/dataset/2_two_dim_LDP/0_dataset/1_real/1_crime/chicago_point.txt";
//        String path = "/Users/admin/MainFiles/1.Research/dataset/2_two_dim_LDP/0_dataset/1_real/2_nyc/nyc_point.txt";
//        String path = "/Users/admin/MainFiles/1.Research/dataset/2_two_dim_LDP/0_dataset/2_synthetic/1_two_normal/two_normal_point.txt";
//        String path = "/Users/admin/MainFiles/1.Research/dataset/2_two_dim_LDP/0_dataset/2_synthetic/2_two_zipf/two_zipf_point.txt";
//        String path = "/Users/admin/MainFiles/1.Research/dataset/2_two_dim_LDP/0_dataset/2_synthetic/3_two_normal_multiple_center/two_normal_point_multiple_centers.txt";
//        String path = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\0_dataset\\1_real\\1_crime\\chicago_point.txt";
//        String path = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\0_dataset\\1_real\\2_nyc\\nyc_point.txt";
//        String path = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\0_dataset\\2_synthetic\\1_two_normal\\two_normal_point.txt";
//        String path = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\0_dataset\\2_synthetic\\2_two_zipf\\two_zipf_point.txt";
        String path = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\0_dataset\\2_synthetic\\3_two_normal_multiple_center\\two_normal_point_multiple_centers.txt";
        List<TwoDimensionalDoublePoint> data = TwoDimensionalPointRead.readPointWithFirstLineCount(path);
        System.out.println(data.size());
        List<TwoDimensionalDoublePoint> result = TwoDimensionalDoublePointListUtilExtend.getTwoBorderPoints(data);
        MyPrint.showList(result);
    }






























}
