package ecnu.dll.construction.comparedscheme.sem_geo_i;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.basic.RandomUtil;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceAble;
import cn.edu.ecnu.differential_privacy.cdp.exponential_mechanism.SimpleLDPExponentialMechanism;
import cn.edu.ecnu.differential_privacy.cdp.exponential_mechanism.utility.UtilityFunction;

import java.util.List;


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
        int inputElementIndex = BasicArray.getFirstFindValueIndex(super.inputList, inputElement);
        Double[] cumulationProbabilityArray = new Double[]{0.0, super.moleculeSum[inputElementIndex] / this.omega};
        Integer index = RandomUtil.getRandomIndexGivenCumulatedPoint(cumulationProbabilityArray);
        if (index == 0) {
            return super.disturbByGivingInputIndex(inputElementIndex);
        }
        return null;
    }
}
