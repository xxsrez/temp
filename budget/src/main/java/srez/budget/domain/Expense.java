package srez.budget.domain;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;
import srez.budget.analyze.Category;

import java.time.LocalDate;

public class Expense implements Comparable<Expense> {
    private final LocalDate transactionDate;
    private final LocalDate postingDate;
    private final String description;
    private final Money money;
    private final String detailedInformation;
    private final Category category;

    public Expense(LocalDate transactionDate, LocalDate postingDate, String description, Money money, String detailedInformation, Category category) {
        this.transactionDate = transactionDate;
        this.postingDate = postingDate;
        this.description = description;
        this.money = money;
        this.detailedInformation = detailedInformation;
        this.category = category;
    }

    public Expense(Expense expense, Category category) {
        transactionDate = expense.transactionDate;
        postingDate = expense.postingDate;
        description = expense.description;
        money = expense.money;
        detailedInformation = expense.detailedInformation;
        this.category = category;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public LocalDate getPostingDate() {
        return postingDate;
    }

    public String getDescription() {
        return description;
    }

    public Money getMoney() {
        return money;
    }

    public String getDetailedInformation() {
        return detailedInformation;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("transactionDate", transactionDate)
                .add("postingDate", postingDate)
                .add("description", description)
                .add("money", money)
                .add("detailedInformation", detailedInformation)
                .toString();
    }

    @Override
    public int compareTo(Expense o) {
        return ComparisonChain.start()
                .compare(postingDate, o.postingDate)
                .compare(money.getMoney(), o.money.getMoney())
                .result();
    }
}
