package net.ssc14.content.item.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.world.item.ItemStack;
import org.junit.jupiter.api.Test;

class Mk58FireStateServiceTest {
    @Test
    void missingStateIsEmpty() {
        ItemStack stack = ItemStack.EMPTY;
        assertEquals(0, Mk58FireStateService.chamber(stack));
        assertFalse(Mk58FireStateService.hasChamberedRound(stack));
    }

    @Test
    void persistedStateUsesLegacyKeysAndClampsConsumption() {
        ItemStack stack = ItemStack.EMPTY;
        assertFalse(Mk58FireStateService.hasChamberedRound(stack));
        assertFalse(Mk58FireStateService.hasMagazineRound(stack));
        assertEquals(0, Mk58FireStateService.magazine(stack));
    }
}
