package ecnu.dll.construction.dataset.trajectory;

import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.common_tools.GridTools;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.utils.TrajectoryFOUtils;

import java.util.ArrayList;
import java.util.List;

public class TrajectoryPointTool {
    public static List<List<TwoDimensionalIntegerPoint>> fromDoubleTrajectoryToIntegerTrajectory(List<List<TwoDimensionalDoublePoint>> trajectoryData, Integer gridSideLength, TwoDimensionalDoublePoint leftBottomPoint, TwoDimensionalDoublePoint rightTopPoint) {
        List<List<TwoDimensionalIntegerPoint>> result = new ArrayList<>(trajectoryData.size());
        List<TwoDimensionalIntegerPoint> integerTrajectory;
        for (List<TwoDimensionalDoublePoint> trajectory : trajectoryData) {
            integerTrajectory = GridTools.fromDoubleTrajectoryToGridTrajectory(trajectory, gridSideLength, leftBottomPoint, rightTopPoint);
            result.add(integerTrajectory);
        }
        return result;
    }
}
