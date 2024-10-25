package io_test;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.extend_tools.trajectory_io.TrajectoryRead;
import ecnu.dll.construction.extend_tools.trajectory_io.TrajectoryWrite;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrajectoryIOTest {
    @Test
    public void fun1() {
        String testBasicPath = Constant.basicDatasetPath;
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, testBasicPath, "test", "trajectory_1.txt");

        System.out.println(outputPath);


        List<List<TwoDimensionalDoublePoint>> trajectoryList = new ArrayList<>();

        List<TwoDimensionalDoublePoint> trajectoryA = new ArrayList<>();
        trajectoryA.add(new TwoDimensionalDoublePoint(2, 1));
        trajectoryA.add(new TwoDimensionalDoublePoint(4, 3));
        trajectoryA.add(new TwoDimensionalDoublePoint(2.8, 4.5));
        trajectoryA.add(new TwoDimensionalDoublePoint(6.2, 7.1));

        List<TwoDimensionalDoublePoint> trajectoryB = new ArrayList<>();
        trajectoryB.add(new TwoDimensionalDoublePoint(23, 1));
        trajectoryB.add(new TwoDimensionalDoublePoint(43, 43));
        trajectoryB.add(new TwoDimensionalDoublePoint(12.8, 47.5));
        trajectoryB.add(new TwoDimensionalDoublePoint(66.2, 97.1));

        trajectoryList.add(trajectoryA);
        trajectoryList.add(trajectoryB);

        TrajectoryWrite trajectoryWrite = new TrajectoryWrite();
        trajectoryWrite.startWriting(outputPath);
        trajectoryWrite.writeTrajectoryAndSize(trajectoryList);
        trajectoryWrite.endWriting();

    }

    @Test
    public void fun2() {
        String testBasicPath = Constant.basicDatasetPath;
        String inputPath = StringUtil.join(ConstantValues.FILE_SPLIT, testBasicPath, "test", "trajectory_1.txt");
        System.out.println(inputPath);

        TrajectoryRead trajectoryRead = new TrajectoryRead();
        trajectoryRead.startReading(inputPath);
        List<List<TwoDimensionalDoublePoint>> result = trajectoryRead.readAllTrajectoryAndSize();
        MyPrint.show2DimensionArray(result, ",", ConstantValues.LINE_SPLIT);
    }
}
