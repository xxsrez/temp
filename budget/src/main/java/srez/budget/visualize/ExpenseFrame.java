package srez.budget.visualize;

import org.jfree.chart.ChartPanel;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;

public class ExpenseFrame extends ApplicationFrame {
    public ExpenseFrame(ExpenseGraph expenseGraph) {
        super("title");
        ChartPanel chartPanel = new ChartPanel(expenseGraph.chart());
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

}
