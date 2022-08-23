package ecnu.dll.construction._config;

import cn.edu.ecnu.basic.StringUtil;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;

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



    public static final DataSetAreaInfo[] crimeDataSetArray = new DataSetAreaInfo[]{
            new DataSetAreaInfo("F:\\dataset\\test\\real_dataset\\chicago_point_A.txt", 41.72, -87.68, 0.09),
            new DataSetAreaInfo("F:\\dataset\\test\\real_dataset\\chicago_point_B.txt", 41.82, -87.73, 0.09),
            new DataSetAreaInfo("F:\\dataset\\test\\real_dataset\\chicago_point_C.txt", 41.92, -87.77,0.07)
    };
    public static final DataSetAreaInfo[] nycDataSetArray = new DataSetAreaInfo[]{
            new DataSetAreaInfo("F:\\dataset\\test\\real_dataset\\nyc_point_A.txt", 40.65, -73.84, 0.10),
            new DataSetAreaInfo("F:\\dataset\\test\\real_dataset\\nyc_point_B.txt", 40.65, -73.95, 0.09),
            new DataSetAreaInfo("F:\\dataset\\test\\real_dataset\\nyc_point_C.txt", 40.82, -73.90,0.07)
    };

    public static final DataSetAreaInfo twoDimNormalDataSet = new DataSetAreaInfo("F:\\dataset\\test\\synthetic_dataset\\two_normal_point_extract.txt", -1.5, -1.5, 3.0);
    public static final DataSetAreaInfo twoDimZipfDataSet = new DataSetAreaInfo("F:\\dataset\\test\\synthetic_dataset\\two_zipf_point.txt", 0.0, 0.0, 1.0);

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
    public static final double[] ALTER_K_PARAMETER_ARRAY = new double[] {
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


    // 记录scheme的Key
    public static final String rhombusSchemeKey = "rhombus";
    public static final String diskSchemeKey = "disk";
    public static final String hybridUniformExponentialSchemeKey = "hue";
    public static final String multiDimensionalSquareWaveSchemeKey = "mdsw";
    public static final String subsetGeoISchemeKey = "subsetGeoI";

    // 记录结果的Key
    public static final String alterBKey = "alteringB";
    public static final String alterBudgetKey = "alteringBudget";
    public static final String alterGKey = "alteringG";
    public static final String alterKKey = "alteringK";


}
