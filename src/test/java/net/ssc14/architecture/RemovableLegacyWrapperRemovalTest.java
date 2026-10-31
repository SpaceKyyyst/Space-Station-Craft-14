package net.ssc14.architecture;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RemovableLegacyWrapperRemovalTest {
    private static final String[] REMOVED_CLASSES = {
            "net.mcreator.ssc.entity.TestSunEntity",
            "net.mcreator.ssc.item.AccessConfigItem",
            "net.mcreator.ssc.item.Backpack2Item"
    };

    @Test
    void explicitlyRemovableLegacyWrappersAreGone() {
        for (String removedClass : REMOVED_CLASSES) {
            assertThrows(ClassNotFoundException.class, () -> Class.forName(removedClass), removedClass);
        }
    }
}
