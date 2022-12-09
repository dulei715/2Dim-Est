package ecnu.dll.construction.analysis.tools;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.struct.point.DoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.Norm1RAMLocalPrivacy;
import ecnu.dll.construction.analysis.Norm2GeoILocalPrivacy;
import ecnu.dll.construction.analysis.Norm2RAMLocalPrivacy;
import ecnu.dll.construction.analysis.basic.TransformLocalPrivacy;
import ecnu.dll.construction.analysis.basic_impl.BasicGeoILocalPrivacy;
import ecnu.dll.construction.analysis.basic_impl.BasicRAMLocalPrivacy;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import ecnu.dll.construction.newscheme.discretization.AbstractDiscretizedScheme;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.DiscretizedRhombusScheme;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class LocalPrivacyTool {
    public static double getPrivacyBudgetByLocalPrivacy() {
        return 0;
    }

    //    public static double getLocalPrivacyValueByPrivacyBudget(double privacyBudget, int sizeD, double[] a1Array, double[]a2Array, double[] a3Array, double[] a4Array, double[] highAreaSizeArray, double[] lowAreaSizeArray) {
//        double eBudget = Math.exp(privacyBudget);
//        double resultLP = 0;
//        double probabilityQ;
//        for (int i = 0; i < a1Array.length; i++) {
//            resultLP = (a1Array[i] * eBudget * eBudget + a2Array[i] * eBudget + a3Array[i]) / (a4Array[i] * (eBudget - 1) + sizeD * sizeD) * probabilityQ;
//        }
//    }

    private static double gridLength = 1.0;
    private static double inputLength = 4.0;
    private static double kParameter = 0.5;
    private static double xLeft = 0.0;
    private static double yLeft = 0.0;

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


    public static void main0(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        Integer[] tempArray = BasicArray.getIncreaseIntegerNumberArray(1, 1, 10);
        Double[] privacyBudgetArray = BasicArray.getLinearTransformFromIntegerArray(tempArray, 0.1, 0);
        MyPrint.showArray(privacyBudgetArray, "; ");

        // For DAM parameters
        DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator = new TwoNormTwoDimensionalIntegerPointDistanceTor();
        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForLDP(privacyBudgetArray, DiscretizedRhombusScheme.class, BasicRAMLocalPrivacy.class);
        MyPrint.showDoubleArray(lpResultArray);

    }
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        Integer[] tempArray = BasicArray.getIncreaseIntegerNumberArray(1, 10, 2000);
        Double[] privacyBudgetArray = BasicArray.getLinearTransformFromIntegerArray(tempArray, 0.001, 0);
//        MyPrint.showArray(privacyBudgetArray);

        // For DAM parameters
//        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForLDP(privacyBudgetArray, DiscretizedRhombusScheme.class, Norm1RAMLocalPrivacy.class);
//        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForLDP(privacyBudgetArray, DiscretizedRhombusScheme.class, Norm2RAMLocalPrivacy.class);

        // For RAM parameters
//        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForLDP(privacyBudgetArray, DiscretizedDiskScheme.class, Norm1RAMLocalPrivacy.class);
        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForLDP(privacyBudgetArray, DiscretizedDiskScheme.class, Norm2RAMLocalPrivacy.class);

        // For Subset-Geo-I
//        DistanceTor<TwoDimensionalIntegerPoint> distanceTor = new TwoNormTwoDimensionalIntegerPointDistanceTor();
//        Double[] lpResultArray = getLocalPrivacyValueByPrivacyBudgetForGeoI(privacyBudgetArray, DiscretizedSubsetExponentialGeoI.class, BasicGeoILocalPrivacy.class, distanceTor);

//        MyPrint.showDoubleArray(lpResultArray);
        int size = privacyBudgetArray.length;
        List<DoublePoint> doublePointList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            doublePointList.add(new TwoDimensionalDoublePoint(privacyBudgetArray[i], lpResultArray[i]));
        }
        PointWrite pointWrite = new PointWrite();
//        pointWrite.startWriting("D:\\temp\\swap_data\\ram_norm2.txt");
        pointWrite.startWriting("D:\\temp\\swap_data\\dam_norm2.txt");
//        pointWrite.startWriting("D:\\temp\\swap_data\\subGeoI_norm2.txt");
        pointWrite.writePoint(doublePointList);
        pointWrite.endWriting();
    }

}
