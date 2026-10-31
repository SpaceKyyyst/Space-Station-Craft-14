package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.energy.DebugGeneratorBlock;
import net.ssc14.content.block.energy.DebugGeneratorBlockEntity;
import net.ssc14.content.block.energy.SmesBlock;
import net.ssc14.content.block.energy.SmesBlockEntity;
import net.ssc14.content.block.energy.Ssc14EnergyMachineBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksEnergyDeviceCompatibilityTest {
    @Test
    void canonicalRegistryOwnsEnergyDeviceIdsAndPreservesCanonicalIds() {
        assertEquals("ssc_14:debu_ggenerator", Ssc14Blocks.DEBU_GGENERATOR.getId().toString());
        assertEquals("ssc_14:smes", Ssc14Blocks.SMES.getId().toString());
        assertSame(Ssc14EnergyMachineBlockEntities.DEBU_GGENERATOR,
                Ssc14EnergyMachineBlockEntities.DEBU_GGENERATOR);
    }

    @Test
    void legacyEnergyDeviceTypesRemainConstructorCompatibleWrappers() throws Exception {
        assertWrapper("net.mcreator.ssc.block.DEBUGgeneratorBlock", DebugGeneratorBlock.class);
        assertWrapper("net.mcreator.ssc.block.SMESBlock", SmesBlock.class);
        assertTrue(DebugGeneratorBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.DEBUGgeneratorBlockEntity")));
        assertTrue(SmesBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.SMESBlockEntity")));
    }

    @Test
    void canonicalEnergyDevicesKeepStateAndStrengthDefaults() {
        DebugGeneratorBlock generator = (DebugGeneratorBlock) Ssc14Blocks.DEBU_GGENERATOR.get();
        SmesBlock smes = (SmesBlock) Ssc14Blocks.SMES.get();
        assertEquals(-1.0F, generator.defaultDestroyTime());
        assertEquals(Direction.NORTH, smes.defaultBlockState().getValue(SmesBlock.FACING));
        assertEquals(0, smes.defaultBlockState().getValue(SmesBlock.BLOCKSTATE));
        assertEquals(50.0F, smes.defaultDestroyTime());
    }

    private static void assertWrapper(String className, Class<? extends Block> canonical)
            throws Exception {
        Class<?> legacy = Class.forName(className);
        assertTrue(canonical.isAssignableFrom(legacy), className);
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }
}
