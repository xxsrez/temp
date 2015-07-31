package srez.util.html;

public class HtmlDocument {
    private static final String prefix = "<html><body><pre>";
    private static final String postfix = "</pre></body></html>";

    private final StringBuilder inner = new StringBuilder();

    public HtmlDocument append(HtmlObject htmlObject) {
        return append(htmlObject.build());
    }

    public HtmlDocument append(String string) {
        inner.append(string);
        return this;
    }

    @Override
    public String toString() {
        return prefix + inner + postfix;
    }
}
