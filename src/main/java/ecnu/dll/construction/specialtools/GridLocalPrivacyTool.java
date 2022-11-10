package ecnu.dll.construction.specialtools;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;

public class GridLocalPrivacyTool {

    public static Integer getGridSize(int sizeD) {
        return sizeD * sizeD;
    }

    /**
     * 这里坐标从(1,1)起
     * @param indexD
     * @param indexB
     * @param sizeD
     * @return
     */
    private static Integer getOuterUpperCrossQuantity(int indexD, int indexB, int sizeD) {
        if (indexD <= 0) {
            indexB = indexB + indexD - 1;
            indexD = 1;
        }
        Integer result = null;
        if (indexD + indexB <= sizeD + 1) {
            result = indexB * (indexB + 1) / 2;
        } else if (indexB <= sizeD) {
            result = (3*indexB + indexD - sizeD) * (2*sizeD - indexD - indexB + 1) / 2;
        } else {
            result = (indexB - sizeD) * sizeD + (indexD + indexB - 2) * (2*sizeD - indexD - indexB +1) / 2;
        }
        return result;
    }

    private static Integer getInnerDropCrossQuantity(int indexD, int indexB, int sizeD, int sizeB) {
        if (indexB <= sizeB) {
            throw new RuntimeException("(" + indexB + ", " + indexD + ") is not an inner cell point!");
        }
        Integer result = null;
        int differ = sizeB + indexD - sizeD;
        if (indexB <= 2*sizeB + 1) {
            if (differ <= 0) {
                result = (indexB - sizeB - 1) * (indexB - sizeB) / 2;
            } else {
                result = (sizeB + sizeD - indexB - indexD) * (sizeB + sizeD - indexB - indexD - 1) / 2;
            }

        } else {
            if (differ <= 0) {
                result = (2*indexB - 3*sizeB - 2) * (sizeB+1) / 2;
            } else {
                result = (2*indexB + 4*sizeB - indexD - sizeD) * (sizeD - indexD + 1) / 2;
            }
        }
        return result;
    }

    protected static Integer getCrossCellQuantity(int sizeD, int sizeB, TwoDimensionalIntegerPoint point) {
        return null;
    }

    public static void main0(String[] args) {
        int sizeD = 6;
        int sizeB = 4;
        int indexD, indexB;


        Integer outerUpperCrossQuantity = null, dropUpperCrossQuantity = null;
        for (indexB = 1; indexB <= sizeB; indexB++) {
            for (indexD = 1; indexD <= sizeD/2; indexD++) {
                outerUpperCrossQuantity = getOuterUpperCrossQuantity(indexD, indexB, sizeD);
                System.out.println("indexD: " + indexD + ", indexB: " + indexB + "; crossNumber: " + outerUpperCrossQuantity);
            }
        }
    }
    public static void main(String[] args) {
        int sizeD = 6;
        int sizeB = 4;
        int indexD, indexB;


        Integer dropUpperCrossQuantity;
        for (indexB = sizeB + 1; indexB <= sizeB + sizeD / 2; indexB++) {
            for (indexD = indexB - sizeB; indexD <= sizeD/2; indexD++) {
                dropUpperCrossQuantity = getInnerDropCrossQuantity(indexD, indexB, sizeD, sizeB);
                System.out.println("indexD: " + indexD + ", indexB: " + indexB + "; dropNumber: " + dropUpperCrossQuantity);
            }
        }
    }

}
