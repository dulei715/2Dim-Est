package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.collection.CollectionTools;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePointUtils;
import ecnu.dll.construction.schemes.basic_schemes.square_wave.continued.DoubleSquareWave;
import ecnu.dll.construction.schemes.basic_schemes.square_wave.continued.SimpleSquareWave;
import ecnu.dll.construction.schemes.basic_schemes.square_wave.utils.SquareWaveUtils;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct.SectorAreas;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct.TrajectoryExponentialMechanism;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils.PivotSamplingUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnchorBasedPivotSampling extends PivotSampling{
    protected final List<TwoDimensionalDoublePoint> totalBasicPointList;
    public AnchorBasedPivotSampling(List<TwoDimensionalDoublePoint> totalTrajectoryPointList) {
        super();
        this.totalBasicPointList = totalTrajectoryPointList;
    }

    public TwoDimensionalDoublePoint getAnchor(List<TwoDimensionalDoublePoint> trajectory) {
        int trajectorySize = trajectory.size();
        Double averageX = 0D, averageY = 0D;
        TwoDimensionalDoublePoint tempPoint;
        for (int i = 0; i < trajectorySize; ++i) {
            tempPoint = trajectory.get(i);
            averageX += tempPoint.getXIndex();
            averageY += tempPoint.getYIndex();
        }
        averageX /= trajectorySize;
        averageY /= trajectorySize;
        return new TwoDimensionalDoublePoint(averageX, averageY);
    }

    protected TwoDimensionalDoublePoint toExistNearestPoint(TwoDimensionalDoublePoint originalAnchor) {
        // todo: 暂时用遍历所有点的方法
        return TwoDimensionalDoublePointUtils.getNearest2NormDistancePointInGivenCollection(originalAnchor, this.totalBasicPointList).getKey();
    }

    protected TwoDimensionalDoublePoint getPerturbAnchor(TwoDimensionalDoublePoint realAnchor, Double privacyBudget) {
        TrajectoryExponentialMechanism trajectoryExponentialMechanism = new TrajectoryExponentialMechanism(privacyBudget, this.totalBasicPointList);
        return trajectoryExponentialMechanism.disturb(realAnchor);
    }

    /**
     * 根据原有的最长距离，获得扰动且无偏估计后的最长距离
     * @param originalLongestDistance
     * @param privacyBudget
     * @param maxDistance   所有从扰动的Anchor点到所有基本点的距离最大值
     * @return
     */
    protected Double getEstimatedLongestDistance(Double originalLongestDistance, Double privacyBudget, Double maxDistance, Double squareWaveB) {
        Double radius = originalLongestDistance / maxDistance;
        DoubleSquareWave<TwoDimensionalDoublePoint> squareWaveMechanism = new SimpleSquareWave(privacyBudget, squareWaveB);
        Double noiseRadius = squareWaveMechanism.getNoiseValue(radius);
        return (noiseRadius + squareWaveB) * maxDistance / (2 * squareWaveB + 1);
    }

    protected Double getEta(List<TwoDimensionalDoublePoint> pointRange, Double privacyBudget, TwoDimensionalDoublePoint disturbedAnchor, Double squareWaveB) {
        int innerPointSize = pointRange.size();
        int outerPointSize = this.totalBasicPointList.size() - innerPointSize;
        Double innerWeightedSum = 0D, outerWeightedSum = 0D;
        double expEpsilon = Math.exp(privacyBudget);
        double q = expEpsilon / (2 * squareWaveB * expEpsilon + 1);
        for (TwoDimensionalDoublePoint currentPoint : this.totalBasicPointList) {
            if (pointRange.contains(currentPoint)) {
                innerWeightedSum += q * TwoDimensionalDoublePointUtils.get2NormDistance(disturbedAnchor, currentPoint);
            } else {
                outerWeightedSum += (1 - q) * TwoDimensionalDoublePointUtils.get2NormDistance(disturbedAnchor, currentPoint);
            }
        }
        return (innerWeightedSum + outerWeightedSum) / (q * innerPointSize + (1 - q) * outerPointSize);
    }

    protected Double getEta(List<TwoDimensionalDoublePoint> totalPoint, TwoDimensionalDoublePoint disturbedAnchor) {
        Double result = 0D;
        for (TwoDimensionalDoublePoint tempPoint : totalPoint) {
            result += TwoDimensionalDoublePointUtils.get2NormDistance(tempPoint, disturbedAnchor);
        }
        return result /= totalPoint.size();
    }

    /**
     *
     * @param estimatedLongestDistance
     * @param squareWaveB
     * @param maxDistance   所有从扰动的Anchor点到所有基本点的距离最大值
     * @return
     */
    protected Double getCalibratedRadius(TwoDimensionalDoublePoint perturbedAnchor, Double estimatedLongestDistance, Double squareWaveB, Double maxDistance, Double calibrateEpsilon) {
        Double[] testValueArray = BasicArrayUtil.getIncreaseDoubleNumberArray(0.1, 0.1, 1);
        Double tempLeftValue, tempRightValue, estimationRadius, eta, ksi, beta;
        Double[] leftRightValue;
        List<TwoDimensionalDoublePoint> pointRange;
        Set<Double> valueRegion = new HashSet<>();
        for (Double vk : testValueArray) {
            tempLeftValue = vk - squareWaveB;
            tempRightValue = vk + squareWaveB;
            estimationRadius = (2 * squareWaveB + 1) * estimatedLongestDistance / maxDistance - squareWaveB;
            if (estimationRadius >= tempLeftValue && estimationRadius <= tempRightValue) {
                valueRegion.add(vk);
            }
        }
        if (valueRegion.isEmpty()) {
            eta = getEta(totalBasicPointList, perturbedAnchor);
        } else {
            leftRightValue = CollectionTools.getMinimalAndMaximalValue(valueRegion);
            pointRange = TwoDimensionalDoublePointUtils.getPointSubCollectionInGiven2NormDistanceRange(this.totalBasicPointList, perturbedAnchor, (2 * squareWaveB + 1) / maxDistance, 0D, leftRightValue[0], leftRightValue[1]);
            eta = getEta(pointRange, calibrateEpsilon, perturbedAnchor, squareWaveB);
        }
        if (estimatedLongestDistance <= eta) {
            beta = (eta - estimatedLongestDistance) / eta;
        } else {
            beta = (estimatedLongestDistance - eta) / (maxDistance - eta);
        }
        ksi = (eta - estimatedLongestDistance) / (1 - Math.exp(-beta / 2));
        return estimatedLongestDistance + ksi * Math.exp(-calibrateEpsilon);
    }


    public List<TwoDimensionalDoublePoint> restrictTrajectoryRegion(List<TwoDimensionalDoublePoint> trajectory, Double regionPrivacyBudget) {
        Double anchorBudget = regionPrivacyBudget * 0.25;
        Double longestDistanceBudget = regionPrivacyBudget - anchorBudget;
        Double squareWaveB = SquareWaveUtils.getOptimalB(longestDistanceBudget);
        // 根据轨迹获取原始anchor，并将原始anchor映射为最邻近的基本点（给定的所有点），称为 anchorPoint
        TwoDimensionalDoublePoint anchorPoint = toExistNearestPoint(getAnchor(trajectory));
        // 对 anchorPoint 进行扰动，获得 perturbedAnchor
        TwoDimensionalDoublePoint perturbedAnchor = getPerturbAnchor(anchorPoint, anchorBudget);


        Double trajectoryLongestDistance = TwoDimensionalDoublePointUtils.getLongestDistance(perturbedAnchor, trajectory).getValue();
        Double maximalDistance =  TwoDimensionalDoublePointUtils.getLongestDistance(perturbedAnchor, this.totalBasicPointList).getValue();
        Double estimatedLongestDistance = getEstimatedLongestDistance(trajectoryLongestDistance, longestDistanceBudget, maximalDistance, squareWaveB);
        Double finalRadius = getCalibratedRadius(perturbedAnchor, estimatedLongestDistance, squareWaveB, maximalDistance, longestDistanceBudget);
        if (finalRadius <= 0) {
            // 处理非正半径的情况。直接选所有
            return this.totalBasicPointList;
        }
        return TwoDimensionalDoublePointUtils.getPointSubCollectionInGiven2NormDistanceRange(this.totalBasicPointList, perturbedAnchor, 1.0, 0.0, 0.0, finalRadius);
    }

    @Override
    public List<TwoDimensionalDoublePoint> execute(List<TwoDimensionalDoublePoint> trajectory, List<TwoDimensionalDoublePoint> pointCircleDomain, Double privacyBudget) {
        throw new RuntimeException("Please use another function with the same name!");
    }

    public List<TwoDimensionalDoublePoint> execute(List<TwoDimensionalDoublePoint> trajectory, Double privacyBudget) {
        Integer optimalSectorSize = PivotSamplingUtils.getOptimalSectorSize(this.candidateSectorSizeList, privacyBudget, trajectory);
        this.sectorSize = optimalSectorSize;
        Double epsilonRegion = 0.25 * privacyBudget;
        Double epsilonPivot = privacyBudget - epsilonRegion;

        List<List<SectorAreas>> neighboringList = PivotSamplingUtils.getNeighboringList(trajectory, optimalSectorSize);

        List<TwoDimensionalDoublePoint> regionA = restrictTrajectoryRegion(trajectory, epsilonRegion / 2);
        List<TwoDimensionalDoublePoint> regionB = restrictTrajectoryRegion(trajectory, epsilonRegion / 2);
        List<TwoDimensionalDoublePoint> perturbedTrajectoryA = super.independentAndPivotPerturbation(trajectory, regionA, epsilonPivot / 2, neighboringList, FlagFirstPivot);
        List<TwoDimensionalDoublePoint> perturbedTrajectoryB = super.independentAndPivotPerturbation(trajectory, regionB, epsilonPivot / 2, neighboringList, FlagFirstTarget);
        Set<TwoDimensionalDoublePoint> regionSet = new HashSet<>();
        regionSet.addAll(regionA);
        regionSet.addAll(regionB);
        List<TwoDimensionalDoublePoint> optimalPerturbedTrajectory = getOptimalPerturbedTrajectory(perturbedTrajectoryA, perturbedTrajectoryB, regionSet);
        return optimalPerturbedTrajectory;
    }

}
