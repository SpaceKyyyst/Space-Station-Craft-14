package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.storage.ClosetBlockEntity;
import net.ssc14.content.block.storage.SecureClosetBlock;
import net.ssc14.content.block.storage.Ssc14ClosetBlockEntities;
import net.ssc14.content.block.storage.StationClosetBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksSecureClosetCompatibilityTest {
    @Test
    void canonicalRegistriesOwnSecureClosetIdsAndLegacyFacadesAliasHolders() {
        assertEquals("ssc_14:closet_secure", Ssc14Blocks.CLOSET_SECURE.getId().toString());
        assertSame(Ssc14Blocks.CLOSET_SECURE, Ssc14ModBlocks.CLOSET_SECURE);
        assertEquals("ssc_14:closet_secure",
                Ssc14ClosetBlockEntities.CLOSET_SECURE.getId().toString());
        assertSame(Ssc14ClosetBlockEntities.CLOSET_SECURE,
                Ssc14ClosetBlockEntities.CLOSET_SECURE);
    }

    @Test
    void legacySecureClosetTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.ClosetSecureBlock");
        assertTrue(SecureClosetBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);

        Class<?> legacyBlockEntity = Class.forName(
                "net.mcreator.ssc.block.entity.ClosetSecureBlockEntity");
        assertTrue(ClosetBlockEntity.class.isAssignableFrom(legacyBlockEntity));
        legacyBlockEntity.getConstructor(BlockPos.class, BlockState.class);
    }

    @Test
    void canonicalSecureClosetKeepsLockedStateAndStrengthContract() {
        SecureClosetBlock block = (SecureClosetBlock) Ssc14Blocks.CLOSET_SECURE.get();
        assertTrue(block.defaultBlockState().getValue(SecureClosetBlock.BLOCKED));
        assertFalse(block.defaultBlockState().getValue(StationClosetBlock.OPEN));
        assertEquals(40.0F, block.defaultDestroyTime());
        assertEquals(15.0F, block.getExplosionResistance());
    }
}
