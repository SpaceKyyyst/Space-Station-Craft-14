package net.ssc14.common.commerce;

import net.minecraft.core.BlockPos;

/** Preserves the legacy comma-separated pallet coordinate format. */
public final class PalletRegistryCodec {
    private PalletRegistryCodec() { }

    public static String encode(BlockPos position) {
        return position.getX() + "," + position.getY() + "," + position.getZ() + ";";
    }

    public static boolean contains(String encodedRegistry, BlockPos position) {
        return encodedRegistry != null
                && (";" + encodedRegistry).contains(";" + encode(position));
    }

    public static String appendIfAbsent(String encodedRegistry, BlockPos position) {
        String registry = encodedRegistry == null ? "" : encodedRegistry;
        return contains(registry, position) ? registry : registry + encode(position);
    }
}
