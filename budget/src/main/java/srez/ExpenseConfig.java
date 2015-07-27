package srez;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import srez.context.ContextTools;

@Configuration
@ComponentScan("srez")
public class ExpenseConfig {
    public static ContextTools startConfig() {
        return ContextTools.create()
                .setConfigClass(ExpenseConfig.class);
    }

    public static void main(String[] args) throws InterruptedException {
        startConfig();
        Thread.currentThread().join();
    }
}
