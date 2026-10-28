package net.ssc14.content.communication;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.mcreator.ssc.network.Ssc14ModVariables;

/** Server-authoritative station alert operations used by legacy console procedures. */
public final class StationAlertService {
    private static final int RED_CODE = 4;
    private static final int MAX_CODE_BEFORE_ALERT = 6;
    private static final Identifier RED_ALERT_SOUND = Identifier.parse("ssc_14:red_code");

    private StationAlertService() { }

    public static void announceRed(ServerLevel level) {
        var state = Ssc14ModVariables.MapVariables.get(level);
        if (state.station_code >= MAX_CODE_BEFORE_ALERT || state.station_code == RED_CODE) return;

        Component message = Component.literal("Внимание " + state.station_name + "! Код КРАСНЫЙ!\n"
                + "Существует прямая threat работе станции. Служба безопасности имеет право носить оружие наготове. "
                + "Рекомендуются обыски персонала и отсеков. Персонал станции обязан повиноваться законным требованиям службы безопасности.")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF4444)).withBold(false));
        for (Player player : level.players()) player.sendSystemMessage(message);

        var sound = BuiltInRegistries.SOUND_EVENT.get(RED_ALERT_SOUND);
        if (sound.isPresent()) {
            level.playSound(null, 0.0, 64.0, 0.0, sound.get().value(), SoundSource.MASTER, 10000.0F, 1.0F);
        }
        state.station_code = RED_CODE;
        state.markSyncDirty();
    }
}
