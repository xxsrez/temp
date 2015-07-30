package srez.budget.parse;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;
import srez.budget.domain.Money;

import java.time.LocalDate;

public class Expense implements Comparable<Expense> {
    private final LocalDate transactionDate;
    private final LocalDate postingDate;
    private final String description;
    private final Money money;
    private final String detailedInformation;

    public Expense(LocalDate transactionDate, LocalDate postingDate, String description, Money money, String detailedInformation) {
        this.transactionDate = transactionDate;
        this.postingDate = postingDate;
        this.description = description;
        this.money = money;
        this.detailedInformation = detailedInformation;
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
