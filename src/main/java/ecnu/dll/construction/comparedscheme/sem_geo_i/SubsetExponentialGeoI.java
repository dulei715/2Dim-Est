package ecnu.dll.construction.comparedscheme.sem_geo_i;

import Jama.Matrix;
import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.basic.BasicCalculation;
import cn.edu.ecnu.basic.BasicSearch;
import cn.edu.ecnu.basic.RandomUtil;
import cn.edu.ecnu.basic.cumulate.CumulativeFunction;
import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.collection.ListUtils;
import cn.edu.ecnu.collection.SetUtils;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceAble;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.exponential_mechanism.utility.UtilityFunction;
import cn.edu.ecnu.differential_privacy.ldp.consistent.Normalization;
import cn.edu.ecnu.math.MathUtils;

import java.util.*;

public class SubsetExponentialGeoI<X> {
    private Double epsilon;
    private Integer setSizeK;
    private Double omega;
//    private UtilityFunction<X, R> utilityFunction;

    private List<X> inputElementList;
//    private List<R> outputElementList;

    private Double differentElementsDistanceSum;

    private Double[] massArray;

    // dis
    private Double[] dis;

    // disset. disset[i][j]记录距离X_i距离为dis[j]的元素集合(记录其索引)
    private List<Integer>[][] disset;

    private Integer[][] discount;

    private DistanceTor<X> distanceTor;

    public SubsetExponentialGeoI(Double epsilon, List<X> inputElementList, DistanceTor<X> distanceTor) throws IllegalAccessException, InstantiationException {
        this.epsilon = epsilon;
//        this.setSizeK = setSizeK;
        this.inputElementList = inputElementList;
//        this.outputElementList = outputElementList;
        this.distanceTor = distanceTor;

        this.massArray = new Double[this.inputElementList.size()];
        this.initializeTotalDistanceAndElementGivenDistance();
        this.setSetSizeKWithMeanEpsilon();
        this.normalizer();
    }

    public void resetEpsilon(Double epsilon) {
       this.epsilon = epsilon;
       this.setSetSizeKWithMeanEpsilon();
       this.normalizer();
    }

    public Double getOmega() {
        return omega;
    }

    public List<X> getInputElementList() {
        return inputElementList;
    }

    public Double[] getMassArray() {
        return massArray;
    }

    public Integer getSetSizeK() {
        return setSizeK;
    }

    private int getDissetElementListLength(int xIndex, int yIndex) {
        List<Integer> tempList = this.disset[xIndex][yIndex];
        if (tempList == null) {
            return 0;
        }
        return tempList.size();
    }

    private void setSetSizeKWithMeanEpsilon() {
        int m = this.inputElementList.size();
        if (m < 2) {
            this.setSizeK = 1;
        } else {
            double epsilonMean = this.differentElementsDistanceSum * this.epsilon / (m * (m - 1));
            this.setSizeK = (int) Math.ceil(m / (Math.exp(epsilonMean) + 1));
        }
    }

    private void addElement(TreeMap<Double, List<Integer>>[] treeMapArray, int mainElementIndex, int judgeElementIndex, Double distance) {
        List<Integer> tempList;
        if (!treeMapArray[mainElementIndex].containsKey(distance)) {
            tempList = new ArrayList<>();
            treeMapArray[mainElementIndex].put(distance, tempList);
        } else {
            tempList = treeMapArray[mainElementIndex].get(distance);
        }
        tempList.add(judgeElementIndex);
    }

    private void initializeTotalDistanceAndElementGivenDistance() throws InstantiationException, IllegalAccessException {
        // 每个X有一个treeMap，用于记录距自己不同距离对应的其他X的集合。该数组下标代表X在inputList中的下标
        TreeMap<Double, List<Integer>>[] treeMapArray = new TreeMap[inputElementList.size()];
        BasicArray.setToEmptyGroup(treeMapArray, TreeMap.class);
        int inputListSize = this.inputElementList.size();

        X elementA, elementB;
        Double tempDistance;
        int tempDistanceIndex;
        List<Integer> tempList;
        TreeMap<Double, List<Integer>> tempTreeMap;

        TreeSet<Double> distanceSet = new TreeSet<>();
        this.differentElementsDistanceSum = 0.0;
        for (int i = 0; i < inputListSize; i++) {
            elementA = this.inputElementList.get(i);
            // 处理 j=i 情况
            distanceSet.add(0.0);
            this.addElement(treeMapArray, i, i, 0.0);
            // 处理 j>i 情况
            for (int j = i + 1; j < inputListSize; j++) {
                elementB = this.inputElementList.get(j);
//                tempDistance = elementA.getDistance(elementB);
                tempDistance = this.distanceTor.getDistance(elementA, elementB);
                // 将每个distance添加到distanceSet里去重并排序
                distanceSet.add(tempDistance);
                // 构建treeMapArray
                this.addElement(treeMapArray, i, j, tempDistance);
                this.addElement(treeMapArray, j, i, tempDistance);

                this.differentElementsDistanceSum += tempDistance * 2;

            }

            // 按照字典顺序排序
            for (Map.Entry<Double, List<Integer>> entry : treeMapArray[i].entrySet()) {
                ListUtils.quickSort(entry.getValue());
            }
//            ListUtils.quickSort(treeMapArray[i].get(tempDistance));
        }

        // 构建dis (此处已经排好序)
        this.dis = SetUtils.toArray(distanceSet, Double.class);

        // 构建disset (元素可能为空)
        this.disset = new ArrayList[inputListSize][this.dis.length];
        for (int i = 0; i < inputListSize; i++) {
            tempTreeMap = treeMapArray[i];
            for (Map.Entry<Double, List<Integer>> entry : tempTreeMap.entrySet()) {
                tempDistance = entry.getKey();
                tempDistanceIndex = Arrays.binarySearch(this.dis, tempDistance);
                tempList = entry.getValue();
                this.disset[i][tempDistanceIndex] = tempList;
            }
        }
        // 初始化discount
        this.discount = new Integer[this.disset.length][this.dis.length];
    }


    /**
     * 计算 Omega
     */
    public void normalizer() {
        int res, former;
        this.omega = 0.0;

        int m = this.disset.length;
        int z = this.disset[0].length;
        int res_k;
//        double[] this.massArray = new double[m];
        double radixValue = Math.exp(-this.epsilon);
        Double tempResult, tempValue;
        X elemA, elemB;

        BasicArray.setIntArrayTo(this.discount, 0);
        for (int i = 0; i < m; i++) {
            res = m;
            former = MathUtils.getBinomialResult(m, this.setSizeK);
            for (int j = 0; j < z; j++) {
//                res -= this.disset[i][j].size();
                res -= this.getDissetElementListLength(i, j);
                res_k = MathUtils.getBinomialResult(res, this.setSizeK);
                this.discount[i][j] = former - res_k;
                former = res_k;
            }
        }


        for (int i = 0; i < m; i++) {
            this.massArray[i] = BasicCalculation.getInnerProduct(this.discount[i], this.dis, radixValue);
        }

        // 计算Omega
        for (int i = 0; i < m; i++) {
            elemA = this.inputElementList.get(i);
            for (int j = 0; j < m; j++) {
                elemB = this.inputElementList.get(j);
//                tempValue = Math.exp(this.epsilon * elemA.getDistance(elemB));
                tempValue = Math.exp(this.epsilon * this.distanceTor.getDistance(elemA, elemB));
                tempResult = (tempValue * this.massArray[i] - this.massArray[j]) / (tempValue - 1);
                if (this.omega < tempResult){
                    this.omega = tempResult;
                }
            }
        }

    }

   private Double[] getDisprobs(int i) {
        Double[] result = new Double[dis.length];
        for (int j = 0; j < result.length; j++) {
            result[j] = this.discount[i][j] * Math.exp(-this.epsilon*this.dis[j]) / this.omega;
        }
        return result;
   }

    /**
     *
     * @param i 输入元素在List里的坐标
     * @return 选出的元素的坐标集合
     */
    public Set<Integer> sampler(int i) {
        Double r = RandomUtil.getRandomDouble(0.0, 1.0);
        if (r >= this.massArray[i] / this.omega) {
            return null;
        }
        Set<Integer> resultSet = new HashSet<>();
        // 分别记录选取的等于最小距离的元素的坐标集合和大于最小距离的元素的坐标集合
        List<Integer> resultPartList;

        // 这三步是根据r获取选取到的距离id
        Double[] disprobs = this.getDisprobs(i);
        Double[] cumulativeDisprobs = CumulativeFunction.getCumulativeDistribution(disprobs);
        int disid = BasicSearch.binaryLatterSearchWithMinimalIndex(cumulativeDisprobs, r);

        List<Integer> smallestDistanceElementsIndexList = this.disset[i][disid];
        List<Integer> allLargerDistanceElementsIndexList = ListUtils.combine(this.disset[i], disid + 1, this.dis.length - 1);
        int sizeeq = smallestDistanceElementsIndexList.size();
        int sizegt = allLargerDistanceElementsIndexList.size();

        Integer[] partCounts = new Integer[this.setSizeK];
        for (int j = 0; j < this.setSizeK; j++) {
            partCounts[j] = MathUtils.getBinomialResult(sizeeq, j + 1) * MathUtils.getBinomialResult(sizegt, this.setSizeK - j - 1);
        }
        // eqnum-1代表选中的partCounts的随机元素，同时eqnum表示选择sizeeq集合中的元素的个数（多少个最小距离）
        Integer eqnum = RandomUtil.getRandomIndexGivenStatisticPoint(partCounts) + 1;

//        if (eqnum > sizeeq) {
//            System.out.println("some wrong!");
//        }

        List<Integer> positionList = RandomUtil.getRandomIntegerArrayWithoutRepeat(0, sizeeq - 1, eqnum);
        resultPartList = BasicArray.getElementListInGivenIndexes(smallestDistanceElementsIndexList, positionList);
        resultSet.addAll(resultPartList);

//        if (sizegt < this.setSizeK - eqnum) {
//            System.out.println("some wrong!");
//        }
//        if (sizegt  == 0) {
//            System.out.println("sizegt=0");
//        }
        positionList = RandomUtil.getRandomIntegerArrayWithoutRepeat(0, sizegt - 1, this.setSizeK - eqnum);
        resultPartList = BasicArray.getElementListInGivenIndexes(allLargerDistanceElementsIndexList, positionList);
        resultSet.addAll(resultPartList);

        return resultSet;

    }


    /**
     *
     * @param reportedSubset 用户提交的隐私处理过的report（每个report是一个子集，子集元素是原元素在inputList中的索引）
     * @return
     */
    public double[] estimator(List<Set<Integer>> reportedSubset) {
        if (this.setSizeK == 1 && this.inputElementList.size() == 1) {
            return new double[]{1.0};
        }
        // 计算h矩阵
        int reportedSize = reportedSubset.size();
        double unitValue = 1.0 / reportedSize;
        int m = this.inputElementList.size();
        int z = this.dis.length;
        int res, former, tempBinomialValue, tempElementIndex;
        double[][] h = new double[m][m];
        BasicArray.setDoubleArrayToZero(h);
        Matrix noiseStatisticMatrix, matrixH, resultMatrix;
        double partmass;
        double[][] noiseStatistic = new double[1][m];
        BasicArray.setDoubleArrayToZero(noiseStatistic);
        for (int i = 0; i < m; i++) {
            res = m - 1;
            former = MathUtils.getBinomialResult(res, this.setSizeK - 1);
            h[i][i] = former / this.omega;
            partmass = 0.0;
            for (int a = 0; a < z - 1; a++) {
//                res -= disset[i][a].size();
                res -= this.getDissetElementListLength(i, a);
                tempBinomialValue = MathUtils.getBinomialResult(res, this.setSizeK - 1);
                partmass += (former - tempBinomialValue) * Math.exp(-this.epsilon*this.dis[a]) / this.omega;
                former = tempBinomialValue;
//                for (int j = 0; j < this.disset[i][a+1].size(); j++) {
                for (int j = 0; j < this.getDissetElementListLength(i, a+1); j++) {
                    tempElementIndex = this.disset[i][a+1].get(j);
                    h[i][tempElementIndex] = partmass + former * Math.exp(-this.epsilon * this.dis[a+1]) / this.omega;
                }
            }
        }
        // 估计频率分布
        for (Set<Integer> subsetElement : reportedSubset) {
            if (subsetElement == null) {
                continue;
            }
            for (Integer index : subsetElement) {
                noiseStatistic[0][index] += unitValue;
            }
        }
        noiseStatisticMatrix = new Matrix(noiseStatistic);
        matrixH = new Matrix(h);
//        inverseMatrixH = matrixH.inverse();
        resultMatrix = noiseStatisticMatrix.times(matrixH.inverse());
        double[] originalResult = resultMatrix.getArray()[0];
        return Normalization.normMul(originalResult);
    }

    public static void main(String[] args) {

    }



}
