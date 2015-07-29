package srez.budget.visualize;

import org.jfree.chart.ChartUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.domain.ExpenseProperties;
import srez.budget.parse.ExpenseLoader;

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

            ChartUtilities.saveChartAsPNG(new File(reportDir, "graph.png"), expenseGraph.chart(), 800, 600);
            buildReportRoot(reportRoot);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildReportRoot(File rootFile) {
        try {
            try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(rootFile)))) {
                out.print("<html><body>");
                out.print(of(expenseLoader.getExpenses())
                        .map(String::valueOf)
                        .collect(joining("\n", "<pre>", "</pre>")));
                out.print("</body></html>");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
