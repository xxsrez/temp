package srez.budget.visualize;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.stereotype.Component;
import srez.budget.analyze.Report;
import srez.budget.domain.Expense;
import srez.budget.domain.Money;
import srez.util.Pair;

import static java.lang.Math.abs;
import static org.jfree.chart.ChartFactory.createPieChart;

@Component
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
                .map(l -> new Pair<>(l.getKey(), l.getValue().stream()
                        .map(Expense::getMoney)
                        .mapToDouble(Money::getMoney)
                        .sum()))
                .forEach(p -> dataset.setValue(p.getKey().name(), abs(p.getValue())));
        return dataset;
    }
}
