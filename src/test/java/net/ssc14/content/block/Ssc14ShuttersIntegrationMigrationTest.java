package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class Ssc14ShuttersIntegrationMigrationTest {
    @Test
    void removedLegacyShuttersAdapterIsNotPartOfRuntime() {
        assertThrows(ClassNotFoundException.class, () ->
                Class.forName(
                        "net.ssc14.content.block.airlock.LegacyShuttersIntegrationAdapter"));
    }
}
