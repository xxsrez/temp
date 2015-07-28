package srez.budget.visualize;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import srez.budget.parse.ExpenseLoader;

import java.awt.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import static org.jfree.chart.ChartFactory.createXYLineChart;

public class ExpenseGraph extends ApplicationFrame {
    private final ExpenseLoader expenseLoader;

    public ExpenseGraph(ExpenseLoader expenseLoader) {
        super("title");
        this.expenseLoader = expenseLoader;
        JFreeChart chart = chart();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    public JFreeChart chart() {
        return createXYLineChart(
                "Expenses chart",
                "Day",
                "Expenses",
                dataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
    }

    public XYSeriesCollection dataset() {
        XYSeries series = new XYSeries("expenses");
        Stream.of(expenseLoader.getExpenses())
                .collect(groupingBy(e -> e.getPostingDate().toEpochDay(), summingDouble(k -> k.getMoney().getMoney())))
                .entrySet().stream()
                .map(e -> new XYDataItem(e.getKey().doubleValue(), e.getValue().doubleValue()))
                .forEach(series::add);
        return new XYSeriesCollection(series);
    }
}
