package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.energy.SheathingBlock;
import net.ssc14.content.block.energy.SheathingBlockEntity;
import net.ssc14.content.block.energy.Ssc14CableBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksSheathingCompatibilityTest {
    @Test
    void canonicalRegistryOwnsSheathingIdsAndLegacyFacadesAliasHolders() {
        assertEquals("ssc_14:sheathing", Ssc14Blocks.SHEATHING.getId().toString());
        assertEquals("ssc_14:sheathing",
                Ssc14CableBlockEntities.SHEATHING.getId().toString());
        assertSame(Ssc14Blocks.SHEATHING, Ssc14ModBlocks.SHEATHING);
    }

    @Test
    void legacySheathingTypesRemainConstructorCompatibleWrappers() throws Exception {
        assertWrapper("net.mcreator.ssc.block.SheathingBlock", SheathingBlock.class);
        assertTrue(SheathingBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.SheathingBlockEntity")));
    }

    @Test
    void canonicalSheathingKeepsLegacyStateAndPhysicalDefaults() {
        SheathingBlock sheathing = (SheathingBlock) Ssc14Blocks.SHEATHING.get();

        assertFalse(sheathing.defaultBlockState().getValue(SheathingBlock.LV));
        assertFalse(sheathing.defaultBlockState().getValue(SheathingBlock.MV));
        assertFalse(sheathing.defaultBlockState().getValue(SheathingBlock.HV));
        assertEquals(-1.0F, sheathing.defaultDestroyTime());
        assertEquals(15, sheathing.getLightDampening(sheathing.defaultBlockState()));
    }

    private static void assertWrapper(String className, Class<? extends Block> canonical)
            throws Exception {
        Class<?> legacy = Class.forName(className);
        assertTrue(canonical.isAssignableFrom(legacy), className);
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }
}
