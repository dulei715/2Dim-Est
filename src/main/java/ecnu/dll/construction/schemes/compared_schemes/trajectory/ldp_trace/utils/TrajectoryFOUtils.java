package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.utils;

public class TrajectoryFOUtils {
    // 设置长度相关的 epsilon_1 占总 epsilon 的 0.1
    public static final double lengthBudgetRatio = 0.1;
    // 设置内部状态相关的 epsilon_2 占 (epsilon-epsilon_1) 的 0.9
    public static final double innerCellBudgetRatio = 0.9;
    public static double[] getSplitPrivacyBudget(Double epsilon) {
        double[] result = new double[3];
        result[0] = epsilon * lengthBudgetRatio;
        result[1] = (epsilon - result[0]) * innerCellBudgetRatio;
        result[2] = epsilon - result[0] - result[1];
        return result;
    }
}
