package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.ssc14.content.block.airlock.AirlockEventLogService;

/** A bounded persisted log; disabled logging erases history rather than hiding it. */
@Deprecated(forRemoval = false)
public final class AirlockEventLog {
    private AirlockEventLog() { }
    public static void append(BlockEntity door, String event) {
        AirlockEventLogService.append(door, event);
    }
    public static void clear(BlockEntity door) {
        AirlockEventLogService.clear(door);
    }
    public static boolean inspect(Player player, BlockPos clicked) {
        return AirlockEventLogService.inspect(player, clicked);
    }
}
