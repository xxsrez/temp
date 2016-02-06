package srez.util.async;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static java.util.stream.IntStream.range;
import static srez.util.async.NamedDaemonFactory.newFixedThreadPool;

public class RepeatAsync extends AbstractAsync {
    private final int threadCount;

    public RepeatAsync(int threadCount) {
        this.threadCount = threadCount;
        executor(newFixedThreadPool(threadCount, this, "worker"));
    }

    @Override
    public RepeatAsync executor(Executor executor) {
        super.executor(executor);
        return this;
    }

    @Override
    public RepeatAsync exceptionHandler(Consumer<Throwable> exceptionHandler) {
        super.exceptionHandler(exceptionHandler);
        return this;
    }

    @Override
    public Cancellation doExec(Runnable runnable) {
        AtomicBoolean active = new AtomicBoolean(true);
        Runnable safeRunnable = safeRunnable(runnable);

        range(0, threadCount).forEach(i ->
                doRun(() -> {
                    while (active.get()) {
                        safeRunnable.run();
                    }
                })
        );
        return new Cancellation(() -> active.set(false));
    }
}
