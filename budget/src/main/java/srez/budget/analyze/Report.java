package srez.budget.analyze;

import srez.budget.domain.Expense;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Report {
    private final String title;
    private final Map<Long, List<Expense>> groupedByDate;
    private final Map<Category, List<Expense>> groupedByCategory;

    public Report(String title, Collection<Expense> expenses) {
        this.title = title;
        groupedByDate = expenses.stream()
                .collect(Collectors.groupingBy(e -> e.getPostingDate().toEpochDay()));


        groupedByCategory = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory));
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
}
