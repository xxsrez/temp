package srez.util.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;

@SuppressWarnings("unused")
public class NamedDaemonFactory implements ThreadFactory {
    private final String threadNamePrefix;
    private final LongSupplier idSupplier;

    private NamedDaemonFactory(String threadNamePrefix, LongSupplier idSupplier) {
        this.threadNamePrefix = threadNamePrefix;
        this.idSupplier = idSupplier;
    }

    public static NamedDaemonFactory create(Object owner, String threadName) {
        AtomicLong adder = new AtomicLong();
        String prefix = owner instanceof Class ? ((Class<?>) owner).getSimpleName() : String.valueOf(owner);
        return new NamedDaemonFactory(prefix + "-" + threadName + "-", adder::getAndIncrement);
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor(Object owner, String threadName) {
        return Executors.newSingleThreadScheduledExecutor(create(owner, threadName));
    }

    public static ExecutorService newSingleThreadExecutor(Object owner, String threadName) {
        return Executors.newSingleThreadExecutor(create(owner, threadName));
    }

    public static ExecutorService newFixedThreadPool(int nThreads, Object owner, String threadName) {
        return Executors.newFixedThreadPool(nThreads, create(owner, threadName));
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName(threadNamePrefix + idSupplier.getAsLong());
        return thread;
    }
}
