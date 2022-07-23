package com.ashuh.nusmoduleplanner.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

    @Test
    public void doubleWithoutTrailingZeros() {
        // real numbers
        assertEquals("-1", StringUtil.doubleWithoutTrailingZeros(-1.0));
        assertEquals("-1.1", StringUtil.doubleWithoutTrailingZeros(-1.1));
        assertEquals("-1.1", StringUtil.doubleWithoutTrailingZeros(-1.10));
        assertEquals("0", StringUtil.doubleWithoutTrailingZeros(0.0));
        assertEquals("0.1", StringUtil.doubleWithoutTrailingZeros(0.1));
        assertEquals("0.1", StringUtil.doubleWithoutTrailingZeros(0.10));
        assertEquals("1", StringUtil.doubleWithoutTrailingZeros(1.0));
        assertEquals("1.1", StringUtil.doubleWithoutTrailingZeros(1.1));
        assertEquals("1.1", StringUtil.doubleWithoutTrailingZeros(1.10));
        assertEquals("10", StringUtil.doubleWithoutTrailingZeros(10.0));
        assertEquals("10.1", StringUtil.doubleWithoutTrailingZeros(10.1));
        assertEquals("10.1", StringUtil.doubleWithoutTrailingZeros(10.10));
        // integers
        assertEquals("-1", StringUtil.doubleWithoutTrailingZeros(-1));
        assertEquals("0", StringUtil.doubleWithoutTrailingZeros(0));
        assertEquals("1", StringUtil.doubleWithoutTrailingZeros(1));
        assertEquals("10", StringUtil.doubleWithoutTrailingZeros(10));
    }
}
