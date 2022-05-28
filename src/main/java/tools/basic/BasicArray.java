package tools.basic;


import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BasicArray {
    public static void setIntArrayTo(int[] element, int value) {
        for (int i = 0; i < element.length; i++) {
            element[i] = value;
        }
    }

    public static void setIntArrayTo(Integer[] element, int value) {
        for (int i = 0; i < element.length; i++) {
            element[i] = value;
        }
    }

    public static void setIntArrayToZero(int[] element) {
        setIntArrayTo(element, 0);
    }

    public static void setIntArrayToZero(Integer[] element) {
        setIntArrayTo(element, 0);
    }

    public static void setDoubleArrayTo(double[] element, double value) {
        for (int i = 0; i < element.length; i++) {
            element[i] = value;
        }
    }

    public static void setDoubleArrayTo(double[][] element, double value) {
        for (int i = 0; i < element.length; i++) {
            for (int j = 0; j < element[0].length; j++) {
                element[i][j] = value;
            }
        }
    }

    public static void setDoubleArrayTo(Double[] element, double value) {
        for (int i = 0; i < element.length; i++) {
            element[i] = value;
        }
    }

    public static void setDoubleArrayTo(Double[][] element, double value) {
        for (int i = 0; i < element.length; i++) {
            for (int j = 0; j < element[0].length; j++) {
                element[i][j] = value;
            }
        }
    }

    public static void setListArrayToEmptyList(List[] element) {
        for (int i = 0; i < element.length; i++) {
            element[i] = new ArrayList();
        }
    }


    public static void setListArrayTo(List[] element, List list) {
        for (int i = 0; i < element.length; i++) {
            element[i] = new ArrayList(list);
        }
    }

    public static void setIntegerListToContinuousNaturalNumber(List<Integer> list, Integer maxNaturalNumber) {
        for (int i = 0; i <= maxNaturalNumber; i++) {
            list.add(i);
        }
    }

    public static void setDoubleArrayToZero(double[] element) {
        setDoubleArrayTo(element, 0.0);
    }

    public static void setDoubleArrayToZero(double[][] element) {
        setDoubleArrayTo(element, 0.0);
    }

    public static void setDoubleArrayToZero(Double[] element) {
        setDoubleArrayTo(element, 0.0);
    }

    public static void setDoubleArrayToZero(Double[][] element) {
        setDoubleArrayTo(element, 0.0);
    }


    public static Integer[] toIntegerArray(String[] arr) {
        Integer[] result = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = Integer.valueOf(arr[i]);
        }
        return result;
    }


    public static int[] toIntArray(String[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = Integer.parseInt(arr[i]);
        }
        return result;
    }

    public static Double[] toDoubleArray(String[] arr) {
        Double[] result = new Double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = Double.valueOf(arr[i]);
        }
        return result;
    }

    public static double[] toDouArray(String[] arr) {
        double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = Double.parseDouble(arr[i]);
        }
        return result;
    }


    public static <T> T[] getInitializedArray(T value, int size) {
        Class clazz = value.getClass();
        Object array = null;
        array = Array.newInstance(clazz, size);
        for (int i = 0; i < size; i++) {
            Array.set(array, i, value);
        }
        T[] result = (T[]) array;
        return result;
    }

    public static Integer[] getIncreaseIntegerNumberArray(Integer startValue, int step, Integer maxValue) {
        int size = (maxValue - startValue) / step + 1;
        Integer[] result = new Integer[size];
        for (int value = startValue, i = 0; value <= maxValue; value += step, i++) {
            result[i] = value;
        }
        return result;
    }


    public static int getFirstFindValueIndex(int[] data, int value) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static List<Integer> getDeclaredValueIndexList(int[] data, int value) {
        List<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == value) {
                resultList.add(i);
            }
        }
        return resultList;
    }


    public static <T extends Comparable<T>> Pair<Integer, T> getMaxValuePair(T... objs) {
        T result = objs[0];
        Integer index = 0;
        for (int i = 1; i < objs.length; i++) {
            if (result.compareTo(objs[i]) < 0) {
                index = i;
                result = objs[i];
            }
        }
        return new Pair<>(index, result);
    }

    public static <T extends Comparable<T>> Pair<Integer, T> getMinValuePair(T... objs) {
        T result = objs[0];
        Integer index = 0;
        for (int i = 1; i < objs.length; i++) {
            if (result.compareTo(objs[i]) > 0) {
                index = i;
                result = objs[i];
            }
        }
        return new Pair<>(index, result);
    }

    public static Integer getSum(Integer... nums) {
        Integer sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        return sum;
    }

    public static Double getSum(Double... nums) {
        Double sum = 0.0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        return sum;
    }


}
