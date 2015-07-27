package srez.partitions;

import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class PartitionEngineLocking implements PartitionEngine {
    @Override
    public <T> Stream<T> exec(Supplier<Stream<Partition>> partitions, Function<Partition, Lock> lockFunction, Function<Partition, T> function) {
        return partitions.get()
                .map(p -> processPartition(p, lockFunction, function));
    }

    private <T> T processPartition(Partition partition, Function<Partition, Lock> lockFunction, Function<Partition, T> function) {
        Lock lock = lockFunction.apply(partition);
        lock.lock();
        try {
            return function.apply(partition);
        } finally {
            lock.unlock();
        }
    }
}
