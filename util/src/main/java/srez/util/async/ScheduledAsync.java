package srez.util.async;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static srez.util.async.NamedDaemonFactory.newSingleThreadScheduledExecutor;

public class ScheduledAsync extends AbstractAsync {
    private static final ScheduledExecutorService executor = newSingleThreadScheduledExecutor(ScheduledAsync.class, "planner");

    private final Duration delay;
    private final Duration period;

    public ScheduledAsync(Duration delay, Duration period) {
        this.delay = delay;
        this.period = period;
    }

    @Override
    protected Cancellation doExec(Runnable runnable) {
        if (period == null) {
            executor.schedule(runnable, delay.getNano(), TimeUnit.NANOSECONDS);
        } else {
            executor.scheduleAtFixedRate(runnable, delay.getNano(), period.getNano(), TimeUnit.NANOSECONDS);
        }
        return new Cancellation(() -> {
        });
    }
}
