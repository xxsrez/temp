package srez.observable;

import com.google.common.base.Stopwatch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        rx.Observable.interval(1, TimeUnit.SECONDS).subscribe(Test::process);
        Stopwatch stopwatch = Stopwatch.createStarted();
        LongStream.range(0, 1000000000).count();
        System.err.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        Thread.currentThread().join();
    }

    private static void process(Long i) {
        CompletableFuture.runAsync(() -> System.err.println(i));
    }
}
