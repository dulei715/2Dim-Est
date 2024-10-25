package ecnu.dll.construction.run._2_trajectory_run;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.LDPTrace;

import java.util.List;

public class LDPTraceRun {
    public ExperimentResult runAndWrite(List<List<TwoDimensionalDoublePoint>> dataSet, Integer gridSideLength, Double epsilon) {
        LDPTrace ldpTrace = new LDPTrace(gridSideLength, gridSideLength);
//        ldpTrace.trajectorySynthesis()
        // todo



        return null;
    }
}
