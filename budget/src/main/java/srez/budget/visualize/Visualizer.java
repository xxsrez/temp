package srez.budget.visualize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import srez.budget.parse.ExpenseLoader;

import javax.annotation.PostConstruct;

@Component
public class Visualizer {
    @Autowired
    ExpenseLoader expenseLoader;

    @PostConstruct
    public void showGraph() throws InterruptedException {
        new ExpenseGraph(expenseLoader);
        Thread.currentThread().join();
    }
}
