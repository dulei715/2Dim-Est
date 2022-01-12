package tools.collection;

import java.util.List;

public class ArraysUtils {
    public static int binaryDescendRangeSearchEqualRightFindLeft(double[] a, int fromIndex, int toIndex, double key) {
        int low = fromIndex;
        int high = toIndex - 1;
        int result = 0;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            double midVal = a[mid];

            if (midVal > key)
                low = mid + 1;  // Neither val is NaN, thisVal is smaller
            else if (midVal < key)
                high = mid - 1; // Neither val is NaN, thisVal is larger
            else {
                long midBits = Double.doubleToLongBits(midVal);
                long keyBits = Double.doubleToLongBits(key);
                if (midBits == keyBits){
                    result =  mid - 1;             // Key found
                    if (result < 0) {
                        return -1;
                    }
                    return result;
                }else if (midBits > keyBits) // (-0.0, 0.0) or (!NaN, NaN)
                    low = mid + 1;
                else                        // (0.0, -0.0) or (NaN, !NaN)
                    high = mid - 1;
            }
        }
        result = low - 1;
        if (result < 0) {
            return -1;
        }
        return result;
    }

    public static int binaryDescendRangeSearchEqualRightFindLeft(double[] a, double key) {
        return binaryDescendRangeSearchEqualRightFindLeft(a, 0, a.length, key);
    }

    public static int binaryDescendRangeSearchEqualRightFindLeft(List<double[]> arrList, int fromIndex, int toIndex, double key) {
        int low = fromIndex;
        int high = toIndex - 1;
        int result = 0;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            double[] midVal = arrList.get(mid);

            if (midVal[0] > key)
                low = mid + 1;  // Neither val is NaN, thisVal is smaller
            else if (midVal[0] < key)
                high = mid - 1; // Neither val is NaN, thisVal is larger
            else {
                long midBits = Double.doubleToLongBits(midVal[0]);
                long keyBits = Double.doubleToLongBits(key);
                if (midBits == keyBits){
                    result =  mid - 1;             // Key found
                    if (result < 0) {
                        return -1;
                    }
                    return result;
                }else if (midBits > keyBits) // (-0.0, 0.0) or (!NaN, NaN)
                    low = mid + 1;
                else                        // (0.0, -0.0) or (NaN, !NaN)
                    high = mid - 1;
            }
        }
        result = low - 1;
        if (result < 0) {
            return -1;
        }
        return result;
    }

    public static int binaryDescendRangeSearchEqualRightFindLeft(List<double[]> arrList, double key) {
        return binaryDescendRangeSearchEqualRightFindLeft(arrList, 0, arrList.size(), key);
    }

    public static int binaryDescendRangeSearchEqualLeftFindLeft(List<double[]> arrList, int fromIndex, int toIndex, double key) {
        int low = fromIndex;
        int high = toIndex - 1;
        int result = 0;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            double[] midVal = arrList.get(mid);

            if (midVal[0] > key)
                low = mid + 1;  // Neither val is NaN, thisVal is smaller
            else if (midVal[0] < key)
                high = mid - 1; // Neither val is NaN, thisVal is larger
            else {
                long midBits = Double.doubleToLongBits(midVal[0]);
                long keyBits = Double.doubleToLongBits(key);
                if (midBits == keyBits){
                    return mid;
                }else if (midBits > keyBits) // (-0.0, 0.0) or (!NaN, NaN)
                    low = mid + 1;
                else                        // (0.0, -0.0) or (NaN, !NaN)
                    high = mid - 1;
            }
        }
        result = low - 1;
        if (result < 0) {
            return -1;
        }
        return result;
    }

    public static int binaryDescendRangeSearchEqualLeftFindLeft(List<double[]> arrList, double key) {
        return binaryDescendRangeSearchEqualLeftFindLeft(arrList, 0, arrList.size(), key);
    }

    public static int getDoubleMaxValue(List<Double> list) {
        int resultIndex = -1;
        double resultValue = Double.MIN_VALUE;
        double temp;
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            if (resultValue < temp) {
                resultValue = temp;
                resultIndex = i;
            }
        }
        return resultIndex;
    }

    public static int getDoubleArrayMaxValueByFirstValue(List<double[]> list) {
        int resultIndex = -1;
        double resultValue = Double.MIN_VALUE;
        double temp;
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i)[0];
            if (resultValue < temp) {
                resultValue = temp;
                resultIndex = i;
            }
        }
        return resultIndex;
    }

}
