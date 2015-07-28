package srez.util.streams;

import srez.util.Pair;
import srez.util.streams.impl.AggregatedIterator;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Spliterators.spliterator;
import static java.util.stream.StreamSupport.stream;

public class MoreStreams {
    public static <T1, T2> Stream<Pair<T1, T2>> zip(Stream<? extends T1> stream1, Stream<? extends T2> stream2) {
        return zip(stream1, stream2, Pair::new);
    }

    public static <T1, T2, Z> Stream<Z> zip(Stream<? extends T1> a,
                                            Stream<? extends T2> b,
                                            BiFunction<? super T1, ? super T2, ? extends Z> zipper) {
        Objects.requireNonNull(zipper);
        @SuppressWarnings("unchecked")
        Spliterator<T1> aSpliterator = (Spliterator<T1>) Objects.requireNonNull(a).spliterator();
        @SuppressWarnings("unchecked")
        Spliterator<T2> bSpliterator = (Spliterator<T2>) Objects.requireNonNull(b).spliterator();

        int characteristics = aSpliterator.characteristics() & bSpliterator.characteristics() &
                ~(Spliterator.DISTINCT | Spliterator.SORTED);

        long zipSize = (characteristics & Spliterator.SIZED) != 0
                ? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown())
                : -1;

        Iterator<T1> aIterator = Spliterators.iterator(aSpliterator);
        Iterator<T2> bIterator = Spliterators.iterator(bSpliterator);
        Iterator<Z> cIterator = new Iterator<Z>() {
            @Override
            public boolean hasNext() {
                return aIterator.hasNext() && bIterator.hasNext();
            }

            @Override
            public Z next() {
                return zipper.apply(aIterator.next(), bIterator.next());
            }
        };

        Spliterator<Z> split = spliterator(cIterator, zipSize, characteristics);
        return stream(split, a.isParallel() || b.isParallel());
    }

    //TODO
    public static <T> Stream<Pair<Integer, T>> withIndex(Stream<T> stream) {
        return zip(Stream.iterate(0, i -> i + 1), stream, Pair::new);
    }

    //TODO
    public static <T> Spliterator<T> takeWhile(Spliterator<T> spliterator, Predicate<? super T> predicate) {
        return new AbstractSpliterator<T>(spliterator.estimateSize(), 0) {
            boolean stillGoing = true;

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                if (stillGoing) {
                    boolean hadNext = spliterator.tryAdvance(elem -> {
                        if (predicate.test(elem)) {
                            consumer.accept(elem);
                        } else {
                            stillGoing = false;
                        }
                    });
                    return hadNext && stillGoing;
                }
                return false;
            }
        };
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
