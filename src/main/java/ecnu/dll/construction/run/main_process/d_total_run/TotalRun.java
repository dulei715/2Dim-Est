package ecnu.dll.construction.run.main_process.d_total_run;

import cn.edu.ecnu.result.ExperimentResult;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run.main_process.c_data_set_run.DataSetRun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class TotalRun {
    public static Map<String, Map<String, Map<String, List<ExperimentResult>>>> run(DataSetAreaInfo[] crimeDataSetArray, DataSetAreaInfo[] nycDataSetArray, DataSetAreaInfo twoDimNormalDataSet, DataSetAreaInfo twoDimZipfDataSet) {

        String dataSetPath, dataSetName;
        Double xBound, yBound, length;

        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = new HashMap<>();

        Map<String, Map<String, List<ExperimentResult>>> tempDataSetResult = null;

        // Crime
        for (DataSetAreaInfo dataSetInfo : crimeDataSetArray) {
            dataSetPath = dataSetInfo.getDataSetPath();
            dataSetName = dataSetInfo.getDataSetName();
            xBound = dataSetInfo.getxBound();
            yBound = dataSetInfo.getyBound();
            length = dataSetInfo.getLength();
            tempDataSetResult = DataSetRun.run(dataSetPath, dataSetName, xBound, yBound, length);
            resultMap.put(dataSetName, tempDataSetResult);
        }

        // NYC
        for (DataSetAreaInfo dataSetInfo : nycDataSetArray) {
            dataSetPath = dataSetInfo.getDataSetPath();
            dataSetName = dataSetInfo.getDataSetName();
            xBound = dataSetInfo.getxBound();
            yBound = dataSetInfo.getyBound();
            length = dataSetInfo.getLength();
            tempDataSetResult = DataSetRun.run(dataSetPath, dataSetName, xBound, yBound, length);
            resultMap.put(dataSetName, tempDataSetResult);
        }

        // 2-Dim Normal
        dataSetPath = twoDimNormalDataSet.getDataSetPath();
        dataSetName = twoDimNormalDataSet.getDataSetName();
        xBound = twoDimNormalDataSet.getxBound();
        yBound = twoDimNormalDataSet.getyBound();
        length = twoDimNormalDataSet.getLength();
        tempDataSetResult = DataSetRun.run(dataSetPath, dataSetName, xBound, yBound, length);
        resultMap.put(dataSetName, tempDataSetResult);

        // 2-Dim Zipf
        dataSetPath = twoDimZipfDataSet.getDataSetPath();
        dataSetName = twoDimZipfDataSet.getDataSetName();
        xBound = twoDimZipfDataSet.getxBound();
        yBound = twoDimZipfDataSet.getyBound();
        length = twoDimZipfDataSet.getLength();
        tempDataSetResult = DataSetRun.run(dataSetPath, dataSetName, xBound, yBound, length);
        resultMap.put(dataSetName, tempDataSetResult);

        return resultMap;
    }


}
