package srez.budget.visualize;

import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.analyze.Analyzer;
import srez.budget.analyze.EpochMonth;
import srez.budget.domain.ExpenseProperties;
import srez.util.Pair;
import srez.util.html.HtmlDocument;
import srez.util.html.HtmlImage;
import srez.util.html.HtmlTable;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.jfree.chart.ChartUtilities.saveChartAsPNG;
import static srez.util.Pair.toMapCollector;

@Component
@Profile("html")
public class HtmlVisualizer {
    @Autowired
    Analyzer analyzer;
    @Autowired
    ExpenseGraph expenseGraph;
    @Autowired
    ExpenseProperties expenseProperties;

    @PostConstruct
    public void buildReport() {
        File reportRoot = new File(expenseProperties.getHtmlLogPath());
        File reportDir = reportRoot.getParentFile();
        reportDir.mkdirs();

        JFreeChart chart = expenseGraph.chart(analyzer.getReport());
        saveChart(reportDir, chart);
        buildReportRoot(reportDir, reportRoot);
    }

    public void buildReportRoot(File reportDir, File rootFile) {
        try {
            List<HtmlTable> htmlTables = analyzer.getReport().getGroupedByCategory().entrySet().stream()
                    .map(entry -> {
                        HtmlTable expenseTable = new HtmlTable("Expenses" + entry.getKey(), "TransactionDate", "PostingDate", "Description", "Money");
                        entry.getValue().forEach(e -> expenseTable.add(e.getTransactionDate(), e.getPostingDate(), e.getDescription(), e.getMoney()));
                        return expenseTable;
                    })
                    .collect(toList());
            Map<EpochMonth, JFreeChart> charts = analyzer.getGroupedByMonth().entrySet().stream()
                    .map(e -> new Pair<>(e.getKey(), expenseGraph.chart(e.getValue())))
                    .collect(toMapCollector());
            charts.forEach((k, v) -> saveChart(reportDir, v));

            HtmlDocument htmlDocument = new HtmlDocument();
            htmlDocument.append(new HtmlImage("main.png"));
            htmlDocument.append("<BR/>\n");
            charts.forEach((k, v) -> htmlDocument.append(new HtmlImage(v.getTitle() + ".png")));
            htmlTables.forEach(htmlDocument::append);
            try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(rootFile)))) {
                out.print(htmlDocument);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveChart(File reportDir, JFreeChart chart) {
        try {
            saveChartAsPNG(new File(reportDir, chart.getTitle() + ".png"), chart, 800, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
