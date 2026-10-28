package net.ssc14.content.block.airlock;

import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.Ssc14Items;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/** Removes an isolated, welded door only after its protective plates have been removed. */
public final class AirlockDisassemblyService {
    private static final double MAX_DISTANCE_SQUARED = 64;

    private AirlockDisassemblyService() { }

    public static boolean interact(Player player, BlockPos pos) {
        if (!(player.level() instanceof ServerLevel level) || !level.hasChunkAt(pos)
                || !player.isShiftKeyDown() || !player.getMainHandItem().is(Ssc14Items.SPANNER.get())) return false;
        var state = level.getBlockState(pos);
        if (!(state.getBlock().getStateDefinition().getProperty("arl_variat") instanceof IntegerProperty frame)) return false;
        var blockEntity = level.getBlockEntity(pos);
        if (!player.isAlive() || player.isSpectator() || !level.mayInteract(player, pos)
                || player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > MAX_DISTANCE_SQUARED
                || blockEntity == null || AirlockMaintenanceService.isArmored(level, pos)) return true;
        if (!(state.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty panel)
                || !state.getValue(panel) || state.getValue(frame) >= 11 && state.getValue(frame) <= 15) return true;
        var data = blockEntity.getPersistentData();
        if (data.getBooleanOr("powered", true) || !data.getBooleanOr("welded", false)) return true;
        var drop = new ItemStack(state.getBlock());
        // Removing the root first prevents upper-part neighbor callbacks from finding a live door.
        if (!level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL)) return true;
        var top = pos.above();
        var topState = level.getBlockState(top);
        if (topState.is(Ssc14Blocks.AIRLOCK_UP_PLUG.get()) || topState.is(Ssc14Blocks.AIRLOCK_UP_PLUG_OPEN.get()))
            level.setBlock(top, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        Block.popResource(level, pos, drop);
        return true;
    }
}
