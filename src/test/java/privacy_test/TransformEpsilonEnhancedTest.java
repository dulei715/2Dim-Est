package privacy_test;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.collection.ListUtils;
import cn.edu.ecnu.constant_values.ConstantValues;
import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePointUtils;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.analysis.lp_to_e.TransformEpsilonEnhanced;
import org.junit.Test;

import java.util.List;

public class TransformEpsilonEnhancedTest {
    @Test
    public void fun1() {
        String inputPointPath = "D:\\temp\\swap_data\\subGeoI_norm1_1.txt";
        List<TwoDimensionalDoublePoint> pointList = TwoDimensionalPointRead.readPointWithFirstLineCount(inputPointPath);
//        MyPrint.showList(pointList, ConstantValues.LINE_SPLIT);
        List<Double> xIndexList = TwoDimensionalDoublePointUtils.getXIndexList(pointList);
        List<Double> yIndexList = TwoDimensionalDoublePointUtils.getYIndexList(pointList);
//        MyPrint.showList(xIndexList, ConstantValues.LINE_SPLIT);
//        MyPrint.showList(yIndexList, ConstantValues.LINE_SPLIT);
        double[] privacyBudgetArray = ArraysUtils.toBasicDoubleArray(xIndexList);
        double[] localPrivacyArray = ArraysUtils.toBasicDoubleArray(yIndexList);
        TransformEpsilonEnhanced transformEpsilonEnhanced = new TransformEpsilonEnhanced(privacyBudgetArray, localPrivacyArray);
        List<TwoDimensionalDoublePoint> effectivePoint = transformEpsilonEnhanced.getEffectivePoint();
        MyPrint.showList(effectivePoint, ConstantValues.LINE_SPLIT);
    }
}
