package srez.async;

import srez.util.async.Async;
import srez.util.async.Cancellation;

import static srez.util.Delayer.sleep;

public class Sample {
    public static void main(String[] args) throws InterruptedException {
        Cancellation cancellation = Async.memoryWatcher();
        sleep(120_000);
        cancellation.close();
        Thread.currentThread().join();
    }
}
