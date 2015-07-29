package srez.util.html;

public class HtmlDocument {
    private static final String prefix = "<html><body><pre>";
    private static final String postfix = "</pre></body></html>";

    private final StringBuilder inner = new StringBuilder();

    public void append(HtmlObject htmlObject) {
        append(htmlObject.build());
    }

    public void append(String string) {
        inner.append(string);
    }

    @Override
    public String toString() {
        return prefix + inner + postfix;
    }
}
