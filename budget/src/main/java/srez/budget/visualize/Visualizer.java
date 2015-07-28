package srez.budget.visualize;

import org.jfree.ui.RefineryUtilities;
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
        ExpenseGraph chart = new ExpenseGraph(expenseLoader);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        Thread.currentThread().join();
    }
}
