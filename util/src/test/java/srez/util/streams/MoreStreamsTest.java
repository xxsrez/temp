package srez.util.streams;

import junit.framework.TestCase;

import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.iterate;
import static srez.util.streams.MoreStreams.*;

public class MoreStreamsTest extends TestCase {
    public void testWithIndex() throws Exception {
        withIndex(range(0, 1000).boxed())
                .forEach(p -> assertEquals(p.getKey(), p.getValue()));
    }

    public void testZip() throws Exception {
        zip(range(0, 1000).boxed(), range(0, 1000).boxed(), (i1, i2) -> {
            assertEquals(i1, i2);
            return 0;
        });
    }

    public void testZipDiffSize() throws Exception {
        assertEquals(100, zip(range(0, 1000).boxed(), range(0, 100).boxed(), (i1, i2) -> {
            assertEquals(i1, i2);
            return i1;
        }).count());
    }

    public void testAggregatedTakeWhile() throws Exception {
        assertEquals(14, more(more(iterate(0, i -> i + 1))
                .mapAggregated(() -> 0, (Integer i, Integer a) -> i + a))
                .takeWhile(i -> i < 100)
                .count());
    }
}