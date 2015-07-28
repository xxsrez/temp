package srez.util.streams.impl;

import java.util.Iterator;
import java.util.function.BiFunction;

public class AggregatedIterator<T, A> implements Iterator<A> {
    private final Iterator<T> spliterator;
    private final BiFunction<T, A, A> mappingFunction;

    public AggregatedIterator(Iterator<T> spliterator, BiFunction<T, A, A> mappingFunction) {
        this.spliterator = spliterator;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public A next() {
        return null;
    }
}
