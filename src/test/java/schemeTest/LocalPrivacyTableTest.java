package schemeTest;

import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.DAMEpsilonLocalPrivacyTable;
import org.junit.Test;

public class LocalPrivacyTableTest {
    @Test
    public void fun1() {
        String inputPath = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\budgetLPTable\\damBudgetLPTable.txt";
        String outputDamBudgetLPTableUpperBoundPath = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\budgetLPTable\\bound\\damBudgetLPTableUpperBound.txt";
        String outputDamBudgetLPTableLowerBoundPath = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\budgetLPTable\\bound\\damBudgetLPTableLowerBound.txt";
        DAMEpsilonLocalPrivacyTable damTable = DAMEpsilonLocalPrivacyTable.readTable(inputPath);
        damTable.writeUpperBoundLPTable(outputDamBudgetLPTableUpperBoundPath);
        damTable.writeLowerBoundLPTable(outputDamBudgetLPTableLowerBoundPath);
    }

    @Test
    public void fun2() {
        String inputPath = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\budgetLPTable\\geoIBudgetLPTable.txt";
        String outputDamBudgetLPTableUpperBoundPath = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\budgetLPTable\\bound\\geoIBudgetLPTableUpperBound.txt";
        String outputDamBudgetLPTableLowerBoundPath = "E:\\1.学习\\4.数据集\\2.dataset_for_spatial_estimation\\budgetLPTable\\bound\\geoIBudgetLPTableLowerBound.txt";
        DAMEpsilonLocalPrivacyTable damTable = DAMEpsilonLocalPrivacyTable.readTable(inputPath);
        damTable.writeUpperBoundLPTable(outputDamBudgetLPTableUpperBoundPath);
        damTable.writeLowerBoundLPTable(outputDamBudgetLPTableLowerBoundPath);
    }

}
