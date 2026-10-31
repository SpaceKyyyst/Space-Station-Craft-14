package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CommerceConsoleIntegrationMigrationTest {
    @Test
    void removedLegacyCommerceAdapterIsNotPartOfRuntime() {
        assertThrows(ClassNotFoundException.class, () ->
                Class.forName(
                        "net.ssc14.content.block.commerce.LegacyCommerceConsoleIntegrationAdapter"));
    }
}
