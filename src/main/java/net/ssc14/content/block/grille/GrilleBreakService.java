package net.ssc14.content.block.grille;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.Ssc14Blocks;

/** Replaces a survival-broken intact grille with a randomly oriented damaged grille. */
public final class GrilleBreakService {
    private GrilleBreakService() {
    }

    public static void replaceWithBroken(LevelAccessor level, BlockPos pos, Entity entity) {
        if (entity == null || entity instanceof Player player && player.gameMode() == GameType.CREATIVE) {
            return;
        }
        replaceWithBroken(level, pos, () -> Direction.getRandom(RandomSource.create()));
    }

    static void replaceWithBroken(LevelAccessor level, BlockPos pos, Supplier<Direction> directionSource) {
        level.setBlock(pos, Ssc14Blocks.BROKEN_GRILLE.get().defaultBlockState(), 3);
        Direction facing = directionSource.get();
        level.setBlock(pos, level.getBlockState(pos).setValue(BrokenGrilleBlock.FACING, facing), 3);
    }
}
