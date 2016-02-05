package srez.util.async;

import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;

public class AsyncTest {
    @Test(invocationTimeOut = 300)
    public void testBasic() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Async.instant().exec(latch::countDown);
        latch.await();
    }
}