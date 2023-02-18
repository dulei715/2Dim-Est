package privacy_test;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.OneNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.struct.point.DoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.*;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.GeoILocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.RAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.basic.TransformLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.basic_impl.BasicGeoILocalPrivacy;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.DiscretizedRhombusScheme;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class LocalPrivacyToolTestFor1Norm {
    private static double privacyBudget = 2.0;
    private static double gridLength = 1.0;
    private static double inputLength = 4.0;
    private static double kParameter = 0.5;
    private static double xLeft = 0.0;
    private static double yLeft = 0.0;

    public static Double[] getLocalPrivacyValueByPrivacyBudgetForRAM(Double[] privacyBudgetArray) {


        DiscretizedRhombusScheme scheme = new DiscretizedRhombusScheme(0.1, gridLength, inputLength, kParameter, xLeft, yLeft);
        RAMLocalPrivacy ramLocalPrivacy = new Norm1RAMLocalPrivacy(scheme);
        Double[] resultLPArray = new Double[privacyBudgetArray.length];

        for (int i = 0; i < privacyBudgetArray.length; i++) {
            ramLocalPrivacy.resetEpsilon(privacyBudgetArray[i]);
            resultLPArray[i] = ramLocalPrivacy.getTransformLocalPrivacyValue();
        }
        return resultLPArray;
    }
    public static Double[] getLocalPrivacyValueByPrivacyBudgetForDAM(Double[] privacyBudgetArray) {


        DiscretizedDiskScheme scheme = new DiscretizedDiskScheme(0.1, gridLength, inputLength, kParameter, xLeft, yLeft);
        DAMLocalPrivacy damLocalPrivacy = new Norm1DAMLocalPrivacy(scheme);
        Double[] resultLPArray = new Double[privacyBudgetArray.length];

        for (int i = 0; i < privacyBudgetArray.length; i++) {
            damLocalPrivacy.resetEpsilon(privacyBudgetArray[i]);
            resultLPArray[i] = damLocalPrivacy.getTransformLocalPrivacyValue();
        }
        return resultLPArray;
    }

    public static Double[] getLocalPrivacyValueByPrivacyBudgetForGeoI(Double[] privacyBudgetArray) throws InstantiationException, IllegalAccessException {

        Integer setSizeK;
        Double omega;
        Double[] massArray;
        List<TwoDimensionalIntegerPoint> rawTwoDimensionalIntegerPointTypeList, outputTypeList;

//        DiscretizedRhombusScheme ram = new DiscretizedRhombusScheme(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);

        DiscretizedSubsetExponentialGeoI scheme = new DiscretizedSubsetExponentialGeoI(1.0, gridLength, inputLength, xLeft, yLeft, new OneNormTwoDimensionalIntegerPointDistanceTor());
        GeoILocalPrivacy geoILocalPrivacy = new Norm1GeoILocalPrivacy(scheme);
        Double[] resultLPArray = new Double[privacyBudgetArray.length];

        for (int i = 0; i < privacyBudgetArray.length; i++) {
            geoILocalPrivacy.resetEpsilon(privacyBudgetArray[i]);
            resultLPArray[i] = geoILocalPrivacy.getTransformLocalPrivacyValue();
        }
        return resultLPArray;
    }







    @Test
    public void resetFun() throws IllegalAccessException, InstantiationException {

        Integer[] tempArray = BasicArray.getIncreaseIntegerNumberArray(1, 10, 2000);
        Double[] privacyBudgetArray = BasicArray.getLinearTransformFromIntegerArray(tempArray, 0.001, 0);
        MyPrint.showArray(privacyBudgetArray);

        // For RAM parameters
        Double[] lpResultArrayForRAM = getLocalPrivacyValueByPrivacyBudgetForRAM(privacyBudgetArray);


        // For DAM parameters
        Double[] lpResultArrayForDAM = getLocalPrivacyValueByPrivacyBudgetForDAM(privacyBudgetArray);


        // For Subset-Geo-I
        Double[] lpResultArrayForSubSetGeoI = getLocalPrivacyValueByPrivacyBudgetForGeoI(privacyBudgetArray);

//        MyPrint.showDoubleArray(lpResultArray);
        int size = privacyBudgetArray.length;
        List<DoublePoint> doublePointListForRAM = new ArrayList<>(size);
        List<DoublePoint> doublePointListForDAM = new ArrayList<>(size);
        List<DoublePoint> doublePointListForSubsetGeoI = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            doublePointListForRAM.add(new TwoDimensionalDoublePoint(privacyBudgetArray[i], lpResultArrayForRAM[i]));
            doublePointListForDAM.add(new TwoDimensionalDoublePoint(privacyBudgetArray[i], lpResultArrayForDAM[i]));
            doublePointListForSubsetGeoI.add(new TwoDimensionalDoublePoint(privacyBudgetArray[i], lpResultArrayForSubSetGeoI[i]));
        }
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting("D:\\temp\\swap_data\\ram_norm1_1.txt");
        pointWrite.writePoint(doublePointListForRAM);
        pointWrite.endWriting();
        pointWrite.startWriting("D:\\temp\\swap_data\\dam_norm1_1.txt");
        pointWrite.writePoint(doublePointListForDAM);
        pointWrite.endWriting();
        pointWrite.startWriting("D:\\temp\\swap_data\\subGeoI_norm1_1.txt");
        pointWrite.writePoint(doublePointListForSubsetGeoI);
        pointWrite.endWriting();
    }




}
