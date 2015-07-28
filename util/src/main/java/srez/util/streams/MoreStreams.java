package srez.util.streams;

import srez.util.Pair;
import srez.util.streams.impl.AggregatedIterator;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.Spliterators.spliterator;
import static java.util.stream.StreamSupport.stream;

public class MoreStreams {
    public static <A, B, C> Stream<C> zip(Stream<? extends A> a,
                                          Stream<? extends B> b,
                                          BiFunction<? super A, ? super B, ? extends C> zipper) {
        Objects.requireNonNull(zipper);
        @SuppressWarnings("unchecked")
        Spliterator<A> aSpliterator = (Spliterator<A>) Objects.requireNonNull(a).spliterator();
        @SuppressWarnings("unchecked")
        Spliterator<B> bSpliterator = (Spliterator<B>) Objects.requireNonNull(b).spliterator();

        int characteristics = aSpliterator.characteristics() & bSpliterator.characteristics() &
                ~(Spliterator.DISTINCT | Spliterator.SORTED);

        long zipSize = (characteristics & Spliterator.SIZED) != 0
                ? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown())
                : -1;

        Iterator<A> aIterator = Spliterators.iterator(aSpliterator);
        Iterator<B> bIterator = Spliterators.iterator(bSpliterator);
        Iterator<C> cIterator = new Iterator<C>() {
            @Override
            public boolean hasNext() {
                return aIterator.hasNext() && bIterator.hasNext();
            }

            @Override
            public C next() {
                return zipper.apply(aIterator.next(), bIterator.next());
            }
        };

        Spliterator<C> split = spliterator(cIterator, zipSize, characteristics);
        return stream(split, a.isParallel() || b.isParallel());
    }

    //TODO
    public static <T> Stream<Pair<Integer, T>> withIndex(Stream<T> stream) {
        return zip(Stream.iterate(0, i -> i + 1), stream, Pair::new);
    }

    //TODO
    public static <T> Stream<T> takeWhile(Stream<T> stream) {
        return null;

    }

    public static <T, A> Stream<A> mapAggregated(Stream<T> stream, BiFunction<T, A, A> mappingFunction) {
        Spliterator<T> spliterator = stream.spliterator();
        Iterator<T> iterator = Spliterators.iterator(spliterator);
        AggregatedIterator<T, A> iteratorNew = new AggregatedIterator<>(iterator, mappingFunction);
        int characteristics = spliterator.characteristics()
                & ~(Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.IMMUTABLE)
                & Spliterator.CONCURRENT;
        Spliterator<A> spliteratorNew = spliterator(iteratorNew, spliterator.estimateSize(), characteristics);
        return stream(spliteratorNew, false);
    }
}
