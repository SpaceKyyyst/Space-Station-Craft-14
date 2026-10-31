package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.gravity.GravityGeneratorPlugBlock;
import net.ssc14.content.block.gravity.GravityGeneratorPlugLifecycleService;
import org.junit.jupiter.api.Test;

class Ssc14BlocksGravityGeneratorPlugCompatibilityTest {
    @Test
    void canonicalRegistryOwnsPlugIdAndPreservesCanonicalId() {
        assertEquals("ssc_14:grav_gen_plug_block",
                Ssc14Blocks.GRAV_GEN_PLUG_BLOCK.getId().toString());
    }

    @Test
    void legacyPlugTypeRemainsAConstructorCompatibleWrapper() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.GravGenPlugBlockBlock");
        assertTrue(GravityGeneratorPlugBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }

    @Test
    void canonicalPlugKeepsLegacyPhysicalDefaults() {
        GravityGeneratorPlugBlock block =
                (GravityGeneratorPlugBlock) Ssc14Blocks.GRAV_GEN_PLUG_BLOCK.get();
        assertEquals(-1.0F, block.defaultDestroyTime());
        assertEquals(3_600_000.0F, block.getExplosionResistance());
        assertEquals(0, block.getLightDampening(block.defaultBlockState()));
        assertTrue(block.propagatesSkylightDown(block.defaultBlockState()));
    }

}
