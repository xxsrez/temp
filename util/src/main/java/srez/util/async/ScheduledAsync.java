package srez.util.async;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static srez.util.async.NamedDaemonFactory.newSingleThreadScheduledExecutor;

public class ScheduledAsync extends AbstractAsync {
    protected static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = newSingleThreadScheduledExecutor(ScheduledAsync.class, "planner");

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
    public ScheduledAsync exceptionHandler(Consumer<Throwable> exceptionHandler) {
        super.exceptionHandler(exceptionHandler);
        return this;
    }

    @Override
    protected Cancellation doExec(Executor executor, Runnable runnable) {
        ScheduledFuture<?> future = SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(
                () -> executor.execute(runnable),
                delay.getNano(), period.getNano(), TimeUnit.NANOSECONDS
        );
        return new Cancellation(() -> future.cancel(true));
    }

    protected Duration getDelay() {
        return delay;
    }
}
