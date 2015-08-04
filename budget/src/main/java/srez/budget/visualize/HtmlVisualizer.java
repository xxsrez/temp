package srez.budget.visualize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.analyze.Analyzer;
import srez.budget.analyze.Report;
import srez.budget.domain.Expense;
import srez.budget.domain.ExpenseProperties;
import srez.budget.domain.Money;
import srez.util.Pair;
import srez.util.html.HtmlDocument;
import srez.util.html.HtmlImage;
import srez.util.html.HtmlTable;

import javax.annotation.PostConstruct;
import java.io.*;
import java.math.BigDecimal;
import java.util.Collection;

import static org.jfree.chart.ChartUtilities.saveChartAsPNG;

@Component
@Profile("html")
public class HtmlVisualizer {
    @Autowired
    Analyzer analyzer;
    @Autowired
    ExpenseGraph expenseGraph;
    @Autowired
    PieGraph pieGraph;
    @Autowired
    ExpenseProperties expenseProperties;

    @PostConstruct
    public void buildReport() throws FileNotFoundException {
        File reportRoot = new File(expenseProperties.getHtmlLogPath());
        File reportDir = reportRoot.getParentFile();
        reportDir.mkdirs();

        saveChart(reportDir, analyzer.getReport());
        HtmlDocument htmlDocument = new HtmlDocument();
        buildReportRoot(reportDir, htmlDocument);
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(reportRoot)))) {
            out.print(htmlDocument);
        }

    }

    public void buildReportRoot(File reportDir, HtmlDocument htmlDocument) {
        buildReport(reportDir, htmlDocument, analyzer.getReport());
        analyzer.getGroupedByMonth().values().stream()
                .sorted((r1, r2) -> -r1.compareTo(r2))
                .forEach(r -> buildReport(reportDir, htmlDocument, r));
    }

    public void buildReport(File reportDir, HtmlDocument document, Report report) {
        saveChart(reportDir, report);
        HtmlTable expenseTable = new HtmlTable("Expenses" + report.getTitle(), "TransactionDate", "PostingDate", "Description", "Money", "Category");
        report.getExpenses().forEach(e -> expenseTable.add(e.getTransactionDate(), e.getPostingDate(), e.getDescription(), e.getMoney(), e.getCategory()));

        document.append(new HtmlImage(getImageUrl1(report))).append("\n");
        document.append(new HtmlImage(
                getImageUrl2(report))).append("\n");

        report.getGroupedByCategory().entrySet().stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue().stream()
                        .map(Expense::getMoney)
                        .map(Money::getMoney)
                        .mapToDouble(BigDecimal::doubleValue)
                        .sum()))
                .forEach(p -> document.append(p.getKey() + ": " + p.getValue()).append("\n"));
        document.append("Total: " + report.getGroupedByCategory().values().stream()
                .flatMap(Collection::stream)
                .map(Expense::getMoney)
                .map(Money::getMoney)
                .mapToDouble(BigDecimal::doubleValue)
                .sum() + '\n');
        document.append("Average/day: " + report.getAverage());


        document.append(expenseTable);
    }

    private void saveChart(File reportDir, Report report) {
        try {
            saveChartAsPNG(new File(reportDir, getImageUrl1(report)), expenseGraph.chart(report), 800, 600);
            saveChartAsPNG(new File(reportDir, getImageUrl2(report)), pieGraph.chart(report), 800, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getImageUrl1(Report report) {
        return report.getTitle() + ".png";
    }

    private String getImageUrl2(Report report) {
        return report.getTitle() + "2.png";
    }
}
