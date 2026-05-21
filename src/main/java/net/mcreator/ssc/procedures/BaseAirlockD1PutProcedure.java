
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.mcreator.ssc.init.Ssc14ModBlocks;

import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;

public class BaseAirlockD1PutProcedure {

	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (world.isClientSide()) return;
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockPos topPos = pos.above();
		initializeBlockNBT(world, pos);
		ensureTopBlockExists(world, topPos, blockstate);
		initializeClosedAirlockState(world, pos, blockstate);
		generateRandomCableValues(world, pos);
	}

	private static void initializeBlockNBT(LevelAccessor world, BlockPos pos) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be == null) return;
		CompoundTag nbt = be.getPersistentData();
		if (nbt.getBoolean("initialized").orElse(false)) return;
		nbt.putBoolean("initialized", true);
		nbt.putBoolean("bolted", false);
		nbt.putBoolean("welded", false);
		nbt.putBoolean("emergency_acs", false);
		nbt.putBoolean("timer", true);
		nbt.putBoolean("safe", true);
		nbt.putBoolean("diods", true);
		nbt.putBoolean("ai_access", true);
		nbt.putBoolean("logs", true);
		nbt.putBoolean("powered", true);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static void ensureTopBlockExists(LevelAccessor world, BlockPos topPos, BlockState sourceState) {
		BlockPos mainPos = topPos.below();
		BlockState currentMain = world.getBlockState(mainPos);
		
		IntegerProperty arlProp = (currentMain.getBlock().getStateDefinition().getProperty("arl_variat") instanceof IntegerProperty p) ? p : null;
		int currentState = arlProp != null ? currentMain.getValue(arlProp) : -1;
		
		boolean shouldBeOpen = (currentState >= 11 && currentState <= 15);
		Block targetPlug = shouldBeOpen 
			? Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN.get() 
			: Ssc14ModBlocks.AIRLOCK_UP_PLUG.get();
			
		BlockState currentTop = world.getBlockState(topPos);
		if (currentTop.getBlock() == targetPlug) return;
		
		BlockState plugState = targetPlug.defaultBlockState();
		Property<?> sourceFacing = currentMain.getBlock().getStateDefinition().getProperty("facing");
		Property<?> plugFacing = plugState.getBlock().getStateDefinition().getProperty("facing");
		if (sourceFacing != null && plugFacing != null && sourceFacing.getValueClass().equals(plugFacing.getValueClass())) {
			try { plugState = plugState.setValue((Property) plugFacing, (Comparable) currentMain.getValue(sourceFacing)); } 
			catch (Exception ignored) {}
		}
		world.setBlock(topPos, plugState, 3);
	}

	// 🔧 ИСПРАВЛЕННАЯ ВЕРСИЯ: полная поддержка всех состояний + правильная защита
	private static void initializeClosedAirlockState(LevelAccessor world, BlockPos pos, BlockState state) {
		IntegerProperty arlProp = getSafeProperty(state, "arl_variat", IntegerProperty.class);
		if (arlProp == null) return;
		
		BlockEntity be = world.getBlockEntity(pos);
		if (be == null) return;
		CompoundTag nbt = be.getPersistentData();
		
		int currentState = state.getValue(arlProp);
		
		// 🔧 ЗАЩИТА: никогда не трогаем эти состояния
		// - Анимация открытия (12-15)
		// - Полностью открыт (11)
		// - Взлом (6, 7) — пока не реализован
		// - Отказ в доступе (8) — временная анимация
		if (currentState == 6 || currentState == 7 || currentState == 8 || 
			currentState == 11 || (currentState >= 12 && currentState <= 15)) {
			return;
		}
		
		// 🔧 Получаем ВСЕ флаги из NBT
		boolean bolted = nbt.getBoolean("bolted").orElse(false);
		boolean welded = nbt.getBoolean("welded").orElse(false);
		boolean emergency = nbt.getBoolean("emergency_acs").orElse(false);
		boolean powered = nbt.getBoolean("powered").orElse(true);
		
		// 🔧 РАСЧЁТ целевого состояния на основе ВСЕХ флагов
		// Сопоставление:
		// 0  - закрытый
		// 1  - закрытый + болты + нет питания
		// 2  - болты
		// 3  - болты + сварка
		// 4  - аварийка
		// 5  - аварийка + сварка
		// 9  - нет питания
		// 10 - нет питания + сварка
		// 16 - сварка
		int targetState;
		
		if (!powered) {
			// Нет питания: 1, 9, 10
			if (welded) {
				targetState = 10; // нет питания + сварка
			} else if (bolted) {
				targetState = 1; // болты + нет питания
			} else {
				targetState = 9; // просто нет питания
			}
		} else if (bolted) {
			// Болты (есть питание): 2, 3
			targetState = welded ? 3 : 2;
		} else if (emergency) {
			// Аварийка (есть питание, нет болтов): 4, 5
			targetState = welded ? 5 : 4;
		} else {
			// Обычное состояние (есть питание, нет болтов/аварийки): 0, 16
			targetState = welded ? 16 : 0;
		}
		
		// 🔧 Меняем только если состояние не совпадает с целевым
		if (currentState != targetState) {
			_setBlockState((Level) world, pos, targetState, arlProp);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T getSafeProperty(BlockState state, String name, Class<T> type) {
		var prop = state.getBlock().getStateDefinition().getProperty(name);
		return type.isInstance(prop) ? (T) prop : null;
	}

	private static void _setBlockState(Level level, BlockPos pos, int value, IntegerProperty prop) {
		if (prop == null || !prop.getPossibleValues().contains(value)) return;
		BlockState oldState = level.getBlockState(pos);
		BlockState newState = oldState.setValue(prop, value);
		level.setBlock(pos, newState, 3);
		level.sendBlockUpdated(pos, oldState, newState, 3);
	}

	private static void generateRandomCableValues(LevelAccessor world, BlockPos pos) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be == null) return;
		CompoundTag nbt = be.getPersistentData();
		if (nbt.getBoolean("cables_randomized").orElse(false)) return;
		nbt.putBoolean("cables_randomized", true);
		long seed = world.getRandom().nextLong() ^ pos.asLong();
		Random rand = new Random(seed);
		var values = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7));
		Collections.shuffle(values, rand);
		for (int i = 0; i < 8; i++) nbt.putInt("log_cab_" + (i+1), values.get(i));
	}
}
