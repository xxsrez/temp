package srez.util.streams;

import org.junit.Test;

import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static srez.util.streams.MoreCollectors.unique;

public class MoreCollectorsTest {
    @Test
    public void testSingle() throws Exception {
        assertEquals((Object) 1L, Stream.of(1L)
                .collect(unique()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() throws Exception {
        assertEquals(1L, Stream.of()
                .collect(unique()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTwo() throws Exception {
        assertEquals((Object) 1L, Stream.of(1L, 2L)
                .collect(unique()));
    }
}