package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling;

import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils.AnchorBasedPivotSamplingUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnchorBasedPivotSampling {
    private final List<TwoDimensionalDoublePoint> totalTrajectoryPointSet;

    private Integer sectorSize;

    public AnchorBasedPivotSampling(final List<TwoDimensionalDoublePoint> totalTrajectoryPointSet, Integer sectorSize) {
        this.totalTrajectoryPointSet = totalTrajectoryPointSet;
        this.sectorSize = sectorSize;
    }

    /**
     * 如果 perturbedPointList 比 perturbedDirectionList 多，
     *      则 perturbedPointList[i] 在 trajectory 中的下一个节点对应 perturbedDirectionList[i]，
     *      且 i 是 trajectory 中第 2i 个元素
     * 如果 perturbedPointList 比 perturbedDirectionList 少，
     *      则 perturbedPointList[i] 在 trajectory 中的下一个节点对应 perturbedDirectionList[i+1]，
     *      且 i 是 trajectory 中第 2i+1 个元素
     * @param targetPoint
     * @param trajectory
     * @param perturbedPointList
     * @param perturbedDirectionList
     * @param firstTargetStartIndex 第一个target在trajectory中的index （取值0或1）
     * @return
     */
    public Set<TwoDimensionalIntegerPoint> getPointDomain(TwoDimensionalDoublePoint targetPoint, List<TwoDimensionalDoublePoint> trajectory, List<TwoDimensionalDoublePoint> perturbedPointList, Integer targetPointIndex, List<Integer> perturbedDirectionList, int firstTargetStartIndex) {
        Set<TwoDimensionalDoublePoint> pointDomain = new HashSet<>();
        if (firstTargetStartIndex == 0) {
            if (targetPointIndex == 0) {
                // 该设计保证了trajectory
                //todo
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet(this.totalTrajectoryPointSet, trajectory.get(trajectoryIndex + 1), trajectory.get(trajectoryIndex), this.sectorSize));
            } else if (targetPointIndex == (trajectory.size() - 1) / 2) {  // 判断targetPointIndex是否为最后一个
                //todo
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet(this.totalTrajectoryPointSet, trajectory.get(trajectoryIndex - 1), trajectory.get(trajectoryIndex), this.sectorSize));
            } else {
                //todo
            }

        }
    }
}
