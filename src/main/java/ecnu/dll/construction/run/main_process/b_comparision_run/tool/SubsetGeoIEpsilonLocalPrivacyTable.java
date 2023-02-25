package ecnu.dll.construction.run.main_process.b_comparision_run.tool;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import ecnu.dll.construction.analysis.e_to_lp.Norm2GeoILocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.basic.TransformLocalPrivacy;
import ecnu.dll.construction.analysis.lp_to_e.SubsetGeoITransformEpsilon;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;

import java.util.TreeMap;

public class SubsetGeoIEpsilonLocalPrivacyTable {
    protected TreeMap<Integer, Integer> sizeDToIndexMap = null;
    protected TreeMap<Double, Integer> budgetToIndexMap = null;

//    protected TransformLocalPrivacy<T, S> transformLocalPrivacy = null;
//    protected DiscretizedSubsetExponentialGeoI subsetGeoI = null;

    protected double[][] lPTable = null;

    public SubsetGeoIEpsilonLocalPrivacyTable(Integer[] sizeDArray, Double[] budgetArray) {
        if (sizeDArray.length != budgetArray.length) {
            throw new RuntimeException("The input lengths for sizeDArray and budgetArray are not equal!");
        }
//        this.transformLocalPrivacy = transformLocalPrivacy;
        int index;
        this.sizeDToIndexMap = new TreeMap<>();
        this.budgetToIndexMap = new TreeMap<>();
        for (index = 0; index < sizeDArray.length; index++) {
            this.sizeDToIndexMap.put(sizeDArray[index], index);
        }
        for (index = 0; index < budgetArray.length; index++) {
            this.budgetToIndexMap.put(budgetArray[index], index);
        }
        this.initializeLPTable(sizeDArray, budgetArray);
    }

    protected void initializeLPTable(Integer[] sizeDArray, Double[] budgetArray) {
        this.lPTable = new double[sizeDArray.length][budgetArray.length];
        Integer tempSizeD;
        Double tempBudget;
        Norm2GeoILocalPrivacy localPrivacy;
        DiscretizedSubsetExponentialGeoI subsetGeoI;
        try {
            subsetGeoI = new DiscretizedSubsetExponentialGeoI(budgetArray[0], 1D, sizeDArray[0]*1.0, 0D, 0D, new TwoNormTwoDimensionalIntegerPointDistanceTor());
            for (int i = 0; i < this.lPTable.length; i++) {
                tempSizeD = sizeDArray[i];
                for (int j = 0; j < this.lPTable[0].length; j++) {
                    tempBudget = budgetArray[j];
//                    subsetGeoI.resetEpsilon();
                }
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
