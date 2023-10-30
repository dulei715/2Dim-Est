package schemeTest;

import cn.edu.ecnu.basic.StringUtil;
import cn.edu.ecnu.constant_values.ConstantValues;
import cn.edu.ecnu.differential_privacy.accuracy.metrics.distance_quantities.TwoDimensionalWassersteinDistance;
import cn.edu.ecnu.io.read.CSVRead;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import edu.ecnu.dll.cpl.expection.CPLException;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CPlex_Sinkhorn_Test {
    @Test
    public void fun1() throws CPLException {
        String basicPath = "E:\\1.学习\\4.数据集\\generate";

        String dataAPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "dataA.txt");
        String dataBPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "dataB.txt");

        TreeMap<TwoDimensionalIntegerPoint, Double> dataA, dataB;
        TwoDimensionalIntegerPoint tempPoint;
        Double tempDistributionValue;

        dataA = new TreeMap<>();
        List<Map<String, String>> dataAMap = CSVRead.readData(dataAPath);
        for (Map<String, String> map : dataAMap) {
            tempPoint = new TwoDimensionalIntegerPoint(Integer.valueOf(map.get("xIndex")), Integer.valueOf(map.get("yIndex")));
            tempDistributionValue = Double.valueOf(map.get("distributionValue"));
            dataA.put(tempPoint, tempDistributionValue);
        }

        dataB = new TreeMap<>();
        List<Map<String, String>> dataBMap = CSVRead.readData(dataBPath);
        for (Map<String, String> map : dataBMap) {
            tempPoint = new TwoDimensionalIntegerPoint(Integer.valueOf(map.get("xIndex")), Integer.valueOf(map.get("yIndex")));
            tempDistributionValue = Double.valueOf(map.get("distributionValue"));
            dataB.put(tempPoint, tempDistributionValue);
        }

        long startTime = System.currentTimeMillis();
        double result = TwoDimensionalWassersteinDistance.getWassersteinDistanceBySinkhorn(dataA, dataB, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND);
        long endTime = System.currentTimeMillis();
        long timeCost = endTime - startTime;

        System.out.println(result);
        System.out.println(timeCost);
    }
    @Test
    public void fun2() throws CPLException {
        String basicPath = "E:\\1.学习\\4.数据集\\temp2";

        String dataAPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "test_for_NAN_data_original_DAM.txt");
        String dataBPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "test_for_NAN_data_estimation_DAM.txt");
//        String dataAPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "test_for_NAN_data_original_subGeoI.txt");
//        String dataBPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "test_for_NAN_data_estimation_subGeoI.txt");

        TreeMap<TwoDimensionalIntegerPoint, Double> dataA, dataB;
        TwoDimensionalIntegerPoint tempPoint;
        Double tempDistributionValue;

        dataA = new TreeMap<>();
        List<Map<String, String>> dataAMap = CSVRead.readData(dataAPath);
        for (Map<String, String> map : dataAMap) {
            tempPoint = new TwoDimensionalIntegerPoint(Integer.valueOf(map.get("xIndex")), Integer.valueOf(map.get("yIndex")));
            tempDistributionValue = Double.valueOf(map.get("distributionValue"));
            dataA.put(tempPoint, tempDistributionValue);
        }

        dataB = new TreeMap<>();
        List<Map<String, String>> dataBMap = CSVRead.readData(dataBPath);
        for (Map<String, String> map : dataBMap) {
            tempPoint = new TwoDimensionalIntegerPoint(Integer.valueOf(map.get("xIndex")), Integer.valueOf(map.get("yIndex")));
            tempDistributionValue = Double.valueOf(map.get("distributionValue"));
            dataB.put(tempPoint, tempDistributionValue);
        }

        long startTime = System.currentTimeMillis();
        double resultSinkhorn = TwoDimensionalWassersteinDistance.getWassersteinDistanceBySinkhorn(dataA, dataB, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND);
        long endTime = System.currentTimeMillis();
        long sinkhornTimeCost = endTime - startTime;

        System.out.println("Sinkhorn: " + resultSinkhorn);
        System.out.println("Sinkhorn time cost: " + sinkhornTimeCost);

//        startTime = System.currentTimeMillis();
//        double resultCPlex = TwoDimensionalWassersteinDistance.getWassersteinDistanceByCPlex(dataA, dataB, 2);
//        endTime = System.currentTimeMillis();
//        long cplexTimeCost = endTime - startTime;
//
//        System.out.println("CPlex: " + resultCPlex);
//        System.out.println("CPlex time cost: " + cplexTimeCost);
    }
}
