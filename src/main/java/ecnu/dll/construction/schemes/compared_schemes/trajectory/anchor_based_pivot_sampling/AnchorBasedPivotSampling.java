package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling;

import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils.AnchorBasedPivotSamplingUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnchorBasedPivotSampling {
    private final List<TwoDimensionalDoublePoint> totalTrajectoryPointList;

    private Integer sectorSize;

    public AnchorBasedPivotSampling(final List<TwoDimensionalDoublePoint> totalTrajectoryPointList, Integer sectorSize) {
        this.totalTrajectoryPointList = totalTrajectoryPointList;
        this.sectorSize = sectorSize;
    }

    /**
     * 1、如果 perturbedPointList 从 trajectory 的第1个元素开始（那targetList从trajectory的第0个开始），
     *      则 perturbedPointList[i] 在 trajectory 中的下一个节点对应 targetList[i+1]，
     *      targetList[i] 在 trajectory 中的下一个节点对应 perturbedPointList[i]，
     *      targetList[i] 在 trajectory 中的上一个节点对应 perturbedPointList[i-1]，
     *      targetList[0] 对应 perturbedDirectionList[0]
     *      targetList[i]（i>0） 对应 perturbedDirectionList[2i-1]和perturbedDirectionList[2i] （trajectory如果是奇数个，则最后一个对应一个）
     *      需要考虑 target 可能为trajectory的第0个元素或[最后一个元素（trajectory为奇数时）]
     *
     * 2、如果 perturbedPointList 从 trajectory 的第0个元素开始（那targetList从trajectory的第1个开始），
     *      则 perturbedPointList[i] 在 trajectory 中的下一个节点对应 targetList[i]，
     *      targetList[i] 在 trajectory 中的下一个节点对应 perturbedPointList[i+1]，
     *      targetList[i] 在 trajectory 中的上一个节点对应 perturbedPointList[i]，
     *      targetList[i] 对应 perturbedDirectionList[2i]和perturbedDirectionList[2i+1]  (trajectory如果是偶数个，则最后一个对应一个）
     *      需要考虑 target 可能为trajectory的最后一个元素（trajectory为偶数时）
     *
     * @param targetPoint
     * @param trajectory
     * @param perturbedPointList 记录pivotPoint
     * @param perturbedDirectionList
     * @param firstTargetStartIndex 第一个target在trajectory中的index （取值0或1）
     * @return
     */
    public Set<TwoDimensionalDoublePoint> getPointDomain(TwoDimensionalDoublePoint targetPoint, List<TwoDimensionalDoublePoint> trajectory, List<TwoDimensionalDoublePoint> perturbedPointList, Integer targetPointIndex, List<Integer> perturbedDirectionList, int firstTargetStartIndex) {
        Set<TwoDimensionalDoublePoint> pointDomain = new HashSet<>();
        int remain = trajectory.size() % 2;
        if (firstTargetStartIndex == 0) {   // 对应第1种情况
            if (targetPointIndex == 0) { // target为trajectory的第0个
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet(this.totalTrajectoryPointList, perturbedPointList.get(targetPointIndex), targetPoint, this.sectorSize, perturbedDirectionList.get(targetPointIndex)));
                pointDomain.add(targetPoint);
            } else if (remain == 1 && targetPointIndex == (trajectory.size() - 1) / 2 ) {  // 判断targetPointIndex是否为最后一个
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet(this.totalTrajectoryPointList, perturbedPointList.get(targetPointIndex - 1), targetPoint, this.sectorSize, perturbedDirectionList.get(2 * targetPointIndex - 1)));
                pointDomain.add(targetPoint);
            } else {
                Set<TwoDimensionalDoublePoint> tempSet = AnchorBasedPivotSamplingUtils.getPointIntersectionSet(this.totalTrajectoryPointList, perturbedPointList.get(targetPointIndex - 1), perturbedPointList.get(targetPointIndex), targetPoint, this.sectorSize, perturbedDirectionList.get(2 * targetPointIndex - 1), perturbedDirectionList.get(2 * targetPointIndex));
                if (tempSet.isEmpty()) {
                    pointDomain.addAll(this.totalTrajectoryPointList);
                } else {
                    pointDomain.addAll(tempSet);
                }
            }

        } else {    // 对应第2种情况
            if (remain == 0 && targetPointIndex == (trajectory.size() - 2) / 2) { // 判断targetPointIndex是否为最后一个
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet(this.totalTrajectoryPointList, perturbedPointList.get(targetPointIndex), targetPoint, this.sectorSize, perturbedDirectionList.get(2 * targetPointIndex)));
                pointDomain.add(targetPoint);
            } else {
                Set<TwoDimensionalDoublePoint> tempSet = AnchorBasedPivotSamplingUtils.getPointIntersectionSet(this.totalTrajectoryPointList, perturbedPointList.get(targetPointIndex), perturbedPointList.get(targetPointIndex + 1), targetPoint, this.sectorSize, perturbedDirectionList.get(2 * targetPointIndex), perturbedDirectionList.get(2 * targetPointIndex + 1));
                if (tempSet.isEmpty()) {
                    pointDomain.addAll(this.totalTrajectoryPointList);
                } else {
                    pointDomain.addAll(tempSet);
                }
            }
        }
        return pointDomain;
    }
}
