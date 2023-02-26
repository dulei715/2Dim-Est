package ecnu.dll.construction.run.basic_preprocess;

import cn.edu.ecnu.collection.ArraysUtils;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.DAMEpsilonLocalPrivacyTable;
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.SubsetGeoIEpsilonLocalPrivacyTable;

public class DAMEpsilonLPTableGeneration {
    public static void main(String[] args) {
        double[] sizeDArrayValue = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison;
        double[] budgetArrayValue = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_SubsetGeoI_Comparison;
        Double[] sizeDArray = ArraysUtils.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = ArraysUtils.toDoubleArray(budgetArrayValue);
        DAMEpsilonLocalPrivacyTable tableStruct = new DAMEpsilonLocalPrivacyTable(sizeDArray, budgetArray);
        tableStruct.writeTable(Constant.damBudgetLPTableGeneratedPath);
    }
}
