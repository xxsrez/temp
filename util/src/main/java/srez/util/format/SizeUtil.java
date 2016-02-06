package srez.util.format;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static java.lang.Math.abs;

public class SizeUtil {
    private static final ThreadLocal<DecimalFormat> format = ThreadLocal.withInitial(() ->
            new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH)));

    private static final String[] MODS = {"Gb", "Mb", "Kb"};

    public static String bytesToString(long bytes) {
        if (bytes == 1) return "1byte";
        if (bytes == -1) return "-1byte";
        double bytesDouble = bytes;
        long div = 1L << 30;
        int index = 0;

        String postfix = bytes + "bytes";
        String minus = bytes < 0 ? "-" : "";

        while (index < MODS.length) {
            double divided = abs(bytesDouble) / div;
            if (divided >= 1.6) {
                String mod = MODS[index];
                return minus + format.get().format(divided) + mod + " " + postfix;
            }
            div >>= 10;
            index++;
        }
        return postfix;
    }
}
