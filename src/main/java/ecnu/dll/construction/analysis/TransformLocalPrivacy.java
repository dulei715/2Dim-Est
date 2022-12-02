package ecnu.dll.construction.analysis;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;

import java.util.List;

/**
 * 定义：局部隐私变换。
 * 1. 定义域 I 和值域 \hat{I}相同
 * 2. 输入i \in I 先映射到 i'\in I'上，再从 i' 映射到 \hat{i}\in\hat{I} 上
 */
public abstract class TransformLocalPrivacy<T> {
//    protected Integer originalSize = null;
    protected List<T> originalSetList = null;
    protected List<T> intermediateSetList = null;


    public TransformLocalPrivacy(List<T> originalSetList, List<T> intermediateSetList) {
        this.originalSetList = originalSetList;
        this.intermediateSetList = intermediateSetList;
    }


    /**
     * 给定中间结点 i', 获取所有原始结点返回该点的总概率
     * @param intermediateElement
     * @return
     */
    protected abstract double getTotalProbabilityGivenIntermediateElement(T intermediateElement);

    /**
     * 给定中间结点 i', 获取配对加权距离
     * @param intermediateElement
     * @return
     */
    protected abstract double getPairWeightedDistance(T intermediateElement);

    public double getTransformLocalPrivacyValue() {
        double value = 0;
        for (T element : this.intermediateSetList) {
            value += this.getPairWeightedDistance(element) / this.getTotalProbabilityGivenIntermediateElement(element);
        }
        return value / this.originalSetList.size();
    }


}
