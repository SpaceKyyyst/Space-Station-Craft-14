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
    private static final Identifier BLUE_ALERT_SOUND = Identifier.parse("ssc_14:blue_code");

    private StationAlertService() { }

    /** Triggers one of the five station codes from a console button. */
    public static boolean announceCode(ServerLevel level, double currentCode, String stationName, int targetCode) {
        return switch (targetCode) {
            case 1 -> announce(level, currentCode, stationName, 1, 0x21a528, BLUE_ALERT_SOUND,
                    "Код ЗЕЛЁНЫЙ!\nОтсутствие угроз безопасности. Станция работает в штатном режиме.");
            case 2 -> announce(level, currentCode, stationName, 2, 0xe6b917, BLUE_ALERT_SOUND,
                    "Код ЖЁЛТЫЙ!\nНа станции выявлена структурная или атмосферная угроза. "
                            + "Инженерный отдел уполномочен координировать ликвидацию последствий. "
                            + "Дополнительные инструкции указаны в КПК.");
            case 3 -> announce(level, currentCode, stationName, 3, 0x1755e6, BLUE_ALERT_SOUND,
                    "Код СИНИЙ!\nНа станции выявлена угроза безопасности I уровня. "
                            + "Служба безопасности усиливает патрулирование. Дополнительные инструкции указаны в КПК.");
            case 4 -> announceRed(level, currentCode, stationName);
            case 5 -> announce(level, currentCode, stationName, 5, 0x9d2896, BLUE_ALERT_SOUND,
                    "Код ФИОЛЕТОВЫЙ!\nНа станции зафиксирована биологическая угроза. "
                            + "Активирован протокол изоляции. Медицинскому персоналу предписано изолировать "
                            + "членов экипажа с любыми симптомами.");
            default -> false;
        };
    }

    public static boolean announceRed(ServerLevel level, double currentCode, String stationName) {
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

    public static boolean announce(ServerLevel level, double currentCode, String stationName,
            int targetCode, int color, Identifier soundId, String text) {
        if (currentCode >= MAX_CODE_BEFORE_ALERT || currentCode == targetCode) return false;
        Component message = Component.literal("Внимание " + stationName + "! " + text)
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)).withBold(false));
        for (Player player : level.players()) player.sendSystemMessage(message);
        var sound = BuiltInRegistries.SOUND_EVENT.get(soundId);
        if (sound.isPresent()) {
            level.playSound(null, 0.0, 64.0, 0.0, sound.get().value(), SoundSource.MASTER, 10000.0F, 1.0F);
        }
        return true;
    }
}
