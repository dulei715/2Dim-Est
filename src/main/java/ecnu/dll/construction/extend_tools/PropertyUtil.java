package ecnu.dll.construction.extend_tools;

import java.util.Properties;

public class PropertyUtil {
    public static String[] getValueArryStringByKey(Properties properties, String key, String splitTag) {
        return properties.getProperty(key).trim().split(splitTag);
    }
}
