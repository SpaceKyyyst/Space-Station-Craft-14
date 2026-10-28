package net.ssc14.content.menu.decal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DecalColorInputTest {
    @Test
    void preservesGeneratedFallbackToZero() {
        assertEquals(0, DecalColorInput.parse(""));
        assertEquals(0, DecalColorInput.parse("not-a-number"));
        assertEquals(12.5, DecalColorInput.parse(" 12.5 "));
    }

    @Test
    void acceptsOnlyFiniteRgbRange() {
        assertTrue(DecalColorInput.isValid("0", "127.5", "255"));
        assertTrue(DecalColorInput.isValid("invalid", "", "0"));
        assertFalse(DecalColorInput.isValid("-1", "0", "0"));
        assertFalse(DecalColorInput.isValid("0", "256", "0"));
        assertFalse(DecalColorInput.isValid("NaN", "0", "0"));
    }
}
