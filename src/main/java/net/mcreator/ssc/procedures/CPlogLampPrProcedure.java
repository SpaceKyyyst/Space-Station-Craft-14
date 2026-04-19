package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class CPlogLampPrProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		// НИЖЕ НАХОДИТСЯ КОСТЫЛЬ ДЛЯ MCreator
		if (getPropertyByName(blockstate, "Crutch_123") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1) && Ssc14ModBlocks.KOSTILNIBLOK.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			if (world instanceof ServerLevel _level) {
				_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u0422\u0440\u0438\u0433\u0433\u0435\u0440 \u043A\u043E\u0441\u0442\u044B\u043B\u044F").withColor(0x000000).withStyle(ChatFormatting.ITALIC), false);
			}
		}
		// === 🎯 ОСНОВНАЯ ЛОГИКА ПРОВЕРКИ КАБЕЛЕЙ ===
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockEntity be = world.getBlockEntity(pos);
		if (be == null)
			return;
		CompoundTag nbt = be.getPersistentData();
		// Вся логика только на сервере
		if (world.isClientSide())
			return;
		boolean needsUpdate = false; // Флаг: нужно ли отправлять обновление
		// === ПЕРЕБИРАЕМ ВСЕ 8 КАБЕЛЕЙ ===
		for (int cableIndex = 0; cableIndex < 8; cableIndex++) {
			String cableTag = "cabel_" + (cableIndex + 1); // cabel_1 ... cabel_8
			String logTag = "log_cab_" + (cableIndex + 1); // log_cab_1 ... log_cab_8
			// 🔧 КЛЮЧЕВОЕ: проверяем, перерезан ли кабель
			boolean isCableCut = !nbt.getBooleanOr(cableTag, false); // true = перерезан
			int logValue = (int) nbt.getDoubleOr(logTag, 0); // 0...7 — какую функцию контролирует
			if (logValue < 0 || logValue > 7)
				continue; // Защита от невалидных значений
			// 🔧 РАЗДЕЛЯЕМ логику: перерезан ИЛИ цел
			if (isCableCut) {
				// === КАБЕЛЬ ПЕРЕРЕЗАН → включаем функцию ===
				switch (logValue) {
					case 0 : // energy_cabel_1
						if (!nbt.getBooleanOr("energy_cabel_1", false)) {
							nbt.putBoolean("energy_cabel_1", true);
							needsUpdate = true;
						}
						break;
					case 1 : // energy_cabel_2 (с проверкой свойства блока)
						if (!getBooleanProperty(blockstate, "energy_cabel_2", false) && !nbt.getBooleanOr("energy_cabel_2", false)) {
							nbt.putBoolean("energy_cabel_2", true);
							needsUpdate = true;
						}
						break;
					case 2 : // 🔒 bolted (меняет и NBT, и BlockState!)
						if (!nbt.getBooleanOr("bolted", false)) {
							// 1️⃣ Устанавливаем флаг в NBT (для логики проверки)
							nbt.putBoolean("bolted", true);
							needsUpdate = true;
							// 2️⃣ 🔧 Устанавливаем свойство в BlockState (для визуала и коллизии)
							Property<?> boltedProp = world.getBlockState(pos).getBlock().getStateDefinition().getProperty("bolted");
							if (boltedProp instanceof BooleanProperty bp) {
								world.setBlock(pos, world.getBlockState(pos).setValue(bp, true), 3);
							}
							// 3️⃣ 🔊 Звук и обновление шлюза
							BuiltInRegistries.SOUND_EVENT.getOptional(ResourceLocation.parse("ssc_14:airlock_bolt_on")).ifPresent(sound -> ((Level) world).playSound(null, pos, sound, SoundSource.NEUTRAL, 1f, 1f));
							BaseAirlockD1PutProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
						}
						break;
					case 3 :
						if (!nbt.getBooleanOr("timer", false)) {
							nbt.putBoolean("timer", true);
							needsUpdate = true;
						}
						break;
					case 4 :
						if (!nbt.getBooleanOr("ai_access", false)) {
							nbt.putBoolean("ai_access", true);
							needsUpdate = true;
						}
						break;
					case 5 :
						if (!nbt.getBooleanOr("logs", false)) {
							nbt.putBoolean("logs", true);
							needsUpdate = true;
						}
						break;
					case 6 :
						if (!nbt.getBooleanOr("diods", false)) {
							nbt.putBoolean("diods", true);
							needsUpdate = true;
						}
						break;
					case 7 :
						if (!nbt.getBooleanOr("safe", false)) {
							nbt.putBoolean("safe", true);
							needsUpdate = true;
						}
						break;
				}
			} else {
				// === КАБЕЛЬ ЦЕЛ → выключаем функцию ===
				switch (logValue) {
					case 0 :
						if (nbt.getBooleanOr("energy_cabel_1", true)) {
							nbt.putBoolean("energy_cabel_1", false);
							needsUpdate = true;
						}
						break;
					case 1 :
						if (nbt.getBooleanOr("energy_cabel_2", true)) {
							nbt.putBoolean("energy_cabel_2", false);
							needsUpdate = true;
						}
						break;
					// case 2: bolted НЕ отключается автоматически при целом кабеле (как в оригинале)
					case 3 :
						if (nbt.getBooleanOr("timer", true)) {
							nbt.putBoolean("timer", false);
							needsUpdate = true;
						}
						break;
					case 4 :
						if (nbt.getBooleanOr("ai_access", true)) {
							nbt.putBoolean("ai_access", false);
							needsUpdate = true;
						}
						break;
					case 5 :
						if (nbt.getBooleanOr("logs", true)) {
							nbt.putBoolean("logs", false);
							needsUpdate = true;
						}
						break;
					case 6 :
						if (nbt.getBooleanOr("diods", true)) {
							nbt.putBoolean("diods", false);
							needsUpdate = true;
						}
						break;
					case 7 :
						if (nbt.getBooleanOr("safe", true)) {
							nbt.putBoolean("safe", false);
							needsUpdate = true;
						}
						break;
				}
			}
		}
		// Отправляем обновление клиентам, только если что-то изменилось
		if (needsUpdate && world instanceof Level level) {
			level.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}

	// ========================================================================
	// === ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ===
	// ========================================================================
	private static boolean getBooleanProperty(BlockState state, String propName, boolean defaultValue) {
		Property<?> prop = state.getBlock().getStateDefinition().getProperty(propName);
		if (prop instanceof BooleanProperty bp) {
			return state.getValue(bp);
		}
		return defaultValue;
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity be = world.getBlockEntity(pos);
		return be != null && be.getPersistentData().getBooleanOr(tag, false);
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity be = world.getBlockEntity(pos);
		return be != null ? be.getPersistentData().getDoubleOr(tag, 0) : -1;
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
}