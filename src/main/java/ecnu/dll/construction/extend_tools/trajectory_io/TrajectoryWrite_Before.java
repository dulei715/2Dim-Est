package ecnu.dll.construction.extend_tools.trajectory_io;

import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;

import java.util.ArrayList;
import java.util.List;

public class TrajectoryWrite_Before {
    private BasicWrite basicWrite = null;

    public TrajectoryWrite_Before() {
        basicWrite = new BasicWrite();
    }

    public void startWriting(String outputFilePath) {
        this.basicWrite.startWriting(outputFilePath);
    }

    public void writeTrajectoryAndSize(List<List<TwoDimensionalDoublePoint>> trajectoryList) {
        int trajectorySize = trajectoryList.size();
        List<String> trajectoryStringList = new ArrayList<>(trajectorySize);
        for (List<TwoDimensionalDoublePoint> trajectory : trajectoryList) {
            trajectoryStringList.add(TrajectoryUtils.fromTrajectoryToTrajectoryFileStoreString(trajectory));
        }
        this.basicWrite.writeSizeAndCollectionDataWithNewLineSplit(trajectoryStringList);
    }

    public void writeDataSize(Integer dataSize) {
        this.basicWrite.writeOneLine(String.valueOf(dataSize));
    }

    public void writeTrajectoryListWithoutWritingDataSize(List<List<TwoDimensionalDoublePoint>> trajectoryList) {
        List<String> trajectoryStringList = new ArrayList<>(trajectoryList.size());
        for (List<TwoDimensionalDoublePoint> trajectory : trajectoryList) {
            trajectoryStringList.add(TrajectoryUtils.fromTrajectoryToTrajectoryFileStoreString(trajectory));
        }
        this.basicWrite.writeStringListWithoutSize(trajectoryStringList);
    }

    public void endWriting() {
        this.basicWrite.endWriting();
    }


}
