package ecnu.dll.construction._config;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.basic.StringUtil;
import cn.edu.ecnu.constant_values.ConstantValues;
import cn.edu.ecnu.filter.file_filter.DirectoryFileFilter;
import cn.edu.ecnu.result.ResultTool;
import cn.edu.ecnu.struct.result.ColumnBean;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
//import io.netty.util.internal.StringUtil;

import java.io.FileFilter;
import java.util.List;

public class Constant {


//    public static final String rootPath = "/root/code/2_2.ProgramForSpatialLDPEstimation/1.JavaCode";
//    public static final String rootPath = "E:\\dataset";
//    public static final String rootPath = "/root/code/2_seldp/";
//    public static final String rootPath = "/root/code/2_2_spatialLDP/";
    public static final String rootPath = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation";
    public static final String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, rootPath, "0_dataset");
    public static final String basicResultPath = StringUtil.join(ConstantValues.FILE_SPLIT, rootPath, "result");
    public static final String extendedResultPath = StringUtil.join(ConstantValues.FILE_SPLIT, rootPath, "result_extended");

    public static final String damBudgetLPTableGeneratedPath = StringUtil.join(ConstantValues.FILE_SPLIT, rootPath, "budgetLPTable", "damBudgetLPTable.txt");
    public static final String subsetGeoIBudgetLPTableGeneratedPath = StringUtil.join(ConstantValues.FILE_SPLIT, rootPath, "budgetLPTable", "geoIBudgetLPTable.txt");

    public static final int eliminateDoubleErrorIndexSize = 2;
    public static final int invalidValue = -1;

    public static final double DEFAULT_PRECISION = Math.pow(10,-6);

//    public static final double DEFAULT_STOP_VALUE_TAO = Math.pow(10, -3);
    public static final double DEFAULT_STOP_VALUE_TAO = Math.pow(10, -6);
//    public static final double DEFAULT_STOP_VALUE_TAO = Double.MAX_VALUE;


    public static final Integer[] DEFAULT_ONE_DIMENSIONAL_COEFFICIENTS = new Integer[]{1, 2, 1};




    // for test
//    public static final String DEFAULT_INPUT_PATH = basicDatasetPath + "\\test\\real_dataset\\chicago_point_A.txt";
    // 记录原始输入数据的长度以及左下方点坐标
    public static final double DEFAULT_INPUT_LENGTH = 0.09;
    public static final double DEFAULT_X_BOUND = 41.72;
    public static final double DEFAULT_Y_BOUND = -87.68;




//    public static final double DEFAULT_CELL_LENGTH = 0.0225; // d/4
    // 记录转成整数cell后，输入数据的长度
//    public static final double DEFAULT_SIDE_LENGTH_NUMBER_SIZE = 5.0;
//    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE = new double[] {
//            2.0, 3.0, 4.0, 5.0, 6.0
//    };
    public static final double DEFAULT_SIDE_LENGTH_NUMBER_SIZE = 5.0;
    public static final double DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison = 10.0;
//    public static final double DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_b_change = DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
    public static final double DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_b_change = 9.0;
    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE = new double[] {
            1.0, 2.0, 3.0, 4.0, 5.0
    };
//    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE = new double[] {
//            5.0, 6.0, 7.0, 8.0, 9.0
//    };
//    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison = new double[] {
//            5.0, 10.0, 15.0, 20.0, 25.0
//    };
    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison = new double[] {
            6.0, 7.0, 8.0, 9.0, 10.0
    };
    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Total = BasicArray.getIncreasedoubleNumberArray(1.0, 1.0, 30.0, 2);

//    public static final double DEFAULT_SIDE_LENGTH_NUMBER_SIZE = 4.0;
//    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE = new double[] {
//            3.0, 4.0, 5.0, 6.0, 7.0
//    };
//    public static final double DEFAULT_SIDE_LENGTH_NUMBER_SIZE = 7.0;
//    public static final double DEFAULT_SIDE_LENGTH_NUMBER_SIZE = 6.0;
//    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE = new double[] {
//            5.0, 6.0, 7.0, 8.0, 9.0
//    };

//    public static final double DEFAULT_CELL_LENGTH = DEFAULT_INPUT_LENGTH / DEFAULT_LENGTH_NUMBER_SIZE;


    public static final double DEFAULT_B_LENGTH = -1;
    public static final double[] ALTER_B_LENGTH_RATIO_ARRAY = new double[]{
            1.0/3, 2.0/3, 1.0, 4.0/3, 5.0/3
    };


//    public static final double DEFAULT_PRIVACY_BUDGET = 0.5;
//    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY = new double[] {
//            0.2, 0.4, 0.6, 0.8, 1.0
//    };
//    public static final double DEFAULT_PRIVACY_BUDGET = 7.5;
//
//    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY = new double[] {
//            4.7, 5.4, 6.1, 6.8, 7.5
//    };
    public static final double DEFAULT_PRIVACY_BUDGET = 2.6;
//    public static final double DEFAULT_PRIVACY_BUDGET = 1.9;
    public static final double DEFAULT_PRIVACY_BUDGET_for_b_change = 2.6;

    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY = new double[] {
            0.5, 1.2, 1.9, 2.6, 3.3
    };

    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison = new double[] {
            0.7, 1.4, 2.1, 2.8, 3.5
    };
    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Total = BasicArray.getIncreasedoubleNumberArray(0.67, 0.01, 4, eliminateDoubleErrorIndexSize);

//    public static final double DEFAULT_PRIVACY_BUDGET = 2.5;
//
//
//    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY = new double[] {
//            0.5, 1.0, 1.5, 2.0, 2.5
//    };
//    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY = new double[] {
//            1.0, 1.5, 2.0, 2.5, 3.0
//    };

//    public static final double[] FINE_GRIT_PRIVACY_BUDGET_ARRAY = BasicArray.getIncreaseDoubleNumberArray(0.2, 0.01, 3.0);
    public static final double[] FINE_GRIT_PRIVACY_BUDGET_ARRAY = BasicArray.getIncreasedoubleNumberArray(0.35, 0.01, 5.05, eliminateDoubleErrorIndexSize);
//    public static final double[] FINE_GRIT_PRIVACY_BUDGET_ARRAY = BasicArray.getIncreasedoubleNumberArray(0.35, 0.01, 3.05);
//    public static final double[] FINE_GRIT_PRIVACY_BUDGET_ARRAY = BasicArray.getIncreaseDoubleNumberArray(0.9, 0.01, 3.1);


//    public static final double DEFAULT_POSITIVE_B_LENGTH = 1;

//    public static final double DEFAULT_K_PARAMETER = 0.25;
    public static final double DEFAULT_K_PARAMETER = 0.0;
    public static final double DEFAULT_K_PARAMETER_for_change_b = 0.25;
    public static final double DEFAULT_K_PARAMETER_for_RAM = DEFAULT_K_PARAMETER;
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
    public static final String diskNonShrinkSchemeKey = "diskNonShrink";
    public static final String hybridUniformExponentialSchemeKey = "hue";
    public static final String multiDimensionalSquareWaveSchemeKey = "mdsw";

    public static final String subsetGeoIOneNormSchemeKey = "subsetGeoIOneNorm";
    public static final String subsetGeoITwoNormSchemeKey = "subsetGeoITwoNorm";

    // 记录结果的Key
    public static final String alterBKey = "alteringB";
    public static final String alterBudgetKey = "alteringBudget";
    public static final String alterGKey = "alteringG";
    public static final String alterKKey = "alteringK";

    public static final String[] basicAlterKeyArray = new String[] {
            alterBKey,
            alterBudgetKey,
            alterGKey,
            alterKKey
    };
    public static final String[] extendedAlterKeyArray = new String[] {
//            alterBudgetKey,
            alterGKey
    };

    // 记录数据集名称
    public static final String chicagoAKey = "ChicagoA";
    public static final String chicagoBKey = "ChicagoB";
    public static final String chicagoCKey = "ChicagoC";
    public static final String nycAKey = "NYCA";
    public static final String nycBKey = "NYCB";
    public static final String nycCKey = "NYCC";
    public static final String normalKey = "TwoDimNormal";
    public static final String zipfKey = "TwoDimZipf";
    public static final String multiNormalKey = "TwoDimMultiCenterNormal";

    // 记录数据集路径
    public static final String chicagoAPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "1_crime", "chicago_point_A.txt");
    public static final String chicagoBPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "1_crime", "chicago_point_B.txt");
    public static final String chicagoCPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "1_crime", "chicago_point_C.txt");
    public static final String nycAPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "2_nyc", "nyc_point_A.txt");
    public static final String nycBPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "2_nyc", "nyc_point_B.txt");
    public static final String nycCPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "2_nyc", "nyc_point_C.txt");
    public static final String normalPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "2_synthetic", "1_two_normal", "two_normal_point_extract.txt");
    public static final String zipfPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "2_synthetic", "2_two_zipf", "two_zipf_point.txt");

    public static final String multiNormalPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "2_synthetic", "3_two_normal_multiple_center", "two_normal_point_multiple_centers_extract.txt");

    // 记录被分割的dataset输出父路径的父路径
    public static final String basicRelativeParentCrimeDir = StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "crime");
    public static final String extendedRelativeParentCrimeDir = StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "crime");
    public static final String basicRelativeParentNYCDir = StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "nyc");
    public static final String extendedRelativeParentNYCDir = StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "nyc");

    // 记录dataset输出父路径
    public static final String[] basicOutputCrimeDirArray = new String[] {
            StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "crime", "crimeA"),
            StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "crime", "crimeB"),
            StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "crime", "crimeC")
    };
    public static final String[] extendedOutputCrimeDirArray = new String[] {
            StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "crime", "crimeA"),
            StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "crime", "crimeB"),
            StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "crime", "crimeC")
    };
    public static final String[] basicOutputNYCDirArray = new String[] {
            StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "nyc", "nycA"),
            StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "nyc", "nycB"),
            StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "nyc", "nycC")
    };
    public static final String[] extendedOutputNYCDirArray = new String[] {
            StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "nyc", "nycA"),
            StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "nyc", "nycB"),
            StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "nyc", "nycC")
    };
    public static final String basicOutputNormalDir = StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "normal");
    public static final String extendedOutputNormalDir = StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "normal");
    public static final String basicOutputZipfDir = StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "zipf");
    public static final String extendedOutputZipfDir = StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "zipf");
    public static final String basicOutputMultiNormalDir = StringUtil.join(ConstantValues.FILE_SPLIT, basicResultPath, "normal_multi_centers");
    public static final String extendedOutputMultiNormalDir = StringUtil.join(ConstantValues.FILE_SPLIT, extendedResultPath, "normal_multi_centers");




    // 记录result
    public static final String dataSetNameKey = "DataSetName";
    public static final String dataPointSizeKey = "DataPointSize";
    public static final String areaLengthKey = "AreaLength";
    public static final String schemeNameKey = "SchemeName";
    public static final String postProcessTimeKey = "PostProcessTime";
    public static final String gridUnitSizeKey = "GridUnitSize";
    public static final String dataTypeSizeKey = "DataTypeSize";
    public static final String sizeDKey = "SizeD";
    public static final String sizeBKey = "SizeB";
    public static final String privacyBudgetKey = "PrivacyBudget";
    public static final String contributionKKey = "ContributionK";
    public static final String wassersteinDistance1Key = "WassersteinDistance1";
    public static final String wassersteinDistance2Key = "WassersteinDistance2";

//    public static final String meanDistanceKey = "MeanDistance";
//    public static final String varianceDistanceKey = "VarianceDistance";

    public static final ColumnBean[] columnBeanArray = new ColumnBean[] {
            new ColumnBean(1, dataSetNameKey, "java.lang.String", false),
            new ColumnBean(2, dataPointSizeKey, "java.lang.Integer", false),
            new ColumnBean(3, areaLengthKey, "java.lang.Double", false),
            new ColumnBean(4, schemeNameKey, "java.lang.String", false),
            new ColumnBean(5, postProcessTimeKey, "java.lang.Long", true),
            new ColumnBean(6, gridUnitSizeKey, "java.lang.Double", false),
            new ColumnBean(7, dataTypeSizeKey, "java.lang.Integer", false),
            new ColumnBean(8, sizeDKey, "java.lang.Integer", false),
            new ColumnBean(9, sizeBKey, "java.lang.Integer", false),
            new ColumnBean(10, privacyBudgetKey, "java.lang.Double", false),
            new ColumnBean(11, contributionKKey, "java.lang.Double", false),
            new ColumnBean(12, wassersteinDistance1Key, "java.lang.Double", true),
            new ColumnBean(13, wassersteinDistance2Key, "java.lang.Double", true)

    };

//    public static final String[] attributeArray = new String[]{
//            dataSetNameKey,
//            dataPointSizeKey,
//            areaLengthKey,
//            schemeNameKey,
//            postProcessTimeKey,
//            gridUnitSizeKey,
//            dataTypeSizeKey,
//            sizeDKey,
//            sizeBKey,
//            privacyBudgetKey,
//            contributionKKey,
//            wassersteinDistanceKey
//    };

    public static final String configName = "default";
    public static final List<ColumnBean> attributeList = ResultTool.getAttributeListFromConfigureFile(configName);
    public static final FileFilter directoryFilter = new DirectoryFileFilter();

    public static final DataSetAreaInfo[] crimeDataSetArray = new DataSetAreaInfo[]{
            new DataSetAreaInfo(chicagoAPath, chicagoAKey, 41.72, -87.68, 0.09),
            new DataSetAreaInfo(chicagoBPath, chicagoBKey, 41.82, -87.73, 0.09),
            new DataSetAreaInfo(chicagoCPath, chicagoCKey, 41.92, -87.77,0.07)
    };
    public static final DataSetAreaInfo[] nycDataSetArray = new DataSetAreaInfo[]{
            new DataSetAreaInfo(nycAPath, nycAKey, 40.65, -73.84, 0.10),
            new DataSetAreaInfo(nycBPath, nycBKey, 40.65, -73.95, 0.09),
            new DataSetAreaInfo(nycCPath, nycCKey, 40.82, -73.90,0.07)
    };

    public static final DataSetAreaInfo twoDimNormalDataSet = new DataSetAreaInfo(normalPath, normalKey, -1.5, -1.5, 3.0);
    public static final DataSetAreaInfo twoDimZipfDataSet = new DataSetAreaInfo(zipfPath, zipfKey, 0.0, 0.0, 1.0);
    public static final DataSetAreaInfo twoDimMultipleCenterNormalDataSet = new DataSetAreaInfo(multiNormalPath, multiNormalKey, -1.5, -2.0, 5.0);
























}
