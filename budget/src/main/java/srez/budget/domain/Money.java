package srez.budget.domain;

import java.math.BigDecimal;

public class Money {
    private final BigDecimal money;
    private final String currency;

    public Money(BigDecimal money, String currency) {
        this.money = money;
        this.currency = currency;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(money);
        if (currency != null) {
            result.append(' ').append(currency);
        }
        return result.toString();
    }
}
