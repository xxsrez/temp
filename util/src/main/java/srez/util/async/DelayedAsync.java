package srez.util.async;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DelayedAsync extends ScheduledAsync {
    public DelayedAsync() {
        super(null);
    }

    public DelayedAsync delay(Duration delay) {
        super.delay(delay);
        return this;
    }

    @Override
    public DelayedAsync executor(Executor executor) {
        super.executor(executor);
        return this;
    }

    public DelayedAsync direct() {
        super.direct();
        return this;
    }

    @Override
    public DelayedAsync exceptionHandler(Consumer<Throwable> exceptionHandler) {
        super.exceptionHandler(exceptionHandler);
        return this;
    }

    @Override
    protected CancellableFuture doExec(Runnable runnable) {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        Runnable wrapped = () -> doRun(
                () -> {
                    try {
                        runnable.run();
                        completableFuture.complete(null);
                    } catch (Error | RuntimeException e) {
                        completableFuture.completeExceptionally(e);
                        throw e;
                    }
                });

        ScheduledFuture<?> future = SCHEDULED_EXECUTOR_SERVICE.schedule(wrapped, getDelay().getNano(), TimeUnit.NANOSECONDS);
        return new CancellableFuture(() -> future.cancel(true), completableFuture);
    }

    @Override
    public CancellableFuture exec(Runnable runnable) {
        return (CancellableFuture) super.exec(runnable);
    }
}
