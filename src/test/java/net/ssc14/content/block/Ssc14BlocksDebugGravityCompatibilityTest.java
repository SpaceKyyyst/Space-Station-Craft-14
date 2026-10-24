package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;
import net.mcreator.ssc.init.Ssc14ModBlockEntities;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.debug.DebugGravityGeneratorBlock;
import net.ssc14.content.block.debug.DebugGravityGeneratorBlockEntity;
import net.ssc14.content.block.debug.DebugGravityStateService;
import net.ssc14.content.block.debug.Ssc14DebugBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksDebugGravityCompatibilityTest {
    @Test
    void canonicalRegistriesOwnDebugGravityIdsAndLegacyFacadesAliasHolders() {
        assertEquals("ssc_14:debug_grav_gen", Ssc14Blocks.DEBUG_GRAV_GEN.getId().toString());
        assertEquals("ssc_14:debug_grav_gen",
                Ssc14DebugBlockEntities.DEBUG_GRAV_GEN.getId().toString());
        assertSame(Ssc14Blocks.DEBUG_GRAV_GEN, Ssc14ModBlocks.DEBUG_GRAV_GEN);
        assertSame(Ssc14DebugBlockEntities.DEBUG_GRAV_GEN,
                Ssc14ModBlockEntities.DEBUG_GRAV_GEN);
    }

    @Test
    void legacyDebugGravityTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.DebugGravGenBlock");
        assertTrue(DebugGravityGeneratorBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        Class<?> legacyEntity = Class.forName(
                "net.mcreator.ssc.block.entity.DebugGravGenBlockEntity");
        assertTrue(DebugGravityGeneratorBlockEntity.class.isAssignableFrom(legacyEntity));
    }

    @Test
    void canonicalDebugGravityKeepsPhysicalAndStateContracts() {
        DebugGravityGeneratorBlock block =
                (DebugGravityGeneratorBlock) Ssc14Blocks.DEBUG_GRAV_GEN.get();
        assertEquals(Direction.NORTH,
                block.defaultBlockState().getValue(DebugGravityGeneratorBlock.FACING));
        assertEquals(-1.0F, block.defaultDestroyTime());
        assertEquals(3_600_000.0F, block.getExplosionResistance());

        AtomicBoolean gravity = new AtomicBoolean();
        DebugGravityStateService.install(gravity::set);
        DebugGravityStateService.enable();
        assertTrue(gravity.get());
        DebugGravityStateService.disable();
        assertTrue(!gravity.get());
    }
}
