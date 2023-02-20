package ecnu.dll.construction.analysis.lp_to_e;

import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * 针对可能SubsetGeo-I中privacy budget转成 local privacy中不单调情况进行处理
 */
public class TransformEpsilonEnhanced {

    protected TransformEpsilon transformEpsilon = null;

    private Double[][] removeNonMonotoneElementPair(double[] ascendingSortedPrivacyBudgetArray, double[] relativeLocalPrivacyArray) {
        /**
         * 反向遍历relativeLocalPrivacyArray，只保留单调递增部分
         */
        double currentMaximalLocalPrivacy = -1;
        List<Double> trimmingRelativeLocalPrivacyList = new ArrayList<>();
        List<Double> trimmingPrivacyBudgetList = new ArrayList<>();
        for (int i = relativeLocalPrivacyArray.length - 1; i >= 0; i--) {
            if (relativeLocalPrivacyArray[i] > currentMaximalLocalPrivacy) {
                trimmingPrivacyBudgetList.add(ascendingSortedPrivacyBudgetArray[i]);
                trimmingRelativeLocalPrivacyList.add(relativeLocalPrivacyArray[i]);
                currentMaximalLocalPrivacy = relativeLocalPrivacyArray[i];
            }
        }
        return new Double[][]{Lists.reverse(trimmingPrivacyBudgetList).toArray(new Double[0]), Lists.reverse(trimmingRelativeLocalPrivacyList).toArray(trimmingRelativeLocalPrivacyList.toArray(new Double[0]))};
    }

    public TransformEpsilonEnhanced(double[] ascendingSortedPrivacyBudgetArray, double[] relativeLocalPrivacyArray) {
        Double[][] eLPPairArray = removeNonMonotoneElementPair(ascendingSortedPrivacyBudgetArray, relativeLocalPrivacyArray);
        this.transformEpsilon = new TransformEpsilon(eLPPairArray[0], eLPPairArray[1]);
    }

    public double getLocalPrivacyByPrivacyBudget(double privacyBudget) {
        return this.transformEpsilon.getLocalPrivacyByPrivacyBudget(privacyBudget);
    }

    public double getPrivacyBudgetByLocalPrivacy(double localPrivacy) {
        return this.transformEpsilon.getPrivacyBudgetByLocalPrivacy(localPrivacy);
    }

    @Deprecated
    public List<TwoDimensionalDoublePoint> getEffectivePoint() {
        return this.transformEpsilon.getParameterPointList();
    }



}
