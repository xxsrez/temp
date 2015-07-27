package srez.budget.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ExpenseLoader {
    private static final Logger log = LoggerFactory.getLogger(ExpenseLoader.class);

    @Autowired
    String fileName;

    @PostConstruct
    public void load() {
        try {
            Files.lines(Paths.get(fileName))
                    .forEach(System.err::println);
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
