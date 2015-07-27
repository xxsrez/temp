package srez.budget.parse;

import com.google.common.base.MoreObjects;
import srez.budget.domain.Money;

import java.time.LocalDate;

public class Expense {
    LocalDate transactionDate;
    LocalDate postingDate;
    String description;
    Money money;
    Money credit;
    Money debit;
    String detailedInformation;

    public Expense(LocalDate transactionDate, LocalDate postingDate, String description, Money money, Money credit, Money debit, String detailedInformation) {
        this.transactionDate = transactionDate;
        this.postingDate = postingDate;
        this.description = description;
        this.money = money;
        this.credit = credit;
        this.debit = debit;
        this.detailedInformation = detailedInformation;
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