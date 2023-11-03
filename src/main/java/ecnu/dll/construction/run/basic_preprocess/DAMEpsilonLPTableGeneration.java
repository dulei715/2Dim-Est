package ecnu.dll.construction.run.basic_preprocess;

import cn.edu.ecnu.collection.ArraysUtils;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.DAMEpsilonLocalPrivacyTable;

public class DAMEpsilonLPTableGeneration {

    public static void generateTotalTable() {
        double[] sizeDArrayValue = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Total;
        double[] budgetArrayValue = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Total;
        Double[] sizeDArray = ArraysUtils.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = ArraysUtils.toDoubleArray(budgetArrayValue);
        DAMEpsilonLocalPrivacyTable tableStruct = new DAMEpsilonLocalPrivacyTable(sizeDArray, budgetArray);
        tableStruct.writeLPTable(Constant.damBudgetLPTableGeneratedTotalPath);
        System.out.println("Finish total table generation for DAM!");
    }

    public static void generateKLTable() {
        double[] sizeDArrayValue = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_KL;
        double[] budgetArrayValue = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_KL;
        Double[] sizeDArray = ArraysUtils.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = ArraysUtils.toDoubleArray(budgetArrayValue);
        DAMEpsilonLocalPrivacyTable tableStruct = new DAMEpsilonLocalPrivacyTable(sizeDArray, budgetArray);
        tableStruct.writeLPTable(Constant.damBudgetLPTableGeneratedPathOnlyForKLDivergence);
        System.out.println("Finish extended table generation for DAM!");
    }

    public static void generateBasicTable() {
        double[] sizeDArrayValue = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Basic;
        double[] budgetArrayValue = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Basic;
        Double[] sizeDArray = ArraysUtils.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = ArraysUtils.toDoubleArray(budgetArrayValue);
        DAMEpsilonLocalPrivacyTable tableStruct = new DAMEpsilonLocalPrivacyTable(sizeDArray, budgetArray, Constant.damBudgetLPTableGeneratedPathOnlyForBasic);
        tableStruct.writeLPTable(Constant.damBudgetLPTableGeneratedPathOnlyForBasic);
        System.out.println("Finish extended table generation for DAM!");
    }
    public static void generateExtendedTable() {
        double[] sizeDArrayValue = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison_Extended;
        double[] budgetArrayValue = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison_Extended;
        Double[] sizeDArray = ArraysUtils.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = ArraysUtils.toDoubleArray(budgetArrayValue);
        DAMEpsilonLocalPrivacyTable tableStruct = new DAMEpsilonLocalPrivacyTable(sizeDArray, budgetArray, Constant.damBudgetLPTableGeneratedPathOnlyForExtended);
        tableStruct.writeLPTable(Constant.damBudgetLPTableGeneratedPathOnlyForExtended);
        System.out.println("Finish extended table generation for DAM!");
    }

    public static void main(String[] args) {
//        generateTotalTable();
        generateExtendedTable();
//        generateBasicTable();
    }
}
