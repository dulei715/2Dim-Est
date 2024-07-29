package ecnu.dll.construction.analysis.e_to_lp.abstract_class;

import cn.edu.dll.differential_privacy.cdp.basic_struct.DistanceTor;
import cn.edu.dll.struct.grid.IntegerSquareGrid;
import cn.edu.dll.struct.grid.sub_struct.DistanceArrayAndSubsetMatrixStruct;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.comparedscheme.sem_geo_i.discretization.DiscretizedSubsetExponentialGeoI;

import java.util.List;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("Duplicates")
@Deprecated
public abstract class GeoILocalPrivacy_TODO {

    protected DiscretizedSubsetExponentialGeoI geoIScheme = null;
    protected Integer sizeD = null;

    protected Integer setSizeK = null;

    protected Double[] distinctDistance = null;
    protected Double[] distinctPairSumDistance = null;
    protected Double[] distinctInputSumToOneSubsetDistance = null;

    protected Double[] massArray = null;

    protected Double omega = null;

    protected Double radix = null;

    protected DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator = null;

//    protected List<List<Integer>> intermediateIndexSetList = null;

//    protected double[][] probabilityMatrix = null;
    protected Map<TwoDimensionalIntegerPoint, Map<Integer, Set<TwoDimensionalIntegerPoint>>> groupStatisticMatrix = null;




//    private void initializeProbabilityMatrix() {
//        this.probabilityMatrix = new double[this.originalSetList.size()][this.intermediateIndexSetList.size()];
//        double tempDistance;
//        for (int i = 0; i < this.originalSetList.size(); i++) {
//            int j = 0;
//            for (; j < this.intermediateIndexSetList.size() - 1; j++) {
//                tempDistance = ListUtils.getMinimalDistanceFromElementToList(i, this.intermediateIndexSetList.get(j), this.originalSetList);
//                this.probabilityMatrix[i][j] = Math.pow(this.radix, tempDistance) / this.omega;
//            }
//            this.probabilityMatrix[i][j] = 1 - this.massArray[i] / this.omega;
//        }
//    }

    private void initializeGroupStatisticMatrix() {
        DistanceArrayAndSubsetMatrixStruct distanceArrayAndSubsetMatrixStruct = IntegerSquareGrid.generateDistanceArrayAndSubsetMatrix(this.sizeD);
        this.distinctDistance = distanceArrayAndSubsetMatrixStruct.getDistanceArray();
        this.groupStatisticMatrix = distanceArrayAndSubsetMatrixStruct.getSubsetMatrix();
    }

    private void resetProbabilityMatrixByRadix() {
//        double tempDistance;
//        for (int i = 0; i < this.originalSetList.size(); i++) {
//            int j = 0;
//            for (; j < this.intermediateIndexSetList.size() - 1; j++) {
//                tempDistance = ListUtils.getMinimalDistanceFromElementToList(i, this.intermediateIndexSetList.get(j), this.originalSetList);
//                this.probabilityMatrix[i][j] = Math.pow(this.radix, tempDistance) / this.omega;
//            }
//            this.probabilityMatrix[i][j] = 1 - this.massArray[i] / this.omega;
//        }
    }

    @Deprecated
    public GeoILocalPrivacy_TODO(List<TwoDimensionalIntegerPoint> originalSetList, Integer setSizeK, Double[] massArray, Double epsilon, Double omega, DistanceTor<TwoDimensionalIntegerPoint> distanceTor) {
        this.sizeD = Integer.valueOf((int) Math.sqrt(originalSetList.size()));
        this.setSizeK = setSizeK;
        this.massArray = massArray;
        this.radix = Math.exp(-epsilon);
        this.omega = omega;
        this.distanceCalculator = distanceTor;
        // 这里的intermediateSetList是中间元素的索引的索引，而非中间元素
        this.initializeGroupStatisticMatrix();
    }

    public GeoILocalPrivacy_TODO(DiscretizedSubsetExponentialGeoI geoIScheme, DistanceTor<TwoDimensionalIntegerPoint> distanceCalculator) {
        this.geoIScheme = geoIScheme;
        this.setSizeK = this.geoIScheme.getSetSizeK();
        this.massArray = this.geoIScheme.getMassArray();
        this.radix = Math.exp(-this.geoIScheme.getEpsilon());
        this.omega = this.geoIScheme.getOmega();
        this.distanceCalculator = distanceCalculator;
        // 这里的intermediateSetList是中间元素的索引的索引，而非中间元素
        this.initializeGroupStatisticMatrix();
    }

    public void resetEpsilon(Double epsilon) {
//        this.geoIScheme.resetEpsilon(epsilon);
//        this.setSizeK = this.geoIScheme.getSetSizeK();
//        this.massArray = this.geoIScheme.getMassArray();
//        this.radix = Math.exp(-this.geoIScheme.getEpsilon());
//        this.omega = this.geoIScheme.getOmega();
//        this.intermediateIndexSetList = SetUtils.getSubsetList(this.originalSetList.size(), this.setSizeK, 0);
//        this.intermediateIndexSetList.add(new ArrayList<>());
//        // 这里的intermediateSetList是中间元素的索引的索引，而非中间元素
//        this.intermediateSetList = BasicArray.getIncreaseIntegerNumberList(0, 1, this.intermediateIndexSetList.size() - 1);
//        this.initializeProbabilityMatrix();
    }

    //    private double getToSubsetProbability(int cellIndex, List<Integer> subsetIndexList) {
//        if (subsetIndexList.isEmpty()) {
//            return 1 - this.massArray[cellIndex] / this.omega;
//        }
//        Double distance = ListUtils.getMinimalDistanceFromElementToList(cellIndex, subsetIndexList, this.originalSetList);
//        return Math.pow(this.radix, distance) / this.omega;
//    }


//    public double getTransformLocalPrivacyValue() {
//        return 0;
//    }


}
