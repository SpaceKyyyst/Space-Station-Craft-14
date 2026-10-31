package net.ssc14.content.item.decal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class DecalToolInteractionMigrationTest {
    @Test
    void removedLegacyDecalAdapterIsNotPartOfRuntime() {
        assertThrows(ClassNotFoundException.class, () ->
                Class.forName("net.ssc14.content.item.decal.LegacyDecalToolInteractionAdapter"));
    }
}
