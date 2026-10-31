package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AirlockDoorIntegrationMigrationTest {
    @Test
    void removedLegacyDoorAdapterIsNotPartOfRuntime() {
        assertThrows(ClassNotFoundException.class, () ->
                Class.forName(
                        "net.ssc14.content.block.airlock.LegacyAirlockDoorIntegrationAdapter"));
    }
}
