
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class CommConsGREENcodeProcedure {
	public static void execute(LevelAccessor world) {
		if (Ssc14ModVariables.MapVariables.get(world).station_code < 6 && Ssc14ModVariables.MapVariables.get(world).station_code != 1) {
			if (!(world instanceof Level level) || level.isClientSide())
				return;
			
			Component announcementMessage = Component.literal("Внимание " + Ssc14ModVariables.MapVariables.get(world).station_name + "! Код ЗЕЛЁНЫЙ!" + "\n" + "Отсутствие угроз безопасности. Станция работает в штатном режиме.")
					.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x21a528)).withBold(false));
			
			for (Player player : level.players()) {
				player.sendSystemMessage(announcementMessage);
			}
			
			var holder = net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("ssc_14:green_code"));
			if (holder != null && holder.isPresent()) {
				// ИСПРАВЛЕНО: Указаны фиксированные координаты (0, 64, 0). 
				// Звук с громкостью 10000.0F гарантированно услышат все игроки в этом мире!
				level.playSound(null, 0.0, 64.0, 0.0, holder.get().value(), net.minecraft.sounds.SoundSource.MASTER, 10000.0F, 1.0F);
			}

			Ssc14ModVariables.MapVariables.get(world).station_code = 1;
			Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
		}
	}
}
