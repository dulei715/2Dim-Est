package ecnu.dll.construction.specialtools;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;

public class GridLocalPrivacyTool {

    public static Integer getGridSize(int sizeD) {
        return sizeD * sizeD;
    }

    /**
     * 用于 indexB <= sizeB
     * 这里坐标从(1,1)起
     * @param indexD
     * @param indexB
     * @param sizeD
     * @return
     */
    private static Integer getOuterUpperCrossQuantity(int indexD, int indexB, int sizeD, int sizeB) {
        if (indexD <= 0) {
            indexB = indexB + indexD - 1;
            indexD = 1;
        }
        Integer result = null;
        if (indexD + indexB <= sizeD) {
            // todo: 优化代码书写
            if (indexB <= sizeB) {
                result = indexB * (indexB + 1) / 2;
            } else {
                result = (2*indexB - sizeB) * (sizeB + 1) / 2;
            }
        } else if (indexB <= sizeD) {
            result = (2*indexB + indexD - sizeD) * (sizeD - indexD + 1) / 2;
        } else {
            result = (indexB - sizeD) * sizeD + (indexD + indexB) * (2*sizeD - indexD - indexB +1) / 2;
        }
        return result;
    }

    private static Integer getInnerUpperDropCrossQuantity(int indexD, int indexB, int sizeD, int sizeB) {
//        if (indexB <= sizeB) {
//            throw new RuntimeException("(" + indexB + ", " + indexD + ") is not an inner cell point!");
//        }
        Integer result = null;
        int differ = sizeD - sizeB;
        if (indexB <= 2*sizeB + 1) {
            if (indexD <= differ) {
                result = (indexB - sizeB - 1) * (indexB - sizeB) / 2;
            } else {
                differ = differ -sizeB + indexB;
                if (indexD <= differ) {
                    result = (differ - indexD) * (differ -indexD - 1) / 2;
                } else {
                    result = 0;
                }
            }

        } else {
            if (indexD <= differ) {
                result = (2*indexB - 3*sizeB - 2) * (sizeB + 1) / 2;
            } else {
                result = (2*indexB - 4*sizeB - indexD + sizeD - 2) * (sizeD - indexD + 1) / 2;
            }
        }
        return result;
    }

    protected static Integer getUpperCrossCellQuantity(int indexD, int indexB, int sizeD, int sizeB) {
        int f1 = getOuterUpperCrossQuantity(indexD, indexB, sizeD, sizeB);
        if (indexB <= sizeB) {
            return f1;
        }
        int dropValue = getInnerUpperDropCrossQuantity(indexD, indexB, sizeD, sizeB);
        return f1 - dropValue;
    }

    private static Integer getOuterBottomCrossQuantity(int indexD, int indexB, int sizeD, int sizeB) {
        if (indexD <= 0) {
            indexB = indexB + indexD - 1;
            indexD = 1;
        }
        Integer result = null;
        if (indexB <= indexD - 1) {
            // todo: 优化代码书写
            if (indexB <= sizeB) {
                result = indexB * (indexB + 1) / 2;
            } else {
                result = (2*indexB - sizeB) * (sizeB + 1) / 2;
            }
        } else if (indexB <= sizeD) {
            result = (2*indexB - indexD + 1) * indexD / 2;
        } else {
            result = (indexB - sizeD) * sizeD + (sizeD + indexB - indexD + 1) * (sizeD + indexD - indexB) / 2;
        }
        return result;
    }

    private static Integer getInnerBottomDropCrossQuantity(int indexD, int indexB, int sizeD, int sizeB) {
//        if (indexB <= sizeB) {
//            throw new RuntimeException("(" + indexB + ", " + indexD + ") is not an inner cell point!");
//        }
        Integer result = null;
        int differ = sizeB + 1;
        if (indexB <= 2*sizeB + 1) {
            if (indexD >= differ) {
                result = (indexB - sizeB - 1) * (indexB - sizeB) / 2;
            } else {
                differ = differ + sizeB - indexB;
                if (indexD >= differ) {
                    result = (indexD - differ) * (indexD - differ - 1) / 2;
                } else {
                    result = 0;
                }
            }

        } else {
            if (indexD <= differ) {
                result = (indexD + 2*indexB - 4*sizeB - 3) * indexD / 2;
            } else {
                result = (2*indexB - 3*sizeB - 2) * (sizeB + 1) / 2;
            }
        }
        return result;
    }

    protected static Integer getBottomCrossCellQuantity(int indexD, int indexB, int sizeD, int sizeB) {
        int f1 = getOuterBottomCrossQuantity(indexD, indexB, sizeD, sizeB);
        if (indexB <= sizeB) {
            return f1;
        }
        int dropValue = getInnerBottomDropCrossQuantity(indexD, indexB, sizeD, sizeB);
        return f1 - dropValue;
    }

    public static Integer getCrossCellQuantity(int indexD, int indexB, int sizeD, int sizeB) {
        int upperCross = getUpperCrossCellQuantity(indexD, indexB, sizeD, sizeB);
        int bottomCross = getBottomCrossCellQuantity(indexD, indexB, sizeD, sizeB);
        return upperCross + bottomCross - Math.min(indexB, sizeD);
    }

    public static void main0(String[] args) {
        int sizeD = 6;
        int sizeB = 4;
        int indexD, indexB;


        Integer outerUpperCrossQuantity = null, dropUpperCrossQuantity = null;
        for (indexB = 1; indexB <= sizeB; indexB++) {
            for (indexD = 1; indexD <= sizeD/2; indexD++) {
                outerUpperCrossQuantity = getOuterUpperCrossQuantity(indexD, indexB, sizeD, sizeB);
                System.out.println("indexD: " + indexD + ", indexB: " + indexB + "; crossNumber: " + outerUpperCrossQuantity);
            }
        }
    }
    public static void main1(String[] args) {
        int sizeD = 6;
        int sizeB = 4;
        int indexD, indexB;

        Integer dropUpperCrossQuantity;
        for (indexB = sizeB + 1; indexB <= sizeB + sizeD / 2; indexB++) {
            for (indexD = indexB - sizeB; indexD <= sizeD/2; indexD++) {
                dropUpperCrossQuantity = getInnerUpperDropCrossQuantity(indexD, indexB, sizeD, sizeB);
                System.out.println("indexD: " + indexD + ", indexB: " + indexB + "; dropNumber: " + dropUpperCrossQuantity);
            }
        }
    }
    public static void main(String[] args) {
//        int sizeD = 6;
        int sizeD = 8;
        int sizeB = 4;
        int indexD, indexB;

        Integer crossQuantity;
        for (indexB = 1; indexB <= sizeB + sizeD / 2; indexB++) {
            indexD = indexB - sizeB;
            if (indexD < 1) {
                indexD = 1;
            }
            for (; indexD <= sizeD/2; indexD++) {
                if (indexB == 6 && indexD == 3) {
                    System.out.println("6,3");
                }
                crossQuantity = getCrossCellQuantity(indexD, indexB, sizeD, sizeB);
                System.out.println("indexB: " + indexB + ", indexD: " + indexD + "; dropNumber: " + crossQuantity);
            }
        }
    }





}
