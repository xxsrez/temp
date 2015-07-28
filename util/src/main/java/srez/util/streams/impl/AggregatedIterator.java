package srez.util.streams.impl;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.BiFunction;

public class AggregatedIterator<T, A> implements Iterator<A> {
    public AggregatedIterator(Spliterator<T> stream, BiFunction<T, A, A> mappingFunction) {

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
