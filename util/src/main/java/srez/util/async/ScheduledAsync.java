package srez.util.async;

import java.time.Duration;

public class ScheduledAsync extends AbstractAsync {
    private final Duration delay;
    private final Duration period;

    public ScheduledAsync(Duration delay, Duration period) {
        this.delay = delay;
        this.period = period;
    }

    @Override
    public void impl(Runnable runnable) {
        runnable.run();
    }
}
