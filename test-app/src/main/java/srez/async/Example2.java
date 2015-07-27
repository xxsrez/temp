package srez.async;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static srez.util.Delayer.sleep;

public class Example2 {
    private CompletableFuture<Integer> sumTwoOperationsAsync() {
        CompletableFuture<Integer> firstTask = getOperationOneAsync();
        CompletableFuture<Integer> secondTask = getOperationTwoAsync();
        return supplyAsync(() -> firstTask.join() + secondTask.join());
    }

    private CompletableFuture<Integer> getOperationOneAsync() {
        return supplyAsync(() -> {
            sleep(500);
            return 10;
        });
    }

    private CompletableFuture<Integer> getOperationTwoAsync() {
        return supplyAsync(() -> {
            sleep(100);
            return 5;
        });
    }

    public static void main(String[] args) {
        System.err.println(new Example2().sumTwoOperationsAsync().join());
    }
}
