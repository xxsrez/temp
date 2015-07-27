package srez.budget.parse;

import java.time.LocalDate;

public class CsvLine {
    final String[] tokens;

    public CsvLine(String line) {
        tokens = line.split(";");
    }

    public String getString(int index) {
        String result = tokens[index];
        return unwrap(result);
    }

    public LocalDate getDate(int index) {
        return LocalDate.parse(getString(index));
    }

    private String unwrap(String result) {
        return result.substring(1, result.length() - 1);
    }

}
