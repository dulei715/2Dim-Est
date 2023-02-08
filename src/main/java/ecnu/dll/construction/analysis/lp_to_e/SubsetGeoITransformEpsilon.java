package ecnu.dll.construction.analysis.lp_to_e;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.collection.ArraysUtils;
import ecnu.dll.construction.analysis.e_to_lp.Norm2GeoILocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.GeoILocalPrivacy;
import ecnu.dll.construction.analysis.e_to_lp.abstract_class.GeoILocalPrivacy_TODO;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;

public class SubsetGeoITransformEpsilon {
    private Double beginEpsilon = null;
    private Double epsilonStep = null;
    private Double endEpsilon = null;
    private double[] epsilonArray = null;
    private double[] localPrivacyArray = null;

    private DiscretizedSubsetExponentialGeoI geoIScheme = null;
    private GeoILocalPrivacy geoILocalPrivacy = null;
    private TransformEpsilon transformation = null;

    public SubsetGeoITransformEpsilon(Double beginEpsilon, Double epsilonStep, Double endEpsilon, DiscretizedSubsetExponentialGeoI geoIScheme) throws CloneNotSupportedException {
        this.beginEpsilon = beginEpsilon;
        this.epsilonStep = epsilonStep;
        this.endEpsilon = endEpsilon;
//        this.geoIScheme = geoIScheme;
        this.geoIScheme = (DiscretizedSubsetExponentialGeoI) geoIScheme.clone();
        this.geoILocalPrivacy = new Norm2GeoILocalPrivacy(this.geoIScheme);
        this.epsilonArray = BasicArray.getIncreasedoubleNumberArray(this.beginEpsilon, this.epsilonStep, this.endEpsilon);
        initializeTransformEpsilon();
    }

    public SubsetGeoITransformEpsilon(double[] epsilonArray, DiscretizedSubsetExponentialGeoI geoIScheme) throws CloneNotSupportedException {
        this.epsilonArray = epsilonArray;
//        this.geoIScheme = geoIScheme;
        this.geoIScheme = (DiscretizedSubsetExponentialGeoI) geoIScheme.clone();
//        this.geoILocalPrivacy = new Norm2GeoILocalPrivacy(this.geoIScheme); // todo: delete
        boolean isAscending = ArraysUtils.isAscending(epsilonArray);
        if (!isAscending) {
            throw new RuntimeException("The epsilonArray is not ascending!");
        }
        initializeTransformEpsilon();
    }

    private void initializeTransformEpsilon() {
        this.geoILocalPrivacy = new Norm2GeoILocalPrivacy(this.geoIScheme);
        int len = epsilonArray.length;
        this.localPrivacyArray = new double[len];
        for (int i = 0; i < this.epsilonArray.length; i++) {
            this.geoILocalPrivacy.resetEpsilon(this.epsilonArray[i]);
            this.localPrivacyArray[i] = this.geoILocalPrivacy.getTransformLocalPrivacyValue();
        }
        this.transformation = new TransformEpsilon(this.epsilonArray, this.localPrivacyArray);
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

