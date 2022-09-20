package ecnu.dll.construction.run.main_repeat_process;

import cn.edu.ecnu.basic.StringUtil;
import cn.edu.ecnu.constant_values.ConstantValues;
import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.result.ExperimentResult;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run.main_process.d_total_run.TotalRun;

import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class RepeatMainRun {
    public static void main(String[] args) {

        DataSetAreaInfo[] crimeDataSetArray = Constant.crimeDataSetArray;
        DataSetAreaInfo[] nycDataSetArray = Constant.nycDataSetArray;
        DataSetAreaInfo twoDimNormalDataSet = Constant.twoDimNormalDataSet;
        DataSetAreaInfo twoDimZipfDataSet = Constant.twoDimZipfDataSet;

        String[] crimeOutputDirArray, nycOutputDirArray;
        String normalOutputDir, zipfOutputDir;

        int repeatTimes = 10;
        for (int i = 0; i < repeatTimes; i++) {
            crimeOutputDirArray = StringUtil.concatGiveString(Constant.outputCrimeDirArray, ConstantValues.FILE_SPLIT.concat(String.valueOf(i)));
            nycOutputDirArray = StringUtil.concatGiveString(Constant.outputNYCDirArray, ConstantValues.FILE_SPLIT.concat(String.valueOf(i)));
//            normalOutputDir = Constant.outputNormalDir;
            normalOutputDir = Constant.outputNormalDir.concat(ConstantValues.FILE_SPLIT).concat(String.valueOf(i));
            zipfOutputDir = Constant.outputZipfDir.concat(ConstantValues.FILE_SPLIT).concat(String.valueOf(i));
            Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalRun.runAndWrite(crimeDataSetArray, crimeOutputDirArray, nycDataSetArray, nycOutputDirArray, twoDimNormalDataSet, normalOutputDir, twoDimZipfDataSet, zipfOutputDir);
            System.out.println("Finish round " + i);
        }

    }
}
