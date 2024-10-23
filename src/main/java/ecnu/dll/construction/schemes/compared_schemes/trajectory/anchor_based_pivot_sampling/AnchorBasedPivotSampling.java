package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.foImp.GeneralizedRandomizedResponse;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct.SectorAreas;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct.TrajectoryExponentialMechanism;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils.AnchorBasedPivotSamplingUtils;

import java.util.*;

public class AnchorBasedPivotSampling {
    private final List<TwoDimensionalDoublePoint> totalTrajectoryPointList;
    public static final Integer FlagFirstPivot = 0;
    public static final Integer FlagFirstTarget = 1;

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
    @Deprecated
    public Set<TwoDimensionalDoublePoint> getPointDomain_before(TwoDimensionalDoublePoint targetPoint, List<TwoDimensionalDoublePoint> trajectory, List<TwoDimensionalDoublePoint> perturbedPointList, Integer targetPointIndex, List<Integer> perturbedDirectionList, int firstTargetStartIndex) {
        Set<TwoDimensionalDoublePoint> pointDomain = new HashSet<>();
        int remain = trajectory.size() % 2;
        if (firstTargetStartIndex == 0) {   // 对应第1种情况
            if (targetPointIndex == 0) { // target为trajectory的第0个
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet_before(this.totalTrajectoryPointList, perturbedPointList.get(targetPointIndex), targetPoint, this.sectorSize, perturbedDirectionList.get(targetPointIndex)));
                pointDomain.add(targetPoint);
            } else if (remain == 1 && targetPointIndex == (trajectory.size() - 1) / 2 ) {  // 判断targetPointIndex是否为最后一个
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet_before(this.totalTrajectoryPointList, perturbedPointList.get(targetPointIndex - 1), targetPoint, this.sectorSize, perturbedDirectionList.get(2 * targetPointIndex - 1)));
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
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet_before(this.totalTrajectoryPointList, perturbedPointList.get(targetPointIndex), targetPoint, this.sectorSize, perturbedDirectionList.get(2 * targetPointIndex)));
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


    /**
     *
     * @param targetPoint
     * @param trajectory
     * @param sectorAreasList   和perturbedDirectionList 一一对应
     * @param targetPointInResListIndex
     * @param perturbedDirectionList
     * @param flag
     * @return
     */
    public Set<TwoDimensionalDoublePoint> getPointDomain(TwoDimensionalDoublePoint targetPoint, Integer targetPointInResListIndex, List<TwoDimensionalDoublePoint> trajectory, List<SectorAreas> sectorAreasList, List<Integer> perturbedDirectionList, int flag) {
        Set<TwoDimensionalDoublePoint> pointDomain = new HashSet<>();
        int remain = trajectory.size() % 2;
        if (flag == FlagFirstTarget) {   // 对应第1种情况
            if (targetPointInResListIndex == 0) { // target为trajectory的第0个
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet(this.totalTrajectoryPointList, sectorAreasList.get(targetPointInResListIndex), perturbedDirectionList.get(targetPointInResListIndex)));
                pointDomain.add(targetPoint);
            } else if (remain == 1 && targetPointInResListIndex == (trajectory.size() - 1) / 2 ) {  // 判断targetPointIndex是否为最后一个
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet(this.totalTrajectoryPointList, sectorAreasList.get(2 * targetPointInResListIndex - 1), perturbedDirectionList.get(2 * targetPointInResListIndex - 1)));
                pointDomain.add(targetPoint);
            } else {
                Set<TwoDimensionalDoublePoint> tempSet = AnchorBasedPivotSamplingUtils.getPointIntersectionSet(this.totalTrajectoryPointList, sectorAreasList.get(2 * targetPointInResListIndex - 1), sectorAreasList.get(2 * targetPointInResListIndex), perturbedDirectionList.get(2 * targetPointInResListIndex - 1), perturbedDirectionList.get(2 * targetPointInResListIndex));
                if (tempSet.isEmpty()) {
                    pointDomain.addAll(this.totalTrajectoryPointList);
                } else {
                    pointDomain.addAll(tempSet);
                }
            }

        } else {    // 对应第2种情况
            if (remain == 0 && targetPointInResListIndex == (trajectory.size() - 2) / 2) { // 判断targetPointIndex是否为最后一个
                pointDomain.addAll(AnchorBasedPivotSamplingUtils.getPointSet(this.totalTrajectoryPointList, sectorAreasList.get(2 * targetPointInResListIndex), perturbedDirectionList.get(2 * targetPointInResListIndex)));
                pointDomain.add(targetPoint);
            } else {
                Set<TwoDimensionalDoublePoint> tempSet = AnchorBasedPivotSamplingUtils.getPointIntersectionSet(this.totalTrajectoryPointList, sectorAreasList.get(2 * targetPointInResListIndex), sectorAreasList.get(2 * targetPointInResListIndex + 1), perturbedDirectionList.get(2 * targetPointInResListIndex), perturbedDirectionList.get(2 * targetPointInResListIndex + 1));
                if (tempSet.isEmpty()) {
                    pointDomain.addAll(this.totalTrajectoryPointList);
                } else {
                    pointDomain.addAll(tempSet);
                }
            }
        }
        return pointDomain;
    }


    /**
     * pivot
     * @param trajectory
     * @param privacyBudget
     * @param discreteDirectionMap 这个用来标识给定轨迹上每个点到其相邻点的方向，
     *                             key为当前节点(pivot点), value记录该节点的前轨迹邻居和后轨迹邻居；
     *                             这里方向直接封装在sectorAreas中
     * @param flag
     * @return
     */
    public List<TwoDimensionalDoublePoint> independentAndPivotPerturbation(List<TwoDimensionalDoublePoint> trajectory, Double privacyBudget, Map<TwoDimensionalDoublePoint, List<SectorAreas>> discreteDirectionMap, Integer flag) {
        TwoDimensionalDoublePoint currentPoint, tempDisturbPoint;
        List<Integer> perturbedDirectionList = new ArrayList<>();
        List<SectorAreas> sectorAreasList = new ArrayList<>();
        List<SectorAreas> tempSectorAreasList;
        List<TwoDimensionalDoublePoint> disturbedPivotPointList = new ArrayList<>();
        List<TwoDimensionalDoublePoint> restPointList = new ArrayList<>();
        List<TwoDimensionalDoublePoint> disturbedRestPointList = new ArrayList<>();
        Double epsilonD = 0.75 * privacyBudget;
        Double epsilonInd = (privacyBudget - epsilonD) / 2;
        Double epsilonRest = epsilonInd;
        int trajectorySize = trajectory.size();
        List<TwoDimensionalDoublePoint> disturbedTrajectory = new ArrayList<>(trajectorySize);
        TrajectoryExponentialMechanism trajectoryEM = new TrajectoryExponentialMechanism(epsilonInd / trajectorySize, this.totalTrajectoryPointList);
        TrajectoryExponentialMechanism tempEM;
        Integer[] sectorDomainArray = BasicArrayUtil.getIncreaseIntegerNumberArray(0, 1, this.sectorSize - 1);
        GeneralizedRandomizedResponse<Integer> sectorKRR = new GeneralizedRandomizedResponse<>(epsilonD / (2 * (trajectorySize - 1)), sectorDomainArray);
        for (int i = 0; i < trajectorySize; ++i) {
            currentPoint = trajectory.get(i);
            if (i % 2 == flag) {
                tempDisturbPoint = trajectoryEM.disturb(currentPoint);
                disturbedPivotPointList.add(tempDisturbPoint);
            } else {
                restPointList.add(currentPoint);
            }
        }
        for (int i = 0; i < restPointList.size(); ++i) {
            currentPoint = restPointList.get(i);
            tempSectorAreasList = discreteDirectionMap.get(currentPoint);
            int tempOriginalAreaIndex, tempDisturbedAreaIndex;
            for (SectorAreas sectorAreas : tempSectorAreasList) { // 该集合大小为1或2
                sectorAreasList.add(sectorAreas);
                tempOriginalAreaIndex = sectorAreas.getTargetPointExistingAreaIndex();
                tempDisturbedAreaIndex = sectorKRR.perturb(tempOriginalAreaIndex);
                perturbedDirectionList.add(tempDisturbedAreaIndex);
            }
            Set<TwoDimensionalDoublePoint> tempPointDomain = getPointDomain(currentPoint, i, trajectory, sectorAreasList, perturbedDirectionList, flag);
            tempEM = new TrajectoryExponentialMechanism(epsilonRest / trajectorySize, new ArrayList<>(tempPointDomain));
            tempDisturbPoint = tempEM.disturb(currentPoint);
            disturbedRestPointList.add(tempDisturbPoint);
        }
        for (int i = 0, j = 0, k = 0; i < trajectorySize; ++i) {
            if (i % 2 == flag) {
                disturbedTrajectory.add(disturbedPivotPointList.get(j++));
            } else {
                disturbedTrajectory.add(disturbedRestPointList.get(k++));
            }
        }
        return disturbedTrajectory;
    }

    List<TwoDimensionalDoublePoint> getOptimalPerturbedTrajectory(List<TwoDimensionalDoublePoint> disturbedTrajectoryA, List<TwoDimensionalDoublePoint> disturbedTrajectoryB) {
        // todo: 考虑解决遍历整个点域的问题
        return null;
    }














}
