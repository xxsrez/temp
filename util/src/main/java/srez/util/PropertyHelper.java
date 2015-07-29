package srez.util;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class PropertyHelper {
    private final Properties properties = new Properties();

    private PropertyHelper(String propertyName) {
        try {
            properties.load(PropertyHelper.class.getClassLoader().getResourceAsStream(propertyName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PropertyHelper create(String propertyName) {
        return new PropertyHelper(propertyName);
    }

    public String getProperty(String property) {
        return Optional.ofNullable(System.getProperty(property, properties.getProperty(property)))
                .orElseThrow(() -> new IllegalArgumentException(property + " not set"));
    }
}
