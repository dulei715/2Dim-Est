package ecnu.dll.construction.newscheme.discretization.state;

public class DiscretizedDiskPlaneState {
    // 包含内部cell状态
    public static final Integer INNER_STATE = 0;
    // 弧线相交的cell且该cell的中心店在弧线之外
    public static final Integer EDGE_STATE = 1;
    // 弧线之外的cell
    public static final Integer OUTER_STATE = 2;
}
