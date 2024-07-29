package ecnu.dll.construction.newscheme.discretization.tool;


import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
    public static boolean isInHighProbabilityArea(TwoDimensionalIntegerPoint centerPoint, int sizeB, TwoDimensionalIntegerPoint judgePoint) {
        return Math.abs(centerPoint.getXIndex() - judgePoint.getXIndex()) + Math.abs(centerPoint.getYIndex() - judgePoint.getYIndex()) <= sizeB ? true : false;
    }

    public static Collection<TwoDimensionalIntegerPoint> getCrossCellCollectionWithinInputCells(TwoDimensionalIntegerPoint centerPoint, int sizeD, int sizeB) {
        HashSet<TwoDimensionalIntegerPoint> resultSet = new HashSet<>();
        TwoDimensionalIntegerPoint judgePoint;
        for (int i = 0; i < sizeD; i++) {
            for (int j = 0; j < sizeD; j++) {
                judgePoint = new TwoDimensionalIntegerPoint(i, j);
                if (isInHighProbabilityArea(centerPoint, sizeB, judgePoint)){
                    resultSet.add(judgePoint);
                }
            }
        }
        return resultSet;
    }


    public static List<TwoDimensionalIntegerPoint> getNoiseIntegerPointTypeList(Integer sizeD, Integer sizeB) {
        Integer positiveBound = sizeD + sizeB;
        // 记录右上边界线在x轴和y轴的截距
        Integer positiveIntercept = sizeB + 2 * sizeD - 2;
        // 记录左上和右下边界线分别在x轴和y轴上的截距(的绝对值)
        Integer negativeIntercept = sizeB + sizeD - 1;

        List<TwoDimensionalIntegerPoint> noiseIntegerPointTypeList = new ArrayList<>();
        for (int i = -sizeB; i < positiveBound; i++) {
            for (int j = -sizeB; j < positiveBound; j++) {
                if (i + j + sizeB >= 0 && i + j - positiveIntercept <= 0 && i - j + negativeIntercept >= 0 && i - j - negativeIntercept <= 0) {
                    noiseIntegerPointTypeList.add(new TwoDimensionalIntegerPoint(i, j));
                }
            }
        }
        return noiseIntegerPointTypeList;
    }

    public static Double[] getProbabilityPQ(Integer sizeD, Integer sizeB, Double epsilon) {
        Integer[] constValues = new Integer[5];
        constValues[0] = 2*sizeB*(sizeB+1)+1;
        constValues[1] = sizeD * (sizeD + 4*sizeB) - 4*sizeB - 1;
        constValues[2] = constValues[0] + constValues[1];
        constValues[4] = sizeD * (1 + sizeB);
        constValues[3] = constValues[2] - constValues[4];

        Double[] result = new Double[2];
        result[1] = 1 / (constValues[0] * Math.exp(epsilon) + constValues[1]);
        result[0] = result[1] * Math.exp(epsilon);
        return result;
    }




































}
