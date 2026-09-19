package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.AtmosphereManager;
import net.mcreator.ssc.AtmosCell;

import javax.annotation.Nullable;

@EventBusSubscriber
public class KPaTextRedactProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
	}

	public static String execute(LevelAccessor world, double x, double y, double z) {
		return execute(null, world, x, y, z);
	}

	private static String execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
		if (!(world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(Identifier.parse("ssc14:hermetic")))) {
			if (world instanceof ServerLevel serverLevel) {
				AtmosCell cell = AtmosphereManager.get(serverLevel).getCellAt(BlockPos.containing(x, y, z));
				if (cell != null) {
					float totalMoles = cell.getTotalMoles(); // ✅ сумма ВСЕХ газов
					float temp = cell.getTemperature();
					float pressurePa = totalMoles * 8.314f * temp; // P в Паскалях
					float pressureKPa = pressurePa / 1000f; // перевод в кПа
					return Math.round(pressureKPa * 10) / 10d + " (кПа)";
				}
			}
		} else {
			return "-";
		}
		return "-";
	}
}