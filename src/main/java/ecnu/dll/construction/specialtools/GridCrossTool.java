package ecnu.dll.construction.specialtools;

import cn.edu.ecnu.struct.pair.IdentityPair;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

public abstract class GridCrossTool {

    protected Integer sizeD = null;

    protected Integer sizeB = null;
    protected TreeSet<IdentityPair<Integer>> inputCellSet = null;

    public GridCrossTool() {
    }

    public GridCrossTool(TreeSet<IdentityPair<Integer>> inputCellSet, Integer sizeB) {
        this.sizeB = sizeB;
        this.inputCellSet = inputCellSet;
        // 保证inputCellSet里面是n^2个cell
        this.sizeD = (int) Math.sqrt(inputCellSet.size());
    }

    public GridCrossTool(Integer sizeD, Integer sizeB) {
        this.sizeD = sizeD;
        this.sizeB = sizeB;
        this.inputCellSet = new TreeSet<>();
        for (int i = 0; i < this.sizeD; i++) {
            for (int j = 0; j < this.sizeD; j++) {
                this.inputCellSet.add(new IdentityPair<>(i, j));
            }
        }
    }

    public Integer getSizeD() {
        return sizeD;
    }

    public void setSizeD(Integer sizeD) {
        this.sizeD = sizeD;
    }

    public Integer getSizeB() {
        return sizeB;
    }

    public void setSizeB(Integer sizeB) {
        this.sizeB = sizeB;
    }

    public TreeSet<IdentityPair<Integer>> getInputCellSet() {
        return inputCellSet;
    }

    public void setInputCellSet(TreeSet<IdentityPair<Integer>> inputCellSet) {
        this.inputCellSet = inputCellSet;
    }

    /**
     * 判断给定的 judgePoint 是否在以 centerPoint 为中心的高概率范围内
     * @param centerPoint
     * @param sizeB
     * @param judgePoint
     * @return
     */
    protected static boolean isInHighProbabilityArea(IdentityPair<Integer> centerPoint, int sizeB, IdentityPair<Integer> judgePoint) {
        return Math.abs(centerPoint.getKey() - judgePoint.getKey()) + Math.abs(centerPoint.getValue() - judgePoint.getValue()) <= sizeB ? true : false;
    }

    protected Collection<IdentityPair<Integer>> getCrossCell(IdentityPair<Integer> centerPoint) {
        return getCrossCell(centerPoint, this.sizeD, this.sizeB);
    }



    protected static Collection<IdentityPair<Integer>> getCrossCell(IdentityPair<Integer> centerPoint, int sizeD, int sizeB) {
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
