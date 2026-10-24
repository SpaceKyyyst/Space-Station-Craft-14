package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.shapes.VoxelShape;

/** @deprecated Use {@link net.ssc14.content.block.airlock.AirlockFloor}. */
@Deprecated(forRemoval = false)
public final class AirlockFloor {
    private AirlockFloor() { }

    public static boolean installTile(Player player, BlockPos clicked) {
        return net.ssc14.content.block.airlock.AirlockFloor.installTile(player, clicked);
    }

    public static boolean hasDoor(BlockGetter level, BlockPos floor) {
        return net.ssc14.content.block.airlock.AirlockFloor.hasDoor(level, floor);
    }

    public static VoxelShape collision(BlockGetter level, BlockPos floor, VoxelShape tile) {
        return net.ssc14.content.block.airlock.AirlockFloor.collision(level, floor, tile);
    }

    public static void ensureLowerPart(LevelAccessor level, BlockPos root) {
        net.ssc14.content.block.airlock.AirlockFloor.ensureLowerPart(level, root);
    }
}
