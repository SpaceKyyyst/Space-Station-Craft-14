package net.ssc14.legacytests.network.validation;

import org.junit.jupiter.api.Test;
import net.ssc14.core.network.ChunkPacketCoordinates;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ChunkPacketCoordinatesTest {
    @Test
    void roundTripsSignedCoordinatesWithoutSwappingAxes() {
        int[] coordinates = {Integer.MIN_VALUE, -175, -1, 0, 1, 94, Integer.MAX_VALUE};
        for (int x : coordinates) {
            for (int z : coordinates) {
                long packed = ChunkPacketCoordinates.pack(x, z);
                assertEquals(x, ChunkPacketCoordinates.x(packed));
                assertEquals(z, ChunkPacketCoordinates.z(packed));
            }
        }
    }

    @Test
    void preservesExistingHighWordXWireLayout() {
        assertEquals(0x00000001fffffffeL, ChunkPacketCoordinates.pack(1, -2));
    }
}
