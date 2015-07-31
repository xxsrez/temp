package srez.budget.visualize;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Component;
import srez.budget.analyze.Report;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import static org.jfree.chart.ChartFactory.createXYLineChart;

@Component
public class ExpenseGraph {
    public JFreeChart chart(Report report) {
        JFreeChart chart = createXYLineChart(
                "Expenses chart",
                "Day",
                "Expenses",
                dataset(report),
                PlotOrientation.VERTICAL,
                true, true, false);
        XYPlot plot = chart.getXYPlot();
        DateAxis axis = new DateAxis("date");
        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM"));
        plot.setDomainAxis(axis);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);
        return chart;
    }

    public XYSeriesCollection dataset(Report report) {
        XYSeries series = new XYSeries("expenses");
        report.getGroupedByDate()
                .forEach((k, v) -> series.add(
                        LocalDate.ofEpochDay(k).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli(),
                        Optional.ofNullable(v).map(Collection::stream).orElse(Stream.empty())
                                .mapToDouble(e -> e.getMoney().getMoney())
                                .sum()
                ));
        return new XYSeriesCollection(series);
    }

}
