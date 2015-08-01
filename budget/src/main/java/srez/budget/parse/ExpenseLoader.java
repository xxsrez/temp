package srez.budget.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import srez.budget.domain.Expense;
import srez.budget.domain.ExpenseProperties;
import srez.budget.domain.Money;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

@Component
public class ExpenseLoader {
    private static final Logger log = LoggerFactory.getLogger(ExpenseLoader.class);

    private Collection<Expense> expenses;

    @Autowired
    ExpenseProperties expenseProperties;

    @PostConstruct
    public void load() {
        expenses = of(expenseProperties.getCsvFileName().split(","))
                .flatMap(f -> {
                    try {
                        return lines(Paths.get(f));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .peek(l -> log.info("{}", l))
                .map(CsvLine::new)
                .map(ExpenseLoader::fromCsv)
                .sorted()
                .collect(toList());
    }

    public static Expense fromCsv(CsvLine line) {
        Money moneyCurrency = line.getMoney(3);
        Money moneySigned = line.getMoney(4);
        Money money = new Money(moneySigned.getMoney(), moneyCurrency.getCurrency());
        return new Expense(
                line.getDate(0),
                line.getDate(1),
                line.getString(2),
                money,
                line.getString(5),
                null);
    }

    public Collection<Expense> getExpenses() {
        return expenses;
    }
}
