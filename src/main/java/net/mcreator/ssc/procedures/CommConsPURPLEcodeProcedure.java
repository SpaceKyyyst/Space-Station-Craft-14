
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class CommConsPURPLEcodeProcedure {
	public static void execute(LevelAccessor world) {
		if (Ssc14ModVariables.MapVariables.get(world).station_code < 6 && Ssc14ModVariables.MapVariables.get(world).station_code != 5) {
			if (!(world instanceof Level level) || level.isClientSide())
				return;
			
			Component announcementMessage = Component.literal("Внимание " + Ssc14ModVariables.MapVariables.get(world).station_name + "! Код ФИОЛЕТОВЫЙ!" + "\n"
					+ "На станции зафиксирована биологическая угроза. Активирован протокол изоляции. Медицинскому персоналу предписано изолировать членов экипажа с любыми симптомами. Экипажу рекомендуется соблюдать дистанцию, следовать мерам предосторожности для предотвращения распространения вируса и выполнять указания главного врача. Дополнительные инструкции указаны в КПК.")
					.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x9d2896)).withBold(false));
			
			for (Player player : level.players()) {
				player.sendSystemMessage(announcementMessage);
			}
			
			var holder = net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("ssc_14:blue_code"));
			if (holder != null && holder.isPresent()) {
				// ИСПРАВЛЕНО: Указаны фиксированные координаты (0, 64, 0). 
				// Звук с громкостью 10000.0F гарантированно услышат все игроки в этом мире!
				level.playSound(null, 0.0, 64.0, 0.0, holder.get().value(), net.minecraft.sounds.SoundSource.MASTER, 10000.0F, 1.0F);
			}

			Ssc14ModVariables.MapVariables.get(world).station_code = 5;
			Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
		}
	}
}
