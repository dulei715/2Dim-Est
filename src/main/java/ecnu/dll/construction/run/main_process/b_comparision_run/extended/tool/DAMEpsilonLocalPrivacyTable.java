package ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.io.read.BasicRead;
import cn.edu.ecnu.io.write.BasicWrite;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.analysis.e_to_lp.Norm2DAMLocalPrivacy;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class DAMEpsilonLocalPrivacyTable {
    protected TreeMap<Double, Integer> sizeDToIndexMap = null;
    protected TreeMap<Double, Integer> budgetToIndexMap = null;


    protected double[][] lPTable = null;

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
    }

    public DAMEpsilonLocalPrivacyTable(TreeMap<Double, Integer> sizeDToIndexMap, TreeMap<Double, Integer> budgetToIndexMap, double[][] lPTable) {
        this.sizeDToIndexMap = sizeDToIndexMap;
        this.budgetToIndexMap = budgetToIndexMap;
        this.lPTable = lPTable;
    }

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
//            System.out.println("finish sizeD: " + sizeDArray[i] + " and budget: " + budgetArray[0] + " for DAM");
            for (int j = 1; j < this.lPTable[0].length; j++) {
                dam.resetEpsilon(budgetArray[j], true);
                localPrivacy = new Norm2DAMLocalPrivacy(dam);
                this.lPTable[i][j] = localPrivacy.getTransformLocalPrivacyValue();
//                System.out.println("finish sizeD: " + sizeDArray[i] + " and budget: " + budgetArray[j] + " for DAM");
            }
//            System.out.println("finish sizeD: " + sizeDArray[i] + " and all budgets for DAM");
//            System.out.println();
        }
    }

    public double[][] getlPTable() {
        return lPTable;
    }

    public void writeTable(String outputPath) {
        List tempList, sizeDList, sizeBudgetList;
        sizeDList = new ArrayList(this.sizeDToIndexMap.keySet());
        sizeBudgetList = new ArrayList(this.budgetToIndexMap.keySet());
        BasicWrite basicWrite = new BasicWrite();
        basicWrite.startWriting(outputPath);
        basicWrite.writeOneLineListData(sizeDList);
        basicWrite.writeOneLineListData(sizeBudgetList);
        Double[] tempDoubleArray;
        for (int i = 0; i < this.lPTable.length; i++) {
            tempDoubleArray = ArraysUtils.toDoubleArray(this.lPTable[i]);
            tempList = Arrays.asList(tempDoubleArray);
            basicWrite.writeOneLineListData(tempList);
        }
        basicWrite.endWriting();
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

    public double getLocalPrivacy(Double sizeD, Double epsilon) {
        Integer xIndex = this.sizeDToIndexMap.get(sizeD);
        Integer yIndex = this.budgetToIndexMap.get(epsilon);
        return this.lPTable[xIndex][yIndex];
    }


    public double getEpsilonByLocalPrivacy(Double sizeD, Double localPrivacy) {
        double[] lPRow = this.lPTable[this.sizeDToIndexMap.get(sizeD)];
        if (!ArraysUtils.isDescending(lPRow)) {
            throw new RuntimeException("The local privacy for sizeD = " + sizeD + " in the table is not Descending!");
        }
        Double[] budgetArray = this.budgetToIndexMap.keySet().toArray(new Double[0]);
        int index = ArraysUtils.binaryDescendSearch(lPRow, localPrivacy);
        if (index >= 0) {
            return budgetArray[index];
        }
        int rightIndex = -index - 1;
        if (rightIndex < 1 || rightIndex >= lPRow.length) {
            throw new RuntimeException("The local privacy is not in supported range! " + "the local privacy value should be in [" + lPRow[0]
                    + ", " + lPRow[lPRow.length-1] +  "], however, the input local privacy value is " + localPrivacy);
        }
        int leftIndex = rightIndex - 1;
        double leftLP = lPRow[leftIndex];
        double rightLP = lPRow[rightIndex];
        double leftEpsilon = budgetArray[leftIndex];
        double rightEpsilon = budgetArray[rightIndex];
        return BasicArray.getLinearTransformValue(leftLP, rightLP, localPrivacy, leftEpsilon, rightEpsilon);
    }

}