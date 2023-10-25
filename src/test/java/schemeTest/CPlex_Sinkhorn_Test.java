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

        double result = TwoDimensionalWassersteinDistance.getWassersteinDistanceBySinkhorn(dataA, dataB, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND);
        System.out.println(result);
    }
}
