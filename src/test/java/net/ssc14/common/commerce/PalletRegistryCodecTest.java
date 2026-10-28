package net.ssc14.common.commerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

class PalletRegistryCodecTest {
    @Test
    void preservesLegacyCoordinateEncoding() {
        assertEquals("-4,12,27;", PalletRegistryCodec.encode(new BlockPos(-4, 12, 27)));
    }
}
