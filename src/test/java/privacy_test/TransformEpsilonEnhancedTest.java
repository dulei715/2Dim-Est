package privacy_test;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePointUtils;
import ecnu.dll.construction.analysis.lp_to_e.version_1.TransformEpsilonEnhanced;
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
        double[] privacyBudgetArray = BasicArrayUtil.toBasicDoubleArray(xIndexList);
        double[] localPrivacyArray = BasicArrayUtil.toBasicDoubleArray(yIndexList);
        TransformEpsilonEnhanced transformEpsilonEnhanced = new TransformEpsilonEnhanced(privacyBudgetArray, localPrivacyArray);
        List<TwoDimensionalDoublePoint> effectivePoint = transformEpsilonEnhanced.getEffectivePoint();
        MyPrint.showList(effectivePoint, ConstantValues.LINE_SPLIT);
    }
}
