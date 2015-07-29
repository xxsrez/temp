package srez.util.html;

public class HtmlImage implements HtmlObject {
    private final String imageUrl;

    public HtmlImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String build() {
        return "<img src=\"" + imageUrl + "\"/>";
    }
}
