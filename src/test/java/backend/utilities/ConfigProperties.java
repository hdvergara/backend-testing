package backend.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
    private static Properties properties;
    private static final String PATH_PROPERTIES_FILE = "src/test/java/resources/configuration.properties";

    public static String getProperty(String key) {
        return readPropertiesFile().getProperty(key);
    }

    public static Properties readPropertiesFile() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(PATH_PROPERTIES_FILE)) {
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties writePropertiesFile(String key, String value) {
        Properties properties =  readPropertiesFile();
        properties.setProperty(key,value);
        try (FileOutputStream  fileOutputStream = new FileOutputStream (PATH_PROPERTIES_FILE)) {
            properties.store(fileOutputStream, null);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
