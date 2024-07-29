package ecnu.dll.construction.run._0_base_run.basic_preprocess;

import cn.edu.dll.basic.BasicArrayUtil;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.run._1_total_run.main_process.b_comparision_run.extended.tool.SubsetGeoIEpsilonLocalPrivacyTable;

public class SubsetGeoIEpsilonLPTableGeneration {

    public static void generateTotalTable() {
        double[] sizeDArrayValue = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Total;
        double[] budgetArrayValue = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Total;
        Double[] sizeDArray = BasicArrayUtil.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = BasicArrayUtil.toDoubleArray(budgetArrayValue);
        SubsetGeoIEpsilonLocalPrivacyTable tableStruct = new SubsetGeoIEpsilonLocalPrivacyTable(sizeDArray, budgetArray);
        tableStruct.writeLPTable(Constant.subsetGeoIBudgetLPTableGeneratedTotalPath);
        System.out.println("Finish total table generation for SubsetGeoI!");
    }
    public static void generateBasicTable() {
        double[] sizeDArrayValue = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Basic;
        double[] budgetArrayValue = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Basic;
        Double[] sizeDArray = BasicArrayUtil.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = BasicArrayUtil.toDoubleArray(budgetArrayValue);
        SubsetGeoIEpsilonLocalPrivacyTable tableStruct = new SubsetGeoIEpsilonLocalPrivacyTable(sizeDArray, budgetArray);
        tableStruct.writeLPTable(Constant.subsetGeoIBudgetLPTableGeneratedPathOnlyForBasic);
        System.out.println("Finish total table generation for SubsetGeoI!");
    }

    public static void generateExtendedTable() {
        double[] sizeDArrayValue = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Extended;
        double[] budgetArrayValue = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Extended;
        Double[] sizeDArray = BasicArrayUtil.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = BasicArrayUtil.toDoubleArray(budgetArrayValue);
        SubsetGeoIEpsilonLocalPrivacyTable tableStruct = new SubsetGeoIEpsilonLocalPrivacyTable(sizeDArray, budgetArray);
        tableStruct.writeLPTable(Constant.subsetGeoIBudgetLPTableGeneratedPathOnlyForExtended);
        System.out.println("Finish extended table generation for SubsetGeoI!");
    }
    public static void generateKLTable() {
        double[] sizeDArrayValue = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_KL;
        double[] budgetArrayValue = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_KL;
        Double[] sizeDArray = BasicArrayUtil.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = BasicArrayUtil.toDoubleArray(budgetArrayValue);
        SubsetGeoIEpsilonLocalPrivacyTable tableStruct = new SubsetGeoIEpsilonLocalPrivacyTable(sizeDArray, budgetArray);
        tableStruct.writeLPTable(Constant.subsetGeoIBudgetLPTableGeneratedPathOnlyForKLDivergence);
        System.out.println("Finish extended table generation for SubsetGeoI!");
    }
    public static void main(String[] args) {
//        generateTotalTable();
        generateExtendedTable();
//        generateBasicTable();
    }
}
