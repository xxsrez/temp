package srez.budget.visualize;

import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.analyze.Analyzer;
import srez.budget.analyze.Report;
import srez.budget.domain.ExpenseProperties;
import srez.util.html.HtmlDocument;
import srez.util.html.HtmlImage;
import srez.util.html.HtmlTable;

import javax.annotation.PostConstruct;
import java.io.*;

import static org.jfree.chart.ChartUtilities.saveChartAsPNG;

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
        HtmlTable expenseTable = new HtmlTable("Expenses" + report.getTitle(), "TransactionDate", "PostingDate", "Description", "Money");
        report.getExpenses().forEach(e -> expenseTable.add(e.getTransactionDate(), e.getPostingDate(), e.getDescription(), e.getMoney()));
        document.append(new HtmlImage(report.getTitle() + ".png")).append("\n");
        document.append(expenseTable);
    }

    private void saveChart(File reportDir, Report report) {
        try {
            JFreeChart chart = expenseGraph.chart(report);
            saveChartAsPNG(new File(reportDir, report.getTitle() + ".png"), chart, 800, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
