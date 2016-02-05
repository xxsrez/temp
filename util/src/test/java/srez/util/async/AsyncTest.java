package srez.util.async;

import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.testng.Assert.assertTrue;
import static srez.util.Delayer.sleep;

public class AsyncTest {
    @Test(invocationTimeOut = 1000)
    public void testBasic() throws Exception {
        AtomicBoolean flag = new AtomicBoolean();
        Async.instant().exec(() -> flag.set(true));
        sleep(10);
        assertTrue(flag.get());
    }
}