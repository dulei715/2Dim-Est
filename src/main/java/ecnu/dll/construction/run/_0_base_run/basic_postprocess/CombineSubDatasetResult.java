package ecnu.dll.construction.run._0_base_run.basic_postprocess;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.result.FileTool;
import cn.edu.dll.struct.result.ColumnBean;
import ecnu.dll.construction._config.Constant;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class CombineSubDatasetResult {

    public static final Double doubleUpperBound = Double.MAX_VALUE;
    public static final Double doubleLowerBound = -Double.MAX_VALUE;
    /**
     * 将给定的目录下的文件按照文件名进行简单合并
     * @param relativeBasicPath 给出的根目录。下面放着一系列文件夹 dirA, dirB, ...
     *                          每个文件夹里是待合并的文件 dirA:{fileA, fileB, fileC, ...}; dirB: {fileA, fileB, fileC, ...}; ...
     *                          需要合并每个文件夹中的fileA, fileB, fileC, ...，并将他们放在根目录下。
     * @param fileNames 需要被合并的文件夹名字（e.g. fileA, fileB, ...）。
     */
    public static void combineCSVResult(String relativeBasicPath, String... fileNames) {

        File relativeBasicFile = new File(relativeBasicPath);
        String[] directoryPathArray = FileTool.toStringArray(relativeBasicFile.listFiles(Constant.directoryFilter));
        String[] tempInputFilePathArray;
        String tempOutputFilePath;
        for (String fileName : fileNames) {
            tempInputFilePathArray = StringUtil.concatGiveString(directoryPathArray, ConstantValues.FILE_SPLIT, fileName);
            tempOutputFilePath = relativeBasicPath.concat(ConstantValues.FILE_SPLIT).concat(fileName);
            FileTool.combineFilesWithTheSameFirstLine(tempInputFilePathArray, tempOutputFilePath);
        }
    }

//    public static void composeCSVResult(List<String> inputPathList, String outputDir, List<ColumnBean> columnBeanList, double divideFactor) {
//
//    }
    public static void composeCSVResult_before(String relativeBasicPath, ColumnBean[] columnBeansArray, String... fileNames) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        File relativeBasicFile = new File(relativeBasicPath);
        // todo: for test
//        System.out.println(relativeBasicFile);
//        System.out.println(relativeBasicFile.getAbsolutePath());

        File[] files = relativeBasicFile.listFiles(Constant.directoryFilter);

//        MyPrint.showArray(files);

        String[] directoryPathArray = FileTool.toStringArray(files);
        String[] tempInputFilePathArray;
        String tempOutputFilePath;
        List<ColumnBean> columnBeanList = Arrays.asList(columnBeansArray);
        for (String fileName : fileNames) {
            tempInputFilePathArray = StringUtil.concatGiveString(directoryPathArray, ConstantValues.FILE_SPLIT, fileName);
            tempOutputFilePath = relativeBasicPath.concat(ConstantValues.FILE_SPLIT).concat(fileName);
            FileTool.composeCSVFileWithTheSameFirstLine(tempInputFilePathArray, tempOutputFilePath, columnBeanList, FileTool.AVERAGE_COMPOSE);
        }
    }
    public static void composeCSVResult(String relativeBasicPath, ColumnBean[] columnBeansArray, String... fileNames) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        File relativeBasicFile = new File(relativeBasicPath);
        // todo: for test
//        System.out.println(relativeBasicFile);
//        System.out.println(relativeBasicFile.getAbsolutePath());

        File[] files = relativeBasicFile.listFiles(Constant.directoryFilter);

//        MyPrint.showArray(files);

        String[] directoryPathArray = FileTool.toStringArray(files);
        String[] tempInputFilePathArray;
        String tempOutputFilePath;
        List<ColumnBean> columnBeanList = Arrays.asList(columnBeansArray);
        for (String fileName : fileNames) {
            tempInputFilePathArray = StringUtil.concatGiveString(directoryPathArray, ConstantValues.FILE_SPLIT, fileName);
            tempOutputFilePath = relativeBasicPath.concat(ConstantValues.FILE_SPLIT).concat(fileName);
            FileTool.composeCSVFileWithTheSameFirstLine(tempInputFilePathArray, tempOutputFilePath, columnBeanList, FileTool.AVERAGE_COMPOSE);
        }
    }

    public static void composeAllBasicRepeat() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String[] allRelativeBasicDir = BasicArrayUtil.combineArray(new String[][]{Constant.basicOutputCrimeDirArray, Constant.basicOutputNYCDirArray, new String[]{Constant.basicOutputNormalDir}, new String[]{Constant.basicOutputZipfDir}, new String[]{Constant.basicOutputMultiNormalDir}});
        String[] fileNameArray = StringUtil.concatGiveString(Constant.basicAlterKeyArray, ".csv");
        for (String relativeBasicPath : allRelativeBasicDir) {
            composeCSVResult(relativeBasicPath, Constant.columnBeanArray, fileNameArray);
        }
    }
    public static void composeAllExtendedRepeat() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String[] allRelativeBasicDir = BasicArrayUtil.combineArray(new String[][]{Constant.extendedOutputCrimeDirArray, Constant.extendedOutputNYCDirArray, new String[]{Constant.extendedOutputNormalDir}, new String[]{Constant.extendedOutputZipfDir}, new String[]{Constant.extendedOutputMultiNormalDir}});
        String[] fileNameArray = StringUtil.concatGiveString(Constant.extendedAlterKeyArray, ".csv");
        for (String relativeBasicPath : allRelativeBasicDir) {
            composeCSVResult(relativeBasicPath, Constant.columnBeanArray, fileNameArray);
        }
    }
    public static void composeAllTrajectoryRepeat() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String[] allRelativeBasicDir = BasicArrayUtil.combineArray(new String[][]{{Constant.trajectoryOutputNYCDir}});
        String[] fileNameArray = StringUtil.concatGiveString(Constant.trajectoryAlterKeyArray, ".csv");
        for (String relativeBasicPath : allRelativeBasicDir) {
            composeCSVResult(relativeBasicPath, Constant.columnBeanArray, fileNameArray);
        }
    }

    public static void composeAllExtendedRepeatForKLDivergence() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String[] allRelativeBasicDir = BasicArrayUtil.combineArray(new String[][]{Constant.extendedOutputCrimeDirArray, Constant.extendedOutputNYCDirArray, new String[]{Constant.extendedOutputNormalDir}, new String[]{Constant.extendedOutputZipfDir}, new String[]{Constant.extendedOutputMultiNormalDir}});
        String[] fileNameArray = StringUtil.concatGiveString(Constant.extendedAlterKeyArray, ".csv");
        for (String relativeBasicPath : allRelativeBasicDir) {
            composeCSVResult(relativeBasicPath, Constant.columnBeanArrayForKLDivergence, fileNameArray);
        }
    }

    public static void combineAllBasicPart() {
        String[] toBeCombinedBasicDir = BasicArrayUtil.combineArray(new String[][]{new String[]{Constant.basicRelativeParentCrimeDir}, new String[]{Constant.basicRelativeParentNYCDir}});
        String[] fileNameArray = StringUtil.concatGiveString(Constant.basicAlterKeyArray, ".csv");
        for (String relativeBasicPath : toBeCombinedBasicDir) {
            combineCSVResult(relativeBasicPath, fileNameArray);
        }
    }
    public static void combineAllExtendedPart() {
        String[] toBeCombinedBasicDir = BasicArrayUtil.combineArray(new String[][]{new String[]{Constant.extendedRelativeParentCrimeDir}, new String[]{Constant.extendedRelativeParentNYCDir}});
        String[] fileNameArray = StringUtil.concatGiveString(Constant.extendedAlterKeyArray, ".csv");
        for (String relativeBasicPath : toBeCombinedBasicDir) {
            combineCSVResult(relativeBasicPath, fileNameArray);
        }
    }


    public static void main0(String[] args) {
        String[] inputArrayParentPath = new String[]{
                "F:\\dataset\\test\\result\\crime\\crimeA",
                "F:\\dataset\\test\\result\\crime\\crimeB",
                "F:\\dataset\\test\\result\\crime\\crimeC",
        };
        String outputParentPath = "F:\\dataset\\test\\result\\crime";
//        String[] inputArrayParentPath = new String[]{
//                "F:\\dataset\\test\\result\\nyc\\nycA",
//                "F:\\dataset\\test\\result\\nyc\\nycB",
//                "F:\\dataset\\test\\result\\nyc\\nycC",
//        };
//        String outputParentPath = "F:\\dataset\\test\\result\\nyc";

        String tempName;
        String[] tempInputFilePathArray;
        String tempOutputFilePath;
        String[] nameArray = new File(inputArrayParentPath[0]).list();
        for (String fileName : nameArray) {
            tempInputFilePathArray = StringUtil.concatGiveString(inputArrayParentPath, "\\"+fileName);
            tempOutputFilePath = outputParentPath + "\\" + fileName;
            FileTool.combineFilesWithTheSameFirstLine(tempInputFilePathArray, tempOutputFilePath);
        }
    }
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
//        composeAllBasicRepeat();
//        combineAllBasicPart();

//        composeAllExtendedRepeat();
//        combineAllExtendedPart();

        composeAllTrajectoryRepeat();
        combineAllExtendedPart();

//        composeAllExtendedRepeatForKLDivergence();
//        combineAllExtendedPart();

    }
}
