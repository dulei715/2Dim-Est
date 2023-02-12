package ecnu.dll.construction.newscheme.discretization.state;

public class DiscretizedDiskPlaneState {
    /**
     * 该状态是为了区分高概率和低概率部分
     * 1. 高概率部分包含内部点，坐标轴方向边，45方向内部边
     * 2. 低概率部分包含外部点
     * 3. 混合概率部分包含外部点和45方向边界点
     */
    // 包含内部cell状态(高概率部分)
    public static final Integer INNER_STATE = 0;
    // 弧线相交的cell且该cell的中心点在弧线之外(混合概率部分)
    public static final Integer EDGE_STATE = 1;
    // 弧线之外的cell(低概率部分)
    public static final Integer OUTER_STATE = 2;
    // 弧线和45线交界cell
    public static final Integer EDGE45_State = 3;
}
