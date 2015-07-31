package srez.budget.analyze;

import com.google.common.base.Objects;

import java.time.LocalDate;
import java.time.Month;

public class EpochMonth {
    private final Month month;
    private final int year;

    public EpochMonth(LocalDate date) {
        this(date.getMonth(), date.getYear());
    }

    public EpochMonth(Month month, int year) {
        this.month = month;
        this.year = year;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EpochMonth that = (EpochMonth) o;

        return Objects.equal(month, that.month) &&
                Objects.equal(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(month, year);
    }

    public Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
