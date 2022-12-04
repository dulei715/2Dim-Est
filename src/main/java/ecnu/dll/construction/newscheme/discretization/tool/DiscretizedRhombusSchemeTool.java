package ecnu.dll.construction.newscheme.discretization.tool;

import cn.edu.ecnu.struct.pair.IdentityPair;

import java.util.Collection;
import java.util.HashSet;

public class DiscretizedRhombusSchemeTool {
    /**
     *
     * @param epsilon
     * @param sizeD
     * @return
     */
    public static Integer getOptimalSizeBOfRhombusScheme(double epsilon, int sizeD) {
        double mA = Math.exp(epsilon) - 1 - epsilon;
        double mB = 1 - (1 - epsilon) * Math.exp(epsilon);
//        return (int)Math.ceil((2*mB+Math.sqrt(4*mB*mB+2*Math.exp(epsilon)*mA*mB))/(2*Math.exp(epsilon)*mA) * sizeD);
        return (int)Math.floor((2*mB+Math.sqrt(4*mB*mB+2*Math.exp(epsilon)*mA*mB))/(2*Math.exp(epsilon)*mA) * sizeD);
    }

    /**
     * 判断给定的 judgePoint 是否在以 centerPoint 为中心的高概率范围内
     * @param centerPoint
     * @param sizeB
     * @param judgePoint
     * @return
     */
    public static boolean isInHighProbabilityArea(IdentityPair<Integer> centerPoint, int sizeB, IdentityPair<Integer> judgePoint) {
        return Math.abs(centerPoint.getKey() - judgePoint.getKey()) + Math.abs(centerPoint.getValue() - judgePoint.getValue()) <= sizeB ? true : false;
    }

    public static Collection<IdentityPair<Integer>> getCrossCellCollectionWithinInputCells(IdentityPair<Integer> centerPoint, int sizeD, int sizeB) {
        HashSet<IdentityPair<Integer>> resultSet = new HashSet<>();
        IdentityPair<Integer> judgePoint;
        for (int i = 0; i < sizeD; i++) {
            for (int j = 0; j < sizeD; j++) {
                judgePoint = new IdentityPair<>(i, j);
                if (isInHighProbabilityArea(centerPoint, sizeB, judgePoint)){
                    resultSet.add(judgePoint);
                }
            }
        }
        return resultSet;
    }
}
