package srez.observable;

import java.util.concurrent.CompletableFuture;

public class Test {
    private static void process(Long i) {
        CompletableFuture.runAsync(() -> System.err.println(i));
    }
}
