package srez.budget.visualize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.parse.ExpenseLoader;

import javax.annotation.PostConstruct;

import static java.util.stream.Stream.of;

@Component
@Profile("log")
public class LogVisualizer {
    private static final Logger log = LoggerFactory.getLogger(LogVisualizer.class);

    @Autowired
    ExpenseLoader expenseLoader;

    @PostConstruct
    public void dumpLog() {
        of(expenseLoader.getExpenses())
                .forEach(e -> log.info("{}", e));
    }
}
