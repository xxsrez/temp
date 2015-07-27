package srez.budget.parse;

import srez.budget.domain.Money;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            int spaceIdx = string.indexOf(" ");
            String sumString;
            String currency;
            if (spaceIdx == -1) {
                sumString = string;
                currency = null;
            } else {
                sumString = string.substring(0, spaceIdx);
                currency = string.substring(spaceIdx + 1);
            }
            Number sum = new DecimalFormat("###,###.##").parse(sumString);
            return new Money((int) (sum.doubleValue() * 100), currency, 0.01);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String unwrap(String result) {
        return result.substring(1, result.length() - 1);
    }

}
