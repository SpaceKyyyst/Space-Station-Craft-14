package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;

/** Compatibility facade for canonical airlock maintenance jobs. */
public final class AirlockMaintenance {
    public static final String ARMOR = net.ssc14.content.block.airlock.AirlockMaintenanceService.ARMOR;
    public static final int BARE = net.ssc14.content.block.airlock.AirlockMaintenanceService.BARE;
    public static final int ATTACHED = net.ssc14.content.block.airlock.AirlockMaintenanceService.ATTACHED;
    public static final int REINFORCED = net.ssc14.content.block.airlock.AirlockMaintenanceService.REINFORCED;
    private AirlockMaintenance() { }
    public static BlockPos root(BlockGetter level, BlockPos pos) { return net.ssc14.content.block.airlock.AirlockMaintenanceService.root(level, pos); }
    public static boolean isArmored(BlockGetter level, BlockPos pos) { return net.ssc14.content.block.airlock.AirlockMaintenanceService.isArmored(level, pos); }
    public static boolean interact(Player player, BlockPos pos) { return net.ssc14.content.block.airlock.AirlockMaintenanceService.interact(player, pos); }
    public static void advance(Player player) { net.ssc14.content.block.airlock.AirlockMaintenanceService.advance(player); }
}
