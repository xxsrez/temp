package srez.util.async;

import java.time.Duration;

public interface Async {
    static DelayedAsync instant() {
        return delay(Duration.ZERO);
    }

    static DelayedAsync delay(Duration delay) {
        return new DelayedAsync().delay(delay);
    }

    static ScheduledAsync interval(Duration duration) {
        return new ScheduledAsync(duration);
    }

    static RepeatAsync repeat() {
        return repeat(1);
    }

    static RepeatAsync repeat(int threadCount) {
        return new RepeatAsync(threadCount);
    }

}
