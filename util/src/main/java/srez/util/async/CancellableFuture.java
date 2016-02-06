package srez.util.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class CancellableFuture extends Cancellation {
    private final CompletableFuture<Void> future;

    public CancellableFuture(Runnable cancelAction, CompletableFuture<Void> future) {
        super(cancelAction);
        this.future = future;
    }

    public void join() throws InterruptedException {
        try {
            future.join();
        } catch (CompletionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw e;
            }
        }
    }

    public CompletableFuture<Void> getFuture() {
        return future;
    }
}
