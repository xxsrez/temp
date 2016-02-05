package srez.util.async;

import java.time.Duration;

public interface Async {
    static AbstractAsync instant() {
        return delay(Duration.ZERO);
    }

    static AbstractAsync delay(Duration duration) {
        return new ScheduledAsync(duration, null);
    }

    static AbstractAsync interval(Duration duration) {
        return new ScheduledAsync(Duration.ZERO, duration);
    }

    static AbstractAsync repeat() {
        return repeat(1);
    }

    static AbstractAsync repeat(int threadCount) {
        return new RepeatAsync(threadCount);
    }

}
