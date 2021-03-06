package srez.util.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

import static java.util.concurrent.CompletableFuture.runAsync;

public abstract class AbstractAsync {
    private static final Logger log = LoggerFactory.getLogger(AbstractAsync.class);

    private Executor executor = CompletableFuture::runAsync;
    private Consumer<Throwable> exceptionHandler = t -> {
    };

    public AbstractAsync executor(Executor executor) {
        this.executor = r -> runAsync(r, executor);
        return this;
    }

    public AbstractAsync exceptionHandler(Consumer<Throwable> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    protected abstract Cancellation doExec(Runnable runnable);

    public Cancellation exec(Runnable runnable) {
        return doExec(runnable);
    }

    protected Runnable safeRunnable(Runnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Error e) {
                exceptionHandler.accept(e);
                log.error("", e);
                throw e;
            } catch (RuntimeException e) {
                exceptionHandler.accept(e);
                log.error("", e);
            }
        };
    }

    protected void doRun(Runnable runnable) {
        executor.execute(safeRunnable(runnable));
    }

    protected void setExecutor(Executor executor) {
        this.executor = executor;
    }
}
