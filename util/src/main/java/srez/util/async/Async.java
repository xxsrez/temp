package srez.util.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static java.lang.Runtime.getRuntime;
import static java.time.Duration.ofSeconds;
import static srez.util.format.SizeUtil.bytesToString;

public class Async {
    private static final Logger log = LoggerFactory.getLogger(Async.class);

    public static DelayedAsync instant() {
        return delay(Duration.ZERO);
    }

    public static DelayedAsync delay(Duration delay) {
        return new DelayedAsync().delay(delay);
    }

    public static ScheduledAsync interval(Duration duration) {
        return new ScheduledAsync(duration);
    }

    public static RepeatAsync repeat() {
        return repeat(1);
    }

    public static RepeatAsync repeat(int threadCount) {
        return new RepeatAsync(threadCount);
    }

    public static void memoryWatcher() {
        interval(ofSeconds(30)).exec(() -> {
            long maxMemory = getRuntime().maxMemory();
            long freeMemory = getRuntime().freeMemory();
            long usedMemory = maxMemory - freeMemory;
            log.info("Memory details {}/{}", bytesToString(usedMemory), bytesToString(maxMemory));
        });
    }
}
