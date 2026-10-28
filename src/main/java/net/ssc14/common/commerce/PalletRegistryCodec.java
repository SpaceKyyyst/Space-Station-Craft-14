package net.ssc14.common.commerce;

import net.minecraft.core.BlockPos;

/** Preserves the legacy comma-separated pallet coordinate format. */
public final class PalletRegistryCodec {
    private PalletRegistryCodec() { }

    public static String encode(BlockPos position) {
        return position.getX() + "," + position.getY() + "," + position.getZ() + ";";
    }
}
