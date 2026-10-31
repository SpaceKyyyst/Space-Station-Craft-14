package net.ssc14.content.item.reagent;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ReagentContainerRuntimeMigrationTest {
    @Test
    void removedLegacyReagentRuntimeIsNotPartOfRuntime() {
        assertThrows(ClassNotFoundException.class, () ->
                Class.forName("net.ssc14.content.item.reagent.LegacyReagentContainerRuntime"));
    }
}
