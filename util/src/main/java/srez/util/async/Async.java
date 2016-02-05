package srez.util.async;

import java.time.Duration;
import java.util.concurrent.Executor;

public interface Async {
    static Async instant() {
        return delay(Duration.ZERO);
    }

    static Async delay(Duration duration) {
        return new ScheduledAsync(duration, null);
    }

    static Async interval(Duration duration) {
        return new ScheduledAsync(Duration.ZERO, duration);
    }

    static Async repeat() {
        return repeat(1);
    }

    static Async repeat(int threadCount) {
        return new RepeatAsync(threadCount);
    }

    Async executor(Executor executor);

    void impl(Runnable runnable);
}
