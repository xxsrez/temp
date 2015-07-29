package srez.budget.domain;

import org.springframework.stereotype.Component;
import srez.util.PropertyHelper;

@Component
public class ExpenseProperties {
    private final String csvFileName;
    private final String htmlLogPath;

    public ExpenseProperties() {
        PropertyHelper helper = PropertyHelper.create("expense.properties");
        csvFileName = helper.getProperty("csvFileName");
        htmlLogPath = helper.getProperty("htmlLogPath");
    }

    public String getCsvFileName() {
        return csvFileName;
    }

    public String getHtmlLogPath() {
        return htmlLogPath;
    }
}
