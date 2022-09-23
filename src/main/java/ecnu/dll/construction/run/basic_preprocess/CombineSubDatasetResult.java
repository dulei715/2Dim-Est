package ecnu.dll.construction.run.basic_preprocess;

import cn.edu.ecnu.basic.StringUtil;
import cn.edu.ecnu.constant_values.ConstantValues;
import cn.edu.ecnu.result.FileTool;
import ecnu.dll.construction._config.Constant;

import java.io.File;
import java.util.List;

public class CombineSubDatasetResult {
    /**
     *
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

    public static void composeCSVResult(List<String> inputPathList, String outputDir, double divideFactor) {

    }
    public static void main(String[] args) {
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
}
