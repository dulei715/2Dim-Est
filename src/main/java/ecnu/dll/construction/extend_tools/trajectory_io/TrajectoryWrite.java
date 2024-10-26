package ecnu.dll.construction.extend_tools.trajectory_io;

import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrajectoryWrite {

    BufferedWriter bufferedWriter;

    public void startWriting(String outputFilePath) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeTrajectoryAndSize(List<List<TwoDimensionalDoublePoint>> trajectoryList) {
        int trajectorySize = trajectoryList.size();
        try {
            this.bufferedWriter.write(trajectorySize);
            this.bufferedWriter.newLine();
            for (List<TwoDimensionalDoublePoint> trajectory : trajectoryList) {
                this.bufferedWriter.write(TrajectoryUtils.fromTrajectoryToTrajectoryFileStoreString(trajectory));
                this.bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void writeDataSize(Integer dataSize) {
        try {
            this.bufferedWriter.write(String.valueOf(dataSize));
            this.bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeTrajectoryListWithoutWritingDataSize(List<List<TwoDimensionalDoublePoint>> trajectoryList) {
        try {
            for (List<TwoDimensionalDoublePoint> trajectory : trajectoryList) {
                this.bufferedWriter.write(TrajectoryUtils.fromTrajectoryToTrajectoryFileStoreString(trajectory));
                this.bufferedWriter.newLine();
            }
//            this.bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void endWriting() {
        try {
            this.bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
