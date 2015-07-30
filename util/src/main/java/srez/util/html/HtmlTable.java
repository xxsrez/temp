package srez.util.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

public class HtmlTable implements HtmlObject {
    private final String tableName;
    private final String[] title;
    private final Collection<Object[]> data = new ArrayList<>();

    public HtmlTable(String tableName, String... title) {
        this.tableName = tableName;
        this.title = title;
    }

    public HtmlTable add(Object... data) {
        this.data.add(data);
        return this;
    }

    @Override
    public String build() {
        StringBuilder result = new StringBuilder("<h3>" + tableName + "</h3>");
        result.append("<table>");
        Collector<CharSequence, ?, String> rawCollector = joining("</td><td>", "<tr><td>", "</td></tr>");
        result.append(of(title)
                .collect(rawCollector));
        result.append(data.stream()
                .map(Stream::of)
                .map(s -> s.map(String::valueOf))
                .map(s -> s.collect(rawCollector))
                .collect(joining()));
        result.append("</table>");
        return result.toString();
    }
}
