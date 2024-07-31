package ecnu.dll.construction.schemes.new_scheme.discretization.struct;


import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

import java.util.List;

/**
 * 记录扇环在一条直线上的信息。该类专门用于45方向的记录
 * leftB: 直线扇环左半径
 * rightB: 直线扇环右侧半径
 * totalAreaSize: 扇环内所有相交的cell的面积总和（它们的权值都一样，所以可以这么记录）
 * relatedPointList: 扇环内相交的所有cell的列表
 * remainAreaMap: 记录扇环内侧半径分割出来的cell的剩余面积映射
 * totalIncludedPoint: 记录扇环完全包含的cell集合（对应的面积为1）
 * partAreaMap: 记录扇环右侧半径分割出来的cell的面积映射
 *
 */
@Deprecated
public class LineAnnular {
    private Integer leftB;
    private Integer rightB;
    private Double totalAreaSize;
    private List<TwoDimensionalIntegerPoint> relatedPointList;
    private Double leftRemainAreaSize;
    private List<TwoDimensionalIntegerPoint> totalIncludedPointList;
    private Double rightPartAreaSize;

    private Double totalProbability;

    public LineAnnular(Integer leftB, Integer rightB, Double leftRemainAreaSize, List<TwoDimensionalIntegerPoint> totalIncludedPointList, Double rightPartAreaSize) {
        this.leftB = leftB;
        this.rightB = rightB;
        this.leftRemainAreaSize = leftRemainAreaSize;
        this.totalIncludedPointList = totalIncludedPointList;
        this.rightPartAreaSize = rightPartAreaSize;

    }






}
