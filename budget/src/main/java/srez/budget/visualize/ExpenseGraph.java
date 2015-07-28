package srez.budget.visualize;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
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
        JFreeChart xylineChart = chart();
        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new Dimension(560, 367));
        XYPlot plot = xylineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
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
                .forEach(e -> series.add(e.getKey().doubleValue(), e.getValue()));
        return new XYSeriesCollection(series);
    }
}
