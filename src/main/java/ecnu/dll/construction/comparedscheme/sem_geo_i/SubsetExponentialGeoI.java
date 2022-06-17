package ecnu.dll.construction.comparedscheme.sem_geo_i;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.basic.BasicCalculation;
import cn.edu.ecnu.collection.ListUtils;
import cn.edu.ecnu.collection.SetUtils;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceAble;
import cn.edu.ecnu.differential_privacy.cdp.exponential_mechanism.utility.UtilityFunction;
import cn.edu.ecnu.math.MathUtils;

import java.util.*;

public class SubsetExponentialGeoI<X extends DistanceAble<X>, R> {
    private Double epsilon;
    private UtilityFunction<X, R> utilityFunction;
    private List<X> inputElementList;
    private List<R> outputElementList;

    private PaddedExponentialMechanism<X, R> pEM;

    // dis
    private Double[] dis;

    // disset. disset[i][j]记录距离X_i距离为dis[j]的元素集合(记录其索引)
    private List<Integer>[][] disset;

    private Integer[][] discount;

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
        for (int i = 0; i < inputListSize; i++) {
            elementA = this.inputElementList.get(i);
            for (int j = i; j < inputListSize; j++) {
                elementB = this.inputElementList.get(j);
                tempDistance = elementA.getDistance(elementB);
                // 将每个distance添加到distanceSet里去重并排序
                distanceSet.add(tempDistance);

                // 投建treeMapArray
                this.addElement(treeMapArray, i, j, tempDistance);
                this.addElement(treeMapArray, j, i, tempDistance);
                ListUtils.quickSort(treeMapArray[i].get(tempDistance));
            }
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
     * @return Omega
     */
    public Double normalizer(int k) {
        int res, former;
        Double omega = 0.0;
        int m = this.disset.length;
        int z = this.disset[0].length;
        int res_k;
        double[] mass = new double[m];
        double radixValue = Math.exp(-this.epsilon);
        Double tempResult, tempValue;
        X elemA, elemB;
        
        BasicArray.setIntArrayTo(this.discount, 0);
        for (int i = 0; i < m; i++) {
            res = m;
            former = MathUtils.getBinaomialResult(m, k);
            for (int j = 0; j < z; j++) {
                res -= this.disset[i][j].size();
                res_k = MathUtils.getBinaomialResult(res, k);
                this.discount[i][j] = former - res_k;
                former = res_k;
            }
        }


        for (int i = 0; i < m; i++) {
            mass[i] = BasicCalculation.getInnerProduct(this.discount[i], this.dis, radixValue);
        }

        // 计算Omega
        for (int i = 0; i < m; i++) {
            elemA = this.inputElementList.get(i);
            for (int j = 0; j < m; j++) {
                elemB = this.inputElementList.get(j);
                tempValue = Math.exp(this.epsilon * elemA.getDistance(elemB));
                tempResult = (tempValue * mass[i] - mass[j]) / (tempValue - 1);
                if (omega < tempResult){
                    omega = tempResult;
                }
            }
        }
        return omega;

    }


}
