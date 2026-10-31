package net.ssc14.content.item.tool;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RsuInteractionServiceMigrationTest {
    @Test
    void removedLegacyRsuAdapterIsNotPartOfRuntime() {
        assertThrows(ClassNotFoundException.class, () ->
                Class.forName("net.ssc14.content.item.tool.LegacyRsuInteractionAdapter"));
    }
}
