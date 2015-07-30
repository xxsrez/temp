package srez.budget.analyze;

import com.google.common.base.MoreObjects;
import srez.budget.domain.Money;
import srez.budget.parse.Expense;

import java.util.Collection;

public class ExpenseGroup {
    private final Collection<Expense> expenses;

    public ExpenseGroup(Collection<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        double profit = 0;
        double deficit = 0;
        for (Expense expense : expenses) {
            Money money = expense.getMoney();
            double money1 = money.getMoney();
            if (money1 > 0) {
                profit += money1;
            } else {
                deficit += money1;
            }
        }
        return MoreObjects.toStringHelper(getClass())
                .add("profit", profit)
                .add("deficit", deficit)
                .toString();
    }
}
