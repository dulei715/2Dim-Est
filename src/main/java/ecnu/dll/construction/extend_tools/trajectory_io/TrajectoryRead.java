package ecnu.dll.construction.extend_tools.trajectory_io;

import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;

import java.util.ArrayList;
import java.util.List;

public class TrajectoryRead {
    private BasicRead basicRead = null;

    public TrajectoryRead() {
        this.basicRead = new BasicRead();
    }

    public void startReading(String filePath) {
        this.basicRead.startReading(filePath);
    }

    public List<List<TwoDimensionalDoublePoint>> readAllTrajectoryAndSize() {
        List<String> trajectoryStringList = this.basicRead.readAll();
        List<List<TwoDimensionalDoublePoint>> result = new ArrayList<>(trajectoryStringList.size());
        List<TwoDimensionalDoublePoint> tempTrajectory;
        for (String trajectoryString : trajectoryStringList) {
            tempTrajectory = TrajectoryUtils.fromTrajectoryFileStoreStringToTrajectory(trajectoryString);
            result.add(tempTrajectory);
        }
        return result;
    }


    public void endReading() {
        this.basicRead.endReading();
    }
}
