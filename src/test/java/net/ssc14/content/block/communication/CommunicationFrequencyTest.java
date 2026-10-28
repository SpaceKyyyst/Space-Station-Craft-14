package net.ssc14.content.block.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

class CommunicationFrequencyTest {
    @Test
    void preservesLegacyTypeAndSavedKeyMapping() {
        assertEquals("Base_Radio_Frequency", CommunicationFrequency.fromBlockType(1).stateKey());
        assertEquals("Cargo_Radio_Frequency", CommunicationFrequency.fromBlockType(8).stateKey());
        assertNull(CommunicationFrequency.fromBlockType(0));
        assertNull(CommunicationFrequency.fromBlockType(9));
    }

    @Test
    void rejectsDifferentBlockOrType() {
        BlockState state = Blocks.STONE.defaultBlockState();
        assertEquals(false, CommunicationServerLifecycleService.matchesType(state, Blocks.DIRT, 0));
        assertEquals(false, CommunicationServerLifecycleService.matchesType(state, Blocks.STONE, 0));
    }
}
