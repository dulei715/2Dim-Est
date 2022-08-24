package ecnu.dll.construction.run.main_process;

import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.result.ExperimentResult;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run.main_process.d_total_run.TotalRun;

import java.util.List;
import java.util.Map;

public class MainRun {
    public static void main(String[] args) {

        DataSetAreaInfo[] crimeDataSetArray = Constant.crimeDataSetArray;
        DataSetAreaInfo[] nycDataSetArray = Constant.nycDataSetArray;
        DataSetAreaInfo twoDimNormalDataSet = Constant.twoDimNormalDataSet;
        DataSetAreaInfo twoDimZipfDataSet = Constant.twoDimZipfDataSet;
        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalRun.run(crimeDataSetArray, nycDataSetArray, twoDimNormalDataSet, twoDimZipfDataSet);
        MyPrint.showMap(resultMap);

    }
}
