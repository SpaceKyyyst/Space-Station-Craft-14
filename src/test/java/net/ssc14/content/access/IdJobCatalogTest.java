package net.ssc14.content.access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IdJobCatalogTest {
    @Test
    void catalogHasStableButtonIds() {
        assertEquals(46, IdJobCatalog.size());
        for (int buttonId = 0; buttonId < IdJobCatalog.size(); buttonId++) {
            IdJobCatalog.Definition definition = IdJobCatalog.definition(buttonId);
            assertNotNull(definition);
            assertEquals(buttonId, definition.buttonId());
            assertTrue(!definition.name().isBlank());
        }
    }

    @Test
    void accessMasksMatchAllDeclaredKeys() {
        assertEquals(32, IdJobCatalog.accessKeys().size());
        for (int buttonId = 0; buttonId < IdJobCatalog.size(); buttonId++) {
            IdJobCatalog.Definition definition = IdJobCatalog.definition(buttonId);
            for (int accessIndex = 0; accessIndex < IdJobCatalog.accessKeys().size(); accessIndex++) {
                definition.grants(accessIndex);
            }
        }
    }
}
