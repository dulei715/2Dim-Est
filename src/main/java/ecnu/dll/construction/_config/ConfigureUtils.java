package ecnu.dll.construction._config;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.result.FileTool;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigureUtils {
    public static String getDatasetBasicPath() {
        Document document = Constant.xmlConfigure.getDocument();
        Element relativePathElement = (Element) document.selectNodes("//datasets/basicPath[@type='relative']").get(0);
        String relativePath = relativePathElement.getTextTrim(), absolutePath;
        relativePath = relativePath.replace(";", ConstantValues.FILE_SPLIT);
        absolutePath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.projectPath, relativePath);
        File datasetFile = new File(absolutePath);
        if (datasetFile.exists()) {
            return datasetFile.getAbsolutePath();
        }
        List<Element> elemnetList = document.selectNodes("//datasets/basicPath[@type='absolute']");
        for (Element element : elemnetList) {
            absolutePath = element.getTextTrim();
            absolutePath = absolutePath.replace(";", ConstantValues.FILE_SPLIT);
            datasetFile = new File(absolutePath);
            if (datasetFile.exists()) {
                return datasetFile.getAbsolutePath();
            }
        }
        throw new RuntimeException("No valid data set path!");
    }

    public static List<List<String>> getDatasetFileNameWithDir(String tagName) {
        Document document = Constant.xmlConfigure.getDocument();
        Element datasetElement = (Element)document.selectNodes("//datasets/dataset[@name='" + tagName + "']").get(0);
        String relativeDir = ((Element) datasetElement.selectNodes("relativeDir").get(0)).getTextTrim().replace(";", ConstantValues.FILE_SPLIT);
        List<Element> fileElementList = datasetElement.selectNodes("subset");
        int size = fileElementList.size();
        List<List<String>> result = new ArrayList<>(size);
        List<String> item;
        String fileName;
        for (Element element : fileElementList) {
            item = new ArrayList<>();
            item.add(relativeDir);
            fileName = element.element("fileName").getTextTrim();
            item.add(fileName);
            result.add(item);
        }
        return result;
    }

    public static List<String> getDatasetRelativeFilePathList(String tagName) {
        List<List<String>> itemList = getDatasetFileNameWithDir(tagName);
        List<String> result = new ArrayList<>();
        for (List<String> item : itemList) {
            result.add(StringUtil.join(ConstantValues.FILE_SPLIT, item));
        }
        return result;
    }

    public static List<String> getDatasetSimpleFileNameList(String tagName) {
        List<List<String>> itemList = getDatasetFileNameWithDir(tagName);
        List<String> result = new ArrayList<>();
        for (List<String> item : itemList) {
            result.add(item.get(item.size()-1));
        }
        return result;
    }

    public static DataSetAreaInfo[] getDatasetInfoArray(String basicDatasetPath, String tagName) {
        DataSetAreaInfo[] dataSetAreaInfos;
        DataSetAreaInfo tempDataSetAreaInfo;
        String titleName, fileName, datasetPath;
        Double[] bound;
        Double areaLength;
        Document document = Constant.xmlConfigure.getDocument();
        Element datasetElement = (Element)document.selectNodes("//datasets/dataset[@name='" + tagName + "']").get(0);
        String relativeDir = ((Element) datasetElement.selectNodes("relativeDir").get(0)).getTextTrim().replace(";", ConstantValues.FILE_SPLIT);
        List<Element> fileElementList = datasetElement.selectNodes("subset");
        int size = fileElementList.size();
        dataSetAreaInfos = new DataSetAreaInfo[size];
        int index = 0;
        for (Element element : fileElementList) {
            titleName = element.element("titleName").getTextTrim();
            fileName = element.element("fileName").getTextTrim();
            datasetPath = FileTool.getPath(basicDatasetPath, relativeDir, fileName);
            bound = BasicArrayUtil.toDoubleArray(element.element("bound").getTextTrim().split(";"));
            areaLength = Double.valueOf(element.element("areaLength").getTextTrim());
            tempDataSetAreaInfo = new DataSetAreaInfo(datasetPath, titleName, bound[0], bound[1], areaLength);
            dataSetAreaInfos[index++] = tempDataSetAreaInfo;
        }
        return dataSetAreaInfos;
    }

    public static String getPropertyName() {
        Document document = Constant.xmlConfigure.getDocument();
        Element idElement = (Element)document.selectNodes("//properties/using").get(0);
        String name = idElement.getTextTrim();
        Element propertyElement = (Element)document.selectNodes("//properties/property[@name='" + name + "']").get(0);
        return propertyElement.getTextTrim();
    }

    public static void main(String[] args) {
        String basicDatasetPath = Constant.basicDatasetPath;
        String datasetTag = "norm_2";
        DataSetAreaInfo[] datasetInfoArray = getDatasetInfoArray(basicDatasetPath, datasetTag);
        MyPrint.showArray(datasetInfoArray, ConstantValues.LINE_SPLIT);
    }

}
