package ecnu.dll.construction.comparedscheme.sem_geo_i;


import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.differential_privacy.cdp.basic_struct.DistanceAble;
import cn.edu.dll.differential_privacy.cdp.exponential_mechanism.SimpleLDPExponentialMechanism;
import cn.edu.dll.differential_privacy.cdp.exponential_mechanism.utility.UtilityFunction;

import java.util.List;

@Deprecated
public class PaddedExponentialMechanism<X extends DistanceAble<X>, R> extends SimpleLDPExponentialMechanism<X, R> {

    protected Double omega = null;

    public PaddedExponentialMechanism(UtilityFunction utilityFunction, Double epsilon, List inputList, List outputList) {
        super(utilityFunction, epsilon, inputList, outputList);
    }

    protected void setOmega() {

    }

    @Override
    protected void setProbabilityConstant() {
        // exp(ε/△u)
        super.probabilityConstant = Math.exp(super.epsilon / super.deltaU);
    }


    @Override
    public R disturb(X inputElement) {
        int inputElementIndex = BasicArrayUtil.getFirstFindValueIndex(super.inputList, inputElement);
        Double[] cumulationProbabilityArray = new Double[]{0.0, super.moleculeSum[inputElementIndex] / this.omega};
        Integer index = RandomUtil.getRandomIndexGivenCumulatedPoint(cumulationProbabilityArray);
        if (index == 0) {
            return super.disturbByGivingInputIndex(inputElementIndex);
        }
        return null;
    }
}
