package ecnu.dll.construction.run._1_total_run.main_process.d_total_run;

import cn.edu.dll.result.ExperimentResult;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run._1_total_run.main_process.c_data_set_run.DataSetRunExtendedForKLDivergenceOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class TotalRunExtendedExtendedForKLDivergenceOnly {
    public static Map<String, Map<String, Map<String, List<ExperimentResult>>>> runAndWrite(DataSetAreaInfo[] crimeDataSetArray, String[] crimeOutputDirArray, DataSetAreaInfo[] nycDataSetArray, String[] nycOutputDirArray, DataSetAreaInfo twoDimNormalDataSet, String normalOutputDir, DataSetAreaInfo twoDimZipfDataSet, String zipfOutputDir, DataSetAreaInfo twoDimNormalMultiCentersDataSet, String normalMultiCentersOutputDir) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {

        String dataSetPath, dataSetName;
        Double xBound, yBound, length;

        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = new HashMap<>();

        Map<String, Map<String, List<ExperimentResult>>> tempDataSetResult = null;

        int k;
        DataSetAreaInfo dataSetInfo;
        if (crimeDataSetArray != null) {
            // Crime
            for (k = 0; k < crimeDataSetArray.length; k++) {
                dataSetInfo = crimeDataSetArray[k];
                dataSetPath = dataSetInfo.getDataSetPath();
                dataSetName = dataSetInfo.getDataSetName();
                xBound = dataSetInfo.getxBound();
                yBound = dataSetInfo.getyBound();
                length = dataSetInfo.getLength();
                tempDataSetResult = DataSetRunExtendedForKLDivergenceOnly.runAndWrite(dataSetPath, dataSetName, crimeOutputDirArray[k], xBound, yBound, length);
                resultMap.put(dataSetName, tempDataSetResult);
            }
        }

        // NYC
        if (nycDataSetArray != null) {
            for (k = 0; k < nycDataSetArray.length; k++) {
                dataSetInfo = nycDataSetArray[k];
                dataSetPath = dataSetInfo.getDataSetPath();
                dataSetName = dataSetInfo.getDataSetName();
                xBound = dataSetInfo.getxBound();
                yBound = dataSetInfo.getyBound();
                length = dataSetInfo.getLength();
                tempDataSetResult = DataSetRunExtendedForKLDivergenceOnly.runAndWrite(dataSetPath, dataSetName, nycOutputDirArray[k], xBound, yBound, length);
                resultMap.put(dataSetName, tempDataSetResult);
            }
        }

        // 2-Dim Normal
        if (twoDimNormalDataSet != null) {
            dataSetPath = twoDimNormalDataSet.getDataSetPath();
            dataSetName = twoDimNormalDataSet.getDataSetName();
            xBound = twoDimNormalDataSet.getxBound();
            yBound = twoDimNormalDataSet.getyBound();
            length = twoDimNormalDataSet.getLength();
            tempDataSetResult = DataSetRunExtendedForKLDivergenceOnly.runAndWrite(dataSetPath, dataSetName, normalOutputDir, xBound, yBound, length);
            resultMap.put(dataSetName, tempDataSetResult);
        }

        // 2-Dim Zipf
        if (twoDimZipfDataSet != null) {
            dataSetPath = twoDimZipfDataSet.getDataSetPath();
            dataSetName = twoDimZipfDataSet.getDataSetName();
            xBound = twoDimZipfDataSet.getxBound();
            yBound = twoDimZipfDataSet.getyBound();
            length = twoDimZipfDataSet.getLength();
            tempDataSetResult = DataSetRunExtendedForKLDivergenceOnly.runAndWrite(dataSetPath, dataSetName, zipfOutputDir, xBound, yBound, length);
            resultMap.put(dataSetName, tempDataSetResult);
        }

        // 2-Dim multiple center Normal
        if (twoDimNormalMultiCentersDataSet != null) {
            dataSetPath = twoDimNormalMultiCentersDataSet.getDataSetPath();
            dataSetName = twoDimNormalMultiCentersDataSet.getDataSetName();
            xBound = twoDimNormalMultiCentersDataSet.getxBound();
            yBound = twoDimNormalMultiCentersDataSet.getyBound();
            length = twoDimNormalMultiCentersDataSet.getLength();
            tempDataSetResult = DataSetRunExtendedForKLDivergenceOnly.runAndWrite(dataSetPath, dataSetName, normalMultiCentersOutputDir, xBound, yBound, length);
            resultMap.put(dataSetName, tempDataSetResult);
        }

        return resultMap;
    }


}
