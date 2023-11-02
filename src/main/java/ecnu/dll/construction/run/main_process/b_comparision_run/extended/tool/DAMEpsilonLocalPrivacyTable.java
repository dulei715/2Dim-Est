package ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool;

import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.io.read.BasicRead;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.analysis.e_to_lp.Norm2DAMLocalPrivacy;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;

import java.util.*;

public class DAMEpsilonLocalPrivacyTable extends LocalPrivacyTable {


    public DAMEpsilonLocalPrivacyTable(Double[] sizeDArray, Double[] budgetArray) {
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

    public DAMEpsilonLocalPrivacyTable(Double[] sizeDArray, Double[] budgetArray, String outputPath) {
        int index;
        this.sizeDToIndexMap = new TreeMap<>();
        this.budgetToIndexMap = new TreeMap<>();
        for (index = 0; index < sizeDArray.length; index++) {
            this.sizeDToIndexMap.put(sizeDArray[index], index);
        }
        for (index = 0; index < budgetArray.length; index++) {
            this.budgetToIndexMap.put(budgetArray[index], index);
        }
        this.initializeLPTableAndWrite(sizeDArray, budgetArray, outputPath);
        this.initializeUpperBoundLPTableByLPTable();
        this.initializeLowerBoundLPTableByLPTable();
    }

    public DAMEpsilonLocalPrivacyTable(TreeMap<Double, Integer> sizeDToIndexMap, TreeMap<Double, Integer> budgetToIndexMap, double[][] lPTable) {
        this.sizeDToIndexMap = sizeDToIndexMap;
        this.budgetToIndexMap = budgetToIndexMap;
        this.lPTable = lPTable;
        this.initializeUpperBoundLPTableByLPTable();
        this.initializeLowerBoundLPTableByLPTable();
    }

    @Override
    protected void initializeLPTable(Double[] sizeDArray, Double[] budgetArray) {
        this.lPTable = new double[sizeDArray.length][budgetArray.length];
        Norm2DAMLocalPrivacy localPrivacy;
        DiscretizedDiskScheme dam;
        Double inputLength = 100D;  // 任意设定的值
        dam = new DiscretizedDiskScheme(budgetArray[0], inputLength / sizeDArray[0], inputLength, Constant.DEFAULT_K_PARAMETER, 0D, 0D);
        for (int i = 0; i < this.lPTable.length; i++) {
            dam.resetEpsilonAndGridLength(budgetArray[0], inputLength / sizeDArray[i], true);
            localPrivacy = new Norm2DAMLocalPrivacy(dam);
            this.lPTable[i][0] = localPrivacy.getTransformLocalPrivacyValue();
            if (sizeDArray[i] >= 10) {
                System.out.println("finish sizeD: " + sizeDArray[i] + " and budget: " + budgetArray[0] + " for DAM");
            }
            for (int j = 1; j < this.lPTable[0].length; j++) {
                dam.resetEpsilon(budgetArray[j], true);
                localPrivacy = new Norm2DAMLocalPrivacy(dam);
                this.lPTable[i][j] = localPrivacy.getTransformLocalPrivacyValue();
                if (sizeDArray[i] >= 10) {
                    System.out.println("finish sizeD: " + sizeDArray[i] + " and budget: " + budgetArray[j] + " for DAM");
                }
            }
            System.out.println("finish sizeD: " + sizeDArray[i] + " and all budgets for DAM");
            System.out.println();
        }
    }

    protected void initializeLPTableAndWrite(Double[] sizeDArray, Double[] budgetArray, String outputPath) {
        this.lPTable = new double[sizeDArray.length][budgetArray.length];
        Norm2DAMLocalPrivacy localPrivacy;
        DiscretizedDiskScheme dam;
        Double inputLength = 100D;  // 任意设定的值
        writeSizeDAndSizeBudgetParameters(outputPath, Arrays.asList(sizeDArray), Arrays.asList(budgetArray));
        dam = new DiscretizedDiskScheme(budgetArray[0], inputLength / sizeDArray[0], inputLength, Constant.DEFAULT_K_PARAMETER, 0D, 0D);
        for (int i = 0; i < this.lPTable.length; i++) {
            dam.resetEpsilonAndGridLength(budgetArray[0], inputLength / sizeDArray[i], true);
            localPrivacy = new Norm2DAMLocalPrivacy(dam);
            this.lPTable[i][0] = localPrivacy.getTransformLocalPrivacyValue();
            if (sizeDArray[i] >= 10) {
                System.out.println("finish sizeD: " + sizeDArray[i] + " and budget: " + budgetArray[0] + " for DAM");
            }
            for (int j = 1; j < this.lPTable[0].length; j++) {
                dam.resetEpsilon(budgetArray[j], true);
                localPrivacy = new Norm2DAMLocalPrivacy(dam);
                this.lPTable[i][j] = localPrivacy.getTransformLocalPrivacyValue();
                if (sizeDArray[i] >= 10) {
                    System.out.println("finish sizeD: " + sizeDArray[i] + " and budget: " + budgetArray[j] + " for DAM");
                }
            }
            System.out.println("finish sizeD: " + sizeDArray[i] + " and all budgets for DAM");
            System.out.println();
            appendTableLineToFile(outputPath, ArraysUtils.toDoubleArray(this.lPTable[i]));
        }
    }

    public static DAMEpsilonLocalPrivacyTable readTable(String inputPath) {
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

        return new DAMEpsilonLocalPrivacyTable(sizeDToIndexMap, budgetToIndexMap, table);

    }

}
