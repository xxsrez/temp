package srez.budget.analyze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import srez.budget.parse.Expense;
import srez.budget.parse.ExpenseLoader;

import javax.annotation.PostConstruct;

import static java.util.stream.Stream.of;

@Component
public class Analyzer {
    private Expense[] expenses;

    @Autowired
    ExpenseLoader expenseLoader;

    @PostConstruct
    public void analyze() {
        expenses = of(expenseLoader.getExpenses())
                .filter(e -> !e.getDescription().contains("Own funds transfer"))
                .toArray(Expense[]::new);
    }

    public Expense[] getExpenses() {
        return expenses;
    }
}
