package net.ssc14.client.menu.access;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.world.item.component.CustomData;
import org.junit.jupiter.api.Test;

class IdCodeAccessDisplayStateTest {
    @Test
    void capturesDecorationsAndMissingAccessFromOnePairOfCards() {
        IdCodeAccessDisplayState state = new IdCodeAccessDisplayState(true, true, CustomData.EMPTY);

        assertTrue(state.showFirstCardDecoration());
        assertFalse(state.showSingleCardDecoration());
        assertTrue(state.showTwoCardDecoration());
        assertTrue(state.locked("Security"));

        IdCodeAccessDisplayState single = new IdCodeAccessDisplayState(true, false, CustomData.EMPTY);
        assertTrue(single.showSingleCardDecoration());
        assertFalse(single.locked("Security"));
    }
}
