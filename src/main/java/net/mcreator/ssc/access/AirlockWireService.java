package net.mcreator.ssc.access;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.ssc14.integration.neoforge.menu.network.MenuAccessGuard;
import net.mcreator.ssc.procedures.CPlogLampPrProcedure;
import net.mcreator.ssc.world.inventory.CabelPannelAirlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/** Server-authoritative wire edits on the door bound to the player's open panel. */
public final class AirlockWireService {
    private static final int WIRE_COUNT = 8;
    private static final double MAX_DISTANCE_SQUARED = 64;

    private AirlockWireService() { }

    public static boolean canUsePanel(Player player, BlockPos pos) {
        var level = player.level();
        if (!player.isAlive() || player.isSpectator() || !level.hasChunkAt(pos)
                || player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > MAX_DISTANCE_SQUARED) return false;
        var state = level.getBlockState(pos);
        return !AirlockMaintenance.isArmored(level, pos)
                && state.getBlock().getStateDefinition().getProperty("arl_variat") != null
                && state.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty panel
                && state.getValue(panel) && level.getBlockEntity(pos) != null;
    }

    public static void toggle(Player player, BlockPos pos, int wire) {
        if (!(player.level() instanceof ServerLevel level) || wire < 1 || wire > WIRE_COUNT
                || !(player.containerMenu instanceof CabelPannelAirlockMenu menu)
                || !MenuAccessGuard.matches(player, menu, pos.getX(), pos.getY(), pos.getZ(), menu.x, menu.y, menu.z)
                || !canUsePanel(player, pos) || !level.mayInteract(player, pos)) return;
        var blockEntity = level.getBlockEntity(pos);
        var data = blockEntity.getPersistentData();
        String key = "cabel_" + wire;
        if (player.getMainHandItem().is(Ssc14ModItems.MULTITUL.get())
                || player.getOffhandItem().is(Ssc14ModItems.MULTITUL.get())) {
            if (!data.getBooleanOr(key, false)) AirlockWirePulses.start(blockEntity, data.getIntOr("log_cab_" + wire, -1));
            return;
        }
        if (!(player.getMainHandItem().is(Ssc14ModItems.NIPPERS.get())
                || player.getOffhandItem().is(Ssc14ModItems.NIPPERS.get()))) return;
        data.putBoolean(key, !data.getBooleanOr(key, false));
        blockEntity.setChanged();
        CPlogLampPrProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ(), level.getBlockState(pos));
        var state = level.getBlockState(pos);
        level.sendBlockUpdated(pos, state, state, 3);
        BuiltInRegistries.SOUND_EVENT.getOptional(Identifier.parse("ssc_14:nippers_use"))
                .ifPresent(sound -> level.playSound(null, pos, sound, SoundSource.BLOCKS, 1, 1));
    }
}
