package srez.util.format;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static srez.util.format.SizeUtil.bytesToString;

public class SizeUtilTest {
    @Test
    public void testT1() throws Exception {
        smartAssert((long) ((2 << 30) * 2.5), "2.5Gb");
    }

    @Test
    public void testT2() throws Exception {
        smartAssert(2 << 31, "2Gb");
    }

    @Test
    public void testT3() throws Exception {
        smartAssert(2 << 30, "1024Mb");
    }

    @Test
    public void testT4() throws Exception {
        smartAssert(2 << 21, "2Mb");
    }

    @Test
    public void testT5() throws Exception {
        smartAssert(2 << 20, "1024Kb");
    }

    @Test
    public void testT6() throws Exception {
        smartAssert(2 << 11, "2Kb");
    }

    @Test
    public void testT7() throws Exception {
        smartAssert(2 << 10, "1024bytes");
    }

    @Test
    public void testT8() throws Exception {
        smartAssert(2, "2bytes");
    }

    @Test
    public void testT9() throws Exception {
        smartAssert(1, "1byte");
    }

    @Test
    public void testT10() throws Exception {
        smartAssert(0, "0bytes");
    }

    @Test
    public void testT11() throws Exception {
        smartAssert(-1, "-1bytes");
    }

    @Test
    public void testT12() throws Exception {
        smartAssert(-2 << 31, "-2Gb");
    }

    @Test
    public void testT13() throws Exception {
        smartAssert(-2 << 30, "-1024Mb");
    }

    private void smartAssert(long bytes, String expected) {
        String expectedModified = expected.endsWith("bytes") ? expected : expected + " " + bytes + "bytes";
        assertEquals(bytesToString(bytes), expectedModified);
    }
}