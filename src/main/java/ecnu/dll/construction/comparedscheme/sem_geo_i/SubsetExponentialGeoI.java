package ecnu.dll.construction.comparedscheme.sem_geo_i;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceAble;
import cn.edu.ecnu.differential_privacy.cdp.exponential_mechanism.utility.UtilityFunction;

import java.util.List;

public class SubsetExponentialGeoI<X extends DistanceAble<X>, R> {
    private UtilityFunction<X, R> utilityFunction;
    private List<X> inputElementList;
    private List<R> outputElementList;

    private PaddedExponentialMechanism<X, R> pEM;



}
