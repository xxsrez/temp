package srez.budget.visualize;

import org.jfree.ui.RefineryUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.analyze.Analyzer;

import javax.annotation.PostConstruct;

@Component
@Profile("graph")
public class GraphVisualizer {
    @Autowired
    ExpenseGraph expenseGraph;
    @Autowired
    Analyzer analyzer;

    @PostConstruct
    public void showGraph() throws InterruptedException {
        ExpenseFrame chart = new ExpenseFrame(expenseGraph.chart(analyzer.getReport()));
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
