package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.ssc14.content.item.Ssc14Items;

/** Canonical persisted event log for airlock diagnostics. */
public final class AirlockEventLogService {
    private static final String PREFIX = "_ssc14_log_";
    private static final int CAPACITY = 16;

    private AirlockEventLogService() {
    }

    public static void append(BlockEntity door, String event) {
        var data = door.getPersistentData();
        if (!data.getBooleanOr("logs", true) || door.getLevel() == null) return;
        int count = Math.min(CAPACITY, data.getIntOr(PREFIX + "size", 0));
        for (int i = Math.min(count, CAPACITY - 1); i > 0; i--) {
            data.putString(PREFIX + i, data.getStringOr(PREFIX + (i - 1), ""));
        }
        data.putString(PREFIX + "0", door.getLevel().getGameTime() + ": " + event);
        data.putInt(PREFIX + "size", Math.min(CAPACITY, count + 1));
        door.setChanged();
    }

    public static void clear(BlockEntity door) {
        var data = door.getPersistentData();
        for (int i = 0; i < CAPACITY; i++) data.remove(PREFIX + i);
        data.remove(PREFIX + "size");
        door.setChanged();
    }

    public static boolean inspect(Player player, BlockPos clicked) {
        if (!(player.level() instanceof ServerLevel level) || !player.isShiftKeyDown()
                || !player.getMainHandItem().is(Ssc14Items.MULTITOOL.get()) || !level.hasChunkAt(clicked)) return false;
        var pos = AirlockMaintenanceService.root(level, clicked);
        var door = level.getBlockEntity(pos);
        if (door == null || !AirlockPowerService.isDoor(door.getBlockState())) return false;
        if (!player.isAlive() || player.isSpectator() || !level.mayInteract(player, pos)
                || !AirlockWireService.canUsePanel(player, pos)) return true;
        var data = door.getPersistentData();
        player.sendSystemMessage(Component.translatable("message.ssc_14.airlock_log"));
        int count = Math.min(CAPACITY, data.getIntOr(PREFIX + "size", 0));
        for (int i = count - 1; i >= 0; i--) {
            player.sendSystemMessage(Component.literal(data.getStringOr(PREFIX + i, "")));
        }
        return true;
    }
}
