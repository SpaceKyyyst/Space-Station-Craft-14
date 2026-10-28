package net.ssc14.content.block.floor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

/** Defines the historical support contract shared by simple station floor tiles. */
public final class FloorTileSupportPolicy {
    public static final TagKey<Block> SUITABLE_SUPPORT = TagKey.create(
            Registries.BLOCK, Identifier.parse("ssc14:suitable_for_tiles"));

    private FloorTileSupportPolicy() {
    }

    public static boolean canSupport(BlockGetter level, BlockPos tilePos) {
        return level.getBlockState(tilePos.below()).is(SUITABLE_SUPPORT);
    }
}
