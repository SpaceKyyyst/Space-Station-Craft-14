
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
		
		initializeBlockNBT(world, pos, blockstate); // Передаем blockstate для проверки типа шлюза
		ensureTopBlockExists(world, topPos, blockstate);
		initializeClosedAirlockState(world, pos, blockstate);
		generateRandomCableValues(world, pos);
	}

	private static void initializeBlockNBT(LevelAccessor world, BlockPos pos, BlockState state) {
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

		// Сначала отключаем ВСЕ доступы по умолчанию (выставляем в false)
		String[] allAccesses = {
			"Technical", "Service", "Out", "gun_room", "HoS", "Brig", "Medical", "Crio", 
			"Security", "Ingeneer", "Command", "Detective", "PNT", "Scientist", "Supply_Deportament", 
			"Atmos", "Kitchen", "Uridic", "Gidroponic", "Teatre", "Bar", "Cleaner", 
			"Utilizat", "Chemistry", "Church", "CE", "Qm", "CMO", "RD", "HoP", "Capitan", "Blue_Sh"
		};
		for (String access : allAccesses) {
			nbt.putBoolean(access, false);
		}

		// Проверяем тип установленного блока через реестр и выдаем нужные доступы
		Block block = state.getBlock();

		if (block == Ssc14ModBlocks.TECH_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Technical", true);
		} else if (block == Ssc14ModBlocks.ATMOSPHERICS_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Atmos", true);
		} else if (block == Ssc14ModBlocks.CARGO_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Supply_Deportament", true);
		} else if (block == Ssc14ModBlocks.CENT_COM_AIRLOCK_D_1.get()) {
			// Пока нету доступов
		} else if (block == Ssc14ModBlocks.CHEMISTRY_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Chemistry", true);
		} else if (block == Ssc14ModBlocks.COMMAND_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Command", true);
		} else if (block == Ssc14ModBlocks.ENGINEERING_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Ingeneer", true);
		} else if (block == Ssc14ModBlocks.HYDROPONICS_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Gidroponic", true);
		} else if (block == Ssc14ModBlocks.MEDICAL_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Medical", true);
		} else if (block == Ssc14ModBlocks.SALVAGE_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Utilizat", true);
		} else if (block == Ssc14ModBlocks.SCIENCE_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Scientist", true);
		} else if (block == Ssc14ModBlocks.SECURITY_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Security", true);
			nbt.putBoolean("Brig", true);
		} else if (block == Ssc14ModBlocks.SYNDICATE_AIRLOCK_D_1.get()) {
			// Нету доступов
		} else if (block == Ssc14ModBlocks.VIROLOGY_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Medical", true);
		}
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

	private static void initializeClosedAirlockState(LevelAccessor world, BlockPos pos, BlockState state) {
		IntegerProperty arlProp = getSafeProperty(state, "arl_variat", IntegerProperty.class);
		if (arlProp == null) return;
		
		BlockEntity be = world.getBlockEntity(pos);
		if (be == null) return;
		CompoundTag nbt = be.getPersistentData();
		
		int currentState = state.getValue(arlProp);
		
		if (currentState == 6 || currentState == 7 || currentState == 8 || 
			currentState == 11 || (currentState >= 12 && currentState <= 15)) {
			return;
		}
		
		boolean bolted = nbt.getBoolean("bolted").orElse(false);
		boolean welded = nbt.getBoolean("welded").orElse(false);
		boolean emergency = nbt.getBoolean("emergency_acs").orElse(false);
		boolean powered = nbt.getBoolean("powered").orElse(true);
		
		int targetState;
		
		if (!powered) {
			if (welded) {
				targetState = 10;
			} else if (bolted) {
				targetState = 1;
			} else {
				targetState = 9;
			}
		} else if (bolted) {
			targetState = welded ? 3 : 2;
		} else if (emergency) {
			targetState = welded ? 5 : 4;
		} else {
			targetState = welded ? 16 : 0;
		}
		
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
