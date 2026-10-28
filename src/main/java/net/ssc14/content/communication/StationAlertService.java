package net.ssc14.content.communication;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

/** Server-authoritative station alert operations used by legacy console procedures. */
public final class StationAlertService {
    private static final int RED_CODE = 4;
    private static final int MAX_CODE_BEFORE_ALERT = 6;
    private static final Identifier RED_ALERT_SOUND = Identifier.parse("ssc_14:red_code");

    private StationAlertService() { }

    public static boolean announceRed(ServerLevel level, int currentCode, String stationName) {
        if (currentCode >= MAX_CODE_BEFORE_ALERT || currentCode == RED_CODE) return false;

        Component message = Component.literal("Внимание " + stationName + "! Код КРАСНЫЙ!\n"
                + "Существует прямая threat работе станции. Служба безопасности имеет право носить оружие наготове. "
                + "Рекомендуются обыски персонала и отсеков. Персонал станции обязан повиноваться законным требованиям службы безопасности.")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF4444)).withBold(false));
        for (Player player : level.players()) player.sendSystemMessage(message);

        var sound = BuiltInRegistries.SOUND_EVENT.get(RED_ALERT_SOUND);
        if (sound.isPresent()) {
            level.playSound(null, 0.0, 64.0, 0.0, sound.get().value(), SoundSource.MASTER, 10000.0F, 1.0F);
        }
        return true;
    }
}
