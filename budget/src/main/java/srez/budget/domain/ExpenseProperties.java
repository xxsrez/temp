package srez.budget.domain;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class ExpenseProperties {
    private final String csvFileName;
    private final String htmlLogPath;

    public ExpenseProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("expense.properties"));
        csvFileName = getProperty("csvFileName", properties);
        htmlLogPath = getProperty("htmlLogPath", properties);
    }

    private static String getProperty(String property, Properties properties) {
        String result = System.getProperty(property, properties.getProperty(property));
        if (result == null) throw new IllegalStateException("Property " + property + " is not set");
        return result;
    }

    public String getCsvFileName() {
        return csvFileName;
    }

    public String getHtmlLogPath() {
        return htmlLogPath;
    }
}
