
package net.ssc14.content.block.airlock;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.ssc14.content.block.Ssc14Blocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.ssc14.common.airlock.AirlockAccessPolicy;

import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;

public final class AirlockStateInitializer {
	private AirlockStateInitializer() {
	}
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (world.isClientSide()) return;
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockPos topPos = pos.above();
		
		initializeBlockNBT(world, pos, blockstate); // Передаем blockstate для проверки типа шлюза
        BlockEntity existing = world.getBlockEntity(pos);
        int action = existing == null ? 0 : existing.getPersistentData().getIntOr("_ssc14_airlock_action", 0);
        if (action != 1 && action != 2) ensureTopBlockExists(world, topPos, blockstate);
		initializeClosedAirlockState(world, pos, blockstate);
		generateRandomCableValues(world, pos);
        net.ssc14.content.block.airlock.AirlockFloor.ensureLowerPart(world, pos);
		BlockEntity be = world.getBlockEntity(pos);
		if (be != null) be.setChanged();
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
		nbt.putBoolean("powered", false);
        nbt.putBoolean("_ssc14_supply_powered", false);
        nbt.putBoolean("energy_cabel_1", true);
        nbt.putBoolean("energy_cabel_2", true);

		// Сначала отключаем ВСЕ доступы по умолчанию (выставляем в false)
		for (String access : AirlockAccessPolicy.accessKeys()) {
			nbt.putBoolean(access, false);
		}

		// Проверяем тип установленного блока через реестр и выдаем нужные доступы
		Block block = state.getBlock();
		String policyAccess = AirlockAccessPolicy.accessKeyFor(
				BuiltInRegistries.BLOCK.getKey(block).getPath());
		if (policyAccess != null) nbt.putBoolean(policyAccess, true);

		if (block == Ssc14Blocks.CENT_COM_AIRLOCK_D_1.get()) {
			// Пока нету доступов
		} else if (block == Ssc14Blocks.SECURITY_AIRLOCK_D_1.get()) {
			nbt.putBoolean("Brig", true);
		} else if (block == Ssc14Blocks.SYNDICATE_AIRLOCK_D_1.get()) {
			// Нету доступов
		} else if (block == Ssc14Blocks.VIROLOGY_AIRLOCK_D_1.get()) {
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
			? Ssc14Blocks.AIRLOCK_UP_PLUG_OPEN.get() 
			: Ssc14Blocks.AIRLOCK_UP_PLUG.get();
			
		BlockState currentTop = world.getBlockState(topPos);
		if (currentTop.getBlock() == targetPlug) return;
        if (!currentTop.canBeReplaced() && currentTop.getBlock() != Ssc14Blocks.AIRLOCK_UP_PLUG.get()
                && currentTop.getBlock() != Ssc14Blocks.AIRLOCK_UP_PLUG_OPEN.get()) return;
		
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
