package app.config;

import app.exception.ErrorHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = new FileInputStream("config/app.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            ErrorHandler.handleError(ex.getMessage());
        }
    }

    public static String getConfigValue(String key) {
        return properties.getProperty(key);
    }
}
