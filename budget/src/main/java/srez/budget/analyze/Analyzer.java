package srez.budget.analyze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import srez.budget.parse.Expense;
import srez.budget.parse.ExpenseLoader;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.groupingBy;
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

    private Collection<Expense> expenses;
    private Collection<Expense> expensesSpecial;
    private Map<Long, List<Expense>> groupedByDate;

    @Autowired
    ExpenseLoader expenseLoader;

    @PostConstruct
    public void analyze() {
        Map<Boolean, List<Expense>> grouping = of(expenseLoader.getExpenses())
                .collect(groupingBy(Analyzer::isSpecial));
        expenses = grouping.get(false);
        expensesSpecial = grouping.get(true);

        groupedByDate = of(expenseLoader.getExpenses())
                .collect(groupingBy(e -> e.getPostingDate().toEpochDay()));
        LongSummaryStatistics stats = groupedByDate.keySet().stream().mapToLong(i -> i).summaryStatistics();
        LongStream.range(stats.getMin(), stats.getMax() + 1)
                .forEach(l -> groupedByDate.computeIfAbsent(l, k -> Collections.<Expense>emptyList()));
    }

    public static boolean isSpecial(Expense expense) {
        return of(PATTERNS)
                .anyMatch(p -> expense.getDescription().contains(p));
    }

    public Collection<Expense> getExpenses() {
        return expenses;
    }

    public Collection<Expense> getExpensesSpecial() {
        return expensesSpecial;
    }

    public Map<Long, List<Expense>> getGroupedByDate() {
        return groupedByDate;
    }
}
