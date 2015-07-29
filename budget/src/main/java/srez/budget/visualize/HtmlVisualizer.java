package srez.budget.visualize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import srez.budget.domain.ExpenseProperties;
import srez.budget.parse.ExpenseLoader;

import java.io.*;

import static java.util.stream.Stream.of;

@Component
@Profile("html")
public class HtmlVisualizer {
    @Autowired
    ExpenseLoader expenseLoader;
    @Autowired
    ExpenseProperties expenseProperties;

    public void buildReport() {
        File reportRoot = new File(expenseProperties.getHtmlLogPath());
        reportRoot.mkdirs();
        buildReportRoot(reportRoot);

    }

    public void buildReportRoot(File rootFile) {
        try {
            try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(rootFile)))) {
                out.println("<html><body>");
                of(expenseLoader.getExpenses())
                        .forEach(out::println);
                out.println("</body></html>");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
