package tool_test;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.io.print.MyPrint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.analysis.lp_to_e.SubsetGeoITransformEpsilon;
import ecnu.dll.construction.run.main_process.b_comparision_run.tool.SubsetGeoIEpsilonLocalPrivacyTable;
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
        tableStruct.writeTable(outputPath);

        SubsetGeoIEpsilonLocalPrivacyTable tableReadStruct = SubsetGeoIEpsilonLocalPrivacyTable.readTable(outputPath);
        double[][] readTable = tableReadStruct.getlPTable();
        MyPrint.show2DimensionDoubleArray(readTable);


    }
}
