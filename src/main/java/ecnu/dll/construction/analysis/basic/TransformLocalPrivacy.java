package ecnu.dll.construction.analysis.basic;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;

import java.util.List;

/**
 * 定义：局部隐私变换。
 * 1. 定义域 I 和值域 \hat{I}相同
 * 2. 输入i \in I 先映射到 i'\in I'上，再从 i' 映射到 \hat{i}\in\hat{I} 上
 */
public abstract class TransformLocalPrivacy<T, S> {

    protected List<T> originalSetList = null;
    protected List<S> intermediateSetList = null;

//    public TransformLocalPrivacy() {
//    }

    public TransformLocalPrivacy(List<T> originalSetList, List<S> intermediateSetList) {
        this.originalSetList = originalSetList;
        this.intermediateSetList = intermediateSetList;
    }

//    public TransformLocalPrivacy(List<T> originalSetList) {
//        this.originalSetList = originalSetList;
//        this.intermediateSetList = generateIntermediateSetList();
//    }

    /**
     * 给定中间结点 i', 获取所有原始结点返回该点的总概率
     * @param intermediateElement
     * @return
     */
    protected abstract double getTotalProbabilityGivenIntermediateElement(S intermediateElement);

    /**
     * 给定中间结点 i', 获取配对加权距离
     * @param intermediateElement
     * @return
     */
    protected abstract double getPairWeightedDistance(S intermediateElement);

//    protected abstract List<S> generateIntermediateSetList();

    public double getTransformLocalPrivacyValue() {
        double value = 0;
        for (S element : this.intermediateSetList) {
            value += this.getPairWeightedDistance(element) / this.getTotalProbabilityGivenIntermediateElement(element);
        }
        return value / this.originalSetList.size();
    }


}
