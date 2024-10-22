package ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.basic_struct;

import cn.edu.dll.geometry.Line;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils.AnchorBasedPivotSamplingUtils;

import java.util.List;

public class SectorAreas {
    private TwoDimensionalDoublePoint pivotPoint;
    private TwoDimensionalDoublePoint targetPoint;
    private Integer sectorSize;
    private List<Line> sectorBorderLineList;
    private List<BasicPair<Integer, Integer>> areaList;

    public SectorAreas(TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, Integer sectorSize) {
        this.pivotPoint = pivotPoint;
        this.targetPoint = targetPoint;
        this.sectorSize = sectorSize;
        // 要求line从targetPoint所在上边界开始按照方位角大小排序，到最大后下一个回到最小
        this.sectorBorderLineList = AnchorBasedPivotSamplingUtils.getSeparateSectorList(pivotPoint, targetPoint, sectorSize);

    }



    public TwoDimensionalDoublePoint getPivotPoint() {
        return pivotPoint;
    }

    public TwoDimensionalDoublePoint getTargetPoint() {
        return targetPoint;
    }

    public Integer getSectorSize() {
        return sectorSize;
    }

    public List<Line> getSectorBorderLineList() {
        return sectorBorderLineList;
    }
}
