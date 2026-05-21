
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;

import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.procedures.BaseAirlockD1PutProcedure;

public class AirlockController_DEBUG_prProcedure {

	private static final double MAX_DISTANCE = 50.0;

	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity, ItemStack itemstack) {
		if (entity == null || world.isClientSide() || !(entity instanceof Player player)) return;
		if (!(world instanceof Level level)) return;

		BlockPos targetPos = raycastToAirlock(player);
		if (targetPos == null) {
			cycleControllerMode(itemstack, player);
			return;
		}

		BlockState targetState = level.getBlockState(targetPos);
		if (!targetState.is(BlockTags.create(ResourceLocation.parse("ssc14:airlocks")))) {
			cycleControllerMode(itemstack, player);
			return;
		}

		CompoundTag itemNbt = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		int mode = (int) itemNbt.getDoubleOr("mode", 0);

		if (mode == 0) {
			toggleBolted(level, targetPos, targetState, player);
		} else if (mode == 1) {
			toggleEmergency(level, targetPos, targetState, player);
		} else if (mode == 2) {
			BaseAirlockOpenCloseProcedure.execute(level, 
				targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5, 
				targetState, player);
		}
	}

	private static BlockPos raycastToAirlock(Player player) {
		try {
			net.minecraft.world.phys.HitResult hit = player.pick(MAX_DISTANCE, 1.0f, false);
			if (hit == null || !(hit instanceof BlockHitResult blockHit)) return null;
			if (blockHit.getType() != HitResult.Type.BLOCK) return null;
			
			BlockPos pos = blockHit.getBlockPos();
			Level level = player.level();
			if (!level.hasChunkAt(pos)) return null;
			
			BlockState state = level.getBlockState(pos);
			if (state.is(BlockTags.create(ResourceLocation.parse("ssc14:airlocks")))) {
				return pos;
			}
		} catch (Exception e) {
			// Тихое игнорирование ошибок рейкаста
		}
		return null;
	}

	// 🔹 Переключение режима (без вывода текста)
	private static void cycleControllerMode(ItemStack itemstack, Player player) {
		CompoundTag nbt = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		int currentMode = (int) nbt.getDoubleOr("mode", 0);
		int newMode = (currentMode + 1) % 3;
		nbt.putDouble("mode", (double) newMode);
		
		itemstack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
		// 🔧 Вывод текста убран по запросу
	}

	// 🔹 Переключение болтов (без вывода текста)
	private static void toggleBolted(Level level, BlockPos pos, BlockState state, Player player) {
		BlockEntity be = level.getBlockEntity(pos);
		if (be == null) return;
		
		CompoundTag nbt = be.getPersistentData();
		boolean currentlyBolted = nbt.getBooleanOr("bolted", false);
		boolean newBolted = !currentlyBolted;
		
		nbt.putBoolean("bolted", newBolted);
		_playSound(level, pos, newBolted ? "ssc_14:airlock_bolt_on" : "ssc_14:airlock_bolt_off");
		
		// 🔧 Принудительная синхронизация с клиентом
		be.setChanged();
		level.setBlock(pos, state, 11);
		level.sendBlockUpdated(pos, state, state, 11);
		
		Ssc14Mod.queueServerWork(1, () -> {
			if (level.isClientSide()) return;
			level.sendBlockUpdated(pos, state, state, 3);
			BaseAirlockD1PutProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ(), state);
		});
		// 🔧 Вывод текста убран по запросу
	}

	// 🔹 Переключение аварийки (без вывода текста)
	private static void toggleEmergency(Level level, BlockPos pos, BlockState state, Player player) {
		BlockEntity be = level.getBlockEntity(pos);
		if (be == null) return;
		
		CompoundTag nbt = be.getPersistentData();
		boolean currentlyEmergency = nbt.getBooleanOr("emergency_acs", false);
		boolean newEmergency = !currentlyEmergency;
		
		nbt.putBoolean("emergency_acs", newEmergency);
		_playSound(level, pos, newEmergency ? "ssc_14:airlock_emergency_on" : "ssc_14:airlock_emergency_off");
		
		// 🔧 Принудительная синхронизация с клиентом
		be.setChanged();
		level.setBlock(pos, state, 11);
		level.sendBlockUpdated(pos, state, state, 11);
		
		Ssc14Mod.queueServerWork(1, () -> {
			if (level.isClientSide()) return;
			level.sendBlockUpdated(pos, state, state, 3);
			BaseAirlockD1PutProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ(), state);
		});
		
		if (!newEmergency) {
			Property<?> prop = state.getBlock().getStateDefinition().getProperty("arl_variat");
			if (prop instanceof IntegerProperty intProp) {
				int current = state.getValue(intProp);
				if (current == 4) {
					boolean bolted = nbt.getBooleanOr("bolted", false);
					boolean welded = nbt.getBooleanOr("welded", false);
					if (!bolted && !welded && intProp.getPossibleValues().contains(0)) {
						BlockState newState = state.setValue(intProp, 0);
						level.setBlock(pos, newState, 11);
						level.sendBlockUpdated(pos, state, newState, 11);
					}
				}
			}
		}
		// 🔧 Вывод текста убран по запросу
	}

	// 🔹 Звук
	private static void _playSound(Level level, BlockPos pos, String soundId) {
		SoundEvent sound = BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse(soundId));
		if (sound != null) {
			level.playSound(null, pos, sound, SoundSource.PLAYERS, 1f, 1f);
		}
	}
}
