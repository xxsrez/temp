package srez.partitions;

import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface PartitionEngine {
    <T> Stream<T> exec(Supplier<Stream<Partition>> partitions, Function<Partition, Lock> lockFunction, Function<Partition, T> function);
}
