package srez.budget.domain;

import com.google.common.base.MoreObjects;

public class Money {
    private final int sum;
    private final String currency;
    private final double multiplicator;

    public Money(int sum, String currency, double multiplicator) {
        this.sum = sum;
        this.currency = currency;
        this.multiplicator = multiplicator;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sum", sum)
                .add("currency", currency)
                .add("multiplicator", multiplicator)
                .toString();
    }
}
