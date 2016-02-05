package srez.util.async;

public interface SafeAutoCloseable extends AutoCloseable {
    @Override
    void close();
}
