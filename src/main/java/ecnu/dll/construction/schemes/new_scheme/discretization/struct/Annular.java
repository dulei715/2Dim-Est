package ecnu.dll.construction.schemes.new_scheme.discretization.struct;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedDiskSchemeTool;

import java.util.*;

/**
 * 记录每个扇环内部的信息
 * leftB: 扇环内侧半径
 * rightB: 扇环右侧半径
 * totalAreaSize: 扇环内所有相交的cell的面积总和（它们的权值都一样，所以可以这么记录）
 * relatedPointList: 扇环内相交的所有cell的列表
 * remainAreaMap: 记录扇环内侧半径分割出来的cell的剩余面积映射
 * totalIncludedPoint: 记录扇环完全包含的cell集合（对应的面积为1）
 * partAreaMap: 记录扇环右侧半径分割出来的cell的面积映射
 */
public class Annular {
    private Integer leftB;
    private Integer rightB;
    private Double totalAreaSize;
    private Double totalRemainAreaSize;
    private Double totalIncludedAreaSize;
    private Double totalPartAreaSize;
    private List<TwoDimensionalIntegerPoint> relatedPointList;
    // 记录各个关联节点在该annular中的面积(实际上就是remainAreaMap、totalIncludedPoint以及partAreaMap的整合)
    private List<Double> relatedPointAreaList;
    private TreeMap<TwoDimensionalIntegerPoint, Double> remainAreaMap;
    private TreeSet<TwoDimensionalIntegerPoint> totalIncludedPoint;
    private TreeMap<TwoDimensionalIntegerPoint, Double> partAreaMap;

    private Double totalProbability;

    public Annular(Integer leftB, Integer rightB, TreeMap<TwoDimensionalIntegerPoint, Double> remainAreaMap, TreeSet<TwoDimensionalIntegerPoint> totalIncludedPoint, TreeMap<TwoDimensionalIntegerPoint, Double> partAreaMap) {
        this.leftB = leftB;
        this.rightB = rightB;
        this.remainAreaMap = remainAreaMap;
        this.totalIncludedPoint = totalIncludedPoint;
        this.partAreaMap = partAreaMap;
        this.totalRemainAreaSize = this.totalIncludedAreaSize = this.totalPartAreaSize = 0.0;
        int totalMapSize = this.remainAreaMap.size() + this.totalIncludedPoint.size() + this.partAreaMap.size();
        this.relatedPointList = new ArrayList<>(totalMapSize);
        this.relatedPointAreaList = new ArrayList<>(totalMapSize);
        Map.Entry<TwoDimensionalIntegerPoint, Double> tempMapEntry;
        TwoDimensionalIntegerPoint tempPoint;
        Double tempValue;
        Iterator<Map.Entry<TwoDimensionalIntegerPoint, Double>> mapIterator = this.remainAreaMap.entrySet().iterator();
        while (mapIterator.hasNext()) {
            tempMapEntry = mapIterator.next();
            tempPoint = tempMapEntry.getKey();
            tempValue = tempMapEntry.getValue();
            this.relatedPointList.add(tempPoint);
            this.relatedPointAreaList.add(tempValue);
//            this.totalAreaSize += tempValue;
            this.totalRemainAreaSize += tempValue;
        }
        Iterator<TwoDimensionalIntegerPoint> listIterator = this.totalIncludedPoint.iterator();
        while (listIterator.hasNext()) {
            tempPoint = listIterator.next();
            this.relatedPointList.add(tempPoint);
            this.relatedPointAreaList.add(1.0);
//            this.totalAreaSize += 1;
            this.totalIncludedAreaSize += 1;
        }
        mapIterator = this.partAreaMap.entrySet().iterator();
        while (mapIterator.hasNext()) {
            tempMapEntry = mapIterator.next();
            tempPoint = tempMapEntry.getKey();
            tempValue = tempMapEntry.getValue();
            this.relatedPointList.add(tempPoint);
            this.relatedPointAreaList.add(tempValue);
//            this.totalAreaSize += tempValue;
            this.totalPartAreaSize += tempValue;
        }
        this.totalAreaSize = this.totalRemainAreaSize + this.totalIncludedAreaSize + this.totalPartAreaSize;
    }

    public static Annular getDefaultLineAnnular() {
        Integer leftB = 0;
        Integer rightB = 1;
        TreeMap<TwoDimensionalIntegerPoint, Double> remainAreaMap = new TreeMap<>();
        TreeSet<TwoDimensionalIntegerPoint> totalIncludedPoint = new TreeSet<>();
        TreeMap<TwoDimensionalIntegerPoint, Double> partAreaMap = new TreeMap<>();
        Double defaultSplitArea = DiscretizedDiskSchemeTool.get45EdgeIndexSplitArea(rightB);
        partAreaMap.put(new TwoDimensionalIntegerPoint(1,1), defaultSplitArea);
        return new Annular(leftB, rightB, remainAreaMap, totalIncludedPoint, partAreaMap);
    }



    public Double getTotalProbability() {
        return totalProbability;
    }

    public void setTotalProbability(Double totalProbability) {
        this.totalProbability = totalProbability;
    }

    public Integer getLeftB() {
        return leftB;
    }

    public Integer getRightB() {
        return rightB;
    }

    public Double getTotalAreaSize() {
        return totalAreaSize;
    }

    public Double getTotalRemainAreaSize() {
        return totalRemainAreaSize;
    }

    public Double getTotalIncludedAreaSize() {
        return totalIncludedAreaSize;
    }

    public Double getTotalPartAreaSize() {
        return totalPartAreaSize;
    }

    public List<Double> getRelatedPointAreaList() {
        return relatedPointAreaList;
    }

    public List<TwoDimensionalIntegerPoint> getRelatedPointList() {
        return relatedPointList;
    }

    public TreeMap<TwoDimensionalIntegerPoint, Double> getRemainAreaMap() {
        return remainAreaMap;
    }

    public TreeSet<TwoDimensionalIntegerPoint> getTotalIncludedPoint() {
        return totalIncludedPoint;
    }

    public TreeMap<TwoDimensionalIntegerPoint, Double> getPartAreaMap() {
        return partAreaMap;
    }
}
