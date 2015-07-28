package srez.util.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "srez/util/context")
public class TestConfig {
    @Autowired
    String message;

    public static ContextTools startContext(String message) {
        return ContextTools.create()
                .setConfigClass(TestConfig.class)
                .addBean("message", message)
                .start();
    }

    @Bean
    public String testBean() {
        System.err.println("Hi " + message);
        return "Ok";
    }

    public static void main(String[] args) {
        startContext("HI!");
    }
}
