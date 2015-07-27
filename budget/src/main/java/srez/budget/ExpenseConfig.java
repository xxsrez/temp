package srez.budget;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import srez.context.ContextTools;

@Configuration
@ComponentScan("srez.budget")
public class ExpenseConfig {
    public static ContextTools initConfig() {
        return ContextTools.create()
                .setConfigClass(ExpenseConfig.class);
    }

    public static void main(String[] args) throws InterruptedException {
        initConfig().start();
    }
}
