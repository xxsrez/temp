package srez.budget.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import srez.budget.domain.ExpenseProperties;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;

import static java.nio.file.Files.lines;
import static java.util.stream.Stream.of;

@Component
public class ExpenseLoader {
    private static final Logger log = LoggerFactory.getLogger(ExpenseLoader.class);

    private Expense[] expenses;

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
                .toArray(Expense[]::new);
    }

    public static Expense fromCsv(CsvLine line) {
        return new Expense(
                line.getDate(0),
                line.getDate(1),
                line.getString(2),
                line.getMoney(3),
                line.getMoney(4),
                line.getMoney(5),
                line.getString(6)
        );
    }

    public Expense[] getExpenses() {
        return expenses;
    }
}
