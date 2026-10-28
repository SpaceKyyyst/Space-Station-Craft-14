package net.ssc14.content.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NetworkConfigButtonIdsTest {
    @Test
    void preservesTriggerAndActionRanges() {
        assertTrue(NetworkConfigButtonIds.isValid(0));
        assertTrue(NetworkConfigButtonIds.isValid(15));
        assertFalse(NetworkConfigButtonIds.isValid(-1));
        assertFalse(NetworkConfigButtonIds.isValid(16));
        assertTrue(NetworkConfigButtonIds.isTrigger(7));
        assertFalse(NetworkConfigButtonIds.isTrigger(8));
        assertEquals(0, NetworkConfigButtonIds.triggerIndex(0));
        assertEquals(0, NetworkConfigButtonIds.actionIndex(8));
        assertEquals(7, NetworkConfigButtonIds.actionIndex(15));
    }
}
