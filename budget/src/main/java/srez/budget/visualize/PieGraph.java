package srez.budget.visualize;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import srez.budget.analyze.Report;
import srez.util.Pair;

import static org.jfree.chart.ChartFactory.createPieChart;

public class PieGraph {
    public JFreeChart chart(Report report) {
        return createPieChart(
                report.getTitle(),
                dataset(report),
                true,
                true,
                false);
    }

    private PieDataset dataset(Report report) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        report.getGroupedByCategory().entrySet().stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue().stream()
                        .mapToDouble(e2 -> e2.getMoney().getMoney())
                        .sum()))
                .forEach(p -> dataset.setValue(p.getKey().name(), p.getValue()));
        return dataset;
    }
}
