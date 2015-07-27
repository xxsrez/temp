package srez.analyze;

import srez.domain.Money;

import java.time.LocalDate;

public class Expense {
    LocalDate transactionDate;
    LocalDate postingDate;
    String description;
    Money money;
    Money credit;
    Money debit;
    String detailedInformation;
}
