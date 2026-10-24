package net.mcreator.ssc.access;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ssc14.content.block.floor.FloorTileDoorwayCollision;

/** The floor keeps its own block and shape; the doorway contributes an additional seal. */
public final class AirlockFloor {
    private AirlockFloor() { }

    public static boolean installTile(net.minecraft.world.entity.player.Player player, BlockPos clicked) {
        var level = player.level();
        if (level.isClientSide() || !(player.getMainHandItem().getItem() instanceof net.minecraft.world.item.BlockItem item)) return false;
        BlockPos floor = hasDoor(level, clicked) ? clicked : clicked.below();
        if (!level.getBlockState(floor).is(Ssc14ModBlocks.AIRLOCK_THRESHOLD.get())) return false;
        var tile = item.getBlock().defaultBlockState();
        if (!tile.is(net.minecraft.tags.BlockTags.create(net.minecraft.resources.Identifier.parse("ssc14:tiles")))) return false;
        if (!level.mayInteract(player, floor) || !tile.canSurvive(level, floor)) return true;
        if (level.setBlock(floor, tile, Block.UPDATE_ALL) && !player.isCreative()) player.getMainHandItem().shrink(1);
        return true;
    }

    public static boolean hasDoor(BlockGetter level, BlockPos floor) {
        return level.getBlockState(floor.above()).getBlock().getStateDefinition().getProperty("arl_variat") instanceof IntegerProperty;
    }

    public static VoxelShape collision(BlockGetter level, BlockPos floor, VoxelShape tile) {
        return FloorTileDoorwayCollision.collision(level, floor, tile);
    }

    public static void ensureLowerPart(LevelAccessor level, BlockPos root) {
        if (!level.isClientSide() && level.getBlockState(root.below()).isAir()
                && !level.isOutsideBuildHeight(root.below()))
            level.setBlock(root.below(), Ssc14ModBlocks.AIRLOCK_THRESHOLD.get().defaultBlockState(), Block.UPDATE_ALL);
    }
}
