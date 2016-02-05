package srez.util.async;

import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.currentThread;
import static java.time.Duration.ofMillis;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static srez.util.Delayer.sleep;

public class AsyncTest {
    @Test(invocationTimeOut = 2000)
    public void testInstant() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Async.instant().exec(latch::countDown);
        latch.await();
    }

    @Test(invocationTimeOut = 2000)
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

    @Test(invocationTimeOut = 2000, expectedExceptions = RuntimeException.class)
    public void testException() throws Exception {
        AtomicBoolean caughtException = new AtomicBoolean();
        try {
            Async.instant().exceptionHandler(t -> caughtException.set(true)).exec(() -> {
                throw new RuntimeException();
            }).join();
        } finally {
            assertTrue(caughtException.get());
        }
    }

    @Test(invocationTimeOut = 2000, expectedExceptions = Error.class)
    public void testError() throws Exception {
        AtomicBoolean caughtException = new AtomicBoolean();
        try {
            Async.instant().exceptionHandler(t -> caughtException.set(true)).exec(() -> {
                throw new Error();
            }).join();
        } finally {
            assertTrue(caughtException.get());
        }
    }

    @Test(invocationTimeOut = 2000)
    public void testExceptionHandler() throws Exception {
        AtomicBoolean caughtException = new AtomicBoolean();
        Async.instant().exceptionHandler(t -> caughtException.set(true)).exec(() -> {
            throw new RuntimeException();
        });
        sleep(10);
        assertTrue(caughtException.get());
    }

    @Test(invocationTimeOut = 2000)
    public void testErrorHandler() throws Exception {
        AtomicBoolean caughtException = new AtomicBoolean();
        Async.instant().exceptionHandler(t -> caughtException.set(true)).exec(() -> {
            throw new Error();
        });
        sleep(10);
        assertTrue(caughtException.get());
    }

    @Test(invocationTimeOut = 2000)
    public void testRepeatCancel() throws Exception {
        AtomicInteger counter = new AtomicInteger();
        Cancellation cancellation = Async.repeat().exec(counter::incrementAndGet);
        sleep(100);
        int checkpoint1 = counter.get();
        assertTrue(checkpoint1 > 0);
        cancellation.close();
        sleep(100);
        int checkpoint2 = counter.get();
        sleep(100);
        int checkpoint3 = counter.get();
        assertEquals(checkpoint2, checkpoint3);
    }

    @Test(invocationTimeOut = 2000)
    public void testIntervalCancel() throws Exception {
        AtomicInteger counter = new AtomicInteger();
        Cancellation cancellation = Async.interval(ofMillis(1)).exec(counter::incrementAndGet);
        sleep(100);
        int checkpoint1 = counter.get();
        assertTrue(checkpoint1 > 0);
        cancellation.close();
        sleep(100);
        int checkpoint2 = counter.get();
        sleep(100);
        int checkpoint3 = counter.get();
        assertEquals(checkpoint2, checkpoint3);
    }
}