import srez.util.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static srez.util.Pair.toMapCollector;

public class PropertyHelper {
    public static Map<String, String> load(String propertyName) {
        try {
            Properties properties = new Properties();
            properties.load(PropertyHelper.class.getClassLoader().getResourceAsStream(propertyName));
            return properties.entrySet().stream()
                    .map(Pair::new)
                    .map(p -> p.map(String::valueOf, String::valueOf))
                    .map(p -> p.map(v -> System.getProperty(p.getKey(), v)))
                    .collect(toMapCollector());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
