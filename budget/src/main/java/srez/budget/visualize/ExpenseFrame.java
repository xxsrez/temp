package srez.budget.visualize;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;

public class ExpenseFrame extends ApplicationFrame {
    public ExpenseFrame(JFreeChart chart) {
        super("title");
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }
}
