package srez.budget.parse;

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

    private String unwrap(String result) {
        return result.substring(1, result.length() - 1);
    }

}
