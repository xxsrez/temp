package srez.budget;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import srez.context.ContextTools;

@Configuration
@ComponentScan("srez.budget")
public class ExpenseConfig {
    public static ContextTools initConfig() {
        return ContextTools.create()
                .setConfigClass(ExpenseConfig.class)
                .addBean("fileName", "/Users/xxsrez/Downloads/rba-statement-20150727.csv");
    }

    public static void main(String[] args) throws InterruptedException {
        initConfig().start();
    }
}
