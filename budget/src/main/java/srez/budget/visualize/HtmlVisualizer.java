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
import java.util.List;

import static java.util.stream.Collectors.toList;

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

            JFreeChart chart = expenseGraph.chart(analyzer.getReport());
            ChartUtilities.saveChartAsPNG(new File(reportDir, "graph.png"), chart, 800, 600);
            buildReportRoot(reportRoot);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildReportRoot(File rootFile) {
        try {
            List<HtmlTable> htmlTables = analyzer.getReport().getGroupedByCategory().entrySet().stream()
                    .map(entry -> {
                        HtmlTable expenseTable = new HtmlTable("Expenses" + entry.getKey(), "TransactionDate", "PostingDate", "Description", "Money");
                        entry.getValue().forEach(e -> expenseTable.add(e.getTransactionDate(), e.getPostingDate(), e.getDescription(), e.getMoney()));
                        return expenseTable;
                    })
                    .collect(toList());

            HtmlDocument htmlDocument = new HtmlDocument();
            htmlDocument.append(new HtmlImage("graph.png"));
            htmlTables.forEach(htmlDocument::append);
            try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(rootFile)))) {
                out.print(htmlDocument);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
