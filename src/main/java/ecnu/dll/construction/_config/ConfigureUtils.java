package ecnu.dll.construction._config;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
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

    public static String[] getDatasetFileNameWithDir(String tagName) {
        Document document = Constant.xmlConfigure.getDocument();
        Element element = (Element)document.selectNodes("//datasets/fileName[@name='" + tagName + "']").get(0);
        String[] names = element.getTextTrim().split(";");
        return names;
    }

    public static String getDatasetFileName(String tagName) {
        String[] names = getDatasetFileNameWithDir(tagName);
        return StringUtil.join(ConstantValues.FILE_SPLIT, names);
    }

}
