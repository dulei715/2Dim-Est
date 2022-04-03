package tools;

import tools.io.print.MyPrint;

import java.util.Random;

public class RandomUtil {
    /**
     *
     * @param lowerBound (包含在内)
     * @param upperBound (包含在内)
     * @return
     */
    public static Integer getRandomInteger(Integer lowerBound, Integer upperBound) {
        double randomValue = Math.random();
        double realValue = randomValue * (upperBound + 1 - lowerBound) + lowerBound;
        Integer result = (int) Math.floor(realValue);
        return result;
    }

    public static Integer getTwoPartRandomInteger(Integer lowerBoundA, Integer upperBoundA, Integer lowerBoundB, Integer upperBoundB) {
        int firstPartSize = upperBoundA - lowerBoundA + 1;
        int secondPartSize = upperBoundB - lowerBoundB + 1;
        if (firstPartSize < 0 || secondPartSize < 0) {
            throw new RuntimeException("Size of some Parts is less than 0!");
        }
        double randomPart = Math.random();
        if (randomPart < firstPartSize * 1.0 / (firstPartSize + secondPartSize)) {
            // 返回第一部分
            return getRandomInteger(lowerBoundA, upperBoundA);
        }
        // 返回第二部分
        return getRandomInteger(lowerBoundB, upperBoundB);
    }

    public static Integer[] getRandomIntegerArray(Integer lowerBound, Integer upperBound, int arraySize) {
        Integer[] resultArray = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            resultArray[i] = getRandomInteger(lowerBound, upperBound);
        }
        return resultArray;
    }

    public static void main(String[] args) {
        Integer[] result = getRandomIntegerArray(0, 100, 20);
        MyPrint.showIntegerArray(result);
    }

}
