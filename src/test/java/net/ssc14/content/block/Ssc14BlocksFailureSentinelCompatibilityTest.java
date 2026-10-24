package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlockEntities;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.debug.FailureSentinelBlock;
import net.ssc14.content.block.debug.FailureSentinelBlockEntity;
import net.ssc14.content.block.debug.FailureSentinelInteractionService;
import net.ssc14.content.block.debug.Ssc14DebugBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksFailureSentinelCompatibilityTest {
    @Test
    void canonicalRegistriesOwnFailureSentinelIdsAndLegacyFacadesAliasHolders() {
        assertEquals("ssc_14:kostilniblok", Ssc14Blocks.KOSTILNIBLOK.getId().toString());
        assertEquals("ssc_14:kostilniblok",
                Ssc14DebugBlockEntities.KOSTILNIBLOK.getId().toString());
        assertSame(Ssc14Blocks.KOSTILNIBLOK, Ssc14ModBlocks.KOSTILNIBLOK);
        assertSame(Ssc14DebugBlockEntities.KOSTILNIBLOK,
                Ssc14ModBlockEntities.KOSTILNIBLOK);
    }

    @Test
    void legacyFailureSentinelTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.KostilniblokBlock");
        assertTrue(FailureSentinelBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);

        Class<?> legacyEntity = Class.forName(
                "net.mcreator.ssc.block.entity.KostilniblokBlockEntity");
        assertTrue(FailureSentinelBlockEntity.class.isAssignableFrom(legacyEntity));
        legacyEntity.getConstructor(net.minecraft.core.BlockPos.class,
                net.minecraft.world.level.block.state.BlockState.class);
    }

    @Test
    void canonicalFailureSentinelKeepsLegacyPhysicalAndInteractionContract() {
        FailureSentinelBlock block =
                (FailureSentinelBlock) Ssc14Blocks.KOSTILNIBLOK.get();
        assertEquals(-1.0F, block.defaultDestroyTime());
        assertEquals(666.0F, block.getExplosionResistance());
        assertEquals(1, block.defaultBlockState().getLightEmission());
        assertEquals("SSC14 Debug: Критический сбой в работе мода. Reason: Костыли.",
                FailureSentinelInteractionService.disconnectMessage().getString());
    }
}
