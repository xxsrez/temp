package srez.budget.parse;

import srez.budget.domain.Money;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CsvLine {
    final String[] tokens;

    public CsvLine(String line) {
        tokens = line.split(";");
    }

    public String getString(int index) {
        if (index >= tokens.length) return "";
        String result = tokens[index];
        return unwrap(result);
    }

    public LocalDate getDate(int index) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        return LocalDate.parse(getString(index), dateTimeFormatter);
    }

    public Money getMoney(int index) {
        try {
            String string = getString(index);
            if (string.isEmpty()) return null;
            int spaceIdx = string.indexOf(' ');
            String sumString;
            String currency;
            if (spaceIdx == -1) {
                sumString = string;
                currency = null;
            } else {
                sumString = string.substring(0, spaceIdx);
                currency = string.substring(spaceIdx + 1);
            }
            Locale currentLocale = new Locale("en", "US");

            DecimalFormatSymbols unusualSymbols =
                    new DecimalFormatSymbols(currentLocale);
            Number sum = new DecimalFormat("###,###.##", unusualSymbols).parse(sumString);
            return new Money((int) (sum.doubleValue() * 100), currency, 100);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static String unwrap(String result) {
        if (result.charAt(0) != '"' || result.charAt(result.length() - 1) != '"') {
            throw new IllegalArgumentException("Wrong format, no quotes");
        }
        return result.substring(1, result.length() - 1);
    }

}
