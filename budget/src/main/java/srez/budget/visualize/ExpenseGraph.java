package srez.budget.visualize;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import srez.budget.parse.ExpenseLoader;

import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import static org.jfree.chart.ChartFactory.createXYLineChart;

@Component
public class ExpenseGraph {
    @Autowired
    ExpenseLoader expenseLoader;

    public JFreeChart chart() {
        JFreeChart chart = createXYLineChart(
                "Expenses chart",
                "Day",
                "Expenses",
                dataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);
        return chart;
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
