package srez.budget.visualize;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.analyze.Analyzer;
import srez.budget.domain.ExpenseProperties;
import srez.util.html.HtmlDocument;
import srez.util.html.HtmlImage;
import srez.util.html.HtmlTable;

import javax.annotation.PostConstruct;
import java.io.*;

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
        try {
            File reportRoot = new File(expenseProperties.getHtmlLogPath());
            File reportDir = reportRoot.getParentFile();
            reportDir.mkdirs();

            JFreeChart chart = expenseGraph.chart();
            ChartUtilities.saveChartAsPNG(new File(reportDir, "graph.png"), chart, 800, 600);
            buildReportRoot(reportRoot);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildReportRoot(File rootFile) {
        try {
            HtmlTable expenseTable = new HtmlTable("Expenses", "TransactionDate", "PostingDate", "Description", "Money");
            analyzer.getExpenses().stream()
                    .forEach(e -> expenseTable.add(e.getTransactionDate(), e.getPostingDate(), e.getDescription(), e.getMoney()));
            HtmlTable expenseTableSpecial = new HtmlTable("Expenses Special", "TransactionDate", "PostingDate", "Description", "Money");
            analyzer.getExpensesSpecial().stream()
                    .forEach(e -> expenseTableSpecial.add(e.getTransactionDate(), e.getPostingDate(), e.getDescription(), e.getMoney()));

            HtmlDocument htmlDocument = new HtmlDocument();
            htmlDocument.append(new HtmlImage("graph.png"));
            htmlDocument.append(expenseTable);
            htmlDocument.append(expenseTableSpecial);
            try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(rootFile)))) {
                out.print(htmlDocument);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
