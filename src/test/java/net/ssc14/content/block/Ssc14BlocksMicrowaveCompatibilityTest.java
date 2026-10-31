package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.machinery.MicrowaveBlock;
import net.ssc14.content.block.machinery.MicrowaveBlockEntity;
import net.ssc14.content.block.machinery.MicrowaveMenuService;
import net.ssc14.content.block.machinery.Ssc14MachineBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksMicrowaveCompatibilityTest {
    @Test
    void canonicalRegistriesOwnMicrowaveIdsAndPreservesCanonicalIds() {
        assertEquals("ssc_14:microwawe", Ssc14Blocks.MICROWAWE.getId().toString());
        assertEquals("ssc_14:microwawe",
                Ssc14MachineBlockEntities.MICROWAWE.getId().toString());
    }

    @Test
    void legacyMicrowaveTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.MicrowaweBlock");
        assertTrue(MicrowaveBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        Class<?> legacyEntity = Class.forName("net.mcreator.ssc.block.entity.MicrowaweBlockEntity");
        assertTrue(MicrowaveBlockEntity.class.isAssignableFrom(legacyEntity));
    }

    @Test
    void canonicalMicrowaveKeepsStatePhysicalAndInventoryDefaults() {
        MicrowaveBlock block = (MicrowaveBlock) Ssc14Blocks.MICROWAWE.get();
        var state = block.defaultBlockState();
        assertEquals(Direction.NORTH, state.getValue(MicrowaveBlock.FACING));
        assertTrue(state.getValue(MicrowaveBlock.ON_OFF));
        assertEquals(false, state.getValue(MicrowaveBlock.ACTIVE));
        assertEquals(30.0F, block.defaultDestroyTime());
        assertEquals(15.0F, block.getExplosionResistance());
        assertEquals(13, MicrowaveBlockEntity.SLOT_COUNT);
    }

    @Test
    void menuServiceRoutesCreationExactlyOnce() {
        int[] calls = {0};
        MicrowaveMenuService.install((id, inventory, pos) -> {
            calls[0]++;
            return null;
        });
        MicrowaveMenuService.createMenu(0, null, BlockPos.ZERO);
        assertEquals(1, calls[0]);
    }
}
