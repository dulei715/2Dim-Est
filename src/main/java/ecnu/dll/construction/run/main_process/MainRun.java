package ecnu.dll.construction.run.main_process;

import cn.edu.ecnu.result.ExperimentResult;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run.main_process.d_total_run.TotalRun;

import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Deprecated
public class MainRun {
    public static void main0(String[] args) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {

        DataSetAreaInfo[] crimeDataSetArray = Constant.crimeDataSetArray;
        DataSetAreaInfo[] nycDataSetArray = Constant.nycDataSetArray;
        DataSetAreaInfo twoDimNormalDataSet = Constant.twoDimNormalDataSet;
        DataSetAreaInfo twoDimZipfDataSet = Constant.twoDimZipfDataSet;
        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalRun.run(crimeDataSetArray, nycDataSetArray, twoDimNormalDataSet, twoDimZipfDataSet);
//        MyPrint.showMap(resultMap);

    }
    public static void main(String[] args) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {

        DataSetAreaInfo[] crimeDataSetArray = Constant.crimeDataSetArray;
        DataSetAreaInfo[] nycDataSetArray = Constant.nycDataSetArray;
        DataSetAreaInfo twoDimNormalDataSet = Constant.twoDimNormalDataSet;
        DataSetAreaInfo twoDimZipfDataSet = Constant.twoDimZipfDataSet;

        String[] crimeOutputDirArray = Constant.basicOutputCrimeDirArray;
        String[] nycOutputDirArray = Constant.basicOutputNYCDirArray;
        String normalOutputDir = Constant.basicOutputNormalDir;
        String zipfOutputDir = Constant.basicOutputZipfDir;

//        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalRun.runAndWrite(crimeDataSetArray, crimeOutputDirArray, nycDataSetArray, nycOutputDirArray, twoDimNormalDataSet, normalOutputDir, twoDimZipfDataSet, zipfOutputDir);
//        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalRun.runAndWrite(null, crimeOutputDirArray, null, nycOutputDirArray, twoDimNormalDataSet, normalOutputDir, null, zipfOutputDir);
//        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalRun.runAndWrite(null, crimeOutputDirArray, null, nycOutputDirArray, null, normalOutputDir, twoDimZipfDataSet, zipfOutputDir);
//        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalRun.runAndWrite(crimeDataSetArray, crimeOutputDirArray, null, nycOutputDirArray, null, normalOutputDir, null, zipfOutputDir);
//        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalRun.runAndWrite(null, crimeOutputDirArray, nycDataSetArray, nycOutputDirArray, null, normalOutputDir, null, zipfOutputDir);
//        MyPrint.showMap(resultMap);

    }
}
