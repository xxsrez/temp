package srez.util.async;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.concurrent.CompletableFuture.runAsync;

public class Cancelation implements SafeAutoCloseable {
    private final Collection<Runnable> cancelActions = new CopyOnWriteArrayList<>();

    public Cancelation(Runnable cancelAction) {
        thenRun(cancelAction);
    }

    public void thenRun(Runnable cancelAction) {
        cancelActions.add(cancelAction);
    }

    public void thenRunAsync(Runnable cancelAction) {
        thenRun(() -> runAsync(cancelAction));
    }

    @Override
    public void close() {
        cancelActions.forEach(Runnable::run);
    }
}
