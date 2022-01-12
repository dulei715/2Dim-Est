package tools;

import tools.io.print.MyPrint;

import java.util.Random;

public class RandomUtil {
    public static Integer getRandomInteger(Integer lowerBound, Integer upperBound) {
        double randomValue = Math.random();
        double realValue = randomValue * (upperBound + 1 - lowerBound) + lowerBound;
        Integer result = (int) Math.floor(realValue);
        return result;
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
