package srez.budget.analyze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import srez.budget.parse.Expense;
import srez.budget.parse.ExpenseLoader;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.of;

@Component
public class Analyzer {
    private Expense[] expenses;
    private List<List<Expense>> grouped;

    @Autowired
    ExpenseLoader expenseLoader;

    @PostConstruct
    public void analyze() {
        expenses = of(expenseLoader.getExpenses())
                .filter(e -> e.getDescription() == null || !e.getDescription().contains("Own funds transfer"))
                .toArray(Expense[]::new);

        Map<Long, List<Expense>> byDate = of(expenseLoader.getExpenses())
                .collect(groupingBy(e -> e.getPostingDate().toEpochDay()));
        LongSummaryStatistics statistics = byDate.keySet().stream()
                .mapToLong(i -> i)
                .summaryStatistics();
        grouped = range(0, (int) (statistics.getMax() - statistics.getMin() + 1))
                .mapToObj(i -> byDate.get(i + statistics.getMin()))
                .collect(toList());
    }

    public Expense[] getExpenses() {
        return expenses;
    }

    public List<List<Expense>> getGrouped() {
        return grouped;
    }
}
