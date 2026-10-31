package net.ssc14.content.item.construction;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ConstructionItemInteractionMigrationTest {
    @Test
    void removedLegacyConstructionAdapterIsNotPartOfRuntime() {
        assertThrows(ClassNotFoundException.class, () ->
                Class.forName(
                        "net.ssc14.content.item.construction.LegacyConstructionItemInteractionAdapter"));
    }
}
