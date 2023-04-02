package ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.io.read.BasicRead;
import cn.edu.ecnu.io.write.BasicWrite;
import ecnu.dll.construction.analysis.e_to_lp.Norm2GeoILocalPrivacy;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class SubsetGeoIEpsilonLocalPrivacyTable extends LocalPrivacyTable {


//    protected double[][] lPTable = null;

    public SubsetGeoIEpsilonLocalPrivacyTable(Double[] sizeDArray, Double[] budgetArray) {
//        if (sizeDArray.length != budgetArray.length) {
//            throw new RuntimeException("The input lengths for sizeDArray and budgetArray are not equal!");
//        }
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
        this.initializeUpperBoundLPTableByLPTable();
        this.initializeLowerBoundLPTableByLPTable();
    }

    public SubsetGeoIEpsilonLocalPrivacyTable(TreeMap<Double, Integer> sizeDToIndexMap, TreeMap<Double, Integer> budgetToIndexMap, double[][] lPTable) {
        this.sizeDToIndexMap = sizeDToIndexMap;
        this.budgetToIndexMap = budgetToIndexMap;
        this.lPTable = lPTable;
        this.initializeUpperBoundLPTableByLPTable();
        this.initializeLowerBoundLPTableByLPTable();
    }

    @Override
    protected void initializeLPTable(Double[] sizeDArray, Double[] budgetArray) {
        this.lPTable = new double[sizeDArray.length][budgetArray.length];
        Norm2GeoILocalPrivacy localPrivacy;
        DiscretizedSubsetExponentialGeoI subsetGeoI;
        Double inputLength = 100D;  // 任意设定的值
        try {
            // 任意参数
            subsetGeoI = new DiscretizedSubsetExponentialGeoI(budgetArray[0], inputLength / sizeDArray[0], inputLength, 0D, 0D, new TwoNormTwoDimensionalIntegerPointDistanceTor());
            for (int i = 0; i < this.lPTable.length; i++) {
                subsetGeoI.resetEpsilonAndGridLength(budgetArray[0], inputLength / sizeDArray[i]);
                localPrivacy = new Norm2GeoILocalPrivacy(subsetGeoI);
                this.lPTable[i][0] = localPrivacy.getTransformLocalPrivacyValue();
                if (sizeDArray[i] >= 10) {
                    System.out.println("finish sizeD: " + sizeDArray[i] + " and budget: " + budgetArray[0] + " for SubsetGeoI");
                }
                for (int j = 1; j < this.lPTable[0].length; j++) {
                    subsetGeoI.resetEpsilon(budgetArray[j]);
                    localPrivacy = new Norm2GeoILocalPrivacy(subsetGeoI);
                    this.lPTable[i][j] = localPrivacy.getTransformLocalPrivacyValue();
                    if (sizeDArray[i] >= 10) {
                        System.out.println("finish sizeD: " + sizeDArray[i] + " and budget: " + budgetArray[j] + " for SubsetGeoI");
                    }
                }
                System.out.println("finish sizeD: " + sizeDArray[i] + " and all budgets for SubsetGeoI");
                System.out.println();
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }




    public static SubsetGeoIEpsilonLocalPrivacyTable readTable(String inputPath) {
        TreeMap<Double, Integer> sizeDToIndexMap = new TreeMap<>(), budgetToIndexMap = new TreeMap<>();
        Double tempValue;
        BasicRead basicRead = new BasicRead();
        basicRead.startReading(inputPath);
        String[] tempStringArray;
        String[] sizeDStringArray = basicRead.readOneLine().split(basicRead.getSplitSymbol());
        for (int i = 0; i < sizeDStringArray.length; i++) {
            tempValue = Double.valueOf(sizeDStringArray[i]);
            sizeDToIndexMap.put(tempValue, i);
        }
        String[] budgetStringArray = basicRead.readOneLine().split(basicRead.getSplitSymbol());
        for (int i = 0; i < budgetStringArray.length; i++) {
            tempValue = Double.valueOf(budgetStringArray[i]);
            budgetToIndexMap.put(tempValue, i);
        }

        double[][] table = new double[sizeDStringArray.length][budgetStringArray.length];
        for (int i = 0; i < table.length; i++) {
            tempStringArray = basicRead.readOneLine().split(basicRead.getSplitSymbol());
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = Double.valueOf(tempStringArray[j]);
            }
        }

        return new SubsetGeoIEpsilonLocalPrivacyTable(sizeDToIndexMap, budgetToIndexMap, table);

    }




}
