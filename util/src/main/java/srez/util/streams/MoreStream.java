package srez.util.streams;

import srez.util.Pair;

import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MoreStream<T> {
    private final Stream<T> stream;

    public MoreStream(Stream<T> stream) {
        this.stream = stream;
    }

    public <T2> Stream<Pair<T, T2>> zip(Stream<T2> stream) {
        return MoreStreams.zip(this.stream, stream);
    }

    public <T2, Z> Stream<Z> zip(Stream<T2> stream, BiFunction<? super T, ? super T2, ? extends Z> zipper) {
        return MoreStreams.zip(this.stream, stream, zipper);
    }

    public Stream<Pair<Integer, T>> withIndex() {
        return MoreStreams.withIndex(stream);
    }

    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        return MoreStreams.takeWhile(stream, predicate);
    }

    public <A> Stream<A> mapAggregated(BiFunction<T, A, A> mappingFunction) {
        return MoreStreams.mapAggregated(stream, mappingFunction);
    }
}
