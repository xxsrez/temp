package srez.budget.domain;

public class Money {
    private final int sum;
    private final String currency;
    private final double multiplicator;

    public Money(int sum, String currency, double multiplicator) {
        this.sum = sum;
        this.currency = currency;
        this.multiplicator = multiplicator;
    }

    public double getMoney() {
        return sum * multiplicator;
    }

    public int getSum() {
        return sum;
    }

    public String getCurrency() {
        return currency;
    }

    public double getMultiplicator() {
        return multiplicator;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getMoney() + "");
        if (currency != null) {
            result.append(' ').append(currency);
        }
        return result.toString();
    }
}
