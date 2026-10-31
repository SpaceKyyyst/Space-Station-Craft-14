package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class IdConsoleIntegrationMigrationTest {
    @Test
    void removedLegacyIdConsoleAdapterIsNotPartOfRuntime() {
        assertThrows(ClassNotFoundException.class, () ->
                Class.forName(
                        "net.ssc14.content.block.access.LegacyIdConsoleIntegrationAdapter"));
    }
}
