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

    public double[][] getLowerBoundLPTable() {
        return lowerBoundLPTable;
    }

    public double[][] getUpperBoundLPTable() {
        return upperBoundLPTable;
    }

    protected abstract void initializeLPTable(Double[] sizeDArray, Double[] budgetArray);

    /**
     * 平滑local privacy带来的非单调情况，这里取下界
     * 对于突增的部分，先设为不增，然后遇到下一个递减的值后对中间不增的部分进行线性拟合。
     * （这会出现中间突增被“线性拟合”但末尾突增“不增”处理）
     */
    protected void initializeLowerBoundLPTableByLPTable() {
        Double currentMaxLineValueLeft, currentMaxLineValueRight, tempValue;
        int leftIndex, rightIndex;
        this.lowerBoundLPTable = new double[this.lPTable.length][this.lPTable[0].length];
        int indexDiffer;
        for (int i = 0; i < this.lPTable.length; i++) {
            currentMaxLineValueLeft = currentMaxLineValueRight = Double.MAX_VALUE;
            leftIndex = rightIndex = -1;
            for (int j = 0; j < this.lPTable[0].length; j++) {
                tempValue = this.lPTable[i][j];
                ++rightIndex;
                if (tempValue < currentMaxLineValueRight) {
                    currentMaxLineValueRight = this.lowerBoundLPTable[i][j] = tempValue;
                    indexDiffer = rightIndex - leftIndex;
                    if (indexDiffer > 1) {
                        // 中间部分进行线性拟合
                        BasicArray.fillLinearTransformValue(currentMaxLineValueLeft, currentMaxLineValueRight, this.lowerBoundLPTable[i], leftIndex + 1, rightIndex - 1, false);
                    }
                    currentMaxLineValueLeft = currentMaxLineValueRight;
                    leftIndex = rightIndex;
                } else {
                    this.lowerBoundLPTable[i][j] = currentMaxLineValueLeft;
                }
            }
        }
    }

    /**
     * 平滑local privacy带来的非单调情况，这里取上界
     */
    protected void initializeUpperBoundLPTableByLPTable(){
        Double currentMinLineValueLeft, currentMinLineValueRight, tempValue;
        int leftIndex, rightIndex;
        this.upperBoundLPTable = new double[this.lPTable.length][this.lPTable[0].length];
        int indexDiffer;
        for (int i = 0; i < this.lPTable.length; i++) {
            currentMinLineValueRight = currentMinLineValueLeft = -1D;
            rightIndex = leftIndex = this.lPTable[0].length;
            for (int j = this.lPTable[0].length - 1; j >= 0; j--) {
                tempValue = this.lPTable[i][j];
                --leftIndex;
                if (tempValue > currentMinLineValueLeft) {
                    currentMinLineValueLeft = this.upperBoundLPTable[i][j] = tempValue;
                    indexDiffer = rightIndex - leftIndex;
                    if (indexDiffer > 1) {
//                        int indexDiffer2 = indexDiffer - 2;
//                        if (indexDiffer2 < 0 || indexDiffer2 < 1 && true) {
//                            System.out.println("wrong!");
//                        }
                        BasicArray.fillLinearTransformValue(currentMinLineValueLeft, currentMinLineValueRight, this.upperBoundLPTable[i], leftIndex + 1, rightIndex - 1, false);
                    }
                    currentMinLineValueRight = currentMinLineValueLeft;
                    rightIndex = leftIndex;
                } else {
                    this.upperBoundLPTable[i][j] = currentMinLineValueRight;
                }
            }
        }
    }
//    protected void initializeUpperBoundLPTableByLPTable(){
//        Double currentMinLineValue, tempValue;
//        this.upperBoundLPTable = new double[this.lPTable.length][this.lPTable[0].length];
//        for (int i = 0; i < this.lPTable.length; i++) {
//            currentMinLineValue = -1D;
//            for (int j = this.lPTable[0].length - 1; j >= 0; j--) {
//                tempValue = this.lPTable[i][j];
//                if (tempValue > currentMinLineValue) {
//                    currentMinLineValue = this.upperBoundLPTable[i][j] = tempValue;
//                } else {
//                    this.upperBoundLPTable[i][j] = currentMinLineValue;
//                }
//            }
//        }
//    }

    public List<Double> getSizeDList() {
        return new ArrayList<>(this.sizeDToIndexMap.keySet());
    }

    public List<Double> getBudgetList() {
        return new ArrayList<>(this.budgetToIndexMap.keySet());
    }

    protected double getMaxLocalPrivacyValueGivenTable(double[][] table, Double sizeD) {
        double[] lPRow = table[this.sizeDToIndexMap.get(sizeD)];
        if (!ArraysUtils.isDescending(lPRow)) {
            throw new RuntimeException("The local privacy for sizeD = " + sizeD + " in the table is not Descending!");
        }
        return lPRow[0];
    }
    protected double getMinLocalPrivacyValueGivenTable(double[][] table, Double sizeD) {
        double[] lPRow = table[this.sizeDToIndexMap.get(sizeD)];
        if (!ArraysUtils.isDescending(lPRow)) {
            throw new RuntimeException("The local privacy for sizeD = " + sizeD + " in the table is not Descending!");
        }
        return lPRow[lPRow.length-1];
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

    protected double getLocalPrivacyByEpsilonGivenTable(double[][] table, Double sizeD, Double epsilon) {
        double[] lPRow = table[this.sizeDToIndexMap.get(sizeD)];
        Double[] budgetArray = this.budgetToIndexMap.keySet().toArray(new Double[0]);
        int index = Arrays.binarySearch(budgetArray, epsilon);
        if (index >= 0) {
            return lPRow[index];
        }
        int rightIndex = -index - 1;
        if (rightIndex < 1 || rightIndex >= budgetArray.length) {
            throw new RuntimeException("The privacy budget is not in supported range! " + "the privacy budget (epsilon) should be in [" + budgetArray[0]
                    + ", " + budgetArray[budgetArray.length-1] + "], however, the input privacy budget (epsilon) is " + epsilon);
        }
        int leftIndex = rightIndex - 1;
        double leftEpsilon = budgetArray[leftIndex];
        double rightEpsilon = budgetArray[rightIndex];
        double leftLP = lPRow[leftIndex];
        double rightLP = lPRow[rightIndex];
        return BasicArray.getLinearTransformValue(leftEpsilon, rightEpsilon, epsilon, leftLP, rightLP);
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

    public double getLocalPrivacyByEpsilon(Double sizeD, Double epsilon) {
        return getLocalPrivacyByEpsilonGivenTable(this.lPTable, sizeD, epsilon);
    }

    public double getUpperBoundLocalPrivacyByEpsilon(Double sizeD, Double epsilon) {
        return getLocalPrivacyByEpsilonGivenTable(this.upperBoundLPTable, sizeD, epsilon);
    }

    public double getLowerBoundLocalPrivacyByEpsilon(Double sizeD, Double epsilon) {
        return getLocalPrivacyByEpsilonGivenTable(this.lowerBoundLPTable, sizeD, epsilon);
    }

    public double getMaxLocalPrivacyValue(Double sizeD) {
        return getMaxLocalPrivacyValueGivenTable(this.lPTable, sizeD);
    }

    public double getMinLocalPrivacyValue(Double sizeD) {
        return getMinLocalPrivacyValueGivenTable(this.lPTable, sizeD);
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


//    public double getLocalPrivacyByEpsilon(Double sizeD, Double epsilon) {
//        Integer xIndex = this.sizeDToIndexMap.get(sizeD);
//        Integer yIndex = this.budgetToIndexMap.get(epsilon);
//        return this.lPTable[xIndex][yIndex];
//    }

    public double[][] getlPTable() {
        return lPTable;
    }


}
