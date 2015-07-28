package srez.util.streams;

import srez.util.streams.impl.UniqueCollector;

import java.util.stream.Collector;

public class MoreCollectors {
    public <T> Collector<T, ?, T> unique() {
        return new UniqueCollector<>();
    }
}
