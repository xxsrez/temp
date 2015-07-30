package srez.budget.parse;

import org.junit.Test;
import srez.budget.domain.Money;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;

public class CsvLineTest {
    @Test
    public void testString() throws Exception {
        String string = new CsvLine("\"ABCDEF\"").getString(0);
        assertEquals("ABCDEF", string);
    }

    @Test
    public void testMoney() throws Exception {
        Money money = new CsvLine("\"74,935.00 RUR\"").getMoney(0);
        assertEquals("RUR", money.getCurrency());
        assertEquals(74935, money.getMoney());
    }

    @Test
    public void testDate() throws Exception {
        LocalDate date = new CsvLine("\"04.01.2015\"").getDate(0);
        assertEquals(LocalDate.of(2015, 1, 4), date);
    }
}