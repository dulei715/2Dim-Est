package schemeTest;

import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.run._1_total_run.main_process.b_comparision_run.extended.tool.DAMEpsilonLocalPrivacyTable;
import org.junit.Test;

public class LocalPrivacyTableTest {

    public static String basicPath = Constant.rootPath;

    @Test
    public void fun1() {
        String inputPath = basicPath + "\\budgetLPTable\\damBudgetLPTable.txt";
        String outputDamBudgetLPTableUpperBoundPath = basicPath + "\\budgetLPTable\\bound\\damBudgetLPTableUpperBound.txt";
        String outputDamBudgetLPTableLowerBoundPath = basicPath + "\\budgetLPTable\\bound\\damBudgetLPTableLowerBound.txt";
        DAMEpsilonLocalPrivacyTable damTable = DAMEpsilonLocalPrivacyTable.readTable(inputPath);
        damTable.writeUpperBoundLPTable(outputDamBudgetLPTableUpperBoundPath);
        damTable.writeLowerBoundLPTable(outputDamBudgetLPTableLowerBoundPath);
    }

    @Test
    public void fun2() {
        String inputPath = basicPath + "\\budgetLPTable\\geoIBudgetLPTable.txt";
        String outputDamBudgetLPTableUpperBoundPath = basicPath + "\\budgetLPTable\\bound\\geoIBudgetLPTableUpperBound.txt";
        String outputDamBudgetLPTableLowerBoundPath = basicPath + "\\budgetLPTable\\bound\\geoIBudgetLPTableLowerBound.txt";
        DAMEpsilonLocalPrivacyTable damTable = DAMEpsilonLocalPrivacyTable.readTable(inputPath);
        damTable.writeUpperBoundLPTable(outputDamBudgetLPTableUpperBoundPath);
        damTable.writeLowerBoundLPTable(outputDamBudgetLPTableLowerBoundPath);
    }
    @Test
    public void fun3() {
        String inputPath = basicPath + "\\budgetLPTable\\damBudgetLPTable2.txt";
        String outputDamBudgetLPTableUpperBoundPath = basicPath + "\\budgetLPTable\\bound\\damBudgetLPTableUpperBound2.txt";
        String outputDamBudgetLPTableLowerBoundPath = basicPath + "\\budgetLPTable\\bound\\damBudgetLPTableLowerBound2.txt";
        DAMEpsilonLocalPrivacyTable damTable = DAMEpsilonLocalPrivacyTable.readTable(inputPath);
        damTable.writeUpperBoundLPTable(outputDamBudgetLPTableUpperBoundPath);
        damTable.writeLowerBoundLPTable(outputDamBudgetLPTableLowerBoundPath);
    }

    @Test
    public void fun4() {
        String inputPath = basicPath + "\\budgetLPTable\\geoIBudgetLPTable2.txt";
        String outputDamBudgetLPTableUpperBoundPath = basicPath + "\\budgetLPTable\\bound\\geoIBudgetLPTableUpperBound2.txt";
        String outputDamBudgetLPTableLowerBoundPath = basicPath + "\\budgetLPTable\\bound\\geoIBudgetLPTableLowerBound2.txt";
        DAMEpsilonLocalPrivacyTable damTable = DAMEpsilonLocalPrivacyTable.readTable(inputPath);
        damTable.writeUpperBoundLPTable(outputDamBudgetLPTableUpperBoundPath);
        damTable.writeLowerBoundLPTable(outputDamBudgetLPTableLowerBoundPath);
    }

}
