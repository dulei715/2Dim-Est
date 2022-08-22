package ecnu.dll.construction._config;

import cn.edu.ecnu.basic.StringUtil;

public class Constant {
    public static final double DEFAULT_PRECISION = Math.pow(10,-6);

//    public static final double DEFAULT_STOP_VALUE_TAO = Math.pow(10, -3);
    public static final double DEFAULT_STOP_VALUE_TAO = Math.pow(10, -6);
//    public static final double DEFAULT_STOP_VALUE_TAO = Double.MAX_VALUE;


    public static final Integer[] DEFAULT_ONE_DIMENSIONAL_COEFFICIENTS = new Integer[]{1, 2, 1};


    // for test
//    public static final String DEFAULT_INPUT_PATH = "F:\\dataset\\test\\chicago_point_small.txt";
    public static final String DEFAULT_INPUT_PATH = "F:\\dataset\\test\\real_dataset\\chicago_point_A.txt";
    // 记录原始输入数据的长度以及左下方点坐标
    public static final double DEFAULT_INPUT_LENGTH = 0.09;
    public static final double DEFAULT_X_BOUND = 41.72;
    public static final double DEFAULT_Y_BOUND = -87.68;

//    public static final double DEFAULT_CELL_LENGTH = 0.0225; // d/4
    // 记录转成整数cell后，输入数据的长度
    public static final double DEFAULT_SIDE_LENGTH_NUMBER_SIZE = 4.0;
    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE = new double[] {
            2.0, 3.0, 4.0, 5.0, 6.0
    };

//    public static final double DEFAULT_CELL_LENGTH = DEFAULT_INPUT_LENGTH / DEFAULT_LENGTH_NUMBER_SIZE;


    public static final double DEFAULT_B_LENGTH = -1;
    public static final double[] ALTER_B_LENGTH_RATIO_ARRAY = new double[]{
            1.0/3, 2.0/3, 1.0, 4.0/3, 5.0/3
    };


    public static final double DEFAULT_PRIVACY_BUDGET = 0.5;
    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY = new double[] {
            0.2, 0.4, 0.6, 0.8, 1.0
    };


//    public static final double DEFAULT_POSITIVE_B_LENGTH = 1;

    public static final double DEFAULT_K_PARAMETER = 0.25;
    public static final double[] ALTER_K_PARAMETER = new double[] {
            0.0, 0.25, 0.5, 0.75, 1.0
    };




//    public static final String DEFAULT_INPUT_PATH = "F:\\dataset\\test\\synthetic_dataset\\test3\\two_dim_norm_extract.txt";
//    public static final double DEFAULT_PRIVACY_BUDGET = 0.5;
//    public static final double DEFAULT_CELL_LENGTH = 0.2;
//    public static final double DEFAULT_INPUT_LENGTH = 2;
//    public static final double DEFAULT_B_LENGTH = -1;
//    public static final double DEFAULT_POSITIVE_B_LENGTH = 1;
//
//    public static final double DEFAULT_K_PARAMETER = 0.25;
//    public static final double DEFAULT_X_BOUND = -1;
//    public static final double DEFAULT_Y_BOUND = -1;


}
