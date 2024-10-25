package ecnu.dll.construction.run._2_trajectory_run.pre_handle;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.dataset.trajectory.TrajectorySampleTool;
import ecnu.dll.construction.extend_tools.trajectory_io.TrajectoryWrite;

import java.util.List;

public class SampleAndWriteTrajectory {
    public static void main(String[] args) {
        DataSetAreaInfo datasetInfo = Constant.nycDataSet;

        Integer trajectorySamplingLengthLowerBound = Constant.TrajectorySamplingLengthLowerBound;
        Integer trajectorySamplingLengthUpperBound = Constant.TrajectorySamplingLengthUpperBound;
        Integer trajectorySamplingSize = Constant.TrajectorySamplingSize;
//        Integer trajectorySamplingSize = 20;
        Integer sampleGridSideLength = Constant.SampleTrajectoryGridSideLength;
//        Integer sampleGridSideLength = 100;

        String dataSetPath = datasetInfo.getDataSetPath();
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetInfo.getParentPath(), "nyc_trajectory.txt");
        System.out.println(dataSetPath);

        TrajectorySampleTool trajectorySampleTool = new TrajectorySampleTool(sampleGridSideLength);
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> pointList = pointRead.getPointList();

        List<List<TwoDimensionalDoublePoint>> trajectoryList = trajectorySampleTool.sampleTrajectory(pointList, trajectorySamplingLengthLowerBound, trajectorySamplingLengthUpperBound, trajectorySamplingSize);
//        MyPrint.show2DimensionArray(trajectoryList, "; ", ConstantValues.LINE_SPLIT);

        TrajectoryWrite trajectoryWrite = new TrajectoryWrite();
        trajectoryWrite.startWriting(outputPath);
        trajectoryWrite.writeTrajectoryAndSize(trajectoryList);
        trajectoryWrite.endWriting();

    }
}
