package ecnu.dll.construction.run.basic_preprocess;

import cn.edu.ecnu.basic.StringUtil;
import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.result.FileTool;

import java.io.File;

public class CombineSubDatasetResult {
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
