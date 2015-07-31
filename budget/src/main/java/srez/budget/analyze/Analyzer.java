package srez.budget.analyze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import srez.budget.domain.Expense;
import srez.budget.parse.ExpenseLoader;
import srez.util.Pair;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

@Component
public class Analyzer {
    private static final String[] PATTERNS = {
            "Own funds transfer",
            "OWN FUNDS TRANSFER",
            "Sale of currency",
            "SALARY",
            "Purchase of currency",
            "B5 FX",
            "P/O"
    };

    private Report report;
    private Map<EpochMonth, Report> groupedByMonth;

    @Autowired
    ExpenseLoader expenseLoader;

    @PostConstruct
    public void analyze() {
        List<Expense> expenses = expenseLoader.getExpenses().stream()
                .map(this::expand)
                .filter(e -> e.getCategory() != Category.INNER)
                .collect(toList());

        report = new Report("main", expenses);
        groupedByMonth = expenses.stream()
                .collect(groupingBy(e -> new EpochMonth(e.getPostingDate())))
                .entrySet().stream()
                .map(e -> new Pair<>(e.getKey(), new Report(e.getKey() + "", e.getValue())))
                .collect(Pair.toMapCollector());
    }

    private Expense expand(Expense expense) {
        if (isSpecial(expense)) {
            return new Expense(expense, Category.INNER);
        } else {
            return new Expense(expense, Category.FOOD_OTHER);
        }
    }

    public static boolean isSpecial(Expense expense) {
        return of(PATTERNS)
                .anyMatch(p -> expense.getDescription().contains(p));
    }

    public Report getReport() {
        return report;
    }

    public Map<EpochMonth, Report> getGroupedByMonth() {
        return groupedByMonth;
    }
}
