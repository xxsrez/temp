package srez.util.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.currentThread;
import static org.testng.Assert.assertTrue;

public class AsyncTest {
    private static final Logger log = LoggerFactory.getLogger(AsyncTest.class);

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
        log.info("testException()");
        Async.instant().exec(() -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void testError() throws Exception {
        Async.instant().exec(() -> {
            throw new Error();
        });
    }

}