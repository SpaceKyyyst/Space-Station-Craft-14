package net.ssc14.content.item.container;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PortableMenuInteractionMigrationTest {
    @Test
    void removedLegacyPortableMenuAdapterIsNotPartOfRuntime() {
        assertThrows(ClassNotFoundException.class, () ->
                Class.forName(
                        "net.ssc14.content.item.container.LegacyPortableMenuInteractionAdapter"));
    }
}
