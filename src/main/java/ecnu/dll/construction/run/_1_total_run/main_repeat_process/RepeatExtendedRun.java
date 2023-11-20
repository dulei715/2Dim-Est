package ecnu.dll.construction.run._1_total_run.main_repeat_process;

import cn.edu.ecnu.basic.StringUtil;
import cn.edu.ecnu.constant_values.ConstantValues;
import cn.edu.ecnu.result.ExperimentResult;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run._1_total_run.main_process.d_total_run.TotalRunExtended;

import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class RepeatExtendedRun {
    public static void main(String[] args) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {

        DataSetAreaInfo[] crimeDataSetArray = Constant.crimeDataSetArray;
        DataSetAreaInfo[] nycDataSetArray = Constant.nycDataSetArray;
        DataSetAreaInfo twoDimNormalDataSet = Constant.twoDimNormalDataSet;
        DataSetAreaInfo twoDimZipfDataSet = Constant.twoDimZipfDataSet;
        DataSetAreaInfo twoDimNormalMultiCentersDataSet = Constant.twoDimMultipleCenterNormalDataSet;

        String[] crimeOutputDirArray, nycOutputDirArray;
        String normalOutputDir, zipfOutputDir, normalMultiCentersOutputDir;

        int repeatTimes = 10;
        for (int i = 0; i < repeatTimes; i++) {
            crimeOutputDirArray = StringUtil.concatGiveString(Constant.extendedOutputCrimeDirArray, ConstantValues.FILE_SPLIT.concat(String.valueOf(i)));
            nycOutputDirArray = StringUtil.concatGiveString(Constant.extendedOutputNYCDirArray, ConstantValues.FILE_SPLIT.concat(String.valueOf(i)));
//            normalOutputDir = Constant.outputNormalDir;
            normalOutputDir = Constant.extendedOutputNormalDir.concat(ConstantValues.FILE_SPLIT).concat(String.valueOf(i));
            zipfOutputDir = Constant.extendedOutputZipfDir.concat(ConstantValues.FILE_SPLIT).concat(String.valueOf(i));
            normalMultiCentersOutputDir = Constant.extendedOutputMultiNormalDir.concat(ConstantValues.FILE_SPLIT).concat(String.valueOf(i));
            Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalRunExtended.runAndWrite(crimeDataSetArray, crimeOutputDirArray, nycDataSetArray, nycOutputDirArray, twoDimNormalDataSet, normalOutputDir, twoDimZipfDataSet, zipfOutputDir, twoDimNormalMultiCentersDataSet, normalMultiCentersOutputDir);
            System.out.println("Finish extended round " + i);
        }

    }
}