package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.block.floor.FloorTileDoorwayCollision;

/** Owns the airlock lower-part seal and floor-tile replacement contract. */
public final class AirlockFloor {
    private static final Identifier TILE_TAG = Identifier.parse("ssc14:tiles");

    private AirlockFloor() { }

    public static boolean installTile(Player player, BlockPos clicked) {
        var level = player.level();
        if (level.isClientSide()
                || !(player.getMainHandItem().getItem() instanceof BlockItem item)) {
            return false;
        }
        BlockPos floor = hasDoor(level, clicked) ? clicked : clicked.below();
        if (!level.getBlockState(floor).is(Ssc14Blocks.AIRLOCK_THRESHOLD.get())) {
            return false;
        }
        var tile = item.getBlock().defaultBlockState();
        if (!tile.is(BlockTags.create(TILE_TAG))) {
            return false;
        }
        if (!level.mayInteract(player, floor) || !tile.canSurvive(level, floor)) {
            return true;
        }
        if (level.setBlock(floor, tile, Block.UPDATE_ALL) && !player.isCreative()) {
            player.getMainHandItem().shrink(1);
        }
        return true;
    }

    public static boolean hasDoor(BlockGetter level, BlockPos floor) {
        return level.getBlockState(floor.above()).getBlock().getStateDefinition()
                .getProperty("arl_variat") instanceof IntegerProperty;
    }

    public static VoxelShape collision(BlockGetter level, BlockPos floor, VoxelShape tile) {
        return FloorTileDoorwayCollision.collision(level, floor, tile);
    }

    public static void ensureLowerPart(LevelAccessor level, BlockPos root) {
        BlockPos floor = root.below();
        if (!level.isClientSide() && level.getBlockState(floor).isAir()
                && !level.isOutsideBuildHeight(floor)) {
            level.setBlock(floor, Ssc14Blocks.AIRLOCK_THRESHOLD.get().defaultBlockState(),
                    Block.UPDATE_ALL);
        }
    }
}
