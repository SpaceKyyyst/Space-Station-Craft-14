package net.ssc14.content.item.weapon;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class FirearmInteractionMigrationTest {
    @Test
    void removedLegacyFirearmAdapterIsNotPartOfRuntime() {
        assertThrows(ClassNotFoundException.class, () ->
                Class.forName("net.ssc14.content.item.weapon.LegacyFirearmInteractionAdapter"));
    }
}
