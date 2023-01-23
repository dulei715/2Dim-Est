package privacy_test;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.struct.point.DoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.Norm2DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.Norm2GeoILocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.Norm2RAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.GeoILocalPrivacy_TODO;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.RAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.basic.TransformLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.basic_impl.BasicGeoILocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.basic_impl.BasicRAMLocalPrivacy;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import ecnu.dll.construction.newscheme.discretization.AbstractDiscretizedScheme;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.DiscretizedRhombusScheme;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class LocalPrivacyToolTest {
    private static double privacyBudget = 2.0;
    private static double gridLength = 1.0;
    private static double inputLength = 4.0;
    private static double kParameter = 0.5;
    private static double xLeft = 0.0;
    private static double yLeft = 0.0;

    @Deprecated
    public static Double[] getLocalPrivacyValueByPrivacyBudgetForLDP(Double[] privacyBudgetArray, Class<? extends AbstractDiscretizedScheme> discretizedSchemeClass, Class<? extends TransformLocalPrivacy<TwoDimensionalIntegerPoint, TwoDimensionalIntegerPoint>> transLPClass) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Integer sizeD, bestSizeB;
        Double probabilityP, probabilityQ;
        List<TwoDimensionalIntegerPoint> rawTwoDimensionalIntegerPointTypeList, outputTypeList;

//        DiscretizedRhombusScheme ram = new DiscretizedRhombusScheme(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);

        TransformLocalPrivacy<TwoDimensionalIntegerPoint, TwoDimensionalIntegerPoint> transformLocalPrivacy;
        Constructor<? extends AbstractDiscretizedScheme> schemeConstructor;
        Constructor<? extends TransformLocalPrivacy<TwoDimensionalIntegerPoint, TwoDimensionalIntegerPoint>> lPConstructor;
        AbstractDiscretizedScheme scheme;
        Double[] resultLPArray = new Double[privacyBudgetArray.length];
        for (int i = 0; i < privacyBudgetArray.length; i++) {
            schemeConstructor = discretizedSchemeClass.getDeclaredConstructor(Double.class, Double.class, Double.class, Double.class, Double.class, Double.class);
            scheme = schemeConstructor.newInstance(privacyBudgetArray[i], gridLength, inputLength, kParameter, xLeft, yLeft);
            sizeD = scheme.getSizeD();
            bestSizeB = scheme.getOptimalSizeB();
            rawTwoDimensionalIntegerPointTypeList = scheme.getRawIntegerPointTypeList();
            outputTypeList = scheme.getNoiseIntegerPointTypeList();
            probabilityP = scheme.getConstP();
            probabilityQ = scheme.getConstQ();
            lPConstructor = transLPClass.getDeclaredConstructor(List.class, Integer.class, List.class, Integer.class, Double.class, Double.class);
            transformLocalPrivacy = lPConstructor.newInstance(rawTwoDimensionalIntegerPointTypeList, sizeD, outputTypeList, bestSizeB, probabilityP, probabilityQ);
            resultLPArray[i] = transformLocalPrivacy.getTransformLocalPrivacyValue();
        }
        return resultLPArray;
    }
    public static Double[] getLocalPrivacyValueByPrivacyBudgetForRAM(Double[] privacyBudgetArray) {


        DiscretizedRhombusScheme scheme = new DiscretizedRhombusScheme(0.1, gridLength, inputLength, kParameter, xLeft, yLeft);
        RAMLocalPrivacy ramLocalPrivacy = new Norm2RAMLocalPrivacy(scheme);
        Double[] resultLPArray = new Double[privacyBudgetArray.length];

        for (int i = 0; i < privacyBudgetArray.length; i++) {
            ramLocalPrivacy.resetEpsilon(privacyBudgetArray[i]);
            resultLPArray[i] = ramLocalPrivacy.getTransformLocalPrivacyValue();
        }
        return resultLPArray;
    }
    public static Double[] getLocalPrivacyValueByPrivacyBudgetForDAM(Double[] privacyBudgetArray) {


        DiscretizedDiskScheme scheme = new DiscretizedDiskScheme(0.1, gridLength, inputLength, kParameter, xLeft, yLeft);
        DAMLocalPrivacy damLocalPrivacy = new Norm2DAMLocalPrivacy(scheme);
        Double[] resultLPArray = new Double[privacyBudgetArray.length];

        for (int i = 0; i < privacyBudgetArray.length; i++) {
            damLocalPrivacy.resetEpsilon(privacyBudgetArray[i]);
            resultLPArray[i] = damLocalPrivacy.getTransformLocalPrivacyValue();
        }
        return resultLPArray;
    }

    @Deprecated
    public static Double[] getLocalPrivacyValueByPrivacyBudgetForGeoI(Double[] privacyBudgetArray, Class<? extends DiscretizedSubsetExponentialGeoI> discretizedSchemeClass, Class<BasicGeoILocalPrivacy> transLPClass, DistanceTor distanceTor) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Integer setSizeK;
        Double omega;
        Double[] massArray;
        List<TwoDimensionalIntegerPoint> rawTwoDimensionalIntegerPointTypeList, outputTypeList;

//        DiscretizedRhombusScheme ram = new DiscretizedRhombusScheme(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);

        TransformLocalPrivacy<TwoDimensionalIntegerPoint, Integer> transformLocalPrivacy;
        Constructor<? extends DiscretizedSubsetExponentialGeoI> schemeConstructor;
        Constructor<? extends TransformLocalPrivacy<TwoDimensionalIntegerPoint, Integer>> lPConstructor;
        DiscretizedSubsetExponentialGeoI scheme;
        Double[] resultLPArray = new Double[privacyBudgetArray.length];

        for (int i = 0; i < privacyBudgetArray.length; i++) {
            schemeConstructor = discretizedSchemeClass.getDeclaredConstructor(Double.class, Double.class, Double.class, Double.class, Double.class, DistanceTor.class);
            scheme = schemeConstructor.newInstance(privacyBudgetArray[i], gridLength, inputLength, xLeft, yLeft, distanceTor);
            rawTwoDimensionalIntegerPointTypeList = scheme.getSortedInputPointList();
//            outputTypeList = scheme.getNoiseIntegerPointTypeList();
            setSizeK = scheme.getSetSizeK();
            massArray = scheme.getMassArray();
            omega = scheme.getOmega();
            lPConstructor = transLPClass.getDeclaredConstructor(List.class, Integer.class, Double[].class, Double.class, Double.class, DistanceTor.class);
            transformLocalPrivacy = lPConstructor.newInstance(rawTwoDimensionalIntegerPointTypeList, setSizeK, massArray, privacyBudgetArray[i], omega, distanceTor);
            resultLPArray[i] = transformLocalPrivacy.getTransformLocalPrivacyValue();
        }
        return resultLPArray;
    }
    @Deprecated
    public static Double[] getLocalPrivacyValueBySizeDForGeoI(Double[] sizeDArray, Class<? extends DiscretizedSubsetExponentialGeoI> discretizedSchemeClass, Class<BasicGeoILocalPrivacy> transLPClass, DistanceTor distanceTor) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Integer setSizeK;
        Double omega;
        Double[] massArray;
        List<TwoDimensionalIntegerPoint> rawTwoDimensionalIntegerPointTypeList, outputTypeList;

//        DiscretizedRhombusScheme ram = new DiscretizedRhombusScheme(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);

        TransformLocalPrivacy<TwoDimensionalIntegerPoint, Integer> transformLocalPrivacy;
        Constructor<? extends DiscretizedSubsetExponentialGeoI> schemeConstructor;
        Constructor<? extends TransformLocalPrivacy<TwoDimensionalIntegerPoint, Integer>> lPConstructor;
        DiscretizedSubsetExponentialGeoI scheme;
        Double[] resultLPArray = new Double[sizeDArray.length];

        for (int i = 0; i < sizeDArray.length; i++) {
            schemeConstructor = discretizedSchemeClass.getDeclaredConstructor(Double.class, Double.class, Double.class, Double.class, Double.class, DistanceTor.class);
            scheme = schemeConstructor.newInstance(privacyBudget, gridLength, sizeDArray[i], xLeft, yLeft, distanceTor);
            rawTwoDimensionalIntegerPointTypeList = scheme.getSortedInputPointList();
//            outputTypeList = scheme.getNoiseIntegerPointTypeList();
            setSizeK = scheme.getSetSizeK();
            massArray = scheme.getMassArray();
            omega = scheme.getOmega();
            lPConstructor = transLPClass.getDeclaredConstructor(List.class, Integer.class, Double[].class, Double.class, Double.class, DistanceTor.class);
            transformLocalPrivacy = lPConstructor.newInstance(rawTwoDimensionalIntegerPointTypeList, setSizeK, massArray, privacyBudget, omega, distanceTor);
            resultLPArray[i] = transformLocalPrivacy.getTransformLocalPrivacyValue();
        }
        return resultLPArray;
    }
    public static Double[] getLocalPrivacyValueByPrivacyBudgetForGeoI(Double[] privacyBudgetArray) throws InstantiationException, IllegalAccessException {

        Integer setSizeK;
        Double omega;
        Double[] massArray;
        List<TwoDimensionalIntegerPoint> rawTwoDimensionalIntegerPointTypeList, outputTypeList;

//        DiscretizedRhombusScheme ram = new DiscretizedRhombusScheme(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);

        DiscretizedSubsetExponentialGeoI scheme = new DiscretizedSubsetExponentialGeoI(1.0, gridLength, inputLength, xLeft, yLeft, new TwoNormTwoDimensionalIntegerPointDistanceTor());
        GeoILocalPrivacy_TODO geoILocalPrivacy = new Norm2GeoILocalPrivacy(scheme);
        Double[] resultLPArray = new Double[privacyBudgetArray.length];

        for (int i = 0; i < privacyBudgetArray.length; i++) {
            geoILocalPrivacy.resetEpsilon(privacyBudgetArray[i]);
            resultLPArray[i] = geoILocalPrivacy.getTransformLocalPrivacyValue();
        }
        return resultLPArray;
    }

    public static void main0(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        Integer[] tempArray = BasicArray.getIncreaseIntegerNumberArray(1, 1, 10);
        Double[] privacyBudgetArray = BasicArray.getLinearTransformFromIntegerArray(tempArray, 0.1, 0);
        MyPrint.showArray(privacyBudgetArray, "; ");

        // For DAM parameters
        DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator = new TwoNormTwoDimensionalIntegerPointDistanceTor();
        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForLDP(privacyBudgetArray, DiscretizedRhombusScheme.class, BasicRAMLocalPrivacy.class);
        MyPrint.showDoubleArray(lpResultArray);

    }

    @Test
    public void reconstructFun() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        Integer[] tempArray = BasicArray.getIncreaseIntegerNumberArray(1, 10, 2000);
        Double[] privacyBudgetArray = BasicArray.getLinearTransformFromIntegerArray(tempArray, 0.001, 0);
//        MyPrint.showArray(privacyBudgetArray);

        // For RAM parameters
//        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForLDP(privacyBudgetArray, DiscretizedRhombusScheme.class, Norm1RAMLocalPrivacy.class);
//        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForLDP(privacyBudgetArray, DiscretizedRhombusScheme.class, Norm2RAMLocalPrivacy.class);

        // For DAM parameters
//        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForLDP(privacyBudgetArray, DiscretizedDiskScheme.class, Norm1DAMLocalPrivacy.class);
//        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForLDP(privacyBudgetArray, DiscretizedDiskScheme.class, Norm2DAMLocalPrivacy.class);

        // For Subset-Geo-I
        DistanceTor<TwoDimensionalIntegerPoint> distanceTor = new TwoNormTwoDimensionalIntegerPointDistanceTor();
        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForGeoI(privacyBudgetArray, DiscretizedSubsetExponentialGeoI.class, BasicGeoILocalPrivacy.class, distanceTor);

//        MyPrint.showDoubleArray(lpResultArray);
        int size = privacyBudgetArray.length;
        List<DoublePoint> doublePointList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            doublePointList.add(new TwoDimensionalDoublePoint(privacyBudgetArray[i], lpResultArray[i]));
        }
        PointWrite pointWrite = new PointWrite();
//        pointWrite.startWriting("D:\\temp\\swap_data\\ram_norm2.txt");
//        pointWrite.startWriting("D:\\temp\\swap_data\\dam_norm2.txt");
        pointWrite.startWriting("D:\\temp\\swap_data\\subGeoI_norm2.txt");
        pointWrite.writePoint(doublePointList);
        pointWrite.endWriting();
    }
    @Test
    public void resetFun() throws IllegalAccessException, InstantiationException {

        Integer[] tempArray = BasicArray.getIncreaseIntegerNumberArray(1, 10, 2000);
        Double[] privacyBudgetArray = BasicArray.getLinearTransformFromIntegerArray(tempArray, 0.001, 0);
//        MyPrint.showArray(privacyBudgetArray);

        // For RAM parameters
//        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForRAM(privacyBudgetArray);


        // For DAM parameters
//        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForDAM(privacyBudgetArray);


        // For Subset-Geo-I
        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForGeoI(privacyBudgetArray);

//        MyPrint.showDoubleArray(lpResultArray);
        int size = privacyBudgetArray.length;
        List<DoublePoint> doublePointList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            doublePointList.add(new TwoDimensionalDoublePoint(privacyBudgetArray[i], lpResultArray[i]));
        }
        PointWrite pointWrite = new PointWrite();
//        pointWrite.startWriting("D:\\temp\\swap_data\\ram_norm2_2.txt");
//        pointWrite.startWriting("D:\\temp\\swap_data\\dam_norm2_2.txt");
        pointWrite.startWriting("D:\\temp\\swap_data\\subGeoI_norm2_2.txt");
        pointWrite.writePoint(doublePointList);
        pointWrite.endWriting();
    }

    @Test
    public void reconstructForSizeDFun() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        Integer[] tempArray = BasicArray.getIncreaseIntegerNumberArray(1, 10, 4000);
        Double[] inputLengthArray = BasicArray.getLinearTransformFromIntegerArray(tempArray, 0.001, 5);
//        MyPrint.showArray(privacyBudgetArray);


        // For Subset-Geo-I
        DistanceTor<TwoDimensionalIntegerPoint> distanceTor = new TwoNormTwoDimensionalIntegerPointDistanceTor();
        Double[] lpResultArray = getLocalPrivacyValueBySizeDForGeoI(inputLengthArray, DiscretizedSubsetExponentialGeoI.class, BasicGeoILocalPrivacy.class, distanceTor);

//        MyPrint.showDoubleArray(lpResultArray);
        int size = inputLengthArray.length;
        List<DoublePoint> doublePointList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            doublePointList.add(new TwoDimensionalDoublePoint(inputLengthArray[i], lpResultArray[i]));
        }
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting("D:\\temp\\swap_data\\subGeoI_norm2_changeD.txt");
        pointWrite.writePoint(doublePointList);
        pointWrite.endWriting();
    }



//    public static Double[] getLPCMPByPrivacyBudgetForDAM(Double[] privacyBudgetArray) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//
//        Integer sizeD, bestSizeB;
//        Double probabilityP, probabilityQ;
//        List<TwoDimensionalIntegerPoint> rawTwoDimensionalIntegerPointTypeList, outputTypeList;
//
//        Class<? extends AbstractDiscretizedScheme> discretizedSchemeClass = DiscretizedDiskScheme.class;
//        Class<? extends TransformLocalPrivacy<TwoDimensionalIntegerPoint, TwoDimensionalIntegerPoint>> transLPClass;
//
////        DiscretizedRhombusScheme ram = new DiscretizedRhombusScheme(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);
//
//        TransformLocalPrivacy<TwoDimensionalIntegerPoint, TwoDimensionalIntegerPoint> transformLocalPrivacy;
//        Constructor<? extends AbstractDiscretizedScheme> schemeConstructor;
//        Constructor<? extends TransformLocalPrivacy<TwoDimensionalIntegerPoint, TwoDimensionalIntegerPoint>> lPConstructor;
//        AbstractDiscretizedScheme scheme;
//        Double[] resultLPArray = new Double[privacyBudgetArray.length];
//        for (int i = 0; i < privacyBudgetArray.length; i++) {
//            schemeConstructor = discretizedSchemeClass.getDeclaredConstructor(Double.class, Double.class, Double.class, Double.class, Double.class, Double.class);
//            scheme = schemeConstructor.newInstance(privacyBudgetArray[i], gridLength, inputLength, kParameter, xLeft, yLeft);
//            sizeD = scheme.getSizeD();
//            bestSizeB = scheme.getOptimalSizeB();
//            rawTwoDimensionalIntegerPointTypeList = scheme.getRawIntegerPointTypeList();
//            outputTypeList = scheme.getNoiseIntegerPointTypeList();
//            probabilityP = scheme.getConstP();
//            probabilityQ = scheme.getConstQ();
//            lPConstructor = transLPClass.getDeclaredConstructor(List.class, Integer.class, List.class, Integer.class, Double.class, Double.class);
//            transformLocalPrivacy = lPConstructor.newInstance(rawTwoDimensionalIntegerPointTypeList, sizeD, outputTypeList, bestSizeB, probabilityP, probabilityQ);
//            resultLPArray[i] = transformLocalPrivacy.getTransformLocalPrivacyValue();
//        }
//        return resultLPArray;
//    }

}
