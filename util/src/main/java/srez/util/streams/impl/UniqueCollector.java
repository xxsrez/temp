package srez.util.streams.impl;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class UniqueCollector<T> implements Collector<T, AtomicReference<T>, T> {
    @Override
    public Supplier<AtomicReference<T>> supplier() {
        return AtomicReference::new;
    }

    @Override
    public BiConsumer<AtomicReference<T>, T> accumulator() {
        return (r, t) -> {
            if (!r.compareAndSet(null, t)) throw new IllegalStateException();
        };
    }

    @Override
    public BinaryOperator<AtomicReference<T>> combiner() {
        return (r1, r2) -> {
            throw new IllegalStateException();
        };
    }

    @Override
    public Function<AtomicReference<T>, T> finisher() {
        return AtomicReference::get;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.noneOf(Characteristics.class);
    }
}
