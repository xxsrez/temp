package srez.util.format;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static srez.util.format.SizeUtil.bytesToString;

public class SizeUtilTest {
    @Test
    public void test25Gb() throws Exception {
        smartAssert((long) ((1L << 30) * 2.5), "2.5Gb");
    }

    @Test
    public void test2Gb() throws Exception {
        smartAssert(1L << 31, "2Gb");
    }

    @Test
    public void test1024Mb() throws Exception {
        smartAssert(1L << 30, "1024Mb");
    }

    @Test
    public void test2Mb() throws Exception {
        smartAssert(1L << 21, "2Mb");
    }

    @Test
    public void test1024Kb() throws Exception {
        smartAssert(1L << 20, "1024Kb");
    }

    @Test
    public void test2Kb() throws Exception {
        smartAssert(1L << 11, "2Kb");
    }

    @Test
    public void test1024b() throws Exception {
        smartAssert(1L << 10, "1024bytes");
    }

    @Test
    public void test2b() throws Exception {
        smartAssert(2, "2bytes");
    }

    @Test
    public void test1b() throws Exception {
        smartAssert(1, "1byte");
    }

    @Test
    public void test0b() throws Exception {
        smartAssert(0, "0bytes");
    }

    @Test
    public void testM1b() throws Exception {
        smartAssert(-1, "-1byte");
    }

    @Test
    public void testM2Gb() throws Exception {
        smartAssert(-1L << 31, "-2Gb");
    }

    @Test
    public void testM1024M() throws Exception {
        smartAssert(-1L << 30, "-1024Mb");
    }

    private static void smartAssert(long bytes, String expected) {
        String expectedModified = expected.endsWith("bytes") || expected.endsWith("byte") ? expected : expected + "(" + bytes + "bytes" + ")";
        assertEquals(bytesToString(bytes), expectedModified);
    }
}