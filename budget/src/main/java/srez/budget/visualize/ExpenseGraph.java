package srez.budget.visualize;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import srez.budget.analyze.Analyzer;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import static org.jfree.chart.ChartFactory.createXYLineChart;
import static srez.util.streams.MoreStreams.more;

@Component
public class ExpenseGraph {
    @Autowired
    Analyzer analyzer;

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
        more(analyzer.getGrouped().stream())
                .withIndex()
                .forEach(p -> series.add(
                        p.getKey().doubleValue(),
                        Optional.ofNullable(p.getValue()).map(Collection::stream).orElse(Stream.empty())
                                .mapToDouble(e -> e.getMoney().getMoney())
                                .sum()
                ));
        return new XYSeriesCollection(series);
    }

}
