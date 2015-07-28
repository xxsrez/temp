package srez.budget.parse;

import com.google.common.base.MoreObjects;
import srez.budget.domain.Money;

import java.time.LocalDate;

public class Expense {
    private final LocalDate transactionDate;
    private final LocalDate postingDate;
    private final String description;
    private final Money money;
    private final Money credit;
    private final Money debit;
    private final String detailedInformation;

    public Expense(LocalDate transactionDate, LocalDate postingDate, String description, Money money, Money credit, Money debit, String detailedInformation) {
        this.transactionDate = transactionDate;
        this.postingDate = postingDate;
        this.description = description;
        this.money = money;
        this.credit = credit;
        this.debit = debit;
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

    public Money getCredit() {
        return credit;
    }

    public Money getDebit() {
        return debit;
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
                .add("credit", credit)
                .add("debit", debit)
                .add("detailedInformation", detailedInformation)
                .toString();
    }
}
