package srez.util.streams.impl;

import srez.util.streams.impl.UniqueCollector.Holder;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class UniqueCollector<T> implements Collector<T, Holder<T>, T> {
    private static final String STREAM_TOO_LONG = "Non unique stream, too much data within";
    private static final String STREAM_EMPTY = "Non unique stream is empty";

    @Override
    public Supplier<Holder<T>> supplier() {
        return Holder::new;
    }

    @Override
    public BiConsumer<Holder<T>, T> accumulator() {
        return Holder::set;
    }

    @Override
    public BinaryOperator<Holder<T>> combiner() {
        return (r1, r2) -> {
            throw new IllegalArgumentException(STREAM_TOO_LONG);
        };
    }

    @Override
    public Function<Holder<T>, T> finisher() {
        return Holder::get;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.noneOf(Characteristics.class);
    }

    static class Holder<T> {
        private T t;
        private boolean isSet;

        public void set(T t) {
            if (isSet) throw new IllegalArgumentException(STREAM_TOO_LONG);
            isSet = true;
            this.t = t;
        }

        public T get() {
            if (!isSet) throw new IllegalArgumentException(STREAM_EMPTY);
            return t;
        }
    }
}
