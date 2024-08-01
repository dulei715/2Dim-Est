package ecnu.dll.construction.schemes.add_schemes.hdg;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.NumberUtil;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.basic_struct.HashFunctionResponsePair;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.foImp.OptimizedIntegerLocalHashing;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll.construction.extend_tools.StatisticUtil;
import ecnu.dll.construction.structs.AttributeIndexPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HDG<T> {

    protected Double epsilon;
    // 这里List中的每个元素是一个三元组(属性标识，最小取值，最大取值)表示一个属性
    protected List<PureTriple<T, Double, Double>> dataDomainList;
//    protected Double gridCellLength;
    protected Integer userSize;

    protected Integer hashFunctionArraySize;

    protected Integer oneDimSize, twoDimSize;
    protected Double alpha1, alpha2;
    protected Integer g1, g2;
    // 代表一维属性组合，用于统计每个一维属性有多少个用户
    protected HashMap<Integer, Integer> oneDimAttributeUserSizeMap;
    protected HashMap<Integer, List<HashFunctionResponsePair<Integer>>> oneDimResponseRecordMap;
    protected HashMap<Integer, List<Double>> oneDimAttributeStatisticMap;
    // 代表二维属性组合，用于统计每个二维属性有多少用户
    protected HashMap<AttributeIndexPair, Integer> twoDimAttributeUserSizeMap;
    protected HashMap<AttributeIndexPair, List<HashFunctionResponsePair<Integer>>> twoDimResponseMap;
    protected HashMap<AttributeIndexPair, List<Double>> twoDimAttributeStatisticMap;
    protected OptimizedIntegerLocalHashing oneDimFO;

    protected OptimizedIntegerLocalHashing twoDimFO;
    protected Map<Integer, Object> userToGroupMap;

    public HDG(Double epsilon, List<PureTriple<T, Double, Double>> dataDomainList, Integer userSize) {
        this.epsilon = epsilon;
        this.dataDomainList = dataDomainList;
        this.userSize = userSize;
        initialize();
    }
    protected void initialize() {
        this.hashFunctionArraySize = HDGUtils.getHashFunctionSizeFromConfig();
        this.oneDimSize = this.dataDomainList.size();
        this.twoDimSize = this.oneDimSize * (this.oneDimSize - 1) / 2;
        Double[] alphaParameterArray = HDGUtils.getAlphaParameterArray();
        this.alpha1 = alphaParameterArray[0];
        this.alpha2 = alphaParameterArray[1];
        this.userToGroupMap = HDGUtils.sampleGroupForUsers(this.userSize, this.dataDomainList.size());
        HashMap[] countMapArray = HDGUtils.countUserForEachGroup(this.userToGroupMap);
        this.oneDimAttributeUserSizeMap = countMapArray[0];
        this.twoDimAttributeUserSizeMap = countMapArray[1];
        this.oneDimResponseRecordMap = HDGUtils.getOneDimInitializedResponseMap(this.oneDimSize);
        this.twoDimResponseMap = HDGUtils.getTwoDimInitializedResponseMap(this.oneDimSize);
    }



    public Object getGroupIndex(Integer userID) {
        return this.userToGroupMap.get(userID);
    }

    /**
     *  Phase 1: Constructing Grids
     *      (1) initializeGrid: 计算最优grid大小，初始化一维和二维FO
     *      (2) perturbAndRecord: 记录每个用户的记录，并将其扰动
     *      (3) 统计每个组经过OLH后的无偏结果
     */
    protected void initializeGrid() {
        Double[] optimalGOneAndGTwo = HDGUtils.getOptimalGOneAndGTwo(this.userSize, this.epsilon, this.dataDomainList.size(), this.alpha1, this.alpha2);
        this.g1 = (int) Math.round(optimalGOneAndGTwo[0]);
        this.g2 = (int) Math.round(optimalGOneAndGTwo[1]);

        Integer[] unionInputOneDimDomainIndexArray = BasicArrayUtil.getIncreaseIntegerNumberArray(0, 1, this.g1 - 1);
        Integer[] unionInputTwoDimDomainIndexArray = BasicArrayUtil.getIncreaseIntegerNumberArray(0, 1, this.g2 * this.g2 - 1);
        this.oneDimFO = new OptimizedIntegerLocalHashing(this.epsilon, unionInputOneDimDomainIndexArray, this.hashFunctionArraySize);
        this.twoDimFO = new OptimizedIntegerLocalHashing(this.epsilon, unionInputTwoDimDomainIndexArray, this.hashFunctionArraySize);

    }


    public void perturbAndRecord(Integer userID, List<Double> rawUserData) {
        Object groupIndex = this.getGroupIndex(userID);
        if (groupIndex instanceof Integer) {
            Integer oneDimGroupIndex = (Integer) groupIndex;
            Double userEffectiveData = rawUserData.get(oneDimGroupIndex);
            Double leftBound = this.dataDomainList.get(oneDimGroupIndex).getValue();
            Double rightBound = this.dataDomainList.get(oneDimGroupIndex).getTag();
            Double gridLength = (rightBound - leftBound) / this.g1;
            int realIndex = HDGUtils.getGridIndex(userEffectiveData, leftBound, gridLength);
            HashFunctionResponsePair<Integer> perturbElement = this.oneDimFO.perturb(realIndex);
            this.oneDimResponseRecordMap.get(oneDimGroupIndex).add(perturbElement);
        } else if (groupIndex instanceof AttributeIndexPair) {
            AttributeIndexPair twoDimGroupIndex = (AttributeIndexPair) groupIndex;
            Integer indexA = twoDimGroupIndex.getIndexA();
            Integer indexB = twoDimGroupIndex.getIndexB();
            Double[] userEffectiveDataArray = new Double[]{
                    rawUserData.get(indexA),
                    rawUserData.get(indexB)
            };
            Double[] leftBoundArray = new Double[] {this.dataDomainList.get(indexA).getValue(), this.dataDomainList.get(indexB).getValue()};
            Double[] gridLengthArray = new Double[] {
                    (this.dataDomainList.get(indexA).getTag() - leftBoundArray[0]) / this.g2,
                    (this.dataDomainList.get(indexB).getTag() - leftBoundArray[1]) / this.g2
            };
            BasicPair<Integer, Integer> realIndex = HDGUtils.getGridIndex(userEffectiveDataArray, leftBoundArray, gridLengthArray);
            Integer transformedOneDimIndex = HDGUtils.toOneDimGridIndex(realIndex, this.g2);
            HashFunctionResponsePair<Integer> perturbElement = this.twoDimFO.perturb(transformedOneDimIndex);
            this.twoDimResponseMap.get(twoDimGroupIndex).add(perturbElement);
        } else {
            throw new RuntimeException("Not support group index!");
        }
    }

    public void statisticEachGroup() {
        Integer oneDimAttributeIndex;
        AttributeIndexPair twoDimAttributeIndex;
        List<HashFunctionResponsePair<Integer>> tempResponsePairList;
        List<Integer> tempNoiseCountList;
        List<Double> tempStatisticList;

        this.oneDimAttributeStatisticMap = new HashMap<>();
        for (Map.Entry<Integer, List<HashFunctionResponsePair<Integer>>> record : this.oneDimResponseRecordMap.entrySet()) {
            oneDimAttributeIndex = record.getKey();
            tempResponsePairList = record.getValue();
            tempNoiseCountList = this.oneDimFO.countNoiseCountOfAllRawValues(tempResponsePairList);
            tempStatisticList = this.oneDimFO.aggregateTotal(tempNoiseCountList, this.oneDimAttributeUserSizeMap.get(oneDimAttributeIndex));
            this.oneDimAttributeStatisticMap.put(oneDimAttributeIndex, tempStatisticList);
        }
        this.twoDimAttributeStatisticMap = new HashMap<>();
        for (Map.Entry<AttributeIndexPair, List<HashFunctionResponsePair<Integer>>> record : this.twoDimResponseMap.entrySet()) {
            twoDimAttributeIndex = record.getKey();
            tempResponsePairList = record.getValue();
            tempNoiseCountList = this.twoDimFO.countNoiseCountOfAllRawValues(tempResponsePairList);
            tempStatisticList = this.twoDimFO.aggregateTotal(tempNoiseCountList, this.twoDimAttributeUserSizeMap.get(twoDimAttributeIndex));
            this.twoDimAttributeStatisticMap.put(twoDimAttributeIndex, tempStatisticList);
        }
    }

    public static void main(String[] args) {
        Double epsilon = 0.5;
        List<PureTriple<String, Double, Double>> dataDomainList = new ArrayList<>();
        Integer userSize = 100;

        dataDomainList.add(new PureTriple<>("loc_a", 0.1, 1.0));
        dataDomainList.add(new PureTriple<>("loc_b", 0.5, 1.4));

        List<List<Double>> userDataList = new ArrayList<>();
        List<Double> tempUserData;
        for (int i = 0; i < userSize; i++) {
            tempUserData = new ArrayList<>();
            tempUserData.add(NumberUtil.roundFormat(RandomUtil.getRandomDouble(0.1, 1.0), 3));
            tempUserData.add(NumberUtil.roundFormat(RandomUtil.getRandomDouble(0.5, 1.4), 3));
            userDataList.add(tempUserData);
        }

        MyPrint.showList(userDataList, ConstantValues.LINE_SPLIT);


        HDG hdg = new HDG(epsilon, dataDomainList, userSize);
        hdg.initializeGrid();
        for (int userID = 0; userID < userSize; userID++) {
            hdg.perturbAndRecord(userID, userDataList.get(userID));
        }
        hdg.statisticEachGroup();

    }




}
