package ecnu.dll.construction.extend_tools.trajectory_io;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TrajectoryUtils {
    /*
        这里不单独封装Trajectory了
        以 List<TwoDimensionalDoublePoint> 为 一条轨迹
     */

    public static String fromTrajectoryToTrajectoryFileStoreString(List<TwoDimensionalDoublePoint> trajectory) {
        /**
         * 规定点的横纵坐标用逗号隔开，点之间用分号隔开
         */
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<TwoDimensionalDoublePoint> iterator = trajectory.iterator();
        TwoDimensionalDoublePoint tempPoint;
        if (!iterator.hasNext()) {
            return "";
        }
        tempPoint = iterator.next();
        stringBuilder.append(tempPoint.getXIndex()).append(",").append(tempPoint.getYIndex());
        while (iterator.hasNext()) {
            tempPoint = iterator.next();
            stringBuilder.append(";").append(tempPoint.getXIndex()).append(",").append(tempPoint.getYIndex());
        }
        return stringBuilder.toString();
    }

    public static List<TwoDimensionalDoublePoint> fromTrajectoryFileStoreStringToTrajectory(String trajectoryFileStoreString) {
        List<TwoDimensionalDoublePoint> trajectory = new ArrayList<>();
        String[] pointStringArray = trajectoryFileStoreString.split(";");
        TwoDimensionalDoublePoint tempPoint;
        for (String pointStr : pointStringArray) {
            String[] indexStrArr = pointStr.split(",");
            tempPoint = new TwoDimensionalDoublePoint(Double.valueOf(indexStrArr[0]), Double.valueOf(indexStrArr[1]));
            trajectory.add(tempPoint);
        }
        return trajectory;
    }

    public static void main(String[] args) {
        List<TwoDimensionalDoublePoint> trajectory = new ArrayList<>();
        trajectory.add(new TwoDimensionalDoublePoint(2, 1));
        trajectory.add(new TwoDimensionalDoublePoint(4, 3));
        trajectory.add(new TwoDimensionalDoublePoint(2.8, 4.5));
        trajectory.add(new TwoDimensionalDoublePoint(6.2, 7.1));
        String trajectoryString = fromTrajectoryToTrajectoryFileStoreString(trajectory);
        System.out.println(trajectoryString);

        List<TwoDimensionalDoublePoint> result = fromTrajectoryFileStoreStringToTrajectory(trajectoryString);
        MyPrint.showList(result, ConstantValues.LINE_SPLIT);
    }

}
