package srez.util.async;

public class RepeatAsync extends AbstractAsync {
    private final int threadCount;

    public RepeatAsync(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public void impl(Runnable runnable) {

    }
}
