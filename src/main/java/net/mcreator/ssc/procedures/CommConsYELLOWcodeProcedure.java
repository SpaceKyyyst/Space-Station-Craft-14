package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class CommConsYELLOWcodeProcedure {
	public static void execute(LevelAccessor world) {
		if (Ssc14ModVariables.MapVariables.get(world).station_code < 6 && Ssc14ModVariables.MapVariables.get(world).station_code != 2) {
			// Проверяем, что world - это Level И что мы на серверной стороне
			if (!(world instanceof Level level) || level.isClientSide())
				return;
			// Формируем сообщение объявления
			Component announcementMessage = Component
					.literal("Внимание " + Ssc14ModVariables.MapVariables.get(world).station_name + "! Код ЖЁЛТЫЙ!" + "\n"
							+ "На станции выявлена структурная или атмосферная угроза. Инженерный отдел уполномочен координировать ликвидацию последствий. Дополнительные инструкции указаны в КПК.")
					.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xe6b917)).withBold(false));
			// Отправляем всем игрокам в измерении
			for (Player player : level.players()) {
				player.displayClientMessage(announcementMessage, false);
			}
			// Воспроизводим глобальный звук (volume = 10000.0 делает его слышимым на всё измерение)
			var soundEvent = net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:blue_code"));
			if (soundEvent != null) {
				level.playSound(null, level.getSharedSpawnPos().getX() + 0.5, level.getSharedSpawnPos().getY() + 0.5, level.getSharedSpawnPos().getZ() + 0.5, soundEvent, net.minecraft.sounds.SoundSource.MASTER, 10000.0F, 1.0F);
			}
			Ssc14ModVariables.MapVariables.get(world).station_code = 2;
			Ssc14ModVariables.MapVariables.get(world).markSyncDirty();
		}
	}
}