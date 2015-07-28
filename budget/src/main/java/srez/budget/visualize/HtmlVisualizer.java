package srez.budget.visualize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.parse.ExpenseLoader;

@Component
@Profile("html")
public class HtmlVisualizer {
    @Autowired
    ExpenseLoader expenseLoader;

    public void buildReport() {
        //TODO
    }
}
