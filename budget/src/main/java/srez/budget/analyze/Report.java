package srez.budget.analyze;

import srez.budget.domain.Expense;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.LongStream.range;

public class Report {
    private final String title;
    private final Map<Long, List<Expense>> groupedByDate;
    private final Map<Category, List<Expense>> groupedByCategory;
    private final double average;

    public Report(String title, Collection<Expense> expenses) {
        this.title = title;
        groupedByDate = expenses.stream()
                .collect(groupingBy(e -> e.getPostingDate().toEpochDay()));
        LongSummaryStatistics statistics = groupedByDate.keySet().stream()
                .mapToLong(i -> i)
                .summaryStatistics();
        range(statistics.getMin(), statistics.getMax() + 1)
                .forEach(l -> groupedByDate.computeIfAbsent(l, k -> Collections.<Expense>emptyList()));
        double totalSum = expenses.stream()
                .mapToDouble(e -> e.getMoney().getMoney())
                .sum();
        average = totalSum / groupedByDate.size();

        groupedByCategory = expenses.stream()
                .collect(groupingBy(Expense::getCategory));
    }

    public String getTitle() {
        return title;
    }

    public Map<Long, List<Expense>> getGroupedByDate() {
        return groupedByDate;
    }

    public Map<Category, List<Expense>> getGroupedByCategory() {
        return groupedByCategory;
    }

    public double getAverage() {
        return average;
    }
}
