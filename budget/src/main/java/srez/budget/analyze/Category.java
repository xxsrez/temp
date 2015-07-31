package srez.budget.analyze;

import static java.util.stream.Stream.of;

public enum Category {
    INNER(
            "Own funds transfer",
            "OWN FUNDS TRANSFER",
            "Sale of currency",
            "SALARY",
            "Purchase of currency",
            "B5 FX",
            "P/O"
    ),
    FOOD_OTHER,
    AUTO(
            "AKSEL",
            "MAZK BP"
    ),
    ATM(
            "ATM"
    ),
    CREDIT(
            "AAQPAC"
    ),
    MOBILE(
            "ITUNES"
    ),
    SMALL(
            "APTEKA"
    );

    private final String[] patterns;

    Category(String... patterns) {
        this.patterns = patterns;
    }

    public static Category find(String line) {
        return of(Category.values())
                .filter(c -> of(c.patterns).anyMatch(line::contains))
                .findAny().orElse(FOOD_OTHER);
    }
}
