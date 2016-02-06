package srez.util.format;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class SizeUtil {
    private static final ThreadLocal<DecimalFormat> format = ThreadLocal.withInitial(() ->
            new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH)));

    private static final String[] MODS = {"Gb", "Mb", "Kb"};

    public static String bytesToString(long bytes) {
        double bytesDouble = bytes;
        long div = 2L << 30;
        int index = 0;

        String postfix = bytes + "bytes";

        while (index < MODS.length) {
            double divided = bytesDouble / div;
            if (divided > 2) {
                String mod = MODS[index];
                return format.get().format(divided) + mod + " " + postfix;
            }
            div >>= 10;
            index++;
        }
        return postfix;
    }
}
