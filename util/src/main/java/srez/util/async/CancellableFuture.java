package srez.util.async;

import java.util.concurrent.CountDownLatch;

public class CancellableFuture extends Cancellation {
    private final CountDownLatch latch;

    public CancellableFuture(Runnable cancelAction, CountDownLatch latch) {
        super(cancelAction);
        this.latch = latch;
    }

    public void join() throws InterruptedException {
        latch.await();
    }
}
