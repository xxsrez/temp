package srez.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;
import static java.util.concurrent.ForkJoinPool.commonPool;

public class Example {
    private Future<Integer> sumTwoOperationsAsync() {
        Future<Integer> firstTask = getOperationOneAsync();
        Future<Integer> secondTask = getOperationTwoAsync();
        return commonPool().submit(() -> firstTask.get() + secondTask.get());
    }

    private Future<Integer> getOperationOneAsync() {
        return commonPool().submit(() -> {
            sleep(500);
            return 10;
        });
    }

    private Future<Integer> getOperationTwoAsync() {
        return commonPool().submit(() -> {
            sleep(100);
            return 5;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.err.println(new Example().sumTwoOperationsAsync().get());
    }
}
