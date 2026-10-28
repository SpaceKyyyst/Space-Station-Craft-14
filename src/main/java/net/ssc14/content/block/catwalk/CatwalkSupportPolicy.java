package net.ssc14.content.block.catwalk;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

/** Defines the historical support contract for catwalk floors. */
public final class CatwalkSupportPolicy {
    public static final TagKey<Block> SUITABLE_SUPPORT = TagKey.create(
            Registries.BLOCK, Identifier.parse("ssc14:suitable_for_catwalk"));

    private CatwalkSupportPolicy() {
    }

    public static boolean canSupport(BlockGetter level, BlockPos catwalkPos) {
        return level.getBlockState(catwalkPos.below()).is(SUITABLE_SUPPORT);
    }
}
