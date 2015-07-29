package srez.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toMap;

public class Pair<K, V> extends SimpleEntry<K, V> {
    public Pair(K key, V value) {
        super(key, value);
    }

    public Pair(Entry<K, V> entry) {
        this(entry.getKey(), entry.getValue());
    }

    public static <K, V> Collector<Pair<K, V>, ?, Map<K, V>> toMapCollector() {
        return toMap(Entry::getKey, Entry::getValue);
    }

    public <KN, VN> Pair<KN, VN> map(Function<K, KN> keyFunction, Function<V, VN> valueFunction) {
        return new Pair<>(keyFunction.apply(getKey()), valueFunction.apply(getValue()));
    }

    public <VN> Pair<K, VN> map(Function<V, VN> valueFunction) {
        return new Pair<>(getKey(), valueFunction.apply(getValue()));
    }

}
