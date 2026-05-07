package com.practice.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("config.properties file was not found");
            }

            PROPERTIES.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties file", e);
        }
    }

    private ConfigManager() {
    }

    public static String getBaseUrl() {
        return System.getProperty("base.url", PROPERTIES.getProperty("base.url"));
    }
}