package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.FrequencyOracle;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.foImp.OptimizedUnaryEncoding;
import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.extend_tools.StatisticUtil;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.TrajectoryFO;

import java.util.List;

public class LDPTrace {
    private Integer maxTravelDistance;
    private Double totalPrivacyBudget;

    private TrajectoryFO trajectoryFO;

    public LDPTrace() {

    }

    protected int sampleLength(Double[] lengthEstimationData) {
        Integer index = RandomUtil.getRandomIndexGivenCountPoint(lengthEstimationData);
        return index + 1;
    }

    protected TwoDimensionalIntegerPoint sampleStartCell(Double[] startEstimationCountData) {
        // todo:
    }

    public List<TwoDimensionalIntegerPoint> trajectorySynthesis() {
        //todo:
    }
}
