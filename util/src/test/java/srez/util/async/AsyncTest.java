package srez.util.async;

import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.currentThread;
import static org.testng.Assert.assertTrue;
import static srez.util.Delayer.sleep;

public class AsyncTest {
    @Test(invocationTimeOut = 1000)
    public void testInstant() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Async.instant().exec(latch::countDown);
        latch.await();
    }

    @Test
    public void testDirect() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Thread> implThread = new AtomicReference<>();
        Async.instant().direct().exec(() -> {
            implThread.set(currentThread());
            latch.countDown();
        });
        latch.await();
        assertTrue(implThread.get().getName().startsWith("ScheduledAsync-planner"));
    }

    @Test
    public void testException() throws Exception {
        AtomicBoolean caughtException = new AtomicBoolean();
        Async.instant().exceptionHandler(t -> caughtException.set(true)).exec(() -> {
            throw new RuntimeException();
        });
        sleep(10);
        assertTrue(caughtException.get());
    }

    @Test
    public void testError() throws Exception {
        AtomicBoolean caughtException = new AtomicBoolean();
        Async.instant().exceptionHandler(t -> caughtException.set(true)).exec(() -> {
            throw new Error();
        });
        sleep(10);
        assertTrue(caughtException.get());
    }

}