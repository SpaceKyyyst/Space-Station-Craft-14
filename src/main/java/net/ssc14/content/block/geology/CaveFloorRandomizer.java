package net.ssc14.content.block.geology;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public final class CaveFloorRandomizer {
    private static final Identifier CAVE_FLOOR_TAG =
            Identifier.fromNamespaceAndPath("ssc14", "cave_floor");

    private CaveFloorRandomizer() {
    }

    public static void replace(LevelAccessor world, BlockPos pos) {
        var selected = BuiltInRegistries.BLOCK
                .getRandomElementOf(BlockTags.create(CAVE_FLOOR_TAG), RandomSource.create())
                .orElseGet(() -> BuiltInRegistries.BLOCK.wrapAsHolder(Blocks.AIR))
                .value();
        world.setBlock(pos, selected.defaultBlockState(), 3);
    }
}
