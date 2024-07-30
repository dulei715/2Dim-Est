package ecnu.dll.construction.run._1_total_run.main_repeat_process.version_2.run_utils;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.io.write.ExperimentResultWrite;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.signal.CatchSignal;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.grid.Grid;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction._config.Initialized;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedSchemeTool;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class BasicRun implements Runnable {

    private String datasetName;
    private List<TwoDimensionalIntegerPoint> integerPointList;
    private double inputSideLength;
    private TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic;
    private Double epsilon;
    private Double sizeD;
    private double xBound;
    private double yBound;
    private String outputDirPath;
    private CountDownLatch latch;

    public BasicRun(String datasetName, List<TwoDimensionalIntegerPoint> integerPointList, double inputSideLength, TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, Double epsilon, Double sizeD, double xBound, double yBound, String outputDirPath, CountDownLatch latch) {
        this.datasetName = datasetName;
        this.integerPointList = integerPointList;
        this.inputSideLength = inputSideLength;
        this.rawDataStatistic = rawDataStatistic;
        this.epsilon = epsilon;
        this.sizeD = sizeD;
        this.xBound = xBound;
        this.yBound = yBound;
        this.outputDirPath = outputDirPath;
        this.latch = latch;
    }

    public void getResult() throws CloneNotSupportedException {

        /*
            1. 设置cell大小的参数（同时也是设置整数input的边长大小）
         */
        double gridLength = inputSideLength / sizeD;

        /*
            2. 设置隐私预算budget的变化数组
         */

        /*
            3. 根据budget，计算Disk方案对应的Optimal sizeB的取值
         */
        int sizeDInteger= (int)Math.ceil(sizeD);
        int diskOptimalSizeB = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, sizeDInteger);

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;


        /*
            针对SubsetGeoI, MSW, Disk, Disk-non-Shrink, HUEM 分别计算对应budget下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> runningResultMap = new HashMap<>();
        String diskKey = Constant.diskSchemeKey, diskNonShrinkKey = Constant.diskNonShrinkSchemeKey, subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey, mdsw = Constant.multiDimensionalSquareWaveSchemeKey, hue = Constant.hybridUniformExponentialSchemeKey;
        ExperimentResult tempDiskExperimentResult, tempDiskNonShrinkExperimentResult;
        ExperimentResult tempSubsetGeoITwoNormExperimentResult, tempMdswExperimentResult, tempHUEMExperimentResult;
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>(), diskNonShrinkExperimentResultList = new ArrayList<>();
        List<ExperimentResult> subsetGeoITwoNormExperimentResultList = new ArrayList<>(), mdswExperimentResultList = new ArrayList<>(), huemExperimentResultList = new ArrayList<>();

        double tempLocalPrivacy, transformedEpsilon;


        // for MDSW
        System.out.println("Start MDSW...");
        tempMdswExperimentResult = MDSWRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, epsilon, xBound, yBound);
        mdswExperimentResultList.add(tempMdswExperimentResult);
        System.out.println("Finish MDSW!");

        // for DAM-non-shrink
        System.out.println("Start DAM-non-shrink...");
        tempDiskNonShrinkExperimentResult = DAMNonShrinkRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, diskOptimalSizeB*gridLength, epsilon, kParameter, xBound, yBound);
        diskNonShrinkExperimentResultList.add(tempDiskNonShrinkExperimentResult);
        System.out.println("Finish DAM-non-shrink!");

        // for DAM
        System.out.println("Start DAM...");
        tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, diskOptimalSizeB*gridLength, epsilon, kParameter, xBound, yBound);
        diskExperimentResultList.add(tempDiskExperimentResult);
        System.out.println("Finish DAM!");


        // for Subset-Geo-I-norm2
        System.out.println("Start SubsetGeoI...");
        tempLocalPrivacy = Initialized.damELPExtendedTable.getLowerBoundLocalPrivacyByEpsilon(sizeD, epsilon);
        transformedEpsilon = Initialized.subGeoIELPExtendedTable.getEpsilonByUpperBoundLocalPrivacy(sizeD, tempLocalPrivacy);
        tempSubsetGeoITwoNormExperimentResult = SubsetGeoITwoNormRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, transformedEpsilon, xBound, yBound);
        subsetGeoITwoNormExperimentResultList.add(tempSubsetGeoITwoNormExperimentResult);
        System.out.println("Finish SubsetGeoI!");

        // for HUEM
        System.out.println("Start HUEM...");
        if (diskOptimalSizeB < 1) {
            tempHUEMExperimentResult = (ExperimentResult) tempDiskExperimentResult.clone();
            tempHUEMExperimentResult.setPair(Constant.schemeNameKey, Constant.hybridUniformExponentialSchemeKey);
        } else {
            tempHUEMExperimentResult = HUEMSRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, diskOptimalSizeB*gridLength, epsilon, kParameter, xBound, yBound);
        }
        huemExperimentResultList.add(tempHUEMExperimentResult);
        System.out.println("Finish HUEM!");


        runningResultMap.put(mdsw, mdswExperimentResultList);
        runningResultMap.put(subsetGeoITwoNorm, subsetGeoITwoNormExperimentResultList);
        runningResultMap.put(diskNonShrinkKey, diskNonShrinkExperimentResultList);
        runningResultMap.put(diskKey, diskExperimentResultList);
        runningResultMap.put(hue, huemExperimentResultList);

        ExperimentResult.addPair(runningResultMap, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(runningResultMap, 0, Constant.dataSetNameKey, datasetName);
        String outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, outputDirPath, FileNameUtils.getFileNameByBudgetAndSizeD(epsilon, sizeDInteger).concat(".csv"));
        System.out.println(outputFilePath);
        ExperimentResultWrite.write(outputFilePath, ExperimentResult.getCombineResultList(runningResultMap));

    }

    @Override
    public void run() {
        try {
            getResult();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        } finally {
            this.latch.countDown();
        }
    }

    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        DataSetAreaInfo datasetInfo = Constant.twoDimMultipleCenterNormalDataSet;
        String datasetName = datasetInfo.getDataSetName();
        double epsilon = 5;
//        double gridLength = 0.4;
        double gridLength = 0.75;
//        double constB = 1.6;
//        double constB = 1.6;
//        Double inputLength = 1.0;
        Double kParameter = Constant.DEFAULT_K_PARAMETER;
        Double inputLength = datasetInfo.getLength();
        Double xLeft = datasetInfo.getxBound();
        Double yLeft = datasetInfo.getyBound();


        Double sizeD = Math.ceil(inputLength / gridLength);

        String outputDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "test2");
//        TwoDimensionalIntegerPoint originalPoint = new TwoDimensionalIntegerPoint(0, 0);
//        List<TwoDimensionalIntegerPoint> pointList = ListUtils.copyToListGivenElement(originalPoint, 500000);

        String dataSetPath = datasetInfo.getDataSetPath();
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();

        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xLeft, yLeft}, gridLength);
        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(sizeD));
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

        CountDownLatch latch = new CountDownLatch(1);
        Runnable runnable = new BasicRun(datasetName, integerPointList, inputLength, rawStatisticMap, epsilon, sizeD, xLeft, yLeft, outputDir, latch);
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
