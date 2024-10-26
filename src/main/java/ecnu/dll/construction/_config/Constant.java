package ecnu.dll.construction._config;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.configure.XMLConfigure;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.filter.file_filter.DirectoryFileFilter;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.result.ResultTool;
import cn.edu.dll.struct.result.ColumnBean;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.extend_tools.PropertyUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Constant {

    public static String projectPath = System.getProperty("user.dir");

    public static String propertyPath;
    public static String configPath;
    public static XMLConfigure xmlConfigure;


    public static List<String> crimeFileNameList;
    public static List<String> nycFileNameList;
    public static String twoNormalFileName;
    public static String twoZipFFileName;
    public static String twoNormalMultipleCenterFileName;

    public static String basicDatasetPath;
//    public static List<String> crimeFilePathList;
//    public static List<String> nycFilePathList;
//    public static String twoNormalFilePath;
//    public static String twoZipFFilePath;
//    public static String twoNormalMultipleCenterFilePath;



    // 实验固定基本参数
    public static final int eliminateDoubleErrorIndexSize = 2;
    public static final int invalidValue = -1;
    public static final double DEFAULT_PRECISION = Math.pow(10,-6);
    public static final double DEFAULT_MINIMAL_DENOMINATOR = Math.pow(10,-6);
    public static final double DEFAULT_STOP_VALUE_TAO = Math.pow(10, -6);
    public static final Integer[] DEFAULT_ONE_DIMENSIONAL_COEFFICIENTS = new Integer[]{1, 2, 1};
    public static final Double SINKHORN_LAMBDA = 5D;
    public static final int SINKHORN_ITERATOR_UPPERBOUND = 100000;


    public static final Double SINKHORN_LOWER_BOUND = Math.pow(10, -6);

    public static final String propertySplit = " ";


    /**
     * 实验变动参数
     */


    /*
        记录 b 的变化
     */
    public static final double DEFAULT_B_LENGTH = -1;
    public static final double[] ALTER_B_LENGTH_RATIO_ARRAY = new double[]{
            1.0/3, 2.0/3, 1.0, 4.0/3, 5.0/3
    };

    public static Double DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_b_change;

    /*
        记录转成整数cell后，输入数据的长度 d 的变化
    */
    //---固定值-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static Double DEFAULT_SIDE_LENGTH_NUMBER_SIZE;

    public static Double DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison;

    public static final double DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_for_KL_Divergence = 20.0;

    //---变动值-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static double[] ALTER_SIDE_LENGTH_NUMBER_SIZE;

    public static double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison;

    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_for_KL_Divergence = new double[] { 10.0, 15.0, 20.0, 25.0, 30.0 };

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        privacy budget 的变化
     */
    //---固定值-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static Double DEFAULT_PRIVACY_BUDGET;    // todo: version 2
    public static Double DEFAULT_PRIVACY_BUDGET_for_b_change;
    public static Double DEFAULT_PRIVACY_BUDGET_for_DAM_and_SubsetGeoI_Comparison;  // todo: version 8

    public static final double DEFAULT_PRIVACY_BUDGET_for_DAM_and_SubsetGeoI_Comparison_for_KL_Divergence = 6.0; // 要取最大的

    //---变动值-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static double[] ALTER_PRIVACY_BUDGET_ARRAY ;
    public static double[] ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison;
    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_for_KL_Divergence = new double[] { 6.0, 6.7, 7.4, 8.1, 8.8 };

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static final double[] FINE_GRIT_PRIVACY_BUDGET_ARRAY = BasicArrayUtil.getIncreasedoubleNumberArray(0.35, 0.01, 5.05, eliminateDoubleErrorIndexSize);

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        k 的变化
     */
    public static final double DEFAULT_K_PARAMETER = 0.0;
    public static final double DEFAULT_K_PARAMETER_for_change_b = 0.25;
    public static final double DEFAULT_K_PARAMETER_for_RAM = DEFAULT_K_PARAMETER;
    public static final double[] ALTER_K_PARAMETER_ARRAY = new double[] {
            0.0, 0.25, 0.5, 0.75, 1.0
    };

    // 实验相对路径
    public static String basicResultPath;
    public static String extendedResultPath;

    public static String damBudgetLPTableGeneratedTotalPath;
    public static String damBudgetLPTableGeneratedPathOnlyForBasic;
    public static String damBudgetLPTableGeneratedPathOnlyForExtended;
    public static String damBudgetLPTableGeneratedPathOnlyForKLDivergence;
    public static String subsetGeoIBudgetLPTableGeneratedTotalPath;
    public static String subsetGeoIBudgetLPTableGeneratedPathOnlyForBasic;
    public static String subsetGeoIBudgetLPTableGeneratedPathOnlyForExtended;
    public static String subsetGeoIBudgetLPTableGeneratedPathOnlyForKLDivergence;


    static {
        configPath = StringUtil.join(ConstantValues.FILE_SPLIT, projectPath, "config", "parameter_config.xml");
        File configFile = new File(configPath);
        if (!configFile.exists()) {
            configPath = StringUtil.join(ConstantValues.FILE_SPLIT, projectPath, "deployment", "config", "parameter_config.xml");
            configFile = new File(configPath);
            if (!configFile.exists()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Not find the parameter_config.xml").append(ConstantValues.LINE_SPLIT);
                throw new RuntimeException(stringBuilder.toString());
            }
        }
        xmlConfigure = new XMLConfigure(configPath);
        String propertyFileName = ConfigureUtils.getPropertyName();
        propertyPath = StringUtil.join(ConstantValues.FILE_SPLIT, projectPath, "config", propertyFileName);
        configFile = new File(propertyPath);
        if (!configFile.exists()) {
            propertyPath = StringUtil.join(ConstantValues.FILE_SPLIT, projectPath, "deployment", "config", propertyFileName);
            configFile = new File(propertyPath);
            if (!configFile.exists()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Not find the parameter.properties").append(ConstantValues.LINE_SPLIT);
                throw new RuntimeException(stringBuilder.toString());
            }
        }

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertyPath));
            DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_b_change = Double.valueOf(properties.getProperty("DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_b_change"));
            DEFAULT_SIDE_LENGTH_NUMBER_SIZE = Double.valueOf(properties.getProperty("DEFAULT_SIDE_LENGTH_NUMBER_SIZE"));
            DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison = Double.valueOf(properties.getProperty("DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison"));

            ALTER_SIDE_LENGTH_NUMBER_SIZE = BasicArrayUtil.toDouArray(PropertyUtil.getValueArryStringByKey(properties, "ALTER_SIDE_LENGTH_NUMBER_SIZE", Constant.propertySplit));
            ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison = BasicArrayUtil.toDouArray(PropertyUtil.getValueArryStringByKey(properties, "ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison", Constant.propertySplit));

            DEFAULT_PRIVACY_BUDGET = Double.valueOf(properties.getProperty("DEFAULT_PRIVACY_BUDGET"));
            DEFAULT_PRIVACY_BUDGET_for_b_change = Double.valueOf(properties.getProperty("DEFAULT_PRIVACY_BUDGET_for_b_change"));
            DEFAULT_PRIVACY_BUDGET_for_DAM_and_SubsetGeoI_Comparison = Double.valueOf(properties.getProperty("DEFAULT_PRIVACY_BUDGET_for_DAM_and_SubsetGeoI_Comparison"));

            ALTER_PRIVACY_BUDGET_ARRAY = BasicArrayUtil.toDouArray(PropertyUtil.getValueArryStringByKey(properties, "ALTER_PRIVACY_BUDGET_ARRAY", Constant.propertySplit));
            ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison = BasicArrayUtil.toDouArray(PropertyUtil.getValueArryStringByKey(properties, "ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison", Constant.propertySplit));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 实验路径
        basicDatasetPath = ConfigureUtils.getDatasetBasicPath();

//        crimeFileNameList = ConfigureUtils.getDatasetSimpleFileNameList("crime");
//        nycFileNameList = ConfigureUtils.getDatasetSimpleFileNameList("nyc");
//        twoNormalFileName = ConfigureUtils.getDatasetSimpleFileNameList("norm_2").get(0);
//        twoZipFFileName = ConfigureUtils.getDatasetSimpleFileNameList("zip_f_2").get(0);
//        twoNormalMultipleCenterFileName = ConfigureUtils.getDatasetSimpleFileNameList("norm_multiple_center_2").get(0);
//
//        crimeFilePathList = ConfigureUtils.getDatasetRelativeFilePathList("crime");
//        nycFilePathList = ConfigureUtils.getDatasetRelativeFilePathList("nyc");
//        twoNormalFilePath = ConfigureUtils.getDatasetRelativeFilePathList("norm_2").get(0);
//        twoZipFFilePath = ConfigureUtils.getDatasetRelativeFilePathList("zip_f_2").get(0);
//        twoNormalMultipleCenterFilePath = ConfigureUtils.getDatasetRelativeFilePathList("norm_multiple_center_2").get(0);


        // 实验相对路径
        basicResultPath = StringUtil.join(ConstantValues.FILE_SPLIT, projectPath, "result");
        extendedResultPath = StringUtil.join(ConstantValues.FILE_SPLIT, projectPath, "result_extended");


        damBudgetLPTableGeneratedTotalPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "budgetLPTable", "damBudgetLPTable.txt");
        damBudgetLPTableGeneratedPathOnlyForBasic = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "budgetLPTable", "damBudgetLPTable_basic.txt");
        damBudgetLPTableGeneratedPathOnlyForExtended = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "budgetLPTable", "damBudgetLPTable_extended.txt");
        damBudgetLPTableGeneratedPathOnlyForKLDivergence = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "budgetLPTable", "damBudgetLPTable2.txt");
        subsetGeoIBudgetLPTableGeneratedTotalPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "budgetLPTable", "geoIBudgetLPTable.txt");
        subsetGeoIBudgetLPTableGeneratedPathOnlyForBasic = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "budgetLPTable", "geoIBudgetLPTable_basic.txt");
        subsetGeoIBudgetLPTableGeneratedPathOnlyForExtended = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "budgetLPTable", "geoIBudgetLPTable_extended.txt");
        subsetGeoIBudgetLPTableGeneratedPathOnlyForKLDivergence = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "budgetLPTable", "geoIBudgetLPTable2.txt");
    }



    /**
     * 生成 LP Table 所需要的参数
     */
    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Total = BasicArrayUtil.getIncreasedoubleNumberArray(1.0, 1.0, 30.0, 2);
//    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Extended = new double[]{10.0, 15.0, 20.0, 25.0, 30.0};
//    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Extended = new double[]{5.0, 10, 15.0, 20.0, 25.0};
    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Basic = new double[]{1.0, 2.0, 3.0, 4.0, 5.0};
//    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Extended = new double[]{5.0, 10.0, 15.0, 20.0, 25.0};
    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Extended = ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Total;
    public static final double[] ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_KL = new double[]{10.0, 15.0, 20.0, 25.0, 30.0};

//    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Total = BasicArray.getIncreasedoubleNumberArray(0.67, 0.01, 10, eliminateDoubleErrorIndexSize);   // todo: version 1

    // 对于太小的budget，subsetGeoI会内存溢出
    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Total = BasicArrayUtil.getIncreasedoubleNumberArray(0.3, 0.01, 10, eliminateDoubleErrorIndexSize);   // todo: version 5
    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Basic = BasicArrayUtil.getIncreasedoubleNumberArray(0.3, 0.01, 10, eliminateDoubleErrorIndexSize);
    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Extended = BasicArrayUtil.getIncreasedoubleNumberArray(0.67, 0.01, 10, eliminateDoubleErrorIndexSize);
    public static final double[] ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_KL = BasicArrayUtil.getIncreasedoubleNumberArray(0.67, 0.01, 10, eliminateDoubleErrorIndexSize);






    /**
     *  记录scheme的Key
     */
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
            alterBudgetKey,
            alterGKey
    };

    // 记录数据集名称
//    public static final String chicagoAKey = "ChicagoA";
//    public static final String chicagoBKey = "ChicagoB";
//    public static final String chicagoCKey = "ChicagoC";
    public static final String chicagoKey = "Chicago";


//    public static final String nycAKey = "NYCA";
//    public static final String nycBKey = "NYCB";
//    public static final String nycCKey = "NYCC";
    public static final String nycKey = "NYC";
    public static final String normalKey = "TwoDimNormal";
    public static final String zipfKey = "TwoDimZipf";
    public static final String multiNormalKey = "TwoDimMultiCenterNormal";

    // 记录数据集路径
//    public static final String chicagoAPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "1_crime", "chicago_point_A.txt");
//    public static final String chicagoBPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "1_crime", "chicago_point_B.txt");
//    public static final String chicagoCPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "1_crime", "chicago_point_C.txt");
//    public static final String chicagoPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "1_real", "1_crime", "chicago_point.txt");

//    public static final String nycAPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "2_nyc", "nyc_point_A.txt");
//    public static final String nycBPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "2_nyc", "nyc_point_B.txt");
//    public static final String nycCPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "1_real", "2_nyc", "nyc_point_C.txt");
//    public static final String nycPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "1_real", "2_nyc", "nyc_point.txt");

//    public static final String normalPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "2_synthetic", "1_two_normal", "two_normal_point_extract.txt");
//    public static final String normalPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "2_synthetic", "1_two_normal", "two_normal_point.txt");
//    public static final String zipfPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "2_synthetic", "2_two_zipf", "two_zipf_point.txt");

//    public static final String multiNormalPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "2_synthetic", "3_two_normal_multiple_center", "two_normal_point_multiple_centers_extract.txt");
//    public static final String multiNormalPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, "2_synthetic", "3_two_normal_multiple_center", "two_normal_point_multiple_centers.txt");

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


    //中间result记录
    public static final String X_INDEX = "xIndex";
    public static final String Y_INDEX = "yIndex";
    public static final String DISTRIBUTION_VALUE = "distributionValue";
    public static final List<String> distributionTitle = ListUtils.valueOf(X_INDEX, Y_INDEX, DISTRIBUTION_VALUE);

    public static final String B_CHANGE_DIRECTORY_NAME = "1_best_b_change";
    public static final String BUDGET_CHANGE_DIRECTORY_NAME = "2_budget_change";
    public static final String D_CHANGE_DIRECTORY_NAME = "3_d_change";

    public static final String RAW_DATA_DEFAULT_DISTRIBUTION_FILE_NAME = "original_distribution.txt";


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
    public static final String klDivergenceKey = "KLDivergence";

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
            new ColumnBean(13, wassersteinDistance2Key, "java.lang.Double", true),
            new ColumnBean(14, klDivergenceKey, "java.lang.Double", true)

    };

    public static final ColumnBean[] columnBeanArrayForKLDivergence = new ColumnBean[] {
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
            new ColumnBean(12, klDivergenceKey, "java.lang.Double", true)

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

    /**
     * The leftBottom must no greater than real left and bottom point
     * The sum of leftBottom and length is strictly greater than the real right and top point
     */
    public static final DataSetAreaInfo[] crimeDataSetArray = ConfigureUtils.getDatasetInfoArray(basicDatasetPath, "crime");
    public static final DataSetAreaInfo crimeDataSet = ConfigureUtils.getDatasetInfoArray(basicDatasetPath, "crime2")[0];
//    public static final DataSetAreaInfo[] crimeDataSetArray = new DataSetAreaInfo[]{
//            new DataSetAreaInfo(chicagoAPath, chicagoAKey, 41.72, -87.68, 0.09),
//            new DataSetAreaInfo(chicagoBPath, chicagoBKey, 41.82, -87.73, 0.09),
//            new DataSetAreaInfo(chicagoCPath, chicagoCKey, 41.92, -87.77,0.07)
//    };
//    public static final DataSetAreaInfo[] crimeDataSetArray = new DataSetAreaInfo[]{
//            new DataSetAreaInfo(chicagoPath, chicagoKey, 41.64459516, -87.93973294,0.416)
//    };
//    public static final DataSetAreaInfo[] nycDataSetArray = new DataSetAreaInfo[]{
//            new DataSetAreaInfo(nycAPath, nycAKey, 40.65, -73.84, 0.10),
//            new DataSetAreaInfo(nycBPath, nycBKey, 40.65, -73.95, 0.09),
//            new DataSetAreaInfo(nycCPath, nycCKey, 40.82, -73.90,0.07)
//    };
    public static final DataSetAreaInfo[] nycDataSetArray = ConfigureUtils.getDatasetInfoArray(basicDatasetPath, "nyc");
    public static final DataSetAreaInfo nycDataSet = ConfigureUtils.getDatasetInfoArray(basicDatasetPath, "nyc2")[0];
//    public static final DataSetAreaInfo[] nycDataSetArray = new DataSetAreaInfo[]{
//            new DataSetAreaInfo(nycPath, nycKey, 0.0, -75.1675,75.1676)
//    };

    public static final DataSetAreaInfo twoDimNormalDataSet = ConfigureUtils.getDatasetInfoArray(basicDatasetPath, "norm_2")[0];
//    public static final DataSetAreaInfo twoDimNormalDataSet = new DataSetAreaInfo(normalPath, normalKey, -4.44, -4.87, 9.45);
    public static final DataSetAreaInfo twoDimZipfDataSet = ConfigureUtils.getDatasetInfoArray(basicDatasetPath, "zip_f_2")[0];
//    public static final DataSetAreaInfo twoDimZipfDataSet = new DataSetAreaInfo(zipfPath, zipfKey, 0.0, 0.0, 1.0);
    public static final DataSetAreaInfo twoDimMultipleCenterNormalDataSet = ConfigureUtils.getDatasetInfoArray(basicDatasetPath, "norm_multiple_center_2")[0];
//    public static final DataSetAreaInfo twoDimMultipleCenterNormalDataSet = new DataSetAreaInfo(multiNormalPath, multiNormalKey, -4.25, -4.32, 10.76);

    // for LDPTrace
    public static final double LDPTraceAlpha = 0.3;
    public static final double LDPTraceBeta = 0.2;
    public static final double LDPTraceLambda = 2.5;

    // for ATP
    public static final List<Integer> CandidateSectorSizeListForNYC = Arrays.asList(5, 10, 15, 20);
    public static final List<Integer> CandidateSectorSizeListForCLE = Arrays.asList(2, 4, 6, 8);


    public static final Integer SampleTrajectoryGridSideLength = 300;
    public static final Integer TrajectorySamplingSize = 10000;
    // 这里的轨迹长度是指轨迹中关注点的数量
    public static final Integer TrajectorySamplingLengthLowerBound = 2;
    public static final Integer TrajectorySamplingLengthUpperBound = 200;




    public static void main0(String[] args) {
        System.out.println(projectPath);
        System.out.println(propertyPath);
        MyPrint.showSplitLine("*", 150);

        System.out.println("DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_b_change: " + DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_b_change);
        System.out.println("DEFAULT_PRIVACY_BUDGET_for_b_change: " + DEFAULT_PRIVACY_BUDGET_for_b_change);
        MyPrint.showSplitLine("*", 150);

        System.out.println("DEFAULT_SIDE_LENGTH_NUMBER_SIZE: " + DEFAULT_SIDE_LENGTH_NUMBER_SIZE);
        System.out.print("ALTER_SIDE_LENGTH_NUMBER_SIZE: ");
        MyPrint.showDoubleArray(ALTER_SIDE_LENGTH_NUMBER_SIZE);
        System.out.print("ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison: ");
        MyPrint.showDoubleArray(ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison);
        MyPrint.showSplitLine("*", 150);

        System.out.println("DEFAULT_PRIVACY_BUDGET: " + DEFAULT_PRIVACY_BUDGET);
        System.out.println("DEFAULT_PRIVACY_BUDGET_for_DAM_and_SubsetGeoI_Comparison: " + DEFAULT_PRIVACY_BUDGET_for_DAM_and_SubsetGeoI_Comparison);
        System.out.print("ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison: ");
        MyPrint.showDoubleArray(ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison);





    }

    public static void main(String[] args) {
        String propertyPath = Constant.propertyPath;
        System.out.println(propertyPath);
    }
























}
