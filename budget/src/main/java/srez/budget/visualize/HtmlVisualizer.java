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

import static java.util.stream.Stream.of;

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
            HtmlTable expenseTable = new HtmlTable("TransactionDate", "PostingDate", "Description", "Money");
            of(analyzer.getExpenses())
                    .forEach(e -> expenseTable.add(e.getTransactionDate(), e.getPostingDate(), e.getDescription(), e.getMoney()));

            HtmlDocument htmlDocument = new HtmlDocument();
            htmlDocument.append(new HtmlImage("graph.png"));
            htmlDocument.append(expenseTable);
            try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(rootFile)))) {
                out.print(htmlDocument);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
