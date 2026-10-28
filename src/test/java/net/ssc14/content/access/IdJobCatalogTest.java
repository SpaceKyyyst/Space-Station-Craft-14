package net.ssc14.content.access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IdJobCatalogTest {
    @Test
    void preservesButtonNamesAndAccessMasks() {
        assertEquals(46, IdJobCatalog.size());
        assertEquals(32, IdJobCatalog.accessKeys().size());
        assertEquals("Капитан", IdJobCatalog.definition(0).name());
        assertEquals(0xFFFFFFFFL, IdJobCatalog.definition(0).accessMask());
        assertEquals("Пассажир", IdJobCatalog.definition(8).name());
        assertEquals(0x00080000L, IdJobCatalog.definition(8).accessMask());
        assertEquals("Офицер \"Синий щит\"", IdJobCatalog.definition(31).name());
        assertTrue(IdJobCatalog.definition(45).grants(31));
        assertNull(IdJobCatalog.definition(-1));
        assertNull(IdJobCatalog.definition(46));
    }
}
