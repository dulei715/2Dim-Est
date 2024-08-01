package ecnu.dll.construction.schemes.add_schemes.hdg;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.structs.AttributeIndexPair;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class HDGUtils {
    public static Double getGridOne(int n1, double epsilon, double alpha1, int m1) {
        double valueA = n1 * Math.pow(Math.exp(epsilon) - 1, 2) * Math.pow(alpha1, 2);
        double valueB = 2 * m1 * Math.exp(epsilon);
        return Math.pow(valueA / valueB, 1.0/3);
    }

    public static Double getGridTwo(int n2, double epsilon, double alpha2, int m2) {
        double innerA = Math.sqrt(n2 / (m2 * Math.exp(epsilon)));
        double innerB = 2 * alpha2 * (Math.exp(epsilon) - 1) * innerA;
        return Math.sqrt(innerB);
    }

    public static Double[] getAlphaParameterArray() {
        Document document = Constant.xmlConfigure.getDocument();
        Element paramsElement = (Element)document.selectNodes("//mechanisms/mechanism[@name='HDG']/params").get(0);
        String alpha1Str = ((Element) paramsElement.selectNodes("param[@name='alpha1']").get(0)).getTextTrim();
        String alpha2Str = ((Element) paramsElement.selectNodes("param[@name='alpha2']").get(0)).getTextTrim();
        Double[] result = new Double[]{Double.valueOf(alpha1Str), Double.valueOf(alpha2Str)};
        return result;
    }

    /**
     * 根据HDG论文的实现，保证每个维度用户相同
     * @param userSize
     * @param oneDimAttributeSize
     * @return
     */
    protected static Integer[] getUserSizeOfDifferentDim(int userSize, int oneDimAttributeSize) {
        int twoDimAttributePairSize = oneDimAttributeSize * (oneDimAttributeSize - 1) / 2;
        int groupSize = oneDimAttributeSize + twoDimAttributePairSize;
        int n1 = (int) Math.round(userSize * 1.0 / groupSize * oneDimAttributeSize);
        int n2 = userSize - n1;
        return new Integer[]{n1, n2};
    }

    public static Double[] getOptimalGOneAndGTwo(int userSize, double epsilon, int oneDimAttributeSize, double alphaOne, double alphaTwo) {
        int twoDimAttributePairSize = oneDimAttributeSize * (oneDimAttributeSize - 1) / 2;
        Integer[] userSizeArray = getUserSizeOfDifferentDim(userSize, oneDimAttributeSize);
        Double gridOne = getGridOne(userSizeArray[0], epsilon, alphaOne, oneDimAttributeSize);
        Double gridTwo = getGridTwo(userSizeArray[1], epsilon, alphaTwo, twoDimAttributePairSize);
        return new Double[]{gridOne, gridTwo};
    }

    public static Integer toOneDimAttributeIndex(AttributeIndexPair indexPair, Integer squareSizeLength) {
        Integer i = indexPair.getIndexA();
        Integer j = indexPair.getIndexB();
        Integer result = 0;
        for (int k = 1; k <= i; ++k) {
            result += squareSizeLength - k;
        }
        result += j - i - 1;
        return result;
    }


    public static AttributeIndexPair toTwoDimAttributeIndex(Integer index, Integer squareSizeLength) {
        int colSize = squareSizeLength - 1;
        int xIndex = 0;
        while (index >= colSize && colSize > 0) {
            ++xIndex;
            index -= colSize;
            --colSize;
        }
        if (colSize == 0) {
            throw new RuntimeException("index out of bound!!!");
        }
        return new AttributeIndexPair(xIndex, xIndex+1+index);
    }

    public static Integer toOneDimGridIndex(BasicPair<Integer, Integer> indexPair, Integer gridSizeWithSideLength) {
        return indexPair.getKey() * gridSizeWithSideLength + indexPair.getValue();
    }

    public static BasicPair<Integer, Integer> toTwoDimGridIndex(Integer index, Integer gridSizeWithSideLength) {
        Integer xIndex = index / gridSizeWithSideLength;
        Integer yIndex = index % gridSizeWithSideLength;
        return new BasicPair<>(xIndex, yIndex);
    }


    /**
     *
     * @param userSize
     * @param oneDimAttributeSize
     * @return TreeMap<UserIndex, OneDimAttributeIndex or TwoDimAttributeIndexPair>
     */
    public static Map<Integer, Object> sampleGroupForUsers(int userSize, int oneDimAttributeSize) {
        int twoDimAttributePairSize = oneDimAttributeSize * (oneDimAttributeSize - 1) / 2;
//        Integer[] userSizeArray = getUserSizeOfDifferentDim(userSize, oneDimAttributeSize);
        Integer groupSize = oneDimAttributeSize + twoDimAttributePairSize;
        HashMap<Integer, Object> resultMap = new HashMap<>();
        Integer tempGroup, tempIndex;
        Object objIndex;
        for (int userIndex = 0; userIndex < userSize; userIndex++) {
            tempGroup = RandomUtil.getRandomInteger(1, groupSize);
            if (tempGroup <= oneDimAttributeSize) {
                // choose 1 dim
                objIndex = RandomUtil.getRandomInteger(0, oneDimAttributeSize - 1);
            } else {
                // choose 2 dim
                tempIndex = RandomUtil.getRandomInteger(0, twoDimAttributePairSize - 1);
                objIndex = toTwoDimAttributeIndex(tempIndex, oneDimAttributeSize);
            }
            resultMap.put(userIndex, objIndex);
        }
        return resultMap;
    }

    public static HashMap[] countUserForEachGroup(Map<Integer, Object> userToGroupMap) {
        HashMap<Integer, Integer> oneDimMap = new HashMap<>();
        HashMap<AttributeIndexPair, Integer> twoDimMap = new HashMap<>();
        Object attributeIndex;
        Integer tempCount;
        for (Map.Entry<Integer, Object> entry : userToGroupMap.entrySet()) {
            attributeIndex = entry.getValue();
            if (attributeIndex instanceof Integer) {
                Integer oneDimAttributeIndex = (Integer) attributeIndex;
                tempCount = oneDimMap.getOrDefault(oneDimAttributeIndex, 0);
                oneDimMap.put(oneDimAttributeIndex, tempCount+1);
            } else {
                AttributeIndexPair twoDimAttributeIndex = (AttributeIndexPair) attributeIndex;
                tempCount = twoDimMap.getOrDefault(twoDimAttributeIndex, 0);
                twoDimMap.put(twoDimAttributeIndex, tempCount + 1);
            }
        }
        return new HashMap[]{oneDimMap, twoDimMap};
    }

    public static Integer getHashFunctionSizeFromConfig() {
        Document document = Constant.xmlConfigure.getDocument();
        Element functionSizeElement = (Element)document.selectNodes("//parameters/parameter[@name='hash_function_size']").get(0);
        return Integer.valueOf(functionSizeElement.getTextTrim());
    }



    public static int getGridIndex(Double value, Double leftBound, Double gridLength) {
        return (int) Math.floor((value - leftBound) / gridLength);
    }

    public static BasicPair<Integer, Integer> getGridIndex(Double[] value, Double[] leftBoundArray, Double[] gridLengthArray) {
        Integer[] paramArray = new Integer[value.length];
        for (int i = 0; i < paramArray.length; i++) {
            paramArray[i] = getGridIndex(value[i], leftBoundArray[i], gridLengthArray[i]);
        }
        return new BasicPair<>(paramArray[0], paramArray[1]);
    }

    public static void main0(String[] args) {
        int index = 44;
        Integer squareSideLength = 10;
        AttributeIndexPair attributePair = toTwoDimAttributeIndex(index, squareSideLength);
        System.out.println(attributePair);
    }

    public static void main1(String[] args) {
        int userSize = 1000;
        int oneDimAttributeSize = 5;
        Map<Integer, Object> result = sampleGroupForUsers(userSize, oneDimAttributeSize);
        MyPrint.showMap(result);
    }

    public static void main2(String[] args) {
        Integer index = 5;
        Integer squareSizeLength = 5;
        BasicPair<Integer, Integer> twoDimIndex = toTwoDimGridIndex(index, squareSizeLength);
        System.out.println(twoDimIndex);
    }

    public static void main(String[] args) {
        Integer hashFunctionSizeFromConfig = getHashFunctionSizeFromConfig();
        System.out.println(hashFunctionSizeFromConfig);
    }
}
