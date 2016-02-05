package srez.util.async;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static srez.util.async.NamedDaemonFactory.newSingleThreadScheduledExecutor;

public class ScheduledAsync extends AbstractAsync {
    private static final ScheduledExecutorService executor = newSingleThreadScheduledExecutor(ScheduledAsync.class, "planner");

    private Duration delay = Duration.ZERO;
    private final Duration period;

    public ScheduledAsync(Duration period) {
        this.period = period;
    }

    public ScheduledAsync delay(Duration delay) {
        this.delay = this.delay.plus(delay);
        return this;
    }

    @Override
    public ScheduledAsync executor(Executor executor) {
        super.executor(executor);
        return this;
    }

    public ScheduledAsync direct() {
        setExecutor(Runnable::run);
        return this;
    }

    @Override
    protected Cancellation doExec(Runnable runnable) {
        ScheduledFuture<?> future;
        if (period == null) {
            future = executor.schedule(runnable, delay.getNano(), TimeUnit.NANOSECONDS);
        } else {
            future = executor.scheduleAtFixedRate(runnable, delay.getNano(), period.getNano(), TimeUnit.NANOSECONDS);
        }
        return new Cancellation(() -> future.cancel(true));
    }
}
