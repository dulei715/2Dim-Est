package ecnu.dll.construction.analysis.lp_to_e.version_1;

import cn.edu.dll.basic.BasicArrayUtil;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.analysis.e_to_lp.Norm1GeoILocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.Norm2GeoILocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.GeoILocalPrivacy;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;

public class SubsetGeoITransformEpsilon {
    private Double beginEpsilon = null;
    private Double epsilonStep = null;
    private Double endEpsilon = null;
    private double[] epsilonArray = null;
    private double[] localPrivacyArray = null;

    private DiscretizedSubsetExponentialGeoI geoIScheme = null;
    private GeoILocalPrivacy geoILocalPrivacy = null;
    private TransformEpsilonEnhanced transformation = null;

    public static final Integer Local_Privacy_Distance_Norm_One = 1;
    public static final Integer Local_Privacy_Distance_Norm_Two = 2;

    public SubsetGeoITransformEpsilon(Double beginEpsilon, Double epsilonStep, Double endEpsilon, DiscretizedSubsetExponentialGeoI geoIScheme, Integer localPrivacyDistanceType) throws CloneNotSupportedException {
        this.beginEpsilon = beginEpsilon;
        this.epsilonStep = epsilonStep;
        this.endEpsilon = endEpsilon;
//        this.geoIScheme = geoIScheme;
        this.geoIScheme = (DiscretizedSubsetExponentialGeoI) geoIScheme.clone();
        this.epsilonArray = BasicArrayUtil.getIncreasedoubleNumberArray(this.beginEpsilon, this.epsilonStep, this.endEpsilon, Constant.eliminateDoubleErrorIndexSize);
        initializeTransformEpsilon(localPrivacyDistanceType);
    }

    public SubsetGeoITransformEpsilon(double[] epsilonArray, DiscretizedSubsetExponentialGeoI geoIScheme, Integer localPrivacyDistanceType) throws CloneNotSupportedException {
        this.epsilonArray = epsilonArray;
//        this.geoIScheme = geoIScheme;
        this.geoIScheme = (DiscretizedSubsetExponentialGeoI) geoIScheme.clone();
//        this.geoILocalPrivacy = new Norm2GeoILocalPrivacy(this.geoIScheme); // todo: delete
        boolean isAscending = BasicArrayUtil.isAscending(epsilonArray);
        if (!isAscending) {
            throw new RuntimeException("The epsilonArray is not ascending!");
        }
        initializeTransformEpsilon(localPrivacyDistanceType);
    }

    private void initializeTransformEpsilon(Integer localPrivacyDistanceType) {
//        this.geoILocalPrivacy = new Norm2GeoILocalPrivacy(this.geoIScheme);
        if (Local_Privacy_Distance_Norm_One.equals(localPrivacyDistanceType)) {
            this.geoILocalPrivacy = new Norm1GeoILocalPrivacy(this.geoIScheme);
        } else if (Local_Privacy_Distance_Norm_Two.equals(localPrivacyDistanceType)) {
            this.geoILocalPrivacy = new Norm2GeoILocalPrivacy(this.geoIScheme);
        } else {
            throw new RuntimeException("Not support for local privacy distance type: " + localPrivacyDistanceType + "!");
        }
        int len = epsilonArray.length;
        this.localPrivacyArray = new double[len];
        for (int i = 0; i < this.epsilonArray.length; i++) {
            this.geoILocalPrivacy.resetEpsilon(this.epsilonArray[i]);
            this.localPrivacyArray[i] = this.geoILocalPrivacy.getTransformLocalPrivacyValue();
        }
        this.transformation = new TransformEpsilonEnhanced(this.epsilonArray, this.localPrivacyArray);
    }

    public double getEpsilonByLocalPrivacy(double localPrivacy) {
        return this.transformation.getPrivacyBudgetByLocalPrivacy(localPrivacy);
    }

    public DiscretizedSubsetExponentialGeoI getSubsetGeoISchemeByNewEpsilon(double epsilon) throws CloneNotSupportedException {
        DiscretizedSubsetExponentialGeoI newScheme = (DiscretizedSubsetExponentialGeoI) this.geoIScheme.clone();
        newScheme.resetEpsilon(epsilon);
        return newScheme;
    }































}

