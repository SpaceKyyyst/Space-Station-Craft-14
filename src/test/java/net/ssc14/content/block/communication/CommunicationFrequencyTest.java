package net.ssc14.content.block.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class CommunicationFrequencyTest {
    @Test
    void preservesLegacyTypeAndSavedKeyMapping() {
        assertEquals("Base_Radio_Frequency", CommunicationFrequency.fromBlockType(1).stateKey());
        assertEquals("Cargo_Radio_Frequency", CommunicationFrequency.fromBlockType(8).stateKey());
        assertNull(CommunicationFrequency.fromBlockType(0));
        assertNull(CommunicationFrequency.fromBlockType(9));
    }
}
