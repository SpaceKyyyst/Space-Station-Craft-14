package net.ssc14.common.commerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

class PalletRegistryCodecTest {
    @Test
    void preservesLegacyCoordinateEncoding() {
        assertEquals("-4,12,27;", PalletRegistryCodec.encode(new BlockPos(-4, 12, 27)));
    }

    @Test
    void detectsExistingCoordinateWithoutChangingFormat() {
        BlockPos position = new BlockPos(1, 2, 3);
        assertEquals(true, PalletRegistryCodec.contains("0,0,0;1,2,3;", position));
        assertFalse(PalletRegistryCodec.contains("11,2,3;", position));
    }
}
