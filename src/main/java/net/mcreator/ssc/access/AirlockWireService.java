package net.mcreator.ssc.access;

import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.access.AirlockPanelAccess;
import net.ssc14.integration.neoforge.menu.network.MenuAccessGuard;
import net.mcreator.ssc.procedures.CPlogLampPrProcedure;
import net.ssc14.content.menu.airlock.CabelPannelAirlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

/** Server-authoritative wire edits on the door bound to the player's open panel. */
public final class AirlockWireService {
    private static final int WIRE_COUNT = 8;

    private AirlockWireService() { }

    public static boolean canUsePanel(Player player, BlockPos pos) {
        return AirlockPanelAccess.canUse(player, pos);
    }

    public static void toggle(Player player, BlockPos pos, int wire) {
        if (!(player.level() instanceof ServerLevel level) || wire < 1 || wire > WIRE_COUNT
                || !(player.containerMenu instanceof CabelPannelAirlockMenu menu)
                || !MenuAccessGuard.matches(player, menu, pos.getX(), pos.getY(), pos.getZ(), menu.x, menu.y, menu.z)
                || !canUsePanel(player, pos) || !level.mayInteract(player, pos)) return;
        var blockEntity = level.getBlockEntity(pos);
        var data = blockEntity.getPersistentData();
        String key = "cabel_" + wire;
        if (player.getMainHandItem().is(Ssc14Items.MULTITOOL.get())
                || player.getOffhandItem().is(Ssc14Items.MULTITOOL.get())) {
            if (!data.getBooleanOr(key, false)) AirlockWirePulses.start(blockEntity, data.getIntOr("log_cab_" + wire, -1));
            return;
        }
        if (!(player.getMainHandItem().is(Ssc14Items.NIPPERS.get())
                || player.getOffhandItem().is(Ssc14Items.NIPPERS.get()))) return;
        data.putBoolean(key, !data.getBooleanOr(key, false));
        blockEntity.setChanged();
        CPlogLampPrProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ(), level.getBlockState(pos));
        var state = level.getBlockState(pos);
        level.sendBlockUpdated(pos, state, state, 3);
        BuiltInRegistries.SOUND_EVENT.getOptional(Identifier.parse("ssc_14:nippers_use"))
                .ifPresent(sound -> level.playSound(null, pos, sound, SoundSource.BLOCKS, 1, 1));
    }
}
