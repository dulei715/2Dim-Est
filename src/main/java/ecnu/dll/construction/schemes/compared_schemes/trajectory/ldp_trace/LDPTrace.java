package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.TrajectoryFO;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot.sub_struct.CellNeighboring;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.struct_utils.AbsolutePositionOneHotUtils;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct.struct_utils.CellNeighboringOneHotUtils;

import java.util.ArrayList;
import java.util.List;

public class LDPTrace {
    private Integer maxTravelDistance;
    private Double totalPrivacyBudget;
    private int rowSize;
    private int colSize;

    private double alpha = Constant.LDPTraceAlpha;
    private double beta = Constant.LDPTraceBeta;

    private TrajectoryFO trajectoryFO;

    public LDPTrace(int gridRowSize, int gridColSize) {
        this.rowSize = gridRowSize;
        this.colSize = gridColSize;
    }

    protected int sampleLength(Double[] lengthEstimationData) {
        Integer index = RandomUtil.getRandomIndexGivenCountPoint(lengthEstimationData);
        return index + 1;
    }

    protected TwoDimensionalIntegerPoint sampleStartCell(Double[] startEstimationData) {
        Integer index = RandomUtil.getRandomIndexGivenCountPoint(startEstimationData);
        TwoDimensionalIntegerPoint result = AbsolutePositionOneHotUtils.toOriginalData(index, this.colSize);
        return result;
    }

    protected TwoDimensionalIntegerPoint sampleNextCell(Double[] cellNeighboringEstimationData, Double[] endEstimationData, int trajectoryLength, TwoDimensionalIntegerPoint currentCell) {
        int[] innerIndexRange = CellNeighboringOneHotUtils.toOneHotDataIndexRange(currentCell, this.rowSize, this.colSize);
        int endIndex = AbsolutePositionOneHotUtils.toOneHotDataIndex(currentCell, this.colSize);
//        Integer chosenIndex = RandomUtil.getRandomIndexGivenCountPoint(cellNeighboringEstimationData, innerIndexRange[0], innerIndexRange[1]);
        Double[] endReWeightEstimation = new Double[]{endEstimationData[endIndex]*(this.alpha+this.beta*trajectoryLength)};
        Integer[] chosenIndexPair = RandomUtil.getTwoPartsRandomIndexGivenCountPoint(cellNeighboringEstimationData, innerIndexRange[0], innerIndexRange[1], endReWeightEstimation, 0, 0);
        if (chosenIndexPair[0] == 0) {
//            return CellNeighboringOneHotUtils.toOriginalData(chosenIndexPair[1], this.rowSize, this.colSize);
            return CellNeighboringOneHotUtils.toOriginalNeighboringData(chosenIndexPair[1], this.rowSize, this.colSize);
        }
        return CellNeighboringOneHotUtils.getNullNeighboringPoint();
    }

    public List<TwoDimensionalIntegerPoint> trajectorySynthesis(Double[] lengthEstimationData, Double[] cellNeighboringEstimationData, Double[] startEstimationData, Double[] endEstimationData) {
        int trajectoryLength = this.sampleLength(lengthEstimationData);
        List<TwoDimensionalIntegerPoint> resultList = new ArrayList<>();
        TwoDimensionalIntegerPoint tempPoint = this.sampleStartCell(startEstimationData);
        resultList.add(tempPoint);
        for (int i = 1; i < trajectoryLength; ++i) {
            tempPoint = sampleNextCell(cellNeighboringEstimationData, endEstimationData, trajectoryLength, tempPoint);
            if (CellNeighboringOneHotUtils.isNullPoint(tempPoint)) {
                break;
            }
            resultList.add(tempPoint);
        }
        return resultList;
    }
}
