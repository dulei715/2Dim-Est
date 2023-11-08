package tool_test;

import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.io.print.MyPrint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.run._1_total_run.main_process.b_comparision_run.extended.tool.SubsetGeoIEpsilonLocalPrivacyTable;
import org.junit.Test;

public class SubsetGeoIEpsilonLocalPrivacyTableTest {
    @Test
    public void fun1() {
        double[] sizeDArrayValue = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE;
        double[] budgetArrayValue = Constant.ALTER_PRIVACY_BUDGET_ARRAY;
//        double[] sizeDArrayValue = BasicArray.getIncreasedoubleNumberArray(5.0, 5, 25);
//        double[] budgetArrayValue = BasicArray.getIncreasedoubleNumberArray(0.5, 0.01, 4);
        Double[] sizeDArray = ArraysUtils.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = ArraysUtils.toDoubleArray(budgetArrayValue);
        SubsetGeoIEpsilonLocalPrivacyTable tableStruct = new SubsetGeoIEpsilonLocalPrivacyTable(sizeDArray, budgetArray);

        double[][] lpTable = tableStruct.getlPTable();
        MyPrint.show2DimensionDoubleArray(lpTable);

        MyPrint.showSplitLine("+", 150);

        String outputPath = "E:\\temp\\budgetLPTable.txt";
        tableStruct.writeLPTable(outputPath);

        SubsetGeoIEpsilonLocalPrivacyTable tableReadStruct = SubsetGeoIEpsilonLocalPrivacyTable.readTable(outputPath);
        double[][] readTable = tableReadStruct.getlPTable();
        MyPrint.show2DimensionDoubleArray(readTable);

    }

    @Test
    public void fun2() {
        double[] sizeDArrayValue = new double[] {
                1.0, 2.0, 3.0, 4.0, 5.0
        };
        double[] budgetArrayValue = new double[] {
                0.5, 1.2, 1.9, 2.6, 3.3
        };
        Double[] sizeDArray = ArraysUtils.toDoubleArray(sizeDArrayValue);
        Double[] budgetArray = ArraysUtils.toDoubleArray(budgetArrayValue);
        SubsetGeoIEpsilonLocalPrivacyTable tableStruct = new SubsetGeoIEpsilonLocalPrivacyTable(sizeDArray, budgetArray);

        double[][] lpTable = tableStruct.getlPTable();
        MyPrint.show2DimensionDoubleArray(lpTable);

        System.out.println(tableStruct.getLocalPrivacyByEpsilon(2.0, 1.9));
        System.out.println(tableStruct.getEpsilonByLocalPrivacy(3.0, 1.2));

    }
}
