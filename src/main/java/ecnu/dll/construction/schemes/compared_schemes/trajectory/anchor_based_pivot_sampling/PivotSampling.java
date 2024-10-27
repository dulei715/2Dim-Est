package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.foImp.GeneralizedRandomizedResponse;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePointUtils;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct.SectorAreas;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct.TrajectoryExponentialMechanism;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils.PivotSamplingUtils;

import java.util.*;

public class PivotSampling {
    public static final Integer FlagFirstPivot = 0;
    public static final Integer FlagFirstTarget = 1;
    protected static List<Integer> candidateSectorSizeList = Constant.CandidateSectorSizeListForNYC;

    protected Integer sectorSize;

    public PivotSampling() {

    }

//    @Deprecated
//    public PivotSampling(final List<TwoDimensionalDoublePoint> pointCircleDomain, Integer sectorSize) {
//        this.poinCircletDomain = pointCircleDomain;
//        this.sectorSize = sectorSize;
//    }

    /**
     * 1、如果 perturbedPointList 从 trajectory 的第1个元素开始（那targetList从trajectory的第0个开始），
     *      则 perturbedPointList[i] 在 trajectory 中的下一个节点对应 targetList[i+1]，
     *      targetList[i] 在 trajectory 中的下一个节点对应 perturbedPointList[i]，
     *      targetList[i] 在 trajectory 中的上一个节点对应 perturbedPointList[i-1]，
     *      targetList[0] 对应 perturbedDirectionList[0]
     *      targetList[i]（i>0） 对应 perturbedDirectionList[4i-1]和perturbedDirectionList[4i] （trajectory如果是奇数个，则最后一个对应一个）
     *      需要考虑 target 可能为trajectory的第0个元素或[最后一个元素（trajectory为奇数时）]
     *
     * 2、如果 perturbedPointList 从 trajectory 的第0个元素开始（那targetList从trajectory的第1个开始），
     *      则 perturbedPointList[i] 在 trajectory 中的下一个节点对应 targetList[i]，
     *      targetList[i] 在 trajectory 中的下一个节点对应 perturbedPointList[i+1]，
     *      targetList[i] 在 trajectory 中的上一个节点对应 perturbedPointList[i]，
     *      targetList[i] 对应 perturbedDirectionList[4i+1]和perturbedDirectionList[4i+2]  (trajectory如果是偶数个，则最后一个对应一个）
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
    public Set<TwoDimensionalDoublePoint> getPointDomain_before(TwoDimensionalDoublePoint targetPoint, List<TwoDimensionalDoublePoint> pointCircleDomain, final List<TwoDimensionalDoublePoint> trajectory, final List<TwoDimensionalDoublePoint> perturbedPointList, Integer targetPointIndex, final List<Integer> perturbedDirectionList, int firstTargetStartIndex) {
        Set<TwoDimensionalDoublePoint> pointDomain = new HashSet<>();
        int remain = trajectory.size() % 2;
        if (firstTargetStartIndex == 0) {   // 对应第1种情况
            if (targetPointIndex == 0) { // target为trajectory的第0个
                pointDomain.addAll(PivotSamplingUtils.getPointSet_before(pointCircleDomain, perturbedPointList.get(targetPointIndex), targetPoint, this.sectorSize, perturbedDirectionList.get(targetPointIndex)));
                pointDomain.add(targetPoint);
            } else if (remain == 1 && targetPointIndex == (trajectory.size() - 1) / 2 ) {  // 判断targetPointIndex是否为最后一个
                pointDomain.addAll(PivotSamplingUtils.getPointSet_before(pointCircleDomain, perturbedPointList.get(targetPointIndex - 1), targetPoint, this.sectorSize, perturbedDirectionList.get(2 * targetPointIndex - 1)));
                pointDomain.add(targetPoint);
            } else {
                Set<TwoDimensionalDoublePoint> tempSet = PivotSamplingUtils.getPointIntersectionSet(pointCircleDomain, perturbedPointList.get(targetPointIndex - 1), perturbedPointList.get(targetPointIndex), targetPoint, this.sectorSize, perturbedDirectionList.get(2 * targetPointIndex - 1), perturbedDirectionList.get(2 * targetPointIndex));
                if (tempSet.isEmpty()) {
                    pointDomain.addAll(pointCircleDomain);
                } else {
                    pointDomain.addAll(tempSet);
                }
            }

        } else {    // 对应第2种情况
            if (remain == 0 && targetPointIndex == (trajectory.size() - 2) / 2) { // 判断targetPointIndex是否为最后一个
                pointDomain.addAll(PivotSamplingUtils.getPointSet_before(pointCircleDomain, perturbedPointList.get(targetPointIndex), targetPoint, this.sectorSize, perturbedDirectionList.get(2 * targetPointIndex)));
                pointDomain.add(targetPoint);
            } else {
                Set<TwoDimensionalDoublePoint> tempSet = PivotSamplingUtils.getPointIntersectionSet(pointCircleDomain, perturbedPointList.get(targetPointIndex), perturbedPointList.get(targetPointIndex + 1), targetPoint, this.sectorSize, perturbedDirectionList.get(2 * targetPointIndex), perturbedDirectionList.get(2 * targetPointIndex + 1));
                if (tempSet.isEmpty()) {
                    pointDomain.addAll(pointCircleDomain);
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
    public Set<TwoDimensionalDoublePoint> getPointDomain(TwoDimensionalDoublePoint targetPoint, List<TwoDimensionalDoublePoint> pointCircleDomain, Integer targetPointInResListIndex, List<TwoDimensionalDoublePoint> trajectory, final List<SectorAreas> sectorAreasList, final List<Integer> perturbedDirectionList, int flag) {
        Set<TwoDimensionalDoublePoint> pointDomain = new HashSet<>();
        int remain = trajectory.size() % 2;
        if (flag == FlagFirstTarget) {   // 对应第1种情况
            if (targetPointInResListIndex == 0) { // target为trajectory的第0个
                pointDomain.addAll(PivotSamplingUtils.getPointSet(pointCircleDomain, sectorAreasList.get(targetPointInResListIndex), perturbedDirectionList.get(targetPointInResListIndex)));
                pointDomain.add(targetPoint);
            } else if (remain == 1 && targetPointInResListIndex == (trajectory.size() - 1) / 2 ) {  // 判断targetPointIndex是否为最后一个
                pointDomain.addAll(PivotSamplingUtils.getPointSet(pointCircleDomain, sectorAreasList.get(4 * targetPointInResListIndex - 1), perturbedDirectionList.get(4 * targetPointInResListIndex - 1)));
                pointDomain.add(targetPoint);
            } else {
                Set<TwoDimensionalDoublePoint> tempSet = PivotSamplingUtils.getPointIntersectionSet(pointCircleDomain, sectorAreasList.get(4 * targetPointInResListIndex - 1), sectorAreasList.get(4 * targetPointInResListIndex), perturbedDirectionList.get(4 * targetPointInResListIndex - 1), perturbedDirectionList.get(4 * targetPointInResListIndex));
                if (tempSet.isEmpty()) {
                    pointDomain.addAll(pointCircleDomain);
                } else {
                    pointDomain.addAll(tempSet);
                }
            }

        } else {    // 对应第2种情况
            if (remain == 0 && targetPointInResListIndex == (trajectory.size() - 2) / 2) { // 判断targetPointIndex是否为最后一个
                pointDomain.addAll(PivotSamplingUtils.getPointSet(pointCircleDomain, sectorAreasList.get(4 * targetPointInResListIndex + 1), perturbedDirectionList.get(4 * targetPointInResListIndex + 1)));
                pointDomain.add(targetPoint);
            } else {


//                {
//                    Integer perturbIndexA = perturbedDirectionList.get(2 * targetPointInResListIndex);
//                    Integer perturbIndexB = perturbedDirectionList.get(2 * targetPointInResListIndex + 1);
//                    SectorAreas sectorAreasA = sectorAreasList.get(2 * targetPointInResListIndex);
//                    SectorAreas sectorAreasB = sectorAreasList.get(2 * targetPointInResListIndex + 1);
//                    if (perturbIndexA >= sectorAreasA.getAreaList().size() || perturbIndexB >= sectorAreasB.getAreaList().size()) {
//                        System.out.println("Woc!");
//                    }
//
//                }

                Set<TwoDimensionalDoublePoint> tempSet = PivotSamplingUtils.getPointIntersectionSet(
                        pointCircleDomain,
                        sectorAreasList.get(4 * targetPointInResListIndex + 1),
                        sectorAreasList.get(4 * targetPointInResListIndex + 2),
                        perturbedDirectionList.get(4 * targetPointInResListIndex + 1),
                        perturbedDirectionList.get(4 * targetPointInResListIndex + 2)
                );
                if (tempSet.isEmpty()) {
                    pointDomain.addAll(pointCircleDomain);
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
     * @param discreteDirectionList 这个用来标识给定轨迹上每个点到其相邻点的方向，
     *                             key为当前节点(pivot点), value记录该节点的前轨迹邻居和后轨迹邻居；
     *                             这里方向直接封装在sectorAreas中
     * @param flag
     * @return
     */
    public List<TwoDimensionalDoublePoint> independentAndPivotPerturbation(final List<TwoDimensionalDoublePoint> trajectory, List<TwoDimensionalDoublePoint> pointCircleDomain, Double privacyBudget, final List<List<SectorAreas>> discreteDirectionList, Integer flag) {
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
        TrajectoryExponentialMechanism trajectoryEM = new TrajectoryExponentialMechanism(epsilonInd / trajectorySize, pointCircleDomain);
        TrajectoryExponentialMechanism tempEM;
        Integer[] sectorDomainArray = BasicArrayUtil.getIncreaseIntegerNumberArray(0, 1, this.sectorSize - 1);
        GeneralizedRandomizedResponse<Integer> sectorKRR = new GeneralizedRandomizedResponse<>(epsilonD / (2 * (trajectorySize - 1)), sectorDomainArray);
        List<List<SectorAreas>> restDiscreteDirectionList = new ArrayList<>();
        List<TwoDimensionalDoublePoint> trajectoryEMInputDomain = trajectoryEM.getInputDomain();
        for (int i = 0; i < trajectorySize; ++i) {
            currentPoint = trajectory.get(i);
            if (i % 2 == flag) {
//                if (!trajectoryEMInputDomain.contains(currentPoint)) {
//                    // 处理选定区域小于轨迹范围的情况
//                    tempDisturbPoint = trajectoryEMInputDomain.get(RandomUtil.getRandomInteger(0, trajectoryEMInputDomain.size() - 1));
//                } else {
//                }
                //todo:
                tempDisturbPoint = trajectoryEM.disturb(currentPoint);
                disturbedPivotPointList.add(tempDisturbPoint);

            } else {
                restPointList.add(currentPoint);
                restDiscreteDirectionList.add(discreteDirectionList.get(i));
            }
        }
        for (int i = 0; i < restPointList.size(); ++i) {
            currentPoint = restPointList.get(i);
            tempSectorAreasList = restDiscreteDirectionList.get(i);
            int tempOriginalAreaIndex, tempDisturbedAreaIndex;
            for (SectorAreas sectorAreas : tempSectorAreasList) { // 该集合大小为1或2
                sectorAreasList.add(sectorAreas);
                tempOriginalAreaIndex = sectorAreas.getTargetPointExistingAreaIndex();
                tempDisturbedAreaIndex = sectorKRR.perturb(tempOriginalAreaIndex);
                perturbedDirectionList.add(tempDisturbedAreaIndex);
            }
            Set<TwoDimensionalDoublePoint> tempPointDomain = getPointDomain(currentPoint, pointCircleDomain, i, trajectory, sectorAreasList, perturbedDirectionList, flag);
            tempEM = new TrajectoryExponentialMechanism(epsilonRest / trajectorySize, new ArrayList<>(tempPointDomain));
            tempDisturbPoint = tempEM.disturb(currentPoint);

            disturbedRestPointList.add(tempDisturbPoint);
        }
        // Ordered by Original Index
        for (int i = 0, j = 0, k = 0; i < trajectorySize; ++i) {
            if (i % 2 == flag) {
                disturbedTrajectory.add(disturbedPivotPointList.get(j++));
            } else {
                disturbedTrajectory.add(disturbedRestPointList.get(k++));
            }
        }
        return disturbedTrajectory;
    }

    /**
     * 在候选集candidatePointList中找出距离pointA和pointB距离和最小的点
     * @param candidatePointList
     * @param pointA
     * @param pointB
     * @return
     */
    private static TwoDimensionalDoublePoint getPointWithMinimalDistanceSum(Collection<TwoDimensionalDoublePoint> candidatePointList, TwoDimensionalDoublePoint pointA, TwoDimensionalDoublePoint pointB) {
        Double minimalDistance = Double.MAX_VALUE, tempDistance;
        TwoDimensionalDoublePoint resultPoint = null;
        for (TwoDimensionalDoublePoint currentPoint : candidatePointList) {
            tempDistance = TwoDimensionalDoublePointUtils.get2NormDistance(currentPoint, pointA) + TwoDimensionalDoublePointUtils.get2NormDistance(currentPoint, pointB);
            if (tempDistance < minimalDistance) {
                minimalDistance = tempDistance;
                resultPoint = currentPoint;
            }
        }
        return resultPoint;
    }

//    private List<TwoDimensionalDoublePoint> getCandidatePointList(List<TwoDimensionalDoublePoint> disturbedTrajectoryA, List<TwoDimensionalDoublePoint> disturbedTrajectoryB) {
//        // todo: 考虑解决遍历整个点域的问题，这里暂时以所有节点代替
//        return this.pointCircleDomain;
//    }

    public List<TwoDimensionalDoublePoint> getOptimalPerturbedTrajectory(List<TwoDimensionalDoublePoint> disturbedTrajectoryA, List<TwoDimensionalDoublePoint> disturbedTrajectoryB, Collection<TwoDimensionalDoublePoint> pointCircleDomain) {
        int trajectorySize = disturbedTrajectoryA.size();
//        List<TwoDimensionalDoublePoint> candidatePointList = getCandidatePointList(disturbedTrajectoryA, disturbedTrajectoryB);
        List<TwoDimensionalDoublePoint> resultList = new ArrayList<>(trajectorySize);
        TwoDimensionalDoublePoint tempPointA, tempPointB, singleOptimalPoint;
        for (int i = 0; i < trajectorySize; ++i) {
            tempPointA = disturbedTrajectoryA.get(i);
            tempPointB = disturbedTrajectoryB.get(i);
            singleOptimalPoint = getPointWithMinimalDistanceSum(pointCircleDomain, tempPointA, tempPointB);
            resultList.add(singleOptimalPoint);
        }
        return resultList;
    }


    public List<TwoDimensionalDoublePoint> execute(final List<TwoDimensionalDoublePoint> trajectory, List<TwoDimensionalDoublePoint> pointCircleDomain, Double privacyBudget) {
        double halfBudget = privacyBudget / 2;
        // 构建每个节点到相邻轨迹节点的sectorArea信息（包含方向）
        Integer optimalSectorSize = PivotSamplingUtils.getOptimalSectorSize(this.candidateSectorSizeList, privacyBudget, trajectory);
        this.sectorSize = optimalSectorSize;
        List<List<SectorAreas>> neighboringList = PivotSamplingUtils.getNeighboringList(trajectory, optimalSectorSize);

        List<TwoDimensionalDoublePoint> perturbedTrajectoryA = independentAndPivotPerturbation(trajectory, pointCircleDomain, halfBudget, neighboringList, FlagFirstPivot);
        List<TwoDimensionalDoublePoint> perturbedTrajectoryB = independentAndPivotPerturbation(trajectory, pointCircleDomain, halfBudget, neighboringList, FlagFirstTarget);
        List<TwoDimensionalDoublePoint> optimalPerturbedTrajectory = getOptimalPerturbedTrajectory(perturbedTrajectoryA, perturbedTrajectoryB, pointCircleDomain);
        return optimalPerturbedTrajectory;
    }




}
