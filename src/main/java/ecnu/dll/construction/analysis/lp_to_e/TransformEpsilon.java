package ecnu.dll.construction.analysis.lp_to_e;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.constant_values.ConstantValues;

import java.util.Arrays;

public class TransformEpsilon {
    /**
     * 一. 初始化
     *      1. 初始化给定范围 R 以及给定精度 δ 的所有 LP 和 ε 的对应关系
     *      2. 并将它们分段
     * 二. 查询给定的 LP 对应的 ε （尽可能大原则 + 线性拟合原则） （断崖部分处进行线性拟合）
     *      1. 给定 LP 的值
     *      2. 计算出对应的 ε
     */


    // 升序排列
    protected double[] ascendingSortedPrivacyBudgetArray;
    // 降序排列
    protected double[] relativeLocalPrivacyArray;

    public TransformEpsilon(double[] ascendingSortedPrivacyBudgetArray, double[] relativeLocalPrivacyArray) {
        if (!ArraysUtils.isAscending(ascendingSortedPrivacyBudgetArray)) {
            throw new RuntimeException("The input privacy budget array is not ascending!");
        }
        this.ascendingSortedPrivacyBudgetArray = ascendingSortedPrivacyBudgetArray;
        if (!ArraysUtils.isDescending(relativeLocalPrivacyArray)) {
            throw new RuntimeException("The input relative local privacy array is not descending!");
        }
        this.relativeLocalPrivacyArray = relativeLocalPrivacyArray;
    }

    public TransformEpsilon(Double[] ascendingSortedPrivacyBudgetArray, Double[] relativeLocalPrivacyArray) {
        this(ArraysUtils.toDoubleArray(ascendingSortedPrivacyBudgetArray), ArraysUtils.toDoubleArray(relativeLocalPrivacyArray));
    }


    public double getLocalPrivacyByPrivacyBudget(double privacyBudget) {
        int index = Arrays.binarySearch(this.ascendingSortedPrivacyBudgetArray, privacyBudget);
        if (index >= 0) {
            return this.relativeLocalPrivacyArray[index];
        }
        int rightIndex = -index - 1;
        if (rightIndex < 1 || rightIndex >= this.ascendingSortedPrivacyBudgetArray.length) {
            throw new RuntimeException("The privacy budget is not in supported range!");
        }
        int leftIndex = rightIndex - 1;
        double leftEpsilon = this.ascendingSortedPrivacyBudgetArray[leftIndex];
        double rightEpsilon = this.ascendingSortedPrivacyBudgetArray[rightIndex];
        double leftLP = this.relativeLocalPrivacyArray[leftIndex];
        double rightLP = this.relativeLocalPrivacyArray[rightIndex];
        return BasicArray.getLinearTransformValue(leftEpsilon, rightEpsilon, privacyBudget, leftLP, rightLP);
    }

    public double getPrivacyBudgetByLocalPrivacy(double localPrivacy) {
        int index = ArraysUtils.binaryDescendSearch(this.relativeLocalPrivacyArray, localPrivacy);
        if (index >= 0) {
            return this.ascendingSortedPrivacyBudgetArray[index];
        }
        int rightIndex = -index - 1;
        if (rightIndex < 1 || rightIndex >= this.relativeLocalPrivacyArray.length) {
            throw new RuntimeException("The local privacy is not in supported range! " + "the local privacy value should be in [" + this.relativeLocalPrivacyArray[0]
                    + ", " + this.relativeLocalPrivacyArray[this.relativeLocalPrivacyArray.length-1] +  "] and the input local privacy value is " + localPrivacy);
        }
        int leftIndex = rightIndex - 1;
        double leftLP = this.relativeLocalPrivacyArray[leftIndex];
        double rightLP = this.relativeLocalPrivacyArray[rightIndex];
        double leftEpsilon = this.ascendingSortedPrivacyBudgetArray[leftIndex];
        double rightEpsilon = this.ascendingSortedPrivacyBudgetArray[rightIndex];
        return BasicArray.getLinearTransformValue(leftLP, rightLP, localPrivacy, leftEpsilon, rightEpsilon);
    }

    public static void main(String[] args) {

    }
}
