package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.FrequencyOracle;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.foImp.OptimizedUnaryEncoding;
import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.extend_tools.StatisticUtil;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.TrajectoryFO;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.sub_struct.CellNeighboring;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.struct_utils.AbsolutePositionOneHotUtils;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.struct_utils.CellNeighboringOneHotUtils;

import java.util.List;

public class LDPTrace {
    private Integer maxTravelDistance;
    private Double totalPrivacyBudget;
    private int rowSize;
    private int colSize;

    private TrajectoryFO trajectoryFO;

    public LDPTrace() {

    }

    protected int sampleLength(Double[] lengthEstimationData) {
        Integer index = RandomUtil.getRandomIndexGivenCountPoint(lengthEstimationData);
        return index + 1;
    }

    protected TwoDimensionalIntegerPoint sampleStartOrEndCell(Double[] startOrEndEstimationCountData) {
        Integer index = RandomUtil.getRandomIndexGivenCountPoint(startOrEndEstimationCountData);
        TwoDimensionalIntegerPoint result = AbsolutePositionOneHotUtils.toOriginalData(index, this.colSize);
        return result;
    }

    protected CellNeighboring sampleNextCell(Double[] cellNeighboringCountData, CellNeighboring currentCell) {
        int[] indexRange = CellNeighboringOneHotUtils.toOneHotDataIndexRange(currentCell);
        Integer chosenIndex = RandomUtil.getRandomIndexGivenCountPoint(cellNeighboringCountData, indexRange[0], indexRange[1]);
        return new CellNeighboring(this.rowSize, this.colSize, )
    }

    public List<TwoDimensionalIntegerPoint> trajectorySynthesis() {
        //todo:
    }
}
