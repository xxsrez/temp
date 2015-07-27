package srez.context;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Fun {
    @PostConstruct
    public void init() {
        System.err.println("Fun");

    }
}
