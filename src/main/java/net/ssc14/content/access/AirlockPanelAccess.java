package net.ssc14.content.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/** Server-compatible validity rules for an open, serviceable airlock wire panel. */
public final class AirlockPanelAccess {
    private static final String ARMOR_KEY = "_ssc14_airlock_armor";
    private static final String PANEL_OPEN_PROPERTY = "panel_open";
    private static final double MAX_DISTANCE_SQUARED = 64;

    private AirlockPanelAccess() {
    }

    public static boolean canUse(Player player, BlockPos pos) {
        var level = player.level();
        if (!player.isAlive() || player.isSpectator() || !level.hasChunkAt(pos)
                || player.distanceToSqr(pos.getCenter()) > MAX_DISTANCE_SQUARED) {
            return false;
        }
        var door = level.getBlockEntity(pos);
        if (door == null || door.getPersistentData().getIntOr(ARMOR_KEY, 0) != 0) {
            return false;
        }
        var state = level.getBlockState(pos);
        return AirlockDoorState.isDoor(state)
                && state.getBlock().getStateDefinition().getProperty(PANEL_OPEN_PROPERTY)
                        instanceof BooleanProperty panel
                && state.getValue(panel);
    }
}
