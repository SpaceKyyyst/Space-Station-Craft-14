package net.ssc14.content.access;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.ssc14.common.network.SlotMenuStateAccess;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** Canonical server-side ID-card validation and access-state synchronization. */
public final class IdCodeCheckService {
    private static final String BUFFER_KEY = "Bufer_LG";
    private static final String JOB_NAME_READACT = "Job_Name_Readact";
    private static final String JOB_NAME = "Job_Name";

    private IdCodeCheckService() {
    }

    public static void execute(LevelAccessor world, BlockPos consolePos, Entity entity) {
        if (!(world instanceof ServerLevel level) || !(entity instanceof Player player)) return;
        if (!(player.containerMenu instanceof SlotMenuStateAccess menu)
                || menu.getSlots().size() < 2) return;

        ItemStack source = menu.getSlots().get(0).getItem();
        ItemStack target = menu.getSlots().get(1).getItem();
        if (source.isEmpty() || target.isEmpty() || isBuffered(level, consolePos)) return;

        if (menu.getMenuState(1, JOB_NAME_READACT, false)) {
            String jobName = menu.getMenuState(0, JOB_NAME, "");
            target.set(DataComponents.CUSTOM_NAME, Component.literal("ID карта (" + jobName + ")"));
            menu.getSlots().get(1).set(target);
        }

        ServerWorkScheduler.schedule(level, 1, () -> IdJobAssignmentService.syncAllAccess(player));
    }

    private static boolean isBuffered(ServerLevel level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity != null
                && blockEntity.getPersistentData().getBoolean(BUFFER_KEY).orElse(false);
    }
}
