package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct;

import cn.edu.dll.differential_privacy.ldp.frequency_oracle.foImp.OptimizedUnaryEncoding;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.utils.TrajectoryFOUtils;

public class TrajectoryFO {
    private double totalPrivacyBudget;
    private OptimizedUnaryEncoding<Integer> lengthOUE;
    private OptimizedUnaryEncoding<Integer> innerPointOUE;
    private OptimizedUnaryEncoding<Integer> startEndPointOUE;
    public TrajectoryFO (double totalPrivacyBudget, int lengthAreaSize, int innerPointAreaSize, int startEndPointAreaSize) {
        this.totalPrivacyBudget = totalPrivacyBudget;
        double[] privacyBudgetSplitArray = TrajectoryFOUtils.getSplitPrivacyBudget(this.totalPrivacyBudget);
        this.lengthOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[0]);
        this.innerPointOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[1]);
        this.startEndPointOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[2]);
    }
}
