package net.ssc14.content.block.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

class CommunicationFrequencyTest {
    @Test
    void resolvesEveryPersistedBlockType() {
        for (CommunicationFrequency frequency : CommunicationFrequency.values()) {
            assertEquals(frequency, CommunicationFrequency.fromBlockType(frequency.blockType()));
        }
    }

    @Test
    void preservesLegacyTypeAndSavedKeyMapping() {
        assertEquals("Base_Radio_Frequency", CommunicationFrequency.fromBlockType(1).stateKey());
        assertEquals("Cargo_Radio_Frequency", CommunicationFrequency.fromBlockType(8).stateKey());
    }

    @Test
    void preservesEveryLegacySavedKey() {
        String[] expected = {
                "Base_Radio_Frequency", "Command_Radio_Frequency", "Engeneer_Radio_Frequency",
                "Medical_Radio_Frequency", "RND_Radio_Frequency", "Sec_Radio_Frequency",
                "Serv_Radio_Frequency", "Cargo_Radio_Frequency"
        };
        for (int type = 1; type <= expected.length; type++) {
            assertEquals(expected[type - 1], CommunicationFrequency.fromBlockType(type).stateKey());
        }
    }

    @Test
    void rejectsUnknownBlockTypesWithoutChangingPersistedMapping() {
        assertNull(CommunicationFrequency.fromBlockType(0));
        assertNull(CommunicationFrequency.fromBlockType(99));
    }

    @Test
    void rejectsDifferentBlockOrType() {
        BlockState state = Blocks.STONE.defaultBlockState();
        assertEquals(false, CommunicationServerLifecycleService.matchesType(state, Blocks.DIRT, 0));
        assertEquals(false, CommunicationServerLifecycleService.matchesType(state, Blocks.STONE, 0));
    }

    @Test
    void persistencePortIgnoresNullLevel() {
        int[] calls = {0};
        CommunicationFrequencyStateService.install((level, key, enabled) -> calls[0]++);
        CommunicationFrequencyStateService.set(null, "Base_Radio_Frequency", true);
        assertEquals(0, calls[0]);
    }
}
