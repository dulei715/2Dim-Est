package ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.io.write.BasicWrite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public abstract class LocalPrivacyTable {
    protected double[][] lPTable = null;
    protected double[][] lowerBoundLPTable = null;
    protected double[][] upperBoundLPTable = null;

    protected TreeMap<Double, Integer> sizeDToIndexMap = null;
    protected TreeMap<Double, Integer> budgetToIndexMap = null;

    protected abstract void initializeLPTable(Double[] sizeDArray, Double[] budgetArray);
    protected void initializeLowerBoundLPTableByLPTable() {
        Double currentMaxLineValue, tempValue;
        for (int i = 0; i < this.lPTable.length; i++) {
            currentMaxLineValue = Double.MAX_VALUE;
            for (int j = 0; j < this.lPTable[0].length; j++) {
                tempValue = this.lPTable[i][j];
                if (tempValue < currentMaxLineValue) {
                    currentMaxLineValue = this.lowerBoundLPTable[i][j] = tempValue;
                } else {
                    this.lowerBoundLPTable[i][j] = currentMaxLineValue;
                }
            }
        }
    }
    protected void initializeUpperBoundLPTableByLPTable(){
        Double currentMinLineValue, tempValue;
        for (int i = 0; i < this.lPTable.length; i++) {
            currentMinLineValue = -1D;
            for (int j = this.lPTable[0].length - 1; j >= 0; j--) {
                tempValue = this.lPTable[i][j];
                if (tempValue > currentMinLineValue) {
                    currentMinLineValue = this.lowerBoundLPTable[i][j] = tempValue;
                } else {
                    this.lowerBoundLPTable[i][j] = currentMinLineValue;
                }
            }
        }
    }


    protected double getEpsilonByLocalPrivacyGivenTable(double[][] table, Double sizeD, Double localPrivacy) {
        double[] lPRow = table[this.sizeDToIndexMap.get(sizeD)];
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

    public double getEpsilonByLocalPrivacy(Double sizeD, Double localPrivacy) {
        return getEpsilonByLocalPrivacyGivenTable(this.lPTable, sizeD, localPrivacy);
    }

    public double getEpsilonByUpperBoundLocalPrivacy(Double sizeD, Double localPrivacy) {
        return getEpsilonByLocalPrivacyGivenTable(this.upperBoundLPTable, sizeD, localPrivacy);
    }

    public double getEpsilonByLowerBoundLocalPrivacy(Double sizeD, Double localPrivacy) {
        return getEpsilonByLocalPrivacyGivenTable(this.lowerBoundLPTable, sizeD, localPrivacy);
    }

    protected void writeTable(String outputPath, double[][] table) {
        List tempList, sizeDList, sizeBudgetList;
        sizeDList = new ArrayList(this.sizeDToIndexMap.keySet());
        sizeBudgetList = new ArrayList(this.budgetToIndexMap.keySet());
        BasicWrite basicWrite = new BasicWrite();
        basicWrite.startWriting(outputPath);
        basicWrite.writeOneLineListData(sizeDList);
        basicWrite.writeOneLineListData(sizeBudgetList);
        Double[] tempDoubleArray;
        for (int i = 0; i < table.length; i++) {
            tempDoubleArray = ArraysUtils.toDoubleArray(table[i]);
            tempList = Arrays.asList(tempDoubleArray);
            basicWrite.writeOneLineListData(tempList);
        }
        basicWrite.endWriting();
    }

    public void writeLPTable(String outputPath) {
        writeTable(outputPath, this.lPTable);
    }

    public void writeUpperBoundLPTable(String outputPath) {
        writeTable(outputPath, this.upperBoundLPTable);
    }

    public void writeLowerBoundLPTable(String outputPath) {
        writeTable(outputPath, this.lowerBoundLPTable);
    }

    public static void writeSizeDAndSizeBudgetParameters(String outputPath, List<Double> sizeDParameter, List<Double> sizeBudgetParameter) {
        BasicWrite basicWrite = new BasicWrite();
        basicWrite.startWriting(outputPath);
        basicWrite.writeOneLineListData(sizeDParameter);
        basicWrite.writeOneLineListData(sizeBudgetParameter);
        basicWrite.endWriting();
    }

    public static void appendTableLineToFile(String outputPath, Double[] lineValues) {
        BasicWrite basicWrite = new BasicWrite();
        basicWrite.startWriting(outputPath, true);
        basicWrite.writeOneLineListData(Arrays.asList(lineValues));
        basicWrite.endWriting();
    }


    public double getLocalPrivacy(Double sizeD, Double epsilon) {
        Integer xIndex = this.sizeDToIndexMap.get(sizeD);
        Integer yIndex = this.budgetToIndexMap.get(epsilon);
        return this.lPTable[xIndex][yIndex];
    }

    public double[][] getlPTable() {
        return lPTable;
    }


}
