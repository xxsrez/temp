package srez.budget.visualize;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.domain.ExpenseProperties;
import srez.budget.parse.ExpenseLoader;
import srez.util.html.HtmlDocument;

import javax.annotation.PostConstruct;
import java.io.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

@Component
@Profile("html")
public class HtmlVisualizer {
    @Autowired
    ExpenseLoader expenseLoader;
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
            HtmlDocument htmlDocument = new HtmlDocument();
            htmlDocument.append(of(expenseLoader.getExpenses())
                    .map(String::valueOf)
                    .collect(joining("\n", "<pre>", "</pre>")));
            try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(rootFile)))) {
                out.print(htmlDocument);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
