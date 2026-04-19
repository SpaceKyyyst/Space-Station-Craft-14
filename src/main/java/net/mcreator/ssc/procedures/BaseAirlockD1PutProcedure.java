package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

import java.util.Random;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;

public class BaseAirlockD1PutProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (getPropertyByName(blockstate, "waterlogged") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1)) {
			//КОСТЫЛЬ, НЕ УБИРАТЬ, ЕСЛИ ИСПОЛЬЗУЕТЕ MCreator. НА НЁМ ДЕРЖИТСЯ ПРОЦЕДУРА
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("KosTil") instanceof BooleanProperty _booleanProp)
					world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
			}
		}
		// Константы свойств блока
		final String PROP_BLOCKSTATE = "blockstate";
		final String PROP_BOLTED = "bolted";
		final String PROP_EMERGENCY = "emergency_acs";
		final String PROP_PANEL = "panel_open";
		final int[] ANIM_CLOSE = {4, 3, 2, 1, 0};
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockPos topPos = pos.above();
		// 🔧 ИСПРАВЛЕНИЕ #1: Инициализация NBT ДО проверки логики шлюза!
		// Чтобы timer/safe и другие флаги были доступны при планировании закрытия
		initializeBlockNBT(world, pos);
		// Создаём верхний блок-плагин, если его нет
		ensureTopBlockExists(world, topPos, blockstate);
		// Основная логика: закрытый шлюз → инициализация, открытый → планирование закрытия
		if (isClosedAirlock(world, pos)) {
			initializeClosedAirlockState(world, pos, blockstate, PROP_BLOCKSTATE, PROP_BOLTED, PROP_EMERGENCY, PROP_PANEL);
		} else if (isOpenAirlock(world, pos)) {
			scheduleAutoCloseLogic(world, pos, blockstate, PROP_BOLTED);
		}
		// 🔧 ИСПРАВЛЕНИЕ #2: Рандомизация кабелей ПОСЛЕ инициализации NBT
		// Используем позицию блока как часть сида для уникального рандома на каждый шлюз
		generateRandomCableValues(world, pos);
	}

	// ============================================================================
	// 🏷️ МОДУЛЬ 2: ПРОВЕРКА ТЕГОВ (OPEN/CLOSED)
	// Неймспейс тегов: ssc14 (без подчёркивания) — как в MCreator
	// ============================================================================
	private static boolean isClosedAirlock(LevelAccessor world, BlockPos pos) {
		return world.getBlockState(pos).is(BlockTags.create(ResourceLocation.parse("ssc14:airlocks_closed")));
	}

	private static boolean isOpenAirlock(LevelAccessor world, BlockPos pos) {
		return world.getBlockState(pos).is(BlockTags.create(ResourceLocation.parse("ssc14:airlocks_open")));
	}

	// ============================================================================
	// 🧱 МОДУЛЬ 3: УПРАВЛЕНИЕ БЛОКАМИ (PLUG / MATCHING)
	// Неймспейс блоков: ssc_14 (с подчёркиванием!) — как в реестре
	// ============================================================================
	private static void ensureTopBlockExists(LevelAccessor world, BlockPos topPos, BlockState sourceState) {
		if (isAirlockPlug(world.getBlockState(topPos)))
			return;
		BlockState newTop = createMatchingPlugBlock(sourceState, false);
		newTop = copyBlockProperties(sourceState, newTop);
		world.setBlock(topPos, newTop, 3);
	}

	private static boolean isAirlockPlug(BlockState state) {
		return BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath().contains("airlock_up_plug");
	}

	private static BlockState createMatchingPlugBlock(BlockState sourceState, boolean open) {
		String sourceName = BuiltInRegistries.BLOCK.getKey(sourceState.getBlock()).getPath();
		String prefix = sourceName.replaceAll("_airlock_d_\\d+.*$", "");
		String plugName = prefix + "_airlock_up_plug" + (open ? "open" : "");
		Block plugBlock = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.tryParse("ssc_14:" + plugName)).orElse(open ? Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN.get() : Ssc14ModBlocks.AIRLOCK_UP_PLUG.get());
		return plugBlock.defaultBlockState();
	}

	// ============================================================================
	// ⚙️ МОДУЛЬ 4: ИНИЦИАЛИЗАЦИЯ СОСТОЯНИЯ ЗАКРЫТОГО ШЛЮЗА
	// Устанавливает blockstate на основе флагов: bolted, emergency, panelOpen
	// ============================================================================
	private record AirlockFlags(boolean bolted, boolean emergency, boolean panelOpen) {
		static AirlockFlags of(boolean b, boolean e, boolean p) {
			return new AirlockFlags(b, e, p);
		}
	}

	private static void initializeClosedAirlockState(LevelAccessor world, BlockPos pos, BlockState state, String propBlockstate, String propBolted, String propEmergency, String propPanel) {
		IntegerProperty stateProp = getStateProperty(state, propBlockstate);
		if (stateProp == null)
			return;
		int currentState = state.getValue(stateProp);
		if (currentState >= 1 && currentState <= 4)
			return; // Уже в анимации
		boolean bolted = getBooleanProperty(state, propBolted, false);
		boolean emergency = getBooleanProperty(state, propEmergency, false);
		boolean panelOpen = getBooleanProperty(state, propPanel, false);
		boolean diodsEnabled = getBlockNBT(world, pos, "diods");
		int newValue = switch (AirlockFlags.of(bolted, emergency, panelOpen)) {
			case AirlockFlags f when f.bolted() && f.panelOpen() -> 9;
			case AirlockFlags f when f.bolted() && !f.panelOpen() -> diodsEnabled ? 5 : 0;
			case AirlockFlags f when !f.bolted() && f.emergency() && f.panelOpen() -> 8;
			case AirlockFlags f when !f.bolted() && f.emergency() && !f.panelOpen() -> diodsEnabled ? 6 : 0;
			case AirlockFlags f when !f.bolted() && !f.emergency() && f.panelOpen() -> 7;
			default -> 0;
		};
		setBlockStateValue(world, pos, stateProp, newValue);
	}

	// ============================================================================
	// ⏱️ МОДУЛЬ 5: ЛОГИКА АВТО-ЗАКРЫТИЯ (ТАЙМЕР)
	// 🔧 ИСПРАВЛЕНИЕ: таймер теперь работает корректно (80т если true, 10т если false)
	// ============================================================================
	private static void scheduleAutoCloseLogic(LevelAccessor world, BlockPos pos, BlockState state, String propBolted) {
		if (!(world instanceof Level level) || level.isClientSide())
			return;
		if (getBooleanProperty(state, propBolted, false))
			return; // Заблокирован болтом
		// 🔧 Читаем флаг таймера из NBT (теперь доступен, т.к. initializeBlockNBT вызван раньше)
		boolean timerEnabled = getBlockNBT(world, pos, "timer");
		int delay = timerEnabled ? 80 : 10; // 80 тиков = 4 секунды, 10 тиков = 0.5 секунды
		Ssc14Mod.queueServerWork(delay, () -> tryCloseAirlock(world, pos, propBolted));
	}

	private static void tryCloseAirlock(LevelAccessor world, BlockPos pos, String propBolted) {
		if (!(world instanceof Level level) || level.isClientSide())
			return;
		if (!isOpenAirlock(world, pos))
			return; // Уже не открыт
		if (getBooleanProperty(world.getBlockState(pos), propBolted, false))
			return; // Заблокировали
		boolean safeMode = getBlockNBT(world, pos, "safe");
		boolean hasEntities = hasEntitiesInAirlock(world, pos);
		if (hasEntities) {
			if (safeMode) {
				// Повторная попытка через 1 секунду (20 тиков)
				Ssc14Mod.queueServerWork(20, () -> tryCloseAirlock(world, pos, propBolted));
				return;
			} else {
				// Опасный режим: наносим урон сущностям
				damageEntitiesInAirlock(world, pos, 10.0f);
			}
		}
		performAirlockClose(world, pos);
	}

	// ============================================================================
	// 👥 МОДУЛЬ 6: ПРОВЕРКА СУЩНОСТЕЙ В ШЛЮЗЕ
	// ============================================================================
	private static boolean hasEntitiesInAirlock(LevelAccessor world, BlockPos pos) {
		double r = 0.2;
		return !world.getEntitiesOfClass(Entity.class, new AABB(pos.getX() - r, pos.getY() - r, pos.getZ() - r, pos.getX() + 1 + r, pos.getY() + 1 + r, pos.getZ() + 1 + r), e -> e instanceof Player || e instanceof Mob).isEmpty();
	}

	private static void damageEntitiesInAirlock(LevelAccessor world, BlockPos pos, float damageAmount) {
		if (!(world instanceof Level level))
			return;
		AABB box = new AABB(pos.getX() - 0.2, pos.getY() - 0.2, pos.getZ() - 0.2, pos.getX() + 1.2, pos.getY() + 1.2, pos.getZ() + 1.2);
		for (Entity e : world.getEntitiesOfClass(Entity.class, box, ent -> ent instanceof Player || ent instanceof Mob)) {
			if (e != null)
				e.hurt(level.damageSources().generic(), damageAmount);
		}
	}

	// ============================================================================
	// 🎬 МОДУЛЬ 7: АНИМАЦИЯ ЗАКРЫТИЯ ШЛЮЗА
	// 🔊 Звук + замена блока + анимация blockstate: 4→3→2→1→0
	// ============================================================================
	private static void performAirlockClose(LevelAccessor world, BlockPos pos) {
		if (!(world instanceof Level level))
			return;
		// Проигрываем звук закрытия (неймспейс ssc_14 с подчёркиванием)
		BuiltInRegistries.SOUND_EVENT.getOptional(ResourceLocation.parse("ssc_14:airlock_close")).ifPresent(sound -> level.playSound(null, pos, sound, SoundSource.NEUTRAL, 1f, 1f));
		// Заменяем блок на закрытую версию + переносим NBT
		replaceBlockWithNBT(world, pos);
		// Получаем свойство blockstate у нового (закрытого) блока
		BlockState afterReplace = world.getBlockState(pos);
		Property<?> rawProp = afterReplace.getBlock().getStateDefinition().getProperty("blockstate");
		IntegerProperty stateProp = (rawProp instanceof IntegerProperty) ? (IntegerProperty) rawProp : null;
		if (stateProp == null)
			return;
		// Устанавливаем стартовое значение анимации = 4
		if (stateProp.getPossibleValues().contains(4)) {
			world.setBlock(pos, afterReplace.setValue(stateProp, 4), 3);
		}
		// Запускаем анимацию 4→3→2→1→0
		runBlockAnimation(world, pos, new int[]{4, 3, 2, 1, 0}, stateProp);
	}

	private static void replaceBlockWithNBT(LevelAccessor world, BlockPos pos) {
		BlockPos topPos = pos.above();
		CompoundTag savedData = null;
		BlockEntity be = world.getBlockEntity(pos);
		if (be != null) {
			savedData = be.getPersistentData().copy();
			be.setRemoved();
		}
		BlockState oldMain = world.getBlockState(pos);
		BlockState newMain = findMatchingBlock(oldMain, true); // true = закрытая версия
		BlockState finalMain = copyBlockProperties(oldMain, newMain);
		world.setBlock(pos, finalMain, 3);
		if (savedData != null) {
			BlockEntity newBe = world.getBlockEntity(pos);
			if (newBe != null)
				newBe.getPersistentData().merge(savedData);
		}
		BlockState oldTop = world.getBlockState(topPos);
		BlockState newTop = findMatchingPlugBlock(oldTop, true);
		BlockState finalTop = copyBlockProperties(oldTop, newTop);
		world.setBlock(topPos, finalTop, 3);
	}

	// 🔧 Универсальный поиск пары блока: закрытый = без суффикса, открытый = +open
	private static BlockState findMatchingBlock(BlockState currentState, boolean toClosed) {
		String currentName = BuiltInRegistries.BLOCK.getKey(currentState.getBlock()).getPath();
		String baseName = currentName.endsWith("open") ? currentName.substring(0, currentName.length() - 4) : currentName;
		String newName = toClosed ? baseName : baseName + "open";
		// 🔧 Неймспейс ssc_14: для блоков (с подчёркиванием!)
		Block newBlock = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.tryParse("ssc_14:" + newName)).orElse(currentState.getBlock());
		return newBlock.defaultBlockState();
	}

	private static BlockState findMatchingPlugBlock(BlockState currentState, boolean toClosed) {
		String currentName = BuiltInRegistries.BLOCK.getKey(currentState.getBlock()).getPath();
		String baseName = currentName.endsWith("open") ? currentName.substring(0, currentName.length() - 4) : currentName;
		String newName = toClosed ? baseName : baseName + "open";
		Block newBlock = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.tryParse("ssc_14:" + newName)).orElse(currentState.getBlock());
		return newBlock.defaultBlockState();
	}

	// ============================================================================
	// 🎞️ МОДУЛЬ 8: ПОШАГОВАЯ АНИМАЦИЯ BLOCKSTATE
	// ============================================================================
	private static void runBlockAnimation(LevelAccessor world, BlockPos pos, int[] states, IntegerProperty prop) {
		if (prop == null || states.length == 0)
			return;
		runAnimationStep(world, pos, prop, states, 0);
	}

	private static void runAnimationStep(LevelAccessor world, BlockPos pos, IntegerProperty prop, int[] states, int index) {
		if (index >= states.length)
			return;
		BlockState current = world.getBlockState(pos);
		int target = states[index];
		if (prop.getPossibleValues().contains(target)) {
			world.setBlock(pos, current.setValue(prop, target), 3);
		}
		if (index + 1 < states.length) {
			Ssc14Mod.queueServerWork(2, () -> runAnimationStep(world, pos, prop, states, index + 1));
		}
	}

	// ============================================================================
	// 💾 МОДУЛЬ 9: ИНИЦИАЛИЗАЦИЯ И РАНДОМИЗАЦИЯ NBT
	// 🔧 ИСПРАВЛЕНИЕ: рандомизация кабелей теперь уникальна для каждого шлюза
	// ============================================================================
	private static void initializeBlockNBT(LevelAccessor world, BlockPos pos) {
		if (world.isClientSide())
			return;
		BlockEntity be = world.getBlockEntity(pos);
		if (be == null || be.getPersistentData().getBooleanOr("Blocking_13", false))
			return;
		CompoundTag nbt = be.getPersistentData();
		nbt.putBoolean("Blocking_13", true); // Флаг "уже инициализирован"
		// Дефолтные значения функций шлюза
		nbt.putBoolean("energy_cabel_1", true);
		nbt.putBoolean("energy_cabel_2", true);
		nbt.putBoolean("timer", true); // 🔧 По умолчанию таймер ВКЛЮЧЕН (80 тиков)
		nbt.putBoolean("ai_access", true);
		nbt.putBoolean("logs", true);
		nbt.putBoolean("diods", true);
		nbt.putBoolean("safe", true);
	}

	private static void generateRandomCableValues(LevelAccessor world, BlockPos pos) {
		if (world.isClientSide())
			return;
		BlockEntity be = world.getBlockEntity(pos);
		if (be == null || be.getPersistentData().getBooleanOr("random_cab", false))
			return;
		CompoundTag nbt = be.getPersistentData();
		nbt.putBoolean("random_cab", true); // Флаг "уже рандомизирован"
		// 🔧 ИСПРАВЛЕНИЕ: Уникальный сид для каждого шлюза на основе его координат!
		// Используем XOR координат с world seed для детерминированного, но уникального рандома
		long uniqueSeed = world.getRandom().nextLong() ^ ((long) pos.getX() << 24) ^ ((long) pos.getZ() << 8) ^ pos.getY();
		Random rand = new Random(uniqueSeed);
		List<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
		Collections.shuffle(values, rand); // Перемешиваем значения 0-7
		// Присваиваем каждому кабелю (log_cab_1...8) уникальное значение без повторов
		for (int i = 0; i < 8; i++) {
			nbt.putInt("log_cab_" + (i + 1), values.get(i));
		}
	}

	// ============================================================================
	// 🧰 МОДУЛЬ 10: ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ (UTILS)
	// ============================================================================
	private static BlockState copyBlockProperties(BlockState from, BlockState to) {
		BlockState result = to;
		for (Property<?> oldProp : from.getProperties()) {
			Property<?> newProp = result.getBlock().getStateDefinition().getProperty(oldProp.getName());
			if (newProp != null && newProp.getValueClass().isAssignableFrom(oldProp.getValueClass())) {
				try {
					@SuppressWarnings({"rawtypes", "unchecked"})
					Property rawProp = newProp;
					@SuppressWarnings({"rawtypes", "unchecked"})
					Comparable value = (Comparable) from.getValue(oldProp);
					result = result.setValue(rawProp, value);
				} catch (Exception ignored) {
				}
			}
		}
		return result;
	}

	private static IntegerProperty getStateProperty(BlockState state, String propName) {
		Property<?> prop = state.getBlock().getStateDefinition().getProperty(propName);
		return (prop instanceof IntegerProperty ip && ip.getPossibleValues().contains(0)) ? ip : null;
	}

	private static boolean getBooleanProperty(BlockState state, String propName, boolean defaultValue) {
		Property<?> prop = state.getBlock().getStateDefinition().getProperty(propName);
		return (prop instanceof BooleanProperty bp) ? state.getValue(bp) : defaultValue;
	}

	private static boolean getBlockNBT(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity be = world.getBlockEntity(pos);
		return be != null && be.getPersistentData().getBooleanOr(tag, false);
	}

	private static void setBlockStateValue(LevelAccessor world, BlockPos pos, IntegerProperty prop, int value) {
		if (prop != null && prop.getPossibleValues().contains(value)) {
			BlockState current = world.getBlockState(pos);
			world.setBlock(pos, current.setValue(prop, value), 3);
		}
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