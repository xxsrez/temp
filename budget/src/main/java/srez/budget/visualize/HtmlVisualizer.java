package srez.budget.visualize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.analyze.Analyzer;
import srez.budget.analyze.Report;
import srez.budget.domain.Expense;
import srez.budget.domain.ExpenseProperties;
import srez.util.Pair;
import srez.util.html.HtmlDocument;
import srez.util.html.HtmlImage;
import srez.util.html.HtmlTable;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Collection;

import static org.jfree.chart.ChartUtilities.saveChartAsPNG;

@Component
@Profile("html")
public class HtmlVisualizer {
    private static final Logger log = LoggerFactory.getLogger(HtmlVisualizer.class);

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

        document.append(new HtmlImage(report.getTitle() + ".png")).append("\n");
        document.append(new HtmlImage(report.getTitle() + "2.png")).append("\n");

        report.getGroupedByCategory().entrySet().stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue().stream()
                        .mapToDouble(ex -> ex.getMoney().getMoney().doubleValue())
                        .sum()))
                .forEach(p -> document.append(p.getKey() + ": " + p.getValue()).append("\n"));
        document.append("Total: " + report.getGroupedByCategory().values().stream()
                .flatMap(Collection::stream)
                .map(Expense::getMoney)
                .mapToDouble(m -> m.getMoney().doubleValue())
                .sum());


        document.append(expenseTable);
    }

    private void saveChart(File reportDir, Report report) {
        try {
            saveChartAsPNG(new File(reportDir, report.getTitle() + ".png"), expenseGraph.chart(report), 800, 600);
            saveChartAsPNG(new File(reportDir, report.getTitle() + "2.png"), pieGraph.chart(report), 800, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
