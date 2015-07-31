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
            "P/O",
            "CPL",
            "HOTEL BEGA"
    ),
    FOOD_OTHER,
    AUTO(
            "AKSEL",
            "MAZK BP",
            "NCH-SPB",
            "PTK",
            "LUKOIL",
            "ELITSERVIS",
            "MOYKA NA LITEYNOM"
    ),
    ATM(
            "ATM"
    ),
    CREDIT(
            "AAQPAC"
    ),
    MOBILE(
            "ITUNES",
            "YOTA",
            "TWITCHTV",
            "mobile pmnt"
    ),
    SMALL(
            "APTEKA",
            "APTEEKK",
            "PETROFARM",
            "TAXI",
            "SPORTMASTER",
            "THE LAB",
            "ERMITAZH"
    ),
    RANDOM(
            "DECATHLON",
            "CALVIN KLEIN"
    ),
    UNKNOWN(
            "YM.GAMES"
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
