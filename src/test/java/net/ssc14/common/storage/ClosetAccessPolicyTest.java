package net.ssc14.common.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;

class ClosetAccessPolicyTest {
    @Test
    void deniesMissingRequiredRole() {
        CompoundTag required = new CompoundTag();
        required.putBoolean("Security", true);
        assertFalse(ClosetAccessPolicy.hasAccess(new CompoundTag(), required));
    }

    @Test
    void allowsMatchingRoleAndUnrestrictedCloset() {
        CompoundTag player = new CompoundTag();
        player.putBoolean("Security", true);
        CompoundTag required = new CompoundTag();
        required.putBoolean("Security", true);
        assertTrue(ClosetAccessPolicy.hasAccess(player, required));
        assertTrue(ClosetAccessPolicy.hasAccess(new CompoundTag(), new CompoundTag()));
    }
}
