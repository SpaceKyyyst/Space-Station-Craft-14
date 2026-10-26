package net.ssc14.content.item.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FoodDefinitionTest {
    @Test
    void withoutTrashRetainsRequiredGameplayData() {
        FoodDefinition definition = FoodDefinition.withoutTrash(3, "taste");

        assertEquals(3, definition.maxBites());
        assertEquals("taste", definition.tasteMessage());
        assertNull(definition.trash());
    }

    @Test
    void rejectsInvalidDescriptors() {
        assertThrows(IllegalArgumentException.class,
                () -> FoodDefinition.withoutTrash(0, "taste"));
        assertThrows(NullPointerException.class,
                () -> FoodDefinition.withoutTrash(1, null));
    }
}
