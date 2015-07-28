package srez.budget.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;

import static java.nio.file.Files.lines;

@Component
public class ExpenseLoader {
    private static final Logger log = LoggerFactory.getLogger(ExpenseLoader.class);

    private Expense[] expenses;

    @Autowired
    String fileName;

    @PostConstruct
    public void load() {
        try {
            expenses = lines(Paths.get(fileName))
                    .map(CsvLine::new)
                    .map(ExpenseLoader::fromCsv)
                    .toArray(Expense[]::new);
        } catch (IOException e) {
            log.error("", e);
        }
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
