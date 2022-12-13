package schemeTest;

import cn.edu.ecnu.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.ecnu.differential_privacy.cdp.basic_struct.impl.TwoNormTwoDimensionalIntegerPointDistanceTor;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.analysis.e_to_lp.Norm2DAMLocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.Norm2GeoILocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.Norm2RAMLocalPrivacy;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;
import ecnu.dll.construction.newscheme.discretization.DiscretizedDiskScheme;
import ecnu.dll.construction.newscheme.discretization.DiscretizedRhombusScheme;
import org.junit.Test;

import java.util.List;

public class LPTest {
    @Test
    public void fun1() throws InstantiationException, IllegalAccessException {

        Integer ramBestSizeB, damBestSizeB;
        double epsilon = 0.5;
//        ramBestSizeB = DiscretizedRhombusSchemeTool.getOptimalSizeBOfRhombusScheme(epsilon, sizeD);
//        damBestSizeB = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, sizeD);

//        List<TwoDimensionalIntegerPoint> rawTwoDimensionalIntegerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList(sizeD);
//        List<TwoDimensionalIntegerPoint> ramOutputTypeList = DiscretizedRhombusSchemeTool.getNoiseIntegerPointTypeList(sizeD, ramBestSizeB);
//        Double[] ramProbabilityPQ = DiscretizedRhombusSchemeTool.getProbabilityPQ(sizeD, ramBestSizeB, epsilon);

//        List<TwoDimensionalIntegerPoint> highProbabilityBorderCellList = DiscretizedDiskSchemeTool.calculateHighProbabilityBorderCellIndexList(damBestSizeB);
//        Integer upperIndex45 = DiscretizedDiskSchemeTool.getUpperIndex45(damBestSizeB);
//        List<TwoDimensionalIntegerPoint> damOutputTypeList = DiscretizedDiskSchemeTool.getNoiseIntegerPointTypeList(highProbabilityBorderCellList, sizeD, damBestSizeB, upperIndex45);

        double gridLength = 1.0;
        double inputLength = 5.0;
        double kParameter = 0;
        double xLeft = 0.0;
        double yLeft = 0.0;

        // For DAM parameters
        DiscretizedRhombusScheme ram = new DiscretizedRhombusScheme(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);
        ramBestSizeB = ram.getOptimalSizeB();
        Integer sizeD = ram.getSizeD();
        List<TwoDimensionalIntegerPoint> rawTwoDimensionalIntegerPointTypeList = ram.getRawIntegerPointTypeList();
        List<TwoDimensionalIntegerPoint> ramOutputTypeList = ram.getNoiseIntegerPointTypeList();
        Double ramProbabilityP = ram.getConstP();
        Double ramProbabilityQ = ram.getConstQ();

        // For RAM parameters
        DiscretizedDiskScheme dam = new DiscretizedDiskScheme(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);
        damBestSizeB = dam.getOptimalSizeB();
        List<TwoDimensionalIntegerPoint> damOutputTypeList = dam.getNoiseIntegerPointTypeList();
        Double damProbabilityP = dam.getConstP();
        Double damProbabilityQ = dam.getConstQ();

        // For Geo-I parameters
        DistanceTor<TwoDimensionalIntegerPoint> distanceTor = new TwoNormTwoDimensionalIntegerPointDistanceTor();
        DiscretizedSubsetExponentialGeoI subsetGeoI = new DiscretizedSubsetExponentialGeoI(epsilon, gridLength, inputLength, xLeft, yLeft, distanceTor);
        Double[] massArray = subsetGeoI.getMassArray();
        Double omega = subsetGeoI.getOmega();
        Integer setSizeK = subsetGeoI.getSetSizeK();


        /**
         * 计算RAM的LP
         */
        Norm2RAMLocalPrivacy ram2LP = new Norm2RAMLocalPrivacy(rawTwoDimensionalIntegerPointTypeList, sizeD, ramOutputTypeList, ramBestSizeB, ramProbabilityP, ramProbabilityQ);
        double ram2LPValue = ram2LP.getTransformLocalPrivacyValue();

        /**
         * 计算DAM的LP
         */
        Norm2DAMLocalPrivacy dam2LP = new Norm2DAMLocalPrivacy(rawTwoDimensionalIntegerPointTypeList, sizeD, damOutputTypeList, damBestSizeB, damProbabilityP, damProbabilityQ);
        double dam2LPValue = dam2LP.getTransformLocalPrivacyValue();


        /**
         * 计算Subset-Geo-I的LP
         */
        Norm2GeoILocalPrivacy geoILP = new Norm2GeoILocalPrivacy(rawTwoDimensionalIntegerPointTypeList, setSizeK, massArray, epsilon, omega);
        double geoI2LPValue = geoILP.getTransformLocalPrivacyValue();


        System.out.println(ram2LPValue);
        System.out.println(dam2LPValue);
        System.out.println(geoI2LPValue);
    }
    @Test
    public void fun2() throws InstantiationException, IllegalAccessException {

        Integer ramBestSizeB, damBestSizeB;
        double epsilon = 1;

        double gridLength = 1.0;
        double inputLength = 2.0;
        double kParameter = 0;
        double xLeft = 0.0;
        double yLeft = 0.0;

        // For DAM parameters
        DiscretizedRhombusScheme ram = new DiscretizedRhombusScheme(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);
        ramBestSizeB = ram.getOptimalSizeB();
        Integer sizeD = ram.getSizeD();
        List<TwoDimensionalIntegerPoint> rawTwoDimensionalIntegerPointTypeList = ram.getRawIntegerPointTypeList();
        List<TwoDimensionalIntegerPoint> ramOutputTypeList = ram.getNoiseIntegerPointTypeList();
        Double ramProbabilityP = ram.getConstP();
        Double ramProbabilityQ = ram.getConstQ();

        // For RAM parameters
        DiscretizedDiskScheme dam = new DiscretizedDiskScheme(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);
        damBestSizeB = dam.getOptimalSizeB();
        List<TwoDimensionalIntegerPoint> damOutputTypeList = dam.getNoiseIntegerPointTypeList();
        Double damProbabilityP = dam.getConstP();
        Double damProbabilityQ = dam.getConstQ();

        // For Geo-I parameters
        DistanceTor<TwoDimensionalIntegerPoint> distanceTor = new TwoNormTwoDimensionalIntegerPointDistanceTor();
        DiscretizedSubsetExponentialGeoI subsetGeoI = new DiscretizedSubsetExponentialGeoI(epsilon, gridLength, inputLength, xLeft, yLeft, distanceTor);
        Double[] massArray = subsetGeoI.getMassArray();
        Double omega = subsetGeoI.getOmega();
        Integer setSizeK = subsetGeoI.getSetSizeK();


        /**
         * 计算RAM的LP
         */
        Norm2RAMLocalPrivacy ram2LP = new Norm2RAMLocalPrivacy(rawTwoDimensionalIntegerPointTypeList, sizeD, ramOutputTypeList, ramBestSizeB, ramProbabilityP, ramProbabilityQ);
        double ram2LPValue = ram2LP.getTransformLocalPrivacyValue();

        /**
         * 计算DAM的LP
         */
        Norm2DAMLocalPrivacy dam2LP = new Norm2DAMLocalPrivacy(rawTwoDimensionalIntegerPointTypeList, sizeD, damOutputTypeList, damBestSizeB, damProbabilityP, damProbabilityQ);
        double dam2LPValue = dam2LP.getTransformLocalPrivacyValue();


        /**
         * 计算Subset-Geo-I的LP
         */
        Norm2GeoILocalPrivacy geoILP = new Norm2GeoILocalPrivacy(rawTwoDimensionalIntegerPointTypeList, setSizeK, massArray, epsilon, omega);
        double geoI2LPValue = geoILP.getTransformLocalPrivacyValue();


        System.out.println(ram2LPValue);
        System.out.println(dam2LPValue);
        System.out.println(geoI2LPValue);
    }
}
