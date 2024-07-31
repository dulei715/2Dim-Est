package ecnu.dll.construction.schemes.add_schemes.hdg;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.structs.AttributeIndexPair;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.TreeMap;

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
     * @param twoDimAttributePairSize
     * @return
     */
    protected static Integer[] getUserSizeOfDifferentDim(int userSize, int oneDimAttributeSize, int twoDimAttributePairSize) {
        int groupSize = oneDimAttributeSize + twoDimAttributePairSize;
        int n1 = (int) Math.round(userSize * 1.0 / groupSize * oneDimAttributeSize);
        int n2 = userSize - n1;
        return new Integer[]{n1, n2};
    }

    public static Double[] getOptimalGOneAndGTwo(int userSize, double epsilon, int oneDimAttributeSize, int twoDimAttributePairSize, double alphaOne, double alphaTwo) {
        Integer[] userSizeArray = getUserSizeOfDifferentDim(userSize, oneDimAttributeSize, twoDimAttributePairSize);
        Double gridOne = getGridOne(userSizeArray[0], epsilon, alphaOne, oneDimAttributeSize);
        Double gridTwo = getGridTwo(userSizeArray[1], epsilon, alphaTwo, twoDimAttributePairSize);
        return new Double[]{gridOne, gridTwo};
    }

    //todo: error
    public static AttributeIndexPair toAttributePair(Integer index, Integer squareSizeLength) {
        int colSize = squareSizeLength - 1;
        int xIndex = 0;
        while (index > colSize && colSize > 0) {
            ++xIndex;
            index -= colSize;
            --colSize;
        }
        if (colSize == 0) {
            throw new RuntimeException("index out of bound!!!");
        }
        return new AttributeIndexPair(xIndex, xIndex+index);
    }

    /**
     *
     * @param userSize
     * @param oneDimAttributeSize
     * @param twoDimAttributePairSize
     * @return TreeMap<UserIndex, OneDimAttributeIndex or TwoDimAttributeIndexPair>
     */
    public TreeMap<Integer, Object> sampleGridsForUsers(int userSize, int oneDimAttributeSize, int twoDimAttributePairSize) {
        Integer[] userSizeArray = getUserSizeOfDifferentDim(userSize, oneDimAttributeSize, twoDimAttributePairSize);
        Integer groupSize = oneDimAttributeSize + twoDimAttributePairSize;
        TreeMap<Integer, Object> resultMap = new TreeMap<>();
        Integer tempGroup;
        for (int userIndex = 0; userIndex < userSize; userIndex++) {
            tempGroup = RandomUtil.getRandomInteger(1, groupSize);
            if (tempGroup <= oneDimAttributeSize) {

            } else {

            }
        }
        return resultMap;
    }

    public static void main(String[] args) {
        int index = 0;
        Integer squareSideLength = 10;
        AttributeIndexPair attributePair = toAttributePair(index, squareSideLength);
        System.out.println(attributePair);
    }
}
