package net.ssc14.content.block.ceiling;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

/** Defines the historical support-above contract for station ceiling tiles. */
public final class CeilingSupportPolicy {
    public static final TagKey<Block> SUITABLE_SUPPORT = TagKey.create(
            Registries.BLOCK, Identifier.parse("ssc14:suitable_for_ceiling"));

    private CeilingSupportPolicy() {
    }

    public static boolean canSupport(BlockGetter level, BlockPos tilePos) {
        return isSuitableSupport(level, tilePos.above());
    }

    public static boolean isSuitableSupport(BlockGetter level, BlockPos supportPos) {
        return level.getBlockState(supportPos).is(SUITABLE_SUPPORT);
    }
}
