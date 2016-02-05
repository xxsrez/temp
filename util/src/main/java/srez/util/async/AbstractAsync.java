package srez.util.async;

import java.util.concurrent.Executor;

public abstract class AbstractAsync implements Async {
    private Executor executor;

    @Override
    public Async executor(Executor executor) {
        this.executor = executor;
        return this;
    }
}
