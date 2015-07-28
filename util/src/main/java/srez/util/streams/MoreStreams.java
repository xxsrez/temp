package srez.util.streams;

import srez.util.Pair;
import srez.util.streams.impl.AggregatedIterator;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Spliterators.iterator;
import static java.util.Spliterators.spliterator;
import static java.util.stream.StreamSupport.stream;

public class MoreStreams {
    public static <T> MoreStream<T> more(Stream<T> stream) {
        return new MoreStream<>(stream);
    }

    public static <T1, T2> Stream<Pair<T1, T2>> zip(Stream<? extends T1> stream1, Stream<? extends T2> stream2) {
        return zip(stream1, stream2, Pair::new);
    }

    public static <T1, T2, Z> Stream<Z> zip(Stream<? extends T1> stream1,
                                            Stream<? extends T2> stream2,
                                            BiFunction<? super T1, ? super T2, ? extends Z> zipper) {
        Objects.requireNonNull(zipper);
        @SuppressWarnings("unchecked")
        Spliterator<T1> spliterator1 = (Spliterator<T1>) Objects.requireNonNull(stream1).spliterator();
        @SuppressWarnings("unchecked")
        Spliterator<T2> spliterator2 = (Spliterator<T2>) Objects.requireNonNull(stream2).spliterator();

        int characteristics = spliterator1.characteristics() & spliterator2.characteristics() &
                ~(Spliterator.DISTINCT | Spliterator.SORTED);

        long zipSize = (characteristics & Spliterator.SIZED) != 0
                ? Math.min(spliterator1.getExactSizeIfKnown(), spliterator2.getExactSizeIfKnown())
                : -1;

        Iterator<T1> iterator1 = iterator(spliterator1);
        Iterator<T2> iterator2 = iterator(spliterator2);
        Iterator<Z> iterator = new Iterator<Z>() {
            @Override
            public boolean hasNext() {
                return iterator1.hasNext() && iterator2.hasNext();
            }

            @Override
            public Z next() {
                return zipper.apply(iterator1.next(), iterator2.next());
            }
        };

        Spliterator<Z> split = spliterator(iterator, zipSize, characteristics);
        return stream(split, stream1.isParallel() || stream2.isParallel());
    }

    //TODO
    public static <T> Stream<Pair<Integer, T>> withIndex(Stream<T> stream) {
        return zip(Stream.iterate(0, i -> i + 1), stream, Pair::new);
    }

    //TODO
    public static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> predicate) {
        Spliterator<T> spliterator = stream.spliterator();
        AbstractSpliterator<T> spliteratorNew = new AbstractSpliterator<T>(spliterator.estimateSize(), 0) {
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
        return stream(spliteratorNew, stream.isParallel());
    }

    public static <T, A> Stream<A> mapAggregated(Stream<T> stream, Supplier<A> accumulatorSupplier, BiFunction<T, A, A> mappingFunction) {
        Spliterator<T> spliterator = stream.spliterator();
        Iterator<T> iterator = iterator(spliterator);
        AggregatedIterator<T, A> iteratorNew = new AggregatedIterator<>(iterator, mappingFunction);
        int characteristics = spliterator.characteristics()
                & ~(Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.IMMUTABLE)
                & Spliterator.CONCURRENT;
        Spliterator<A> spliteratorNew = spliterator(iteratorNew, spliterator.estimateSize(), characteristics);
        return stream(spliteratorNew, false);
    }
}
