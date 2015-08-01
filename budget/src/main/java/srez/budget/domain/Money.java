package srez.budget.domain;

import static com.google.common.base.Strings.repeat;

public class Money {
    private final int sum;
    private final String currency;
    private final int divident;

    public Money(int sum, String currency, int divident) {
        this.sum = sum;
        this.currency = currency;
        this.divident = divident;
    }

    public double getMoney() {
        return (double) sum / divident;
    }

    public int getSum() {
        return sum;
    }

    public String getCurrency() {
        return currency;
    }

    public int getDivident() {
        return divident;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(format(sum, divident));
        if (currency != null) {
            result.append(' ').append(currency);
        }
        return result.toString();
    }

    private String format(int value, int divident) {
        if (value < 0) return '-' + format(-value, divident);
        StringBuilder result = new StringBuilder();
        int intPart = value / divident;
        int modPart = value % divident;
        result.append(intPart);
        if (modPart != 0) {
            result.append('.');
            int missing = (divident + "").length() - (modPart + "").length() - 1;
            if (missing > 0) {
                result.append(repeat(" ", missing));
            }
            result.append(modPart);
        }
        return result.toString();
    }
}
