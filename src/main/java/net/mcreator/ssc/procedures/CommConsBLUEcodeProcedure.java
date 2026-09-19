
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier; // ДОБАВЛЕН ИМПОРТ ИДЕНТИФИКАТОРА

import net.mcreator.ssc.network.Ssc14ModVariables;

public class CommConsBLUEcodeProcedure {
	public static void execute(LevelAccessor world) {
		if (Ssc14ModVariables.MapVariables.get(world).station_code < 6 && Ssc14ModVariables.MapVariables.get(world).station_code != 3) {
			// Проверяем, что world - это Level И что мы на серверной стороне
			if (!(world instanceof Level level) || level.isClientSide())
				return;
			// Формируем сообщение объявления
			Component announcementMessage = Component.literal("Внимание " + Ssc14ModVariables.MapVariables.get(world).station_name + "! Код СИНИЙ!" + "\n"
					+ "На станции выявлена угроза безопасности I уровня. Служба безопасности усиливает патрулирование. Дополнительные инструкции указаны в КПК.").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x1755e6)).withBold(false));
			// Отправляем всем игрокам в измерении
			for (Player player : level.players()) {
				// ИСПРАВЛЕНО ПОД 1.21.4: sendSystemMessage вместо displayClientMessage
				player.sendSystemMessage(announcementMessage);
			}
			// Воспроизводим глобальный звук (volume = 10000.0 делает его слышимым на всё измерение)
			// ИСПРАВЛЕНО ПОД 1.21.4: Identifier.parse вместо ResourceLocation.parse, и достаем чистый звук через .value()
			var holder = net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("ssc_14:blue_code"));
			if (holder != null && holder.isPresent()) {
				// ИСПРАВЛЕНО: Указаны фиксированные координаты (0, 64, 0). 
				// Звук с громкостью 10000.0F гарантированно услышат все игроки в этом мире!
				level.playSound(null, 0.0, 64.0, 0.0, holder.get().value(), net.minecraft.sounds.SoundSource.MASTER, 10000.0F, 1.0F);
			}

			Ssc14ModVariables.MapVariables.get(world).station_code = 3;
			Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
		}
	}
}
