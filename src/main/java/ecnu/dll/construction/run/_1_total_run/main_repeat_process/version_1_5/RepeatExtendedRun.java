package ecnu.dll.construction.run._1_total_run.main_repeat_process.version_1_5;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.result.ExperimentResult;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run._1_total_run.main_process.d_total_run.TotalRunExtended;

import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class RepeatExtendedRun {
    public static void main(String[] args) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {

        DataSetAreaInfo crimeDataSet = Constant.crimeDataSet;
        DataSetAreaInfo nycDataSet = Constant.nycDataSet;
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

            System.out.println(normalOutputDir);
            System.out.println(zipfOutputDir);
            System.out.println(normalMultiCentersOutputDir);

            Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalRunExtended.runAndWrite(new DataSetAreaInfo[]{crimeDataSet}, crimeOutputDirArray, new DataSetAreaInfo[]{nycDataSet}, nycOutputDirArray, twoDimNormalDataSet, normalOutputDir, twoDimZipfDataSet, zipfOutputDir, twoDimNormalMultiCentersDataSet, normalMultiCentersOutputDir);
            System.out.println("Finish extended round " + i);
        }

    }
}
