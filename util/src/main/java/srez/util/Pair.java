package srez.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toMap;

public class Pair<K, V> extends SimpleEntry<K, V> {
    public Pair(K key, V value) {
        super(key, value);
    }

    public static <K, V> Collector<Pair<K, V>, ?, Map<K, V>> toMapCollector() {
        return toMap(Entry::getKey, Entry::getValue);
    }

}
