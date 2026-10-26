package net.mcreator.ssc.integration;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.airlock.AirlockDoorIntegrationService;

/** Bridges canonical airlock doors to existing access and animation services. */
public final class LegacyAirlockDoorIntegrationAdapter
        implements AirlockDoorIntegrationService.Adapter {
    @Override public void place(Level level, BlockPos pos, BlockState state) {
        net.mcreator.ssc.procedures.BaseAirlockD1PutProcedure.execute(
                level, pos.getX(), pos.getY(), pos.getZ(), state);
    }

    @Override public void use(Level level, BlockPos pos, BlockState state, Player player) {
        net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure.execute(
                level, pos.getX(), pos.getY(), pos.getZ(), state, player);
    }

    @Override public void tick(ServerLevel level, BlockPos pos) {
        net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure.tick(level, pos);
    }

    @Override public void neighborChanged(Level level, BlockPos pos) {
        net.mcreator.ssc.access.AirlockFloor.ensureLowerPart(level, pos);
        if (level instanceof ServerLevel server) {
            net.mcreator.ssc.access.AirlockSignals.changed(server, pos);
        }
    }

    @Override public AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos) {
        return new net.mcreator.ssc.world.inventory.AccessConfigMENUMenu(
                id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
    }
}
