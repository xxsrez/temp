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
        result.append(sum / divident);
        int restPart = sum % divident;
        if (restPart != 0) {
            result.append('.');
            int missing = (divident + "").length() - (restPart + "").length() - 1;
            if (missing > 0) {
                result.append(repeat(" ", missing));
            }
            result.append(restPart);
        }
        if (currency != null) {
            result.append(' ').append(currency);
        }
        return result.toString();
    }
}
