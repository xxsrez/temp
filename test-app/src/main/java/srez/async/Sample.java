package srez.async;

import srez.util.async.Async;

public class Sample {
    public static void main(String[] args) throws InterruptedException {
        Async.memoryWatcher();
        Thread.currentThread().join();
    }
}
