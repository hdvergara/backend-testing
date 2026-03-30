package com.petstore.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Carga de solo lectura desde el classpath ({@code configuration.properties} en {@code src/main/resources}).
 */
public final class ConfigProperties {

    private static final String RESOURCE = "configuration.properties";
    private static volatile Properties cached;

    private ConfigProperties() {
    }

    public static String getProperty(String key) {
        return readProperties().getProperty(key);
    }

    private static Properties readProperties() {
        Properties local = cached;
        if (local != null) {
            return local;
        }
        synchronized (ConfigProperties.class) {
            if (cached == null) {
                Properties loaded = new Properties();
                try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE)) {
                    if (in == null) {
                        throw new IllegalStateException("Missing classpath resource: " + RESOURCE);
                    }
                    loaded.load(in);
                    cached = loaded;
                } catch (IOException e) {
                    throw new IllegalStateException("Could not load " + RESOURCE, e);
                }
            }
            return cached;
        }
    }
}
