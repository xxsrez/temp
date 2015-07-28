package srez.util.streams.impl;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class AggregatedIterator<T, A> implements Iterator<A> {
    private A aggregator;

    private final Iterator<T> iterator;
    private final BiFunction<T, A, A> mappingFunction;

    public AggregatedIterator(Iterator<T> iterator, Supplier<A> accumulatorSupplier, BiFunction<T, A, A> mappingFunction) {
        this.iterator = iterator;
        this.mappingFunction = mappingFunction;

        aggregator = accumulatorSupplier.get();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public A next() {
        return aggregator = mappingFunction.apply(iterator.next(), aggregator);
    }
}
