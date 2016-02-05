package srez.util.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static java.util.concurrent.CompletableFuture.runAsync;

public abstract class AbstractAsync {
    private static final Logger log = LoggerFactory.getLogger(AbstractAsync.class);

    private Executor executor = CompletableFuture::runAsync;

    public AbstractAsync executor(Executor executor) {
        this.executor = r -> runAsync(r, executor);
        return this;
    }

    public AbstractAsync direct() {
        executor = Runnable::run;
        return this;
    }

    protected abstract void doExec(Runnable runnable);

    public void exec(Runnable runnable) {
        doExec(() ->
                executor.execute(() -> {
                    try {
                        runnable.run();
                    } catch (Error e) {
                        log.error("", e);
                        throw e;
                    } catch (RuntimeException e) {
                        log.error("", e);
                    }
                }));
    }
}
