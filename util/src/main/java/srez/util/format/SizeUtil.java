package srez.util.format;

import java.text.DecimalFormat;

public class SizeUtil {
    private static final ThreadLocal<DecimalFormat> format = ThreadLocal.withInitial(() -> new DecimalFormat("#.##"));

    private static final String[] MODS = {"Gb", "Mb", "Kb"};

    public static String bytesToString(long bytes) {
        double bytesDouble = bytes;
        int div = 2 << 30;
        int index = 0;

        String postfix = bytes + "bytes";

        while (index <= MODS.length) {
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
