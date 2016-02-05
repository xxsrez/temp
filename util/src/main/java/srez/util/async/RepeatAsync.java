package srez.util.async;

import static java.util.stream.IntStream.range;
import static srez.util.async.NamedDaemonFactory.newFixedThreadPool;

public class RepeatAsync extends AbstractAsync {
    private final int threadCount;

    public RepeatAsync(int threadCount) {
        this.threadCount = threadCount;
        executor(newFixedThreadPool(threadCount, this, "worker"));
    }

    @Override
    public AbstractAsync direct() {
        throw new IllegalStateException();
    }

    @Override
    public Cancellation doExec(Runnable runnable) {
        range(0, threadCount).forEach(i -> runnable.run());
        return new Cancellation(() -> {
        });
    }
}
