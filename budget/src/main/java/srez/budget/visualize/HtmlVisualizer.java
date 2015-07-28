package srez.budget.visualize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.domain.ExpenseProperties;
import srez.budget.parse.ExpenseLoader;

@Component
@Profile("html")
public class HtmlVisualizer {
    @Autowired
    ExpenseLoader expenseLoader;
    @Autowired
    ExpenseProperties expenseProperties;

    public void buildReport() {
        //TODO
        expenseProperties.getHtmlLogPath();
    }
}
