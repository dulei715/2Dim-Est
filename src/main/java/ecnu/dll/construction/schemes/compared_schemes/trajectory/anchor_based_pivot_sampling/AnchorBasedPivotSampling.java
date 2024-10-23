package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling;

import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePointUtils;
import ecnu.dll.construction.schemes.basic_schemes.square_wave.continued.DoubleSquareWave;
import ecnu.dll.construction.schemes.basic_schemes.square_wave.continued.SimpleSquareWave;
import ecnu.dll.construction.schemes.basic_schemes.square_wave.continued.IntegerSquareWave;
import ecnu.dll.construction.schemes.basic_schemes.square_wave.utils.SquareWaveUtils;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct.TrajectoryExponentialMechanism;

import java.util.List;

public class AnchorBasedPivotSampling extends PivotSampling{
    public AnchorBasedPivotSampling(List<TwoDimensionalDoublePoint> totalTrajectoryPointList, Integer sectorSize) {
        super(totalTrajectoryPointList, sectorSize);
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

    protected TwoDimensionalDoublePoint toExitNearestPoint(TwoDimensionalDoublePoint originalAnchor) {
        // todo: 暂时用遍历所有点的方法
        return TwoDimensionalDoublePointUtils.getNearest2NormDistancePointInGivenCollection(originalAnchor, this.totalTrajectoryPointList).getKey();
    }

    protected TwoDimensionalDoublePoint getPerturbAnchor(TwoDimensionalDoublePoint realAnchor, Double privacyBudget) {
        TrajectoryExponentialMechanism trajectoryExponentialMechanism = new TrajectoryExponentialMechanism(privacyBudget, this.totalTrajectoryPointList);
        return trajectoryExponentialMechanism.disturb(realAnchor);
    }

    protected Double getPerturbLongestDistance(Double originalLongestDistance, Double privacyBudget, Double maxDistance) {
        Double radius = originalLongestDistance / maxDistance;
        Double b = SquareWaveUtils.getOptimalB(privacyBudget);
        DoubleSquareWave<TwoDimensionalDoublePoint> squareWaveMechanism = new SimpleSquareWave(privacyBudget, b);
        Double noiseRadius = squareWaveMechanism.getNoiseValue(radius);
        return (noiseRadius + b) * maxDistance / (2 * b + 1);
    }


    public List<TwoDimensionalDoublePoint> restrictTrajectoryRegion(List<TwoDimensionalDoublePoint> trajectory, Double regionPrivacyBudget) {
        Double anchorBudget = regionPrivacyBudget * 0.25;
        Double longestDistanceBudget = regionPrivacyBudget - anchorBudget;
        TwoDimensionalDoublePoint anchorPoint = toExitNearestPoint(getAnchor(trajectory));
        TwoDimensionalDoublePoint perturbedAnchor = getPerturbAnchor(anchorPoint, anchorBudget);
        Double trajectoryLongestDistance = TwoDimensionalDoublePointUtils.getLongestDistance(perturbedAnchor, trajectory).getValue();
        Double maximalDistance =  TwoDimensionalDoublePointUtils.getLongestDistance(perturbedAnchor, this.totalTrajectoryPointList).getValue();
        Double perturbedLongestDistance = getPerturbLongestDistance(trajectoryLongestDistance, longestDistanceBudget, maximalDistance);
        // todo:xxx
    }

    @Override
    public List<TwoDimensionalDoublePoint> execute(List<TwoDimensionalDoublePoint> trajectory, Double privacyBudget) {
        Double epsilonRegion = 0.25 * privacyBudget;
        Double epsilonPivot = privacyBudget - epsilonRegion;

        return super.execute(trajectory, privacyBudget);
    }
}
